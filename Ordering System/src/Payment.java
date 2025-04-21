package computer.retail.management.system;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


    // Payment Interface
    interface Payment {
        boolean processPayment(double amount);
         String getPaymentMethod();
    }
    


    // Top-Up Credit Payment Class
    class TopUpCreditPayment implements Payment {
        public boolean processPayment(double amount) {
            System.out.println("Processing top-up credit payment of RM" + amount);
            return true;
        }
        public String getPaymentMethod() {
        return "TopUp Payment";
        }
    }

    // E-Wallet (TNG) Payment Class
    class EWalletPayment implements Payment {
        public boolean processPayment(double amount) {
            System.out.println("Processing e-wallet (TNG) payment of RM" + amount);
            return true;
        }
        public String getPaymentMethod() {
        return "TNG eWallet";
        }
    }

    // Order Class
    class Order {
        private String orderId;
        private double amount;
        private Payment paymentMethod;
        private List<CartItem> products;
        private Customer customer;

        public Order( Payment paymentMethod, Customer customer, List<CartItem> products) {
            this.orderId=System.currentTimeMillis() + "-" + new Random().nextInt(1000);
            this.customer = customer;
            this.products = new ArrayList<>(products);
            this.amount = calculateTotalAmount();
            this.paymentMethod = paymentMethod; 
        }

        public void processOrder() {
         if (paymentMethod.processPayment(amount)) {
             if (!(paymentMethod instanceof PointPayment)) {
                 customer.addPoints((int) amount);
             }
             saveOrder();
             generateInvoice();
         } else {
             System.out.println("Payment failed.");
         }
        }

        private void generateInvoice() {
            System.out.println("\u001B[32mPayment Successful\u001B[0m");
            System.out.println("\n============ Receipt ============");
            System.out.println("Company: CompuMart");
            System.out.println("Customer Name: " + customer);

            // 打印所有商品
            System.out.println("\nProducts Purchased:");
            System.out.println("----------------------------------------------------------------------------");
            System.out.printf("%-25s %10s %15s %15s%n", "Product Name", "Quantity", "Unit Price", "Total Price");
            System.out.println("----------------------------------------------------------------------------");

            for (CartItem item : products) {
                System.out.printf("%-25s %10d %15.2f %15.2f%n",
                        item.getProductName(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getTotalAmount());
                System.out.println("----------------------------------------------------------------------------");
            }

            System.out.println("Payment Method: " + paymentMethod.getPaymentMethod());
            System.out.println("Total Price: RM" + String.format("%.2f", amount));

            if (paymentMethod instanceof PointPayment) {
                System.out.println("Point Balance: " + customer.getPoints() + " points");
            }

            System.out.println("=================================");
        }

        
        public double getAmount() {
        return amount;
        }
        
        private double calculateTotalAmount() {
        double total = 0.0;
         for (CartItem item : products) {
        total += item.getTotalAmount();
        }
        return total;
}

    private void saveOrder() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("OrderHistory.txt", true))) {
        writer.write("OrderID: " + this.orderId);
        writer.newLine();
        writer.write("Customer: " + customer.getName());
        writer.newLine();
        writer.write("Payment Method: " + paymentMethod.getPaymentMethod()); 
        writer.newLine();
        writer.write("Total Amount: " + this.amount);
        writer.newLine();
        writer.write("Products:");
        writer.newLine();
        for (CartItem item : products) {
            writer.write("- " + item.getProductName() + ", Qty: " + item.getQuantity() + ", UnitPrice: " + item.getUnitPrice());
            writer.newLine();
        }
        writer.write("-----");
        writer.newLine();
    } catch (IOException e) {
        System.out.println("Error saving order: " + e.getMessage());
    }

    // Remove from cart.txt
    try {
        // Read all lines from cart
        List<String> cartLines = Files.readAllLines(Paths.get("cart.txt"));
        
        // Filter out lines for this customer's purchased products
        List<String> newCartLines = new ArrayList<>();
        for (String line : cartLines) {
            String[] parts = line.split(",");
            if (parts.length >= 2) {
                String cartCustomer = parts[0].trim();
                String cartProduct = parts[1].trim();
                
                // Keep line if:
                // 1. Not our customer OR
                // 2. Our customer but product not in this order
                if (!cartCustomer.equals(customer.getName()) || 
                    products.stream().noneMatch(p -> p.getProductName().equals(cartProduct))) {
                    newCartLines.add(line);
                }
            }
        }
        
        // Write back to cart.txt
        Files.write(Paths.get("cart.txt"), newCartLines);
        
    } catch (IOException e) {
        System.out.println("Error updating cart: " + e.getMessage());
    }
    }
        
        }


import java.io.IOException;
import java.io.PrintWriter;


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
        private double amount;
        private Payment paymentMethod;
        private String productName;
        private Customer customer;

        public Order(double amount, Payment paymentMethod, Customer customer, String productName) {
            this.customer = customer;;
            this.productName = productName;
            this.amount = amount;
            this.paymentMethod = paymentMethod; 
        }

        public void processOrder() {
         if (paymentMethod.processPayment(amount)) {
             if (!(paymentMethod instanceof PointPayment)) {
                 customer.addPoints((int) amount);
             }
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
           System.out.println("Product: " + productName);
           System.out.println("Payment Method: " + paymentMethod.getPaymentMethod());
           System.out.println("Total Price: RM" + String.format("%.2f", amount));
           
           if (paymentMethod instanceof PointPayment) {
            System.out.println("Point Balance: " + customer.getPoints() + " points");
            }
           
           System.out.println("=================================");
        
         
        }
//        public void generateReceipt() {
//    try {
//        // Initialize PrintWriter with file path for saving the receipt
//        PrintWriter writer = new PrintWriter("receipt.txt");
//        
//        // Print the receipt details to the file
//        writer.println("\n============ Receipt ============");
//        writer.println("Company: CompuMart");
//        writer.println("Customer Name: " + customer);
//        writer.println("Product: " + productName);
//        writer.println("Payment Method: " + paymentMethod.getPaymentMethod());
//        writer.println("Total Price: RM" + String.format("%.2f", amount));
//        
//        if (paymentMethod instanceof PointPayment) {
//            writer.println("Point Balance: " + customer.getPoints() + " points");
//        }
//        
//        writer.println("=================================");
//        
//        // Close the writer to ensure the receipt is saved properly
//        writer.close();
//        
//        // Let the user know the invoice has been saved
//        System.out.println("Receipt successfully saved to 'receipt.txt'.");
//        
//    } catch (IOException e) {
//        System.out.println("\u001B[31mFailed to save receipt: " + e.getMessage() + "\u001B[0m");
//    }
//}
    }
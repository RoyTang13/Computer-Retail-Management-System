// Payment Interface
interface Payment {
    boolean processPayment(double amount);
    String getPaymentMethod();
}

// Bank Payment Class
class BankPayment implements Payment {
    public boolean processPayment(double amount) {
        System.out.println("Processing bank payment of RM" + String.format("%.2f", amount));
        return true;
    }
    public String getPaymentMethod() {
        return "Bank Payment";
    }
}

// Point Payment Class
class PointPayment implements Payment {
    public boolean processPayment(double amount) {
        System.out.println("Processing point payment of " + String.format("%.2f", amount) + " points");
        return true;
    }
    public String getPaymentMethod() {
        return "Point Payment";
    }
}

// Top-Up Credit Payment Class
class TopUpCreditPayment implements Payment {
    public boolean processPayment(double amount) {
        System.out.println("Processing top-up credit payment of RM" + String.format("%.2f", amount));
        return true;
    }
    public String getPaymentMethod() {
        return "TopUp Payment";
    }
}

// E-Wallet (TNG) Payment Class
class EWalletPayment implements Payment {
    public boolean processPayment(double amount) {
        System.out.println("Processing e-wallet (TNG) payment of RM" + String.format("%.2f", amount));
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
    private String custName;
    
    public Order(String custName,String productName,double amount, Payment paymentMethod) {
        this.custName=custName;
        this.productName=productName;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }
    
    public void processOrder() {
        if (paymentMethod.processPayment(amount)) {
            generateInvoice();
        } else {
            System.out.println("Payment failed.");
        }
    }
    
    private void generateInvoice() {
        System.out.println("\n============ Receipt ============");
        
        System.out.println("Company: CompuMart");
        System.out.println("Customer Name: " + custName);
        System.out.println("Product: " + productName);
        System.out.println("Payment Method: " + paymentMethod.getPaymentMethod());
        System.out.println("Total Price: RM" + String.format("%.2f", amount));
        System.out.println("=================================");
    }
}
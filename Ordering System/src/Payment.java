
    // Payment Interface
    interface Payment {
        boolean processPayment(double amount);
         String getPaymentMethod();
    }

    // Bank Payment Class
    class BankPayment implements Payment {
        private String cardNumber;
        private String expiryDate;
        private String cvv;


        public BankPayment(String cardNumber, String expiryDate, String cvv) {
            this.cardNumber = cardNumber;
            this.expiryDate = expiryDate;
            this.cvv = cvv;
        }

        public boolean processPayment(double amount) {
            System.out.println("");
            System.out.println("Processing bank payment of RM" + String.format("%.2f", amount));
            System.out.println("Card Number: **** **** **** " + cardNumber.substring(12));
            System.out.println("Expiry Date: " + expiryDate);
            simulateLoading();
            return true;
        }

         private void simulateLoading() {
            System.out.print("Connecting to the bank");
            try {
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(600); // Simulate delay (500ms)
                    System.out.print(".");
                }
                System.out.println();
            } catch (InterruptedException e) {
                e.printStackTrace();
                }
            }

            public String getPaymentMethod() {
            return "Bank Payment";
            }
         }
    // Point Payment Class
    class PointPayment implements Payment {
        public boolean processPayment(double amount) {
            System.out.println("Processing point payment of " + amount + " points");
            return true;
        }
        public String getPaymentMethod() {
        return "Point Payment";
        }
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
        private String custName;

        public Order(double amount, Payment paymentMethod, String custName, String productName) {
            this.custName = custName;
            this.productName = productName;
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
           System.out.println("\u001B[32mPayment Successful\u001B[0m");
           System.out.println("\n============ Receipt ============");
           System.out.println("Company: CompuMart");
           System.out.println("Customer Name: " + custName);
           System.out.println("Product: " + productName);
           System.out.println("Payment Method: " + paymentMethod.getPaymentMethod());
           System.out.println("Total Price: RM" + String.format("%.2f", amount));
           System.out.println("=================================");
        }
    }

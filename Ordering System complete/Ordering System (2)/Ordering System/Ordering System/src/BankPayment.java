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

         @Override
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


            @Override
            public String getPaymentMethod() {
            return "Bank Payment";
            }
         }


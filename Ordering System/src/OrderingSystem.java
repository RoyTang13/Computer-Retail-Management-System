
import java.util.Scanner;

// Main Class for Testing
public class OrderingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double amount = 100.0; // Predefined payment amount
        String productName = "Laptop"; // Predefined product name
        Customer customer = new Customer("James");
        customer.setPoints(500);
        int choice = -1;
        System.out.println("Order amount: RM" + amount);
        
        Payment paymentMethod = null;
//        userSystem startUser = new userSystem();
//        startUser.startUserSystem();开usersystem的

        Cart cart =new Cart(customer);
        
            
            
            
        cart.cartPage();}
        
//             while (true) {
//                System.out.println("Choose payment method:");
//                System.out.println("1. Bank Payment");
//                System.out.println("2. Point Payment");
//                System.out.println("3. Top-Up Credit Payment");
//                System.out.println("4. E-Wallet (TNG) Payment");
//                System.out.print("Enter your choice: ");
//
//                if (scanner.hasNextInt()) {
//                    choice = scanner.nextInt();
//                    scanner.nextLine();
//                    System.out.println("");
//
//                    switch (choice) {
//                        case 1:
//                        String cardNumber;
//                        boolean isValid = false;
//
//                        do {
//                            System.out.print("Enter 16-digit card number: ");
//                            cardNumber = scanner.nextLine();
//
//                            if (cardNumber.matches("\\d{16}")) {
//                                isValid = true;
//                            } else {
//                                System.out.println("\u001B[31mInvalid card number. Must be 16 digits.\u001B[0m");
//                            }
//                        } while (!isValid);
//
//                        // Continue to expiry date and cvv input (similar pattern)
//                        System.out.print("Enter expiry date (MM/YY): ");
//                        String expiryDate = scanner.nextLine();
//                        while (!expiryDate.matches("(0[1-9]|1[0-2])/\\d{2}")) {
//                            System.out.println("\u001B[31mInvalid format. Use MM/YY.\u001B[0m");
//                            System.out.print("Enter expiry date (MM/YY): ");
//                            expiryDate = scanner.nextLine();
//                            }
//
//                        System.out.print("Enter CVV (3 digits): ");
//                        String cvv = scanner.nextLine();
//                        while (!cvv.matches("\\d{3}")) {
//                            System.out.printrint("Enter CVV (3 digits): ");
//                            cvv = scanner.nextLine();
//                        }
//
//                            paymentMethod = new BankPayment(cardNumber, expiryDate, cvv);
//                            break;
//                        case 2:
//                            paymentMethod = new PointPayment(customer);
//                            break;
//                        case 3:
//                            paymentMethod = new TopUpCreditPayment();
//                            break;
//                        case 4:
//                            paymentMethod = new EWalletPayment();
//                            break;
//                        default:
//                            System.out.println("\033[31mInvalid choice. Please enter a number between 1 and 4.\033[0m");
//                            continue;
//                    }
//                    break;
//                } else {
//                    System.out.println("\033[31mInvalid input. Please enter a valid number.\033[0m");
//                    scanner.next(); // Clear invalid input
//                }
//            }
//
//           Order order = new Order(amount, paymentMethod, customer, productName);
//           order.processOrder();
//
//        // Ask user if they want to download the receipt
//            String downloadChoice = "";
//
//            while (true) {
//                System.out.print("Do you want to download the receipt? (yes/no): ");
//                downloadChoice = scanner.nextLine().trim().toLowerCase();
//
//                if (downloadChoice.equals("yes")) {
//                    Receipt receipt = new Receipt(); 
//                    receipt.generateReceipt(customer, productName, paymentMethod, amount);
//                    break;
//                } else if (downloadChoice.equals("no")) {
//                    System.out.println("Thank you for purchasing the product in CompuMart.");
//                    System.out.println("Hope you have a nice day!! :)");
//                    break;
//                } else {
//                    System.out.println("\u001B[31mInvalid input. Please enter 'yes' or 'no'.\u001B[0m");
//                }
//            }
//            scanner.close();
//            }ln("\u001B[31mInvalid CVV. Must be 3 digits.\u001B[0m");
//                            System.out.print("Enter CVV (3 digits): ");
//                            cvv = scanner.nextLine();
//                        }
//
//                            paymentMethod = new BankPayment(cardNumber, expiryDate, cvv);
//                            break;
//                        case 2:
//                            paymentMethod = new PointPayment(customer);
//                            break;
//                        case 3:
//                            paymentMethod = new TopUpCreditPayment();
//                            break;
//                        case 4:
//                            paymentMethod = new EWalletPayment();
//                            break;
//                        default:
//                            System.out.println("\033[31mInvalid choice. Please enter a number between 1 and 4.\033[0m");
//                            continue;
//                    }
//                    break;
//                } else {
//                    System.out.println("\033[31mInvalid input. Please enter a valid number.\033[0m");
//                    scanner.next(); // Clear invalid input
//                }
//            }
//
//           Order order = new Order(amount, paymentMethod, customer, productName);
//           order.processOrder();
//
//        // Ask user if they want to download the receipt
//            String downloadChoice = "";
//
//            while (true) {
//                System.out.print("Do you want to download the receipt? (yes/no): ");
//                downloadChoice = scanner.nextLine().trim().toLowerCase();
//
//                if (downloadChoice.equals("yes")) {
//                    Receipt receipt = new Receipt(); 
//                    receipt.generateReceipt(customer, productName, paymentMethod, amount);
//                    break;
//                } else if (downloadChoice.equals("no")) {
//                    System.out.println("Thank you for purchasing the product in CompuMart.");
//                    System.out.println("Hope you have a nice day!! :)");
//                    break;
//                } else {
//                    System.out.println("\u001B[31mInvalid input. Please enter 'yes' or 'no'.\u001B[0m");
//                }
//            }
//            scanner.close();
}

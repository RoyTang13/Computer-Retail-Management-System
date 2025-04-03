
import java.util.Scanner;

public class OrderingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        double amount = 100.0; // Predefined payment amount
        String productName = "Laptop"; // Predefined product name
        String custName = "James";
        
        // Display available payment methods
        System.out.println("Choose payment method:");
        System.out.println("1. Bank Payment");
        System.out.println("2. Point Payment");
        System.out.println("3. Top-Up Credit Payment");
        System.out.println("4. E-Wallet (TNG) Payment");

        while (true) {
            try {
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(scanner.nextLine());

                // Ensure the input is valid
                if (choice < 1 || choice > 4) {
                    throw new IllegalArgumentException("Invalid choice! Please choose a number between 1 and 4.");
                }

                break; // Exit the loop if a valid choice is entered
            } catch (NumberFormatException e) {
                System.out.println("\u001B[31mInvalid input! Please enter a number.\u001B[0m");
            } catch (IllegalArgumentException e) {
                System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
            }
        }

        Payment selectedPayment = null;

        switch (choice) {
            case 1:
                selectedPayment = new BankPayment();
                break;
            case 2:
                selectedPayment = new PointPayment();
                break;
            case 3:
                selectedPayment = new TopUpCreditPayment();
                break;
            case 4:
                selectedPayment = new EWalletPayment();
                break;
        }

        Order order = new Order(custName,productName,amount, selectedPayment);
        order.processOrder();

        scanner.close();
    }
}

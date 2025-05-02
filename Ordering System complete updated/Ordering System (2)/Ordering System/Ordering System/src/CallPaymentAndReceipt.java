import java.util.List;
import java.util.Scanner;

public class CallPaymentAndReceipt {

    public static void callPaymentAndReceipt( User customer, List<CartItem> items, UserSystem system) {
        Scanner scanner = new Scanner(System.in);

         // ✅ Process the payment
        Payment paymentMethod = null;
        Order order = new Order(paymentMethod, customer, items,system);
        boolean cancel=order.processOrder();
        
        if(cancel){
        //Calculate the totalamount
        double totalAmount = 0.0;
        for (CartItem item : items) {
            totalAmount += item.getTotalAmount();
        }

        
        
        // ✅ Ask user if they want to download the receipt
        while (true) {
            System.out.print("Do you want to download the receipt? (yes/no): ");
            String downloadChoice = scanner.nextLine().trim().toLowerCase();

            if (downloadChoice.equals("yes")) {
                Receipt receipt = new Receipt();
                receipt.generateReceipt(customer, items, order.getPaymentMethod(), totalAmount);
                break;
                
            } else if (downloadChoice.equals("no")) {
                
                break;
            } else {
                System.out.println("\u001B[31mInvalid input. Please enter 'yes' or 'no'.\u001B[0m");
            }
        }
        
        while (true) {
            System.out.print("Do you want to continue shopping? (yes/no): ");
            String continueChoice = scanner.nextLine().trim().toLowerCase();

            if (continueChoice.equals("yes")) {
                return; // 💥 Go back to menu (cartPage() continues)
            } else if (continueChoice.equals("no")) {
                System.out.println("\u001B[32mThank you for purchasing the product(s) at CompuMart!");
                System.out.println("Hope you have a nice day!! :)\u001B[0m");
                
                
                System.exit(0); // 💀 End the whole program
            } else {
                System.out.println("\u001B[31mInvalid input. Please enter 'yes' or 'no'.\u001B[0m");
            }
        }}
        else{
         System.out.print("Cancel by this checkout ");
         
        }
        }
        

}


import java.util.Scanner;

public class TopUpCreditPayment implements Payment {
    private Customer customer;

    public TopUpCreditPayment(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean processPayment(double amount) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Your current credit balance: RM" + String.format("%.2f", customer.getCreditBalance()));

        if (customer.getCreditBalance() >= amount) {
            customer.deductCredit(amount);
            System.out.println("Payment successful using Top-Up Credit!");
            System.out.println("Remaining balance: RM" + String.format("%.2f", customer.getCreditBalance()));
            return true;
        } else {
            System.out.println("Not enough credit balance to complete the payment.");
            System.out.print("Would you like to top up? (yes/no): ");
            String choice = scanner.nextLine().trim().toLowerCase();
            
            if (choice.equals("yes")) {
                System.out.print("Enter top-up amount: RM");
                double topUpAmount = scanner.nextDouble();
                scanner.nextLine(); // consume newline
                customer.addCredit(topUpAmount);
                System.out.println("Top-up successful! New balance: RM" + String.format("%.2f", customer.getCreditBalance()));
                return processPayment(amount); // Try payment again
            } else {
                System.out.println("Payment cancelled.");
                return false;
            }
        }
    }

    @Override
    public String getPaymentMethod() {
        return "Top-Up Credit";
    }
}

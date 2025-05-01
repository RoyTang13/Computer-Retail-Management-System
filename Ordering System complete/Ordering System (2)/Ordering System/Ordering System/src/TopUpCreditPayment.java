import java.util.Scanner;

class TopUpCreditPayment implements Payment {
    private final User customer;
    private final UserSystem system;

    public TopUpCreditPayment(User customer, UserSystem system) {
        this.customer = customer;
        this.system = system;
    }

    public void topUp() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Your current credit balance: RM " + String.format("%.2f", customer.getCreditBalance()));
        System.out.print("Enter top-up amount: RM ");

        try {
            double amount = Double.parseDouble(scanner.nextLine());
            if (amount <= 0) {
                System.out.println("Amount must be more than RM 0.");
                return;
            }

            customer.addCredit(amount);
            system.saveUsersToFile();
            System.out.println("Top-up successful. New credit balance: RM " + String.format("%.2f", customer.getCreditBalance()));
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a numeric value.");
        }
    }

    @Override
    public boolean processPayment(double amount) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Your current credit balance: RM" + String.format("%.2f", customer.getCreditBalance()));

        if (customer.getCreditBalance() >= amount) {
            customer.deductCredit(amount);
            system.saveUsersToFile();
            System.out.println("Payment successful using Top-Up Credit!");
            System.out.println("Remaining balance: RM" + String.format("%.2f", customer.getCreditBalance()));
            return true;
        } else {
            System.out.println("Not enough credit balance to complete the payment.");
            System.out.print("Would you like to top up? (yes/no): ");
            String choice = scanner.nextLine().trim().toLowerCase();

            if (choice.equals("yes")) {
                topUp(); // call topUp() method here
                return processPayment(amount); // retry
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

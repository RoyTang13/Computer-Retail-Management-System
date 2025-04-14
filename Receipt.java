import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;

public class Receipt {
    private int receiptCounter;

    public Receipt() {
        // Initialize folder and counter
        File folder = new File("Receipt");
        if (!folder.exists()) {
            folder.mkdir();
        }

        // Count existing receipt files to avoid overwriting
        File[] files = folder.listFiles((dir, name) -> name.matches("receipt\\d+\\.txt"));
        receiptCounter = (files != null) ? files.length + 1 : 1;
    }

    public void generateReceipt(Customer customer, String productName, Payment paymentMethod, double amount) {
        String filename = "Receipt/receipt" + receiptCounter + ".txt";

        try (PrintWriter writer = new PrintWriter(filename)) {
            writer.println("============ Receipt ============");
            writer.println("Company: CompuMart");
            writer.println("Customer Name: " + customer.getName());
            writer.println("Product: " + productName);
            writer.println("Payment Method: " + paymentMethod.getPaymentMethod());
            writer.println("Total Price: RM" + String.format("%.2f", amount));

            if (paymentMethod instanceof PointPayment) {
                writer.println("Point Balance: " + customer.getPoints() + " points");
            }

            writer.println("=================================");
            System.out.println(" Receipt saved to: " + filename);
            receiptCounter++;
        } catch (IOException e) {
            System.out.println(" Failed to save receipt: " + e.getMessage());
        }
    }
}

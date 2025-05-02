import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Receipt {
    private int receiptCounter;

    public Receipt() {
        File folder = new File("Receipt");
        if (!folder.exists()) {
            folder.mkdir();
        }

        File[] files = folder.listFiles((dir, name) -> name.matches("receipt\\d+\\.txt"));
        receiptCounter = (files != null) ? files.length + 1 : 1;
    }

    public void generateReceipt(User customer, List<CartItem> items, Payment paymentMethod, double totalAmount) {
        String filename = "Receipt/receipt" + receiptCounter + ".txt";
        
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        try (PrintWriter writer = new PrintWriter(filename)) {
            
        writer.println();
        writer.println("=".repeat(80));
        writer.printf("%40s\n", "RECEIPT");
        writer.println("=".repeat(80));
        writer.printf("%-20s %s\n", "Company:", "CompuMart");
        writer.printf("%-20s %s\n", "Customer Name:", customer.getUserName());
        writer.printf("%-20s %s\n", "Purchase Date:", formattedDateTime);
        writer.println("-".repeat(80));
        writer.printf("%-30s %10s %15s %15s\n", "Product Name", "Quantity", "Unit Price", "Total Price");
        writer.println("-".repeat(80));

        for (CartItem item : items) {
            writer.printf("%-30s %10d %15.2f %15.2f\n",
                    item.getProductName(),
                    item.getQuantity(),
                    item.getUnitPrice(),
                    item.getTotalAmount());
        }

        writer.println("-".repeat(80));
        writer.printf("%-20s %s\n", "Payment Method:", paymentMethod.getPaymentMethod());
        writer.printf("%-20s RM%.2f\n", "Total Price:", totalAmount);

        // Optional: If Payment is PointPayment, show remaining points
        if (paymentMethod instanceof PointPayment) {
            PointPayment pointPayment = (PointPayment) paymentMethod;
            writer.printf("%-20s %d \n", "Point Balance:", customer.getPoints());
        }
        
        // Optional: If Payment is TopUpCreditPayment, show remaining balance
        if (paymentMethod instanceof TopUpCreditPayment) {
            TopUpCreditPayment creditBalance = (TopUpCreditPayment) paymentMethod;
            writer.printf("%-20s RM%.2f \n", "Credit Balance:", customer.getCreditBalance());
        }

        writer.println("=".repeat(80));
        writer.printf("%40s\n", "Thank you for shopping with us!");
        writer.println("=".repeat(80));  
            
//          
            // ✅ Merge your style of colorful confirmation
            System.out.println("\u001B[32m✅ Receipt saved successfully at: " + filename + "\u001B[0m");

            receiptCounter++;
        } catch (IOException e) {
            System.out.println("\u001B[31m⚠️ Failed to save receipt: " + e.getMessage() + "\u001B[0m");
        }
    }
}

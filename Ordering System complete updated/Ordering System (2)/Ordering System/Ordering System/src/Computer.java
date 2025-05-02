    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author user
 */
public class Computer extends Product {
    private String processor;
    private int ramSize;

    public Computer(String productId, String name, double price, int quantity, String processor, int ramSize) {
        super(productId, name, price, quantity);
        this.processor = processor;
        this.ramSize = ramSize;
    }

    @Override
    public String getCategory() {
        return "Computer";
    }
    
    public String getBrand() { return processor; }
    public int getWarranty() { return ramSize; }

    @Override
    public String toString() {
        return String.format("%-10s | %-35s | %-10.2f | %-7d | %-15s | %-10d",
                         productId, name, price, quantity, processor, ramSize);
    }

    @Override
    public String toDataString() {
       return String.format("%s,%s,%s,%.2f,%d,%s,%d",
                         getCategory(), productId, name, price, quantity, processor, ramSize);
    }
}

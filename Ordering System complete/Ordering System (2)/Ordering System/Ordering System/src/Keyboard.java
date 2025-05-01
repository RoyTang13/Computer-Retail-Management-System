/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author user
 */
public class Keyboard extends Product {
     private boolean isMechanical;

    public Keyboard(String productId, String name, double price, int quantity, boolean isMechanical) {
        super(productId, name, price, quantity);
        this.isMechanical = isMechanical;
    }

    @Override
    public String getCategory() {
        return "Keyboard";
    }
    
    public boolean isMechanical() { return isMechanical; }
    
    @Override
    public String toString() {
         return String.format("%-10s | %-35s | %-10.2f | %-7d | %-12s",
                         productId, name, price, quantity, isMechanical ? "Yes" : "No");
    }

    @Override
    public String toDataString() {
        return String.format("%s,%s,%s,%.2f,%d,%b",
                         getCategory(), productId, name, price, quantity, isMechanical);
    }

}

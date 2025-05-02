
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author user
 */
public class CartItem {
     private String productName;
    private int quantity;
    private double unitPrice;
    private double amount;

    public CartItem(String productName, int quantity, double unitPrice) {
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.amount = getTotalAmount();
      
    }
    private List<CartItem> items;
    public List<CartItem> getItems() {
        return items;
    }
    

   

    public String getProductName() {
        return productName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }
    
    
     public double getTotalAmount() {
        return unitPrice * quantity;
    }
    


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
         this.amount = getTotalAmount(); 
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
        this.amount = getTotalAmount(); 
    }
    
     @Override
    public String toString() {
        return String.format("| %-37s | %8d | %12.2f | %11.2f  |",
            productName, 
            quantity, 
            unitPrice, 
            getTotalAmount());
           
    }
}

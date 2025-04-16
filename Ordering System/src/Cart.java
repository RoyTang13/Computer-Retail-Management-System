/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author user
 */
class CartItem{
    private String productName;
    private int quantity;
    private double unitPrice;
    private double amount;
    //spec  ？

    

    public CartItem(String productName, int quantity, double unitPrice) {
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.amount = getTotalAmount(); 
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
        return "Product: " + productName + ", Quantity: " + quantity + ",  Unit Price: " + unitPrice +
                "Total amount : " + amount;
    }
  
    

}


public class Cart {
    private String cart_id;
    //private Customer customer;
    //private Product productor;
    private String customerName;
    

    private List<CartItem> items = new ArrayList<>();
  

    
    
    //need enviroment change
    public Cart( String customerName) {
        this.cart_id = System.currentTimeMillis() + "-" + new Random().nextInt(1000);
        this.customerName = customerName;
          this.items = new ArrayList<>(); 
    }

    



   
    
    public void addItem(String productName, int quantity ,double unitPrice){
    for(CartItem item : items){
     
      if(item.getProductName().equals(productName)){
      
            item.setQuantity(item.getQuantity() + quantity);
            item.getTotalAmount();
            
            return; 
          
      }
    }
    
    items.add(new CartItem(productName,quantity ,unitPrice));
    
    };
    
    
    
    public void removeItem(String productedName,int quantityToRemove){
      
        //if the button click is decrease function
         for(CartItem item : items){
             System.out.println("it loop ");
         if (item.getProductName().equals(productedName)) {
             
             
            
            int newQuantity = item.getQuantity() - quantityToRemove;
             System.out.println("newQuantity : "+newQuantity +" GetQuantity : "+ item.getQuantity()+
                     "quantityToRemove : "+ quantityToRemove);
            if (newQuantity <= 0) {
               
                System.out.println("Item removed from cart.");
                item.setQuantity(newQuantity);
               
                return;
            } 
                item.setQuantity(newQuantity);
                
                
            
            break;
        }
    }
    };
    
    public void viewCart(){
             if (items.isEmpty()) {
            System.out.println(" Cart is empty.");
        } else {
            System.out.println(customerName+" Your Cart:");
            for (CartItem item : items) {
                System.out.println(item);
            }
        }
    };

    
   
    
    public void saveCart(Cart cart) {
    List<String> lines = new ArrayList<>();

    // 讀取原始 Cart.txt 內容
    try (BufferedReader reader = new BufferedReader(new FileReader("Cart.txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
    } catch (IOException e) {
        System.out.println("❌ Error reading cart file: " + e.getMessage());
    }

    // 更新 cart 中的每個 item
    for (CartItem newItem : cart.getItems()) {
        String product = newItem.getProductName();
        String customer = cart.getCustomerName();

        int existingIndex = -1;
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            if (parts.length < 5) continue; // 防呆
            String fileCustomer = parts[1];
            String fileProduct = parts[2];
            if (fileCustomer.equals(customer) && fileProduct.equals(product)) {
                existingIndex = i;
                break;
            }
        }

        // 數量 <= 0：從檔案刪除該紀錄
        if (newItem.getQuantity() <= 0) {
            if (existingIndex != -1) {
                lines.remove(existingIndex);
            }
            continue;
        }

        // 數量正常：準備新行
        String newLine = cart.getCart_id() + "," + customer + "," +
                product + "," + newItem.getQuantity() + "," + newItem.getUnitPrice();

        if (existingIndex != -1) {
            lines.set(existingIndex, newLine); // 更新
        } else {
            lines.add(newLine); // 新增
        }
    }

    // 寫回 Cart.txt
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("Cart.txt"))) {
        for (String l : lines) {
            writer.write(l);
            writer.newLine();
        }
        System.out.println("✅ Cart saved with merging and removal.");
    } catch (IOException e) {
        System.out.println("❌ Error writing cart: " + e.getMessage());
    }
}

    
    
     public  void loadCart() {
      
        System.out.println(this.customerName);
        try (BufferedReader reader = new BufferedReader(new FileReader("Cart.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String fileCartId = parts[0];
                String fileCustomerName = parts[1];
                String productName = parts[2];
                int quantity = Integer.parseInt(parts[3]);
                double unitPrice = Double.parseDouble(parts[4]);
                System.out.println(fileCartId+","+fileCustomerName+","+productName+","+quantity+","+unitPrice);

                if (fileCustomerName.equals(this.customerName)) {
                    this.setCart_id(fileCartId); 
                    this.addItem(productName, quantity,unitPrice);
                    System.out.println("Item loaded: " + productName + " x" + quantity);
                }
            }
        } catch (IOException e) {
            System.out.println("️ No existing cart found. Starting fresh.");
        }

        
    }

    
    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

        public String getCustomerName() {
        return customerName;
    }

    public List<CartItem> getItems() {
        return items;
    }
    
      @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cart Contents:\n");
        for (CartItem item : items) {
            sb.append(item.toString()).append("\n");
        }
        return sb.toString();
    }
    
    
    
    
    
    
}





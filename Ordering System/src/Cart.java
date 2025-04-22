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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

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
        return String.format("| %-37s | %8d | %12.2f | %11.2f  |",
            productName, 
            quantity, 
            unitPrice, 
            getTotalAmount());
           
    }
  
    

}


public class Cart {
    
    private Customer customer;
    //private Product productor;
    private String customerName;
    Scanner scanner = new Scanner(System.in);

    private List<CartItem> items = new ArrayList<>();
  

    
    
    //need enviroment change
    public Cart( Customer customer) {
        //this.cart_id = System.currentTimeMillis() + "-" + new Random().nextInt(1000);
        this.customer = customer;
        this.customerName = customer.getName();
        this.items = new ArrayList<>(); 
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    



   
    
    public void addItem(String productName, int quantity ,double unitPrice ){
    for(CartItem item : items){
     
      if(item.getProductName().equals(productName)){
      
            item.setQuantity(item.getQuantity() + quantity);
            item.getTotalAmount();
            saveCart(this);
            return; 
          
      }
    }
    
    items.add(new CartItem(productName,quantity ,unitPrice ));
    saveCart(this);
    
    };
    
    
    
    public void removeItem(String productedName,int quantityToRemove){
      
        //if the button click is decrease function
         for(CartItem item : items){
             
         if (item.getProductName().equals(productedName)) {
             
             
            
            int newQuantity = item.getQuantity() - quantityToRemove;
             System.out.println("newQuantity : "+newQuantity +" GetQuantity : "+ item.getQuantity()+
                     "quantityToRemove : "+ quantityToRemove);
            if (newQuantity <= 0) {
               
                System.out.println("Item removed from cart.");
                item.setQuantity(newQuantity);
                items.remove(item);
                saveCart(this);
                return;
            } 
                item.setQuantity(newQuantity);
                saveCart(this);
                
            
            break;
        }
    }
    };
    
    
    
     
    
    
    public void viewCart(){
        
    if (items.isEmpty()) {
        System.out.println( "Your cart is empty.");
        return;
    }

    int itemsPerPage = 5;
    int totalPages = (int) Math.ceil((double) items.size() / itemsPerPage);
    int currentPage = 1;

    while (true) {
        // 清除控制台（可略過）
        System.out.print("\033[H\033[2J");
        System.out.flush();

        displayCartPage(this, currentPage);

        // 顯示操作選項
        System.out.println("\nOptions:");
        if (currentPage > 1) System.out.println("1. Previous Page");
        if (currentPage < totalPages) System.out.println("2. Next Page");
        System.out.println("3. Edit curernt page cart  ");
        System.out.println("4. Exit");

        System.out.print("Enter your choice: ");
        int input = scanner.nextInt();
        scanner.nextLine();

       switch (input) {
        case 1: if (currentPage > 1) currentPage--; break;
        case 2: if (currentPage < totalPages) currentPage++; break;
        case 3: editCart(currentPage); break;
        case 4: return; 
        default: System.out.println("Invalid input");
    }
    }
    }

    
   
    
    private void saveCart(Cart cart) {
    List<String> lines = new ArrayList<>();

    // 讀取原始 Cart.txt 內容
    try (BufferedReader reader = new BufferedReader(new FileReader("Cart.txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
    } catch (IOException e) {
        System.out.println(" Error reading cart file: " + e.getMessage());
    }

    // 更新 cart 中的每個 item
  for (CartItem newItem : cart.getItems()) {
        String customer = cart.getCustomerName();
        String product = newItem.getProductName();

        // 先移除原本所有相同 customer + product 的紀錄
        lines.removeIf(line -> {
            String[] parts = line.split(",");
            if (parts.length < 4) return false;
            return parts[0].equals(customer) && parts[1].equals(product);
        });

        // 數量 > 0 才寫入新資料
        if (newItem.getQuantity() > 0) {
            String newLine = customer + "," + product + "," + newItem.getQuantity() + "," + newItem.getUnitPrice();
            lines.add(newLine);
           
           
        } else {
            System.out.println("Removed item: " + product);
        }
    }

    // 寫回 Cart.txt
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("Cart.txt"))) {
        for (String l : lines) {
            writer.write(l);
            writer.newLine();
        }
      
    } catch (IOException e) {
        System.out.println(" Error writing cart: " + e.getMessage());
    }
}

    
    
    public  void displayCartPage(Cart cart, int page) {
       
        if (items.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        
        int itemsPerPage = 5;
        int totalPages = (int) Math.ceil((double) items.size() / itemsPerPage);
        page = Math.max(1, Math.min(page, totalPages));
        
        
        System.out.println("\n"+this.customerName+" your cart have:");
        // Display table header
        System.out.println("+-----+---------------------------------------+----------+--------------+--------------+");
        System.out.println("| No. |               Product                 | Quantity | Unit Price   | Total Price  |");
        System.out.println("+-----+---------------------------------------+----------+--------------+--------------+");
        // Display current page items
        int start = (page - 1) * itemsPerPage;
        int end = Math.min(start + itemsPerPage, items.size());
        int displayNo = 1;
        
        for (int i = start; i < end; i++) {
            System.out.printf("| %-3d ", displayNo++);
            System.out.println(items.get(i));
            
        }
        
        // Display footer
        System.out.println("+-----+---------------------------------------+----------+--------------+--------------+");
        System.out.printf("Page %d of %d | Total items: %d%n", page, totalPages, items.size());
    }
    
    public  void loadCart() {
      
        
        try (BufferedReader reader = new BufferedReader(new FileReader("Cart.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                
                String fileCustomerName = parts[0];
                String productName = parts[1];
                int quantity = Integer.parseInt(parts[2]);
                double unitPrice = Double.parseDouble(parts[3]);
               
                

                if (fileCustomerName.equals(this.customerName)) {
                   
                    this.addItem(productName, quantity,unitPrice);
                   
                }
            }
        } catch (IOException e) {
            System.out.println("️ No existing cart found. Starting fresh.");
        }

        
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

    
    
    private void editCart(int currentPage) {
    

    int itemsPerPage = 5;
    int start = (currentPage - 1) * itemsPerPage;
    int end = Math.min(start + itemsPerPage, items.size());
    int countOnPage = end - start;
    
   
    
    if (countOnPage == 0) {
        System.out.println("No items to edit on this page.");
        return;
    }

    displayCartPage(this,currentPage);
    
    System.out.print("Enter the number (1-" + countOnPage + ") of the item to edit: ");
    int choice = scanner.nextInt();
    scanner.nextLine(); // consume newline

    if (choice < 1 || choice > countOnPage) {
        System.out.println("Invalid choice.");
        return;
    }

    int actualIndex = start + (choice - 1);
    CartItem selectedItem = items.get(actualIndex);
    System.out.println("Selected item: " + selectedItem.getProductName());

    System.out.println("1. Change quantity");
    System.out.println("2. Remove item");
    System.out.println("3. Cancel");
    System.out.print("Choose action: ");
    int action = scanner.nextInt();
    scanner.nextLine();

    switch (action) {
        case 1:
            System.out.println("Enter new quantity: ");
            int newQty;

            while (true) {
                newQty = scanner.nextInt();

                if (newQty <= 0) {
                    
                    selectedItem.setQuantity(0);
                     saveCart(this);
                    items.remove(actualIndex);
                    System.out.println("Item removed due to zero quantity.");
                    break; 
                }

                if (newQty > 20) {
                    System.out.println("Bro, cannot be too many. Enter again (1-19): \n");
                    
                } else {
                    selectedItem.setQuantity(newQty);
                    saveCart(this);
                    System.out.println("Quantity updated.");
                    break; 
                }
            }
            
           
            break;


        case 2:
             selectedItem.setQuantity(0);
             saveCart(this);
             items.remove(actualIndex);
            System.out.println("Item removed.");
            break;
            
            
        case 3:
            break;

        default:
            System.out.println("Invalid action.");
            break;
    }
    }
    
    
    public void cartPage(){
    
    System.out.println("Reloading cart...");
    this.items.clear();
    this.loadCart();
    while (true) {
            
           
            
            
            System.out.println("\n--- Menu ---");
            System.out.println("1. View cart ");
            System.out.println("2. History ");
            System.out.println("3. Checkout");
            System.out.println("4. Exit");

            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    this.viewCart();
                    break;
                case 2: 
                    this.viewOrderHistory();
                    break;
                    
                case 3:
                    List<CartItem> selectedItems = this.selectItemsForCheckout();
                    if (!selectedItems.isEmpty()) {
                        System.out.println("\nProceeding with checkout...");
                        Payment paymentmethod = showPaymentPage(customer);
                        Order order = new Order( paymentmethod, customer, selectedItems);
                        order.processOrder();
                        String downloadChoice = "";

                        while (true) {
                            System.out.print("Do you want to download the receipt? (yes/no): ");
                            downloadChoice = scanner.nextLine().trim().toLowerCase();

                            if (downloadChoice.equals("yes")) {
                                Receipt receipt = new Receipt(); 
                                receipt.generateReceipt(customer, productName, paymentMethod, amount);
                                break;
                            } else if (downloadChoice.equals("no")) {
                                System.out.println("Thank you for purchasing the product in CompuMart.");
                                System.out.println("Hope you have a nice day!! :)");
                                break;
                            } else {
                                System.out.println("\u001B[31mInvalid input. Please enter 'yes' or 'no'.\u001B[0m");
                            }
                        }
                    } else {
                     System.out.println(" Checkout was cancelled or no items selected.");
                     }
                    break;
                    
                case 4:
                    System.out.println(" Thank you! Goodbye.");
                    return;
                    
                default:
                    System.out.println(" Invalid choice. Try again.");
            }
        }
    }
    
    
    
     public List<CartItem> selectItemsForCheckout() {
            Scanner scanner = new Scanner(System.in);
            List<CartItem> selectedItems = new ArrayList<>();
            double totalamount=0;

            if (items.isEmpty()) {
                System.out.println("Your cart is empty.");
                return selectedItems;
            }

            System.out.println("Select items to checkout:");
            System.out.println("+-----+---------------------------------------+----------+--------------+--------------+");
            System.out.println("| No. |               Product                 | Quantity | Unit Price   | Total Price  |");
            System.out.println("+-----+---------------------------------------+----------+--------------+--------------+");
            int displayNo = 1;

            for (int i = 0; i < items.size(); i++) {
                System.out.printf("| %-3d ", displayNo++);
                System.out.println(items.get(i));
               System.out.println("+-----+---------------------------------------+----------+--------------+--------------+");

            }

            // Display footer

            System.out.printf("Total items: %d%n", items.size());

            System.out.println("Enter item numbers separated by commas (e.g., 1,3): ");
            String input = scanner.nextLine();
            String[] selections = input.split(",");
            //Set 用來存放選中的索引值
            //使用 LinkedHashSet 是因為： 它會 自動過濾重複元素
            Set<Integer> uniqueIndexes = new LinkedHashSet<>();

            for (String s : selections) {
                try {
                    //去空白 (trim())
                    int index = Integer.parseInt(s.trim()) - 1;
                    if (index >= 0 && index < items.size()) {
                        uniqueIndexes.add(index);
                    } else {
                        System.out.println("Invalid item number: " + (index + 1));
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input: " + s);
                }
            }

            for (int index : uniqueIndexes) {
                selectedItems.add(items.get(index));
                items.remove(index);
            }


            if (selectedItems.isEmpty()) {
                System.out.println("No items selected for checkout.");
            }

            System.out.println("\nYou selected the following products for checkout:");
            System.out.println("----------------------------------------------------------------------------");
            System.out.printf("%-25s %10s %15s %15s%n", "Product Name", "Quantity", "Unit Price", "Total Price");
            System.out.println("----------------------------------------------------------------------------");

            for (CartItem item : selectedItems) {
             System.out.printf("%-25s %10d %15.2f %15.2f%n",
                          item.getProductName(),
                          item.getQuantity(),
                          item.getUnitPrice(),
                          item.getTotalAmount());
            totalamount+=item.getTotalAmount();
            System.out.println("----------------------------------------------------------------------------");
            }


            System.out.printf("%-51s %15.2f%n", "Total Amount:", totalamount);



        while (true) {
        System.out.println("\nOption");
        System.out.println("1. Confirm");
        System.out.println("2. Cancel");

        System.out.print("Enter choice: ");

        // 確保輸入是數字
        if (scanner.hasNextInt()) {
           int choice = scanner.nextInt();
           scanner.nextLine();

            if (choice == 1) {

                return selectedItems;

            } else if (choice == 2) {

               return new ArrayList<>();
            } else {
                System.out.println("Invalid choice. Please enter 1 or 2.");
            }
        } else {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); 
        }
        }
     }
     
     
    private Payment showPaymentPage(Customer customer){
     Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nChoose payment method:");
            System.out.println("1. Bank Payment");
            System.out.println("2. Point Payment");
            System.out.println("3. Top-Up Credit Payment");
            System.out.println("4. E-Wallet (TNG) Payment");
            System.out.print("Enter your choice: ");
            
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                System.out.println("");
                
                switch (choice) {
                    case 1:
                    String cardNumber;
                    boolean isValid = false;

                    do {
                        System.out.print("Enter 16-digit card number: ");
                        cardNumber = scanner.nextLine();

                        if (cardNumber.matches("\\d{16}")) {
                            isValid = true;
                        } else {
                            System.out.println("\u001B[31mInvalid card number. Must be 16 digits.\u001B[0m");
                        }
                    } while (!isValid);

                    // Continue to expiry date and cvv input (similar pattern)
                    System.out.print("Enter expiry date (MM/YY): ");
                    String expiryDate = scanner.nextLine();
                    while (!expiryDate.matches("(0[1-9]|1[0-2])/\\d{2}")) {
                        System.out.println("\u001B[31mInvalid format. Use MM/YY.\u001B[0m");
                        System.out.print("Enter expiry date (MM/YY): ");
                        expiryDate = scanner.nextLine();
                        }
                    
                    System.out.print("Enter CVV (3 digits): ");
                    String cvv = scanner.nextLine();
                    while (!cvv.matches("\\d{3}")) {
                        System.out.println("\u001B[31mInvalid CVV. Must be 3 digits.\u001B[0m");
                        System.out.print("Enter CVV (3 digits): ");
                        cvv = scanner.nextLine();
                    }

                        return new BankPayment(cardNumber, expiryDate, cvv);
                       
                    case 2:
                        return new PointPayment(customer);
                       
                    case 3:
                        return new TopUpCreditPayment();
                        
                    case 4:
                        return new EWalletPayment();
                       
                    default:
                        System.out.println("\033[31mInvalid choice. Please enter a number between 1 and 4.\033[0m");
                        continue;
                }
               
            } else {
                System.out.println("\033[31mInvalid input. Please enter a valid number.\033[0m");
                scanner.next(); // Clear invalid input
            }
            
            
        }
        
        
        
    
    
    }
    
    public  void viewOrderHistory() {
             Scanner scanner = new Scanner(System.in);
            List<List<String>> orders = getCustomerOrders();

            if (orders.isEmpty()) {
                System.out.println("No orders found for customer: " + customerName);
                return;
            }

            
            System.out.println("\nOrders found for " + customerName + ":");
            for (int i = 0; i < orders.size(); i++) {
                for (String line : orders.get(i)) {
                    if (line.startsWith("OrderID:")) {
                        System.out.println((i + 1) + ". " + line);
                        break;
                    }
                }
            }

            
            System.out.print("Enter order number to view: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            if (choice < 1 || choice > orders.size()) {
                System.out.println("Invalid choice.");
                return;
            }

            
            System.out.println("\n-------------- ORDER --------------");
            for (String line : orders.get(choice - 1)) {
                System.out.println(line);
            }
            System.out.println("------------ END ORDER ------------");
    }

    private List<List<String>> getCustomerOrders() {
        List<List<String>> allOrders = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("OrderHistory.txt"))) {
            String line;
            boolean isMatchingOrder = false;
            boolean isInsideOrder = false;
            List<String> currentOrder = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                if (line.equals("-----")) {
                    if (isInsideOrder && isMatchingOrder) {
                        allOrders.add(new ArrayList<>(currentOrder)); // 儲存整個訂單
                    }
                    currentOrder.clear();
                    isInsideOrder = false;
                    isMatchingOrder = false;
                    continue;
                }

                if (line.startsWith("OrderID:")) {
                    isInsideOrder = true;
                    isMatchingOrder = false;
                }

                if (line.startsWith("Customer: ")) {
                    String name = line.substring("Customer: ".length()).trim();
                    if (name.equals(customerName)) {
                        isMatchingOrder = true;
                    }
                }

                if (isInsideOrder) {
                    currentOrder.add(line); 
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading history: " + e.getMessage());
        }
        return allOrders;
    }

        
    }

    
    
    
    
    
    
    
    
    





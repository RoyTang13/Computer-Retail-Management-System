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




public class Cart {
    
    private User customer;
    private List<Product> products = new ArrayList<>();
    private String customerName;
    private List<CartItem> items = new ArrayList<>();
    private UserSystem system;
    private Product product;
    
    Scanner scanner = new Scanner(System.in);

    
    
    //need enviroment change
    public Cart(User customer,UserSystem system , List<Product> products) {
        //this.cart_id = System.currentTimeMillis() + "-" + new Random().nextInt(1000);
        this.customer = customer;
        this.customerName = customer.getUserName();
        this.items = new ArrayList<>(); 
        this.system = system;
        this.products=products;
       
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }   
    
    public void addItem(String productName, int quantity ,double unitPrice ){
        for(CartItem item : items){

          if(item.getProductName().equals(productName)){
                System.out.println("getProductName :"+item.getProductName());
                System.out.println("productName :"+productName);
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

        int itemsPerPage = 10;
        int totalPages = (int) Math.ceil((double) items.size() / itemsPerPage);
        int currentPage = 1;

        while (true) {
            // 清除控制台（可略過）
            System.out.print("\033[H\033[2J");
            System.out.flush();

            displayCartPage(this, currentPage);

            // 顯示操作選項
            System.out.println("\nOptions:");
            System.out.println("1. Edit curernt page cart  ");
            if (currentPage > 1) System.out.println("2. Previous Page");
            if (currentPage < totalPages) System.out.println("3. Next Page");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            int input = scanner.nextInt();
            scanner.nextLine();

           switch (input) {
            case 1: editCart(currentPage); break;
            case 2: if (currentPage > 1) currentPage--; break;
            case 3: if (currentPage < totalPages) currentPage++; break;
            case 0: return; 
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

            int itemsPerPage = 10;
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
    
    public void loadCart() {
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
             
            Product matchedProduct = findProductByCombinedName(selectedItem.getProductName());
            

            int oldQty = selectedItem.getQuantity();

            switch (action) {
                case 1:
                    System.out.println("Enter new quantity: ");
                    int newQty;

                    while (true) {
                       
                        newQty = scanner.nextInt();
                      
                        System.out.println(matchedProduct);
                       
                        int diff = newQty - oldQty;
                       
                    
                        if (newQty <= 0) {//remove cart
                            matchedProduct.setQuantity(matchedProduct.getQuantity() + oldQty);
                            selectedItem.setQuantity(0);
                            saveCart(this);
                            system.saveProducts();
                            items.remove(actualIndex);
                            System.out.println("Item removed due to zero quantity.");
                            break; 
                        }
////
//                        if (newQty > 20) {
//                            System.out.println("Bro, cannot be too many. Enter again (1-19): \n");
//
//                        }
//                      
//                        
//                      
//                        if (matchedProduct != null) {
//                            if (diff > 0) {//+cart
//                                if (matchedProduct.getQuantity() >= diff) {//-product
//                                    matchedProduct.setQuantity(matchedProduct.getQuantity() - diff);
//                                     selectedItem.setQuantity(newQty);
//                                     saveCart(this);
//                                     system.saveProducts();
//                                     System.out.println("Quantity updated.");
//                                     break; 
//                                } else {
//                                    System.out.println("Insufficient stock in product. Only " + matchedProduct.getQuantity() + " available.");
//                                    return;
//                                }
//                            } 
//                        }
//                      
//                        break;
                    if (diff > 0) {
                        // 使用者增加數量 → 商品庫存減少
                        if (matchedProduct.getQuantity() >= diff) {
                            matchedProduct.setQuantity(matchedProduct.getQuantity() - diff);
                            selectedItem.setQuantity(newQty);
                            saveCart(this);
                            system.saveProducts();
                            System.out.println("Quantity updated.");
                            break;
                        } else {
                            System.out.println("Insufficient stock in product. Only " + matchedProduct.getQuantity() + " available.");
                            return;
                        }
                    } else if (diff < 0) {
                        // 使用者減少數量 → 商品庫存增加
                        matchedProduct.setQuantity(matchedProduct.getQuantity() + Math.abs(diff));
                        selectedItem.setQuantity(newQty);
                        saveCart(this);
                        system.saveProducts();
                        System.out.println("Quantity reduced.");
                        break;
                    }

                    }


                    break;


                case 2:
                    matchedProduct.setQuantity(matchedProduct.getQuantity() + oldQty);
                    selectedItem.setQuantity(0);
                    saveCart(this);
                    system.saveProducts();
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
   
        while (true) {
   
            System.out.println("\n--- Menu ---");
            System.out.println("1. View cart ");
            System.out.println("2. History ");
            System.out.println("3. Checkout");
            System.out.println("4. Back");

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
                        
                    CallPaymentAndReceipt.callPaymentAndReceipt(customer, selectedItems, system);
                    items.removeAll(selectedItems);

                    
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
            boolean selectall = false;
            System.out.println("Enter item numbers separated by commas (e.g., 1,3 )or enter 0 to select all: ");
            String input = scanner.nextLine();
            
            String[] selections = input.split(",");
            //Set 用來存放選中的索引值
            //使用 LinkedHashSet 是因為： 它會 自動過濾重複元素
            Set<Integer> uniqueIndexes = new LinkedHashSet<>();

            for (String s : selections) {
                try {
                    //去空白 (trim())
                    int index = Integer.parseInt(s.trim()) - 1;
                     
                    if (index == -1){
                        selectall = true;
                        break; 
                    }
                    if (index >= 0 && index < items.size()) {
                        uniqueIndexes.add(index);
                    } else {
                        System.out.println("Invalid item number: " + (index + 1));
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input: " + s);
                }
            }
            
            if(selectall){
                 for (int i = 0; i < items.size(); i++) {
                 uniqueIndexes.add(i);
                 }
            }
            
            for (int index : uniqueIndexes) {
                selectedItems.add(items.get(index));
               
            }


            if (selectedItems.isEmpty()) {
                System.out.println("No items selected for checkout.");
                return new ArrayList<>();
            }
            
            else{
            System.out.println("\nYou selected the following products for checkout:");
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.printf("%-40s %10s %15s %15s%n", "Product Name", "Quantity", "Unit Price", "Total Price");
            System.out.println("--------------------------------------------------------------------------------------");

            for (CartItem item : selectedItems) {
                System.out.printf("%-40s %10d %15.2f %15.2f%n",
                        item.getProductName(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getTotalAmount());
                totalamount += item.getTotalAmount();
                System.out.println("--------------------------------------------------------------------------------------");
            }



            System.out.printf("%-65s %15.2f%n", "Total Amount:", totalamount);
            }


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
    public void viewOrderHistory() {
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
    
    public double calculateTotalPrice() {
        double total = 0.0;
        for (CartItem item : items) {
            total += item.getTotalAmount();
        }
        return total;
    }

    public void handleProductSelection( List<Integer> canBeChoice ,String category,String input) {
        Scanner scanner = new Scanner(System.in);
  
        try {
            int selectedId = Integer.parseInt(input);
            if (canBeChoice.contains(selectedId)) {
                    
                    for (Product p : products) {
                    if (p.getProductId().equals(String.format("%03d", selectedId))) {
                        System.out.println("Selected product:");
                        System.out.println("Product name :"+p.getName());
                        System.out.print("Enter quantity: ");
                        int qty = scanner.nextInt();
                        
                        if(qty <=0){
                        System.out.println("Cancel add to cart");
                        return;
                        }
                        
                        if(qty >=20){
                        System.out.println("Maximun 20");
                        return;
                        }

                        System.out.println("1. Confirm");
                        System.out.println("2. Back");
                        

                        System.out.print("Choose an option: ");
                        int opt = scanner.nextInt();
                        if (opt == 2) {
                          //可能要 clear 掉canBeChoice;
                          return;
                        } 
                       

                        if (opt == 1) {
                            if (p.getQuantity() >= qty) {
                                p.setQuantity(p.getQuantity() - qty);
                                String finalProductName = combineTheName(category,p);
                                
                                int existingQty = getCartQuantityByName(finalProductName);
                                    if (existingQty + qty > 20) {
                                        System.out.println("You cannot have more than 20 units of this product in your cart.");
                                        return;
                                    }
                                addItem(finalProductName, qty, p.getPrice());
                                System.out.print("Product successfull add to cart \n\n");
                            } else {
                                System.out.println("Insufficient stock to reduce.");
                            }
                        } 

                        else {
                            System.out.println("Invalid option.");
                        }
                        system.saveProducts();
                        return;
                    }
            } // Format to 3-digit string
                return;
            } else {
                System.out.println("Invalid ID. Please select from the listed product IDs.");
            }
        } catch (NumberFormatException e) {
                // Treat as product name (fuzzy match)
                List<Product> matches = new ArrayList<>();
                for (Product p : products) {
                    if (p.getName().toLowerCase().contains(input.toLowerCase())) {
                        matches.add(p);
                    }
                }

                if (matches.isEmpty()) {
                    System.out.println("No matching product names found.");
                } else if (matches.size() == 1) {
                    Product matchedProduct = matches.get(0);
                    System.out.println("Found one match: " + matchedProduct.getName());
                    System.out.println(matchedProduct);

                    System.out.println("1. Confirm");
                    System.out.println("2. Back");
                    System.out.print("Choose an option: ");
                    int opt = scanner.nextInt();

                    if (opt == 2) return;

                    System.out.print("Enter quantity: ");
                    int qty = scanner.nextInt();
                    if(qty <=0){
                        System.out.println("Cancel add to cart");
                        return;
                        }
                    
                    if(qty >=20){
                        System.out.println("Maximun 20");
                        return;
                        }

                    
                    if (opt == 1) {
                        if (matchedProduct.getQuantity() >= qty) {
                            matchedProduct.setQuantity(matchedProduct.getQuantity() - qty);
                            String finalProductName = combineTheName(category, matchedProduct);
                            int existingQty = getCartQuantityByName(finalProductName);
                                if (existingQty + qty > 20) {
                                    System.out.println("You cannot have more than 20 units of this product in your cart.");
                                    return;
                                }
                            addItem(finalProductName, qty, matchedProduct.getPrice());
                             System.out.print("Product successfull add to cart \n\n");
                        } else {
                            System.out.println("Insufficient stock to reduce.");
                        }
                    } else {
                        System.out.println("Invalid option.");
                    }

                    system.saveProducts();
                    return;





                } else {
                    // 多筆結果處理
                    System.out.println("Multiple matches found:");
                    for (int i = 0; i < matches.size(); i++) {
                        System.out.printf("%d. %s (%s)\n", i + 1, matches.get(i).getName(), matches.get(i).getProductId());
                    }
                    System.out.print("Select a product number: ");
                    //int choice = scanner.nextInt();
                    int choice = system.getIntInput(1, matches.size());
                    scanner.nextLine();

                    if (choice >= 1 && choice <= matches.size()) {
                        Product matchedProduct = matches.get(choice - 1);
                        System.out.println("Selected: " + matchedProduct);

                        System.out.println("1. Confirm");
                        System.out.println("2. Back");
                        System.out.print("Choose an option: ");
                        int opt = scanner.nextInt();

                        if (opt == 2) return;

                        System.out.print("Enter quantity: ");
                        int qty = scanner.nextInt();
                        if(qty <=0){
                        System.out.println("Cancel add to cart");
                        return;
                        }
                        if(qty >=20){
                        System.out.println("Maximun 20");
                        return;
                        }

                        
                        if (opt == 1) {
                            if (matchedProduct.getQuantity() >= qty) {
                                matchedProduct.setQuantity(matchedProduct.getQuantity() - qty);
                                String finalProductName = combineTheName(category, matchedProduct);
                                int existingQty = getCartQuantityByName(finalProductName);
                                if (existingQty + qty > 20) {
                                    System.out.println("You cannot have more than 20 units of this product in your cart.");
                                    return;
                                }
                                addItem(finalProductName, qty, matchedProduct.getPrice());
                                System.out.print("Product successfull add to cart \n\n");
                            } else {
                                System.out.println("Insufficient stock to reduce.");
                            }
                        } else {
                            System.out.println("Invalid option.");
                        }

                        system.saveProducts();
                    } else {
                        System.out.println("Invalid selection.");
                    }
            }
        }
    }

    public String combineTheName(String category, Product p) {
        String finalProductName="";
        if (category.equals("Computer") ) {
            Computer comp = (Computer) p;
             finalProductName = comp.getName() + " - " + comp.getBrand() + " - " + comp.getWarranty() + "GB";
        } else if (category.equalsIgnoreCase("CPU") ) {
            CPU cpu = (CPU) p;
            finalProductName = cpu.getName() + " - " + cpu.getCores() + "C/" + cpu.getThreads() + "T";
        } else if (category.equalsIgnoreCase("Keyboard") ) {
            Keyboard kb = (Keyboard) p;
            finalProductName = kb.getName() + " - " + (kb.isMechanical() ? "Mechanical" : "Membrane");
        }
         return finalProductName;
    }
    
    public int getCartQuantityByName(String productName) {
    for (CartItem item : items) {
        if (item.getProductName().equals(productName)) {
            return item.getQuantity();
        }
    }
    return 0;
}
    
     public Product findProductByCombinedName(String combinedName ) {
           
            for (Product p : products) {
                

                // Try match CPU
                if (p.getCategory().equals("CPU")) {
                    CPU cpu = (CPU) p;
                    String expected = p.getName() + " - " + cpu.getCores() + "C/" + cpu.getThreads() + "T";
                  
                    if (combinedName.equalsIgnoreCase(expected)) {
                        return p;
                    }
                }

                // Try match Keyboard
                else if (p.getCategory().equals("Keyboard")) {
                    Keyboard kb = (Keyboard) p;
                    String expectedType = kb.isMechanical() ? "Mechanical" : "Membrane";
                    String expected = p.getName() + " - " + expectedType;
                   
                    if (combinedName.equalsIgnoreCase(expected)) {
                        return p;
                    }
                }

                // Try match Computer
                else if (p.getCategory().equals("Computer")) {
                    Computer comp = (Computer) p;
                    String expected = p.getName() + " - " + comp.getBrand() + " - " + comp.getWarranty() + "GB";
                    
                    if (combinedName.equalsIgnoreCase(expected)) {
                        return p;
                    }
                }
            }

            return null;
}


        
    }

    
    
    
    
    
    
    
    
    





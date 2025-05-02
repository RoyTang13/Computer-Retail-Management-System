    import java.util.ArrayList;
    import java.util.Comparator;
    import java.util.List;
    import java.util.Scanner;


    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */

    /**
     *
     * @author user 
     */
    public class Staff {
        private static List<Product> products = new ArrayList<>();
       
        
        
        private UserSystem system;
        Scanner scanner = new Scanner(System.in);
        
        public Staff(UserSystem userSystem,List<Product> products) {
        this.system = userSystem;
        this.products=products;
         // 可选择在构造函数中加载产品
    }

        

        public void addProduct(Product p) {
            products.add(p);
        }
        
        public void addNewProductOption(){
            //System.out.print(products);
            Scanner sc =new Scanner(System.in);
            int nextIdNum = products.stream()
                        .mapToInt(p -> Integer.parseInt(p.getProductId().replaceAll("[^0-9]", "")))
                        .max()
                        .orElse(0) + 1;

           String id = String.format("%03d", nextIdNum);
           System.out.println("Select Product Type:");
                    System.out.println("1. Computer");
                    System.out.println("2. Keyboard");
                    System.out.println("3. CPU");
                    int type = sc.nextInt();
                    sc.nextLine();
                    
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Price: ");
                    double price = sc.nextDouble();
                    System.out.print("Quantity: ");
                    int qty = sc.nextInt();
                    sc.nextLine();
                    
            switch (type) {
                        case 1:
                            String[] processors = {"Intel i5", "Intel i7", "AMD Ryzen 5", "AMD Ryzen 7"};
                            for (int i = 0; i < processors.length; i++) {
                                System.out.printf("%d. %s%n", i + 1, processors[i]);
                            }
                            System.out.print("Select Processor: ");
                            int procChoice = sc.nextInt();
                            sc.nextLine(); // 清除缓冲
                            String processor = processors[procChoice - 1];

                            int[] ramOptions = {4, 8, 16, 32, 64};
                            for (int i = 0; i < ramOptions.length; i++) {
                                System.out.printf("%d. %d GB%n", i + 1, ramOptions[i]);
                            }
                            System.out.print("Select RAM Size: ");
                            int ramChoice = sc.nextInt();
                            sc.nextLine();
                            int ram = ramOptions[ramChoice - 1];
                            addProduct(new Computer(id, name, price, qty, processor, ram));
                            system.saveProducts();
                            break;
                        case 2:
                            System.out.println("1. Mechanical");
                            System.out.println("2. Membrane");
                            System.out.print("Select Keyboard Type: ");
                            int mechChoice = sc.nextInt();
                            sc.nextLine();
                            boolean mech = (mechChoice == 1);
                            addProduct(new Keyboard(id, name, price, qty, mech));
                            system.saveProducts();
                            break;
                        case 3:
                            int[] coreOptions = {2, 4, 6, 8, 12, 16};
                            for (int i = 0; i < coreOptions.length; i++) {
                                System.out.printf("%d. %d cores%n", i + 1, coreOptions[i]);
                            }
                            System.out.print("Select Cores: ");
                            int coreChoice = sc.nextInt();
                            int cores = coreOptions[coreChoice - 1];

                            int[] threadOptions = {4, 8, 12, 16, 24, 32};
                            for (int i = 0; i < threadOptions.length; i++) {
                                System.out.printf("%d. %d threads%n", i + 1, threadOptions[i]);
                            }
                            System.out.print("Select Threads: ");
                            int threadChoice = sc.nextInt();
                            sc.nextLine();
                            int threads = threadOptions[threadChoice - 1];
                            
                            addProduct(new CPU(id, name, price, qty, cores, threads));
                            system.saveProducts();
                            break;
                        default:
                            System.out.println("Invalid type!");
                    }
                   
                    


        }
        
        public void viewProductOption(){
            Scanner sc =new Scanner(System.in);
            ArrayList<Integer> canBeChoice = new ArrayList<>();
            
            System.out.println("1. Computer");
            System.out.println("2. Keyboard");
            System.out.println("3. CPU");
            System.out.println("Select Product Type:");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline
            
                    
            String category = "";
            switch (choice) {
                case 1 -> category = "Computer";
                case 2 -> category = "Keyboard";
                case 3 -> category = "CPU";
                default -> {
                    System.out.println("Invalid choice.");
                    return;
                }
            }

            system.showProductOutput(category, canBeChoice);
            
            
            
            
            while (true) {
                System.out.print("\nEnter the product ID to edit (0 to go back): ");
                String input = sc.nextLine();

                if (input.equals("0")) {
                    System.out.println("Returning...");
                    return;
                }

                try {
                    int selectedId = Integer.parseInt(input);
                    if (canBeChoice.contains(selectedId)) {
                        editProductOption(String.format("%03d", selectedId)); // 轉回ID格式如 "003"
                        return;
                    } else {
                        System.out.println("Invalid ID. Please select from the listed product IDs.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            }
                    
        
        }
        

        public void editProductOption(String productId){
            Scanner sc = new Scanner(System.in);

            for (Product p : products) {
                if (p.getProductId().equals(productId)) {
                    System.out.println("Selected product:");
                    System.out.println(p);

                    System.out.println("1. Add stock");
                    System.out.println("2. Reduce stock");
                    System.out.println("3. Delete product");
                    
                    System.out.print("Choose an option: ");
                    int opt = sc.nextInt();
                    if (opt == 3) {
                        products.remove(p);
                        System.out.println("Product deleted.");
                        system.saveProducts();
                         return;
                    } 
                    System.out.print("Enter quantity: ");
                    int qty = sc.nextInt();

                    if (opt == 1) {
                        p.setQuantity(p.getQuantity() + qty);
                        System.out.println("Stock increased. New quantity: " + p.getQuantity());
                    } else if (opt == 2) {
                        if (p.getQuantity() >= qty) {
                            p.setQuantity(p.getQuantity() - qty);
                            System.out.println("Stock reduced. New quantity: " + p.getQuantity());
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
            }

            System.out.println("Product not found.");


        }
        
        
        
        
        //edit staff
        public void viewAllUsers(ArrayList<User>users) {
        
        System.out.println("\n=== Staff Users ===");

        // 显示工作人员(S开头)，按ID数字部分排序
        System.out.println("\n[Staff Members]");
        System.out.printf("%-10s %-15s %-25s %-15s %n",
                          "ID", "Username", "Email", "Phone");
        //跳不出來
        users.stream()
             .filter(User::isStaff)//筛选是员工的账号
             .sorted(Comparator.comparingInt(User::getUserID)) // 按数字部分排序
             .forEach(user -> printUserRow(user));

        
        
        
        System.out.println("\n[Customer Members]");
        System.out.printf("%-10s %-15s %-25s %-15s %-10s %-10s%n",
                          "ID", "Username", "Email", "Phone","Points","Credit(RM)");
        
        users.stream()
             .filter(user -> !user.isStaff())//筛选是员工的账号
             .sorted(Comparator.comparingInt(User::getUserID)) // 按数字部分排序
             .forEach(user -> printUserRow(user));
    }

        public void registerStaff(ArrayList<User> users) {
        System.out.println("\nIf don't want register just key 0 can be quit");
        System.out.println("\n=== Staff Registration ===");
        
        String email = system.getValidInput("Enter staff email: ", 
                                     "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$", 
                                     "Invalid email format");
        if(email == null){
            return;
        }
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email)) {
            System.out.println("This email is already registered.");
            return;
            }
        }
        
        
        String username = system.getValidInput("Enter staff username (4-20 word): ", 
                                        "^[a-zA-Z0-9]{4,20}$", 
                                        "Username must be 4-20 number or letter");
        if(username == null){
            return;
        }
        
        for (User u : users) {
            if (u.getUserName().equalsIgnoreCase(username)) {
            System.out.println("This username is already registered.");
            return;
            }
        }
        
        String password = system.getValidInput("Enter new password :", 
                                        //"^(?=.[0-9])(?=.[a-z])(?=.[A-Z])(?=.[!@#$%^&*]).{8,}$",
                                        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$",

                                        "Password must contain at least 8 characters, " +
                                        "including uppercase, lowercase, numbers and special characters (!@#$%^&*)");
        if(password == null){
            return;
        }
        for (User u : users) {
            if (u.getPassword().equalsIgnoreCase(password)) {
            System.out.println("This password is already registered.");
            return;
            }
        }
        
        String phone = system.getValidInput("Enter staff phone (e.g 012-3456789): ", 
                                     "^01[0-9]-\\d{7,8}$", 
                                     "Invalid phone number");
        if(phone == null){
            return;
        }
        
        for (User u : users) {
            if (u.getPhone().equals(phone)) {
            System.out.println("This phone number is already registered.");
            return;
            }
        }
        
        
        System.out.print("Enter age (18-100): ");
        int age = system.getIntInput(18, 100);
        
        
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        
        
        StaffDetail newStaff = new StaffDetail(email, username, password, phone, 
                                              system.generateNextID(true), age, address);
        users.add(newStaff);
        
        System.out.println("\nNew staff member registered successfully!");
        system.saveUsersToFile();
    }

    private void printUserRow(User user) {
    if (user.isStaff()) {
        System.out.printf("%-10s %-15s %-25s %-15s %n",
                user.getUserID(), user.getUserName(), user.getEmail(),
                user.getPhone());
    } 
    else{
            System.out.printf("%-10s %-15s %-25s %-15s %-10s %-10s%n",
                          user.getUserID(), user.getUserName(), user.getEmail(), user.getPhone()
                        ,user.getPoints(),user.getCreditBalance());
               
    }
}

    void deleteStaff(ArrayList<User> users) {
       
        System.out.println("\n[Staff Members]");
        System.out.printf("%-10s %-15s %-25s %-15s %n",
                          "ID", "Username", "Email", "Phone");
        //跳不出來
        users.stream()
             .filter(User::isStaff)//筛选是员工的账号
             .sorted(Comparator.comparingInt(User::getUserID)) // 按数字部分排序
             .forEach(user -> printUserRow(user));
        
        
        
    System.out.print("\nEnter the ID of the staff to delete (or 0 to cancel): ");
    int idToDelete = system.getIntInput(0, Integer.MAX_VALUE);

    if (idToDelete == 0) {
        System.out.println("Deletion cancelled.");
        return;
    }
    
     if (idToDelete == 1) {
        System.out.println("Bro no delete yourself le?");
        return;
    }

    // 找到并删除指定 staff
    User toRemove = null;
    for (User user : users) {
        if (user.getUserID() == idToDelete && user.isStaff()) {
            toRemove = user;
            break;
        }
    }

    if (toRemove != null) {
        users.remove(toRemove);
        System.out.println("Staff with ID " + idToDelete + " has been deleted successfully.");
        system.saveUsersToFile();
    } else {
        System.out.println("Staff with ID " + idToDelete + " not found.");
 }
}
}

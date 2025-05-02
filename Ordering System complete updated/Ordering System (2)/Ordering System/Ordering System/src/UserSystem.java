import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;


public class UserSystem {
    private final ArrayList<User> users = new ArrayList<>();
    private static List<Product> products = new ArrayList<>();

    Scanner scanner = new Scanner(System.in);
    private User currentUser = null;
    private Staff staff =null;
    
    public void startUserSystem(){
        
        loadUsersFromFile();
        
        // 检查员工账号，若为0，自动注册新员工账号
        if (getStaffCount() == 0) {
            System.out.println("\n=== SYSTEM INITIALIZATION ===");
            System.out.println("No staff accounts found. Creating first staff account.");
            registerFirstStaff();
        }
        
        showMainMenu();
    }

    //算总共有几个员工账号
    public int getStaffCount() {
        int count = 0;
        for (User user : users) {
            if (user.isStaff()) {
                count++;
            }
        }
        return count;
    }
    //算总共有几个顾客账号
    public int getCustomerCount() {
        int count = 0;
        for (User user : users) {
            if (!user.isStaff()) {
                count++;
            }
        }
        return count;
    }
    //系统打开后，自动注册第一个员工账号
    public void registerFirstStaff() {
        System.out.println("\n=== CREATE FIRST STAFF ACCOUNT ===");
        
        registerStaff();
        
        System.out.println("\nFirst staff account created successfully!");
        System.out.println("Please login with this account to manage the system.");
    }
    //主菜单
    public void showMainMenu() {
        while (true) {
            System.out.println("\n=== CompuMart Login System ===");
            System.out.println("1. Login");
            System.out.println("2. Register as Customer");
            
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = getIntInput(0, 2);

            switch (choice) {
                case 1 -> loginUser();
                case 2 -> registerCustomer();
                case 3 -> {
                    if (currentUser != null && currentUser.isStaff()) {
                        registerStaff();
                    }
                }
                case 0 -> {
                    System.out.println("Thank you for using CompuMart. Goodbye!");
                    
                    System.exit(0);
                }
                default -> {
                System.out.println("Invalid input, Please try again");
            }
            }
        }
    }
    //登入账号
    public void loginUser() {
        System.out.println("\n=== Login ===");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                System.out.println("\nLogin successful! Welcome, " + username + "!");
                if(currentUser.isStaff()){
                    showUserStaffMenu();
                }else{
                    showUserCustomerMenu();
                }
                return;
            }
        }
        System.out.println("Invalid username or password.");
    }
    //顾客注册账号
    public void registerCustomer() {
        System.out.println("\nIf don't want register just key 0 can be quit");
        System.out.println("\n=== Customer Registration ===");
        
        String email = getValidInput("Enter customer email: ", 
                                     "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$", 
                                     "Invalid email format");
        if(email == null){
            return;
        }
        
        String username = getValidInput("Enter customer username (4-20 word): ", 
                                        "^[a-zA-Z0-9]{4,20}$", 
                                        "Username must be 4-20 number or letter");
       if(username == null){
            return;
        }
        
        String password = getValidInput("Enter new password: ", 
                                        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$",
                                        "Password must contain at least 8 characters, " +
                                        "including uppercase, lowercase, numbers and special characters (!@#$%^&*)");
       if(password == null){
            return;
        }
        
        String phone = getValidInput("Enter customer phone (e.g 012-3456789): ", 
                                     "^01[0-9]-\\d{7,8}$", 
                                     "Invalid phone number");
        if(phone == null){
            return;
        }
        
        
        System.out.print("Enter age: ");
        int age = getIntInput(1, 150);
        
        
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        
        
        CustomerDetail newCustomer = new CustomerDetail(email, username, password, phone, 
                                                      generateNextID(false), age, address);
        users.add(newCustomer);
        saveUsersToFile();
        System.out.println("\nRegistration successful! Your user ID is: " + newCustomer.getUserID());
    }
    
    //注册员工账号
    public void registerStaff() {
        System.out.println("\nIf don't want register just key 0 can be quit");
        System.out.println("\n=== Staff Registration ===");
        
        String email = getValidInput("Enter staff email: ", 
                                     "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$", 
                                     "Invalid email format");
        if(email == null){
            return;
        }
        
        String username = getValidInput("Enter staff username (4-20 word): ", 
                                        "^[a-zA-Z0-9]{4,20}$", 
                                        "Username must be 4-20 number or letter");
        if(username == null){
            return;
        }
        
        String password = getValidInput("Enter new password:", 
                                        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$",
                                        "Password must contain at least 8 characters, " +
                                        "including uppercase, lowercase, numbers and special characters (!@#$%^&*)");
        if(password == null){
            return;
        }
        
        String phone = getValidInput("Enter staff phone (e.g 012-3456789): ", 
                                     "^01[0-9]-\\d{7,8}$", 
                                     "Invalid phone number");
        if(phone == null){
            return;
        }
        
        
        System.out.print("Enter age (18-100): ");
        int age = getIntInput(18, 100);
        
        
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        
        
        StaffDetail newStaff = new StaffDetail(email, username, password, phone, 
                                              generateNextID(true), age, address);
        users.add(newStaff);
        
        System.out.println("\nNew staff member registered successfully!");
        saveUsersToFile();
    }
    
    //用户菜单
    public void showUserCustomerMenu() {
       
        Cart cart = new Cart(currentUser,this,products);
        
        boolean loop=true;
        
        while (currentUser != null) {
            loop=true;
            saveUsersToFile();
            products.clear();
            loadProducts();
            cart.getItems().clear();
            cart.loadCart();
            System.out.println("\n=== User Menu ===");
            System.out.println("1. View Profile");
            System.out.println("2. Menu");
            System.out.println("3. Cart");
            System.out.println("4. Top-up");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");
            
            int choice = getIntInput(0,4);

            switch (choice) {
               
                case 1 -> viewProfile();
                case 2 -> {     
                                while (loop) {
                                ArrayList<Integer> canBeChoice = new ArrayList<>();

                                System.out.println("1. Computer");
                                System.out.println("2. Keyboard");
                                System.out.println("3. CPU");
                                System.out.println("4. Back");
                                System.out.println("Select Product Type:");
                                int choiceOne = scanner.nextInt();
                                scanner.nextLine(); // consume newline


                                String category = "";
                                switch (choiceOne) {
                                    case 1 -> category = "Computer";
                                    case 2 -> category = "Keyboard";
                                    case 3 -> category = "CPU";
                                    case 4 -> { loop=false;
                                                break;}
                                    default -> {
                                        System.out.println("Invalid choice.");
                                       
                                    }
                                }
                                if(category != ""){
                                System.out.println("\n=== Products in Category: " + category + " ===");
                                showProductOutput(category, canBeChoice);

                                
                                    System.out.print("\nEnter the product ID or name to cart (0 to go back): ");
                                    String input = scanner.nextLine();

                                    if (input.equals("0")) {
                                        System.out.println("Returning...");
                                        break;
                                    }
                                    
                                    cart.handleProductSelection(canBeChoice,category,input);
                                }
                }
                
                }//Product
                case 3 -> cart.cartPage();//Cart 
                case 4 -> {
                    new TopUpCreditPayment(currentUser, this).topUp();
                }
                case 0 -> logout();
                default -> {
                System.out.println("Invalid input, Please try again");
            }
            }
        saveUsersToFile();
        }
    }
    
    public void showUserStaffMenu() {
        while (currentUser != null) {
            Staff staff =new Staff(this,products);
            products.clear();
            loadProducts();
            
           
            
            System.out.println("\n=== Staff Menu ===");
            System.out.println("1. View Profile");
            System.out.println("2. View All Users");
            System.out.println("3. Register Staff");
            System.out.println("4. Product");

            
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");
            
            int choice = getIntInput(0,4);

            switch (choice) {
               
                case 1 -> viewProfile();
                case 2 -> staff.viewAllUsers(users);
                case 3 -> {
                    if (
                        currentUser.getUserID()==1 ) {
                        System.out.println("\n=== Staff Registration Menu ===");
                        System.out.println("1. Add Staff");
                        System.out.println("2. Delete Staff");
                        System.out.println("0. Back");
                        System.out.print("Enter your choice: ");

                        int subChoice = getIntInput(0, 2);

                        switch (subChoice) {
                            case 1 -> staff.registerStaff(users);
                            case 2 -> staff.deleteStaff(users);
                            case 0 -> System.out.println("Returning to Staff Menu...");
                            default -> System.out.println("Invalid input, Please try again");
                        }
                    } else {
                        System.out.println("No permission");
                    }
                }
                
                case 4 ->{
                        System.out.println("\n=== Product Registration Menu ===");
                        System.out.println("1. Add New Product");
                        System.out.println("2. View Product");
                        
                        System.out.println("3. Back");
                        System.out.println("Enter your option :");
                        int subChoice = getIntInput(1, 3);
                        
                        

                        switch (subChoice) {
                            case 1 -> staff.addNewProductOption();
                            case 2 -> staff.viewProductOption();
                           
                            case 3 -> System.out.println("Returning to Staff Menu...");
                           
                            default -> System.out.println("Invalid input, Please try again");
                        }
                        }
                case 0 -> logout();
                default -> {
                System.out.println("Invalid input, Please try again");
            }
            }
        saveUsersToFile();
        }
    }
    //个人资料
    public void viewProfile() {
        System.out.println("\n=== Your Profile ===");
        System.out.println("User ID: " + currentUser.getUserID());
        System.out.println("Username: " + currentUser.getUserName());
        System.out.println("Email: " + currentUser.getEmail());
        System.out.println("Phone: " + currentUser.getPhone());
        System.out.println("Age: " + currentUser.getAge());
        System.out.println("Address: " + currentUser.getAddress());
        if(!currentUser.isStaff()){
            System.out.println("Points: " + currentUser.getPoints());
            System.out.println("Credit: RM" + currentUser.getCreditBalance());
        }
        System.out.println("Account Type: " + (currentUser.isStaff() ? "Staff" : "Customer"));
        
        System.out.println("\n================================");
        System.out.println("1. Edit profile");
        System.out.println("2. Return menu");
        int choice = getIntInput(1,2);
        switch(choice) {
            case 1 ->{
                editProfile();
            }
            case 2 ->{
                
            }

            default ->{
                System.out.println("Invalid input, Please try again");
            }
        }
        
    }
    //编辑个人资料
    public void editProfile() {
        System.out.println("\n=== Edit Profile ===");
        System.out.println("1. Change Email");
        System.out.println("2. Change Password");
        System.out.println("3. Change Phone");
        System.out.println("4. Change Age");
        System.out.println("5. Change Address");
        System.out.println("0. Back to Menu");
        System.out.print("Enter your choice: ");
        
        int choice = getIntInput(0, 5);
        
        switch (choice) {
            case 1 -> {
                String newEmail = getValidInput("Enter new email: ", 
                                                "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$", 
                                                "Invalid email format");
                currentUser.setEmail(newEmail);
                System.out.println("Email updated successfully.");
                
            }
            case 2 -> {
                String newPassword = getValidInput("Enter new password:", 
                                        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$",
                                        "Password must contain at least 8 characters, " +
                                        "including uppercase, lowercase, numbers and special characters (!@#$%^&*)");
                currentUser.setPassword(newPassword);
                System.out.println("Password updated successfully.");
                
            }
            case 3 -> {
                String newPhone = getValidInput("Enter new phone: ", 
                                                "^01[0-9]-\\d{7,8}$", 
                                                "Invalid phone number");
                currentUser.setPhone(newPhone);
                System.out.println("Phone updated successfully.");
                
            }
            case 4 -> {
                int newAge;
                System.out.print("Enter new age: ");
                if(currentUser.isStaff()){
                newAge = getIntInput(18, 100);
                }else{
                newAge = getIntInput(1, 100);
                }
                currentUser.setAge(newAge);
                System.out.println("Age updated successfully.");
                
            }
            case 5 -> {
                System.out.print("Enter new address: ");
                String newAddress = scanner.nextLine();
                currentUser.setAddress(newAddress);
                System.out.println("Address updated successfully.");
                
            }
            case 0 -> {
            }
            default -> {
                System.out.println("Invalid input, Please try again");
            }
            
        }
        
            saveUsersToFile();   
    }


    public void viewAllUsers() {
    System.out.println("\n=== All Users ===");
    
    // 显示工作人员(S开头)，按ID数字部分排序
    System.out.println("\n[Staff Members]");
    System.out.printf("%-10s %-15s %-25s %-15s %-5s %-70s%n",
                      "ID", "Username", "Email", "Phone", "Age", "Address");
    
    users.stream()
         .filter(User::isStaff)//筛选是员工的账号
         .sorted(Comparator.comparingInt(User::getUserID)) // 按数字部分排序
         .forEach(user -> printUserRow(user));

    // 显示顾客(C开头)，按ID数字部分排序
    System.out.println("\n[Customers]");
    System.out.printf("%-10s %-15s %-25s %-15s %-5s %-70s %-10s %-10s%n",
                    "ID", "Username", "Email", "Phone", "Age", "Address", "Points", "Credit(RM)");
    
    users.stream()
         .filter(user -> !user.isStaff())//筛选是顾客的账号
         .sorted(Comparator.comparingInt(User::getUserID)) // 按数字部分排序
         .forEach(user -> printUserRow(user));
}

    //显示用户账号资料
    public void printUserRow(User user) {
    if(currentUser.isStaff()){
    System.out.printf("%-10s %-15s %-25s %-15s %-5d %-70s%n",
                    user.getFormattedID(), // 显示格式化ID (S001/C001)
                    user.getUserName(),
                    user.getEmail(),
                    user.getPhone(),
                    user.getAge(),
                    user.getAddress());
                    
    }
    if(!currentUser.isStaff()){
    System.out.printf("%-10s %-15s %-25s %-15s %-5d %-70s %-10d %-10s%n",
                    user.getFormattedID(), // 显示格式化ID (S001/C001)
                    user.getUserName(),
                    user.getEmail(),
                    user.getPhone(),
                    user.getAge(),
                    user.getAddress(),
                    user.getPoints(),
                    user.getCreditBalance());
        }
    }

    private void logout() {
        
        currentUser = null;
        System.out.println("Logged out successfully.");
    }

    // 捕抓一切需要输入“选择”的地方；如果输入错误的int，会跳出错误讯息
    public int getIntInput(int min, int max) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.printf("Please enter a number between %d and %d: ", min, max);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    
    //捕抓一切需要输入“讯息”的地方；如果输入错误的double，会跳出错误讯息
    public String getValidInput(String prompt, String regex, String errorMsg) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input.equals("0")){
            return null;
            }
            if (input.matches(regex)) {
                return input;
            }
            System.out.println(errorMsg);
        }
    }
    
    //储存用户资料
    public void saveUsersToFile() {
        try {
            // 保存 staff 数据到 staff.txt
            BufferedWriter staffWriter = new BufferedWriter(new FileWriter("staff.txt"));
            for (User user : users) {
                if (user.isStaff()) {
                    staffWriter.write(user.toTextFormat());
                    staffWriter.newLine();
                    }
                staffWriter.flush();
                }
                
            }catch (IOException e) {
                System.out.println("Error saving data: " + e.getMessage());
                return;
            }
        
        // 保存 customer 数据到 customer.txt
        try {
            BufferedWriter customerWriter = new BufferedWriter(new FileWriter("customer.txt"));
            for (User user : users) {
                if (!user.isStaff()) {
                    customerWriter.write(user.toTextFormat());
                    customerWriter.newLine();
                    }
                customerWriter.flush();
                }
                
               
            }catch (IOException e) {
                System.out.println("Error saving data: " + e.getMessage());
    }
}

    //加载用户资料
    public void loadUsersFromFile() {
    

    // 加载 staff 数据
    File staffFile = new File("staff.txt");
    if (staffFile.exists()) {
        try (BufferedReader reader = new BufferedReader(new FileReader("staff.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromTextFormat(line);
                if (user != null) {
                    users.add(user);
                }
            }
            //System.out.println("Loaded " + getStaffCount() + " staff members from staff.txt");
        } catch (IOException e) {
            System.out.println("Error loading staff data: " + e.getMessage());
        }
    }

    // 加载 customer 数据
    File customerFile = new File("customer.txt");
    if (customerFile.exists()) {
        try (BufferedReader reader = new BufferedReader(new FileReader("customer.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromTextFormat(line);
                if (user != null) {
                    users.add(user);
                }
            }
            //System.out.println("Loaded " + getCustomerCount() + " customers from customer.txt");
        } catch (IOException e) {
            System.out.println("Error loading customer data: " + e.getMessage());
        }
    }
}
    
    public int generateNextID(boolean isStaff) {
        if (users.isEmpty()) return 1;

        // 如果是 staff，查找最大的 staff ID
        if (isStaff) {
        return users.stream()
                    .filter(User::isStaff)
                    .mapToInt(User::getUserID)
                    .max()
                    .orElse(0) + 1;
        }
        // 如果是 customer，查找最大的 customer ID
        else {
        return users.stream()
                    .filter(user -> !user.isStaff())
                    .mapToInt(User::getUserID)
                    .max()
                    .orElse(0) + 1;
        }
    }
    
    public void loadProducts() {
            try (BufferedReader br = new BufferedReader(new FileReader("products.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    String type = parts[0];
                    switch (type) {
                        case "Computer":
                            products.add(new Computer(parts[1], parts[2], Double.parseDouble(parts[3]), Integer.parseInt(parts[4]), parts[5], Integer.parseInt(parts[6])));
                            break;
                        case "Keyboard":
                            products.add(new Keyboard(parts[1], parts[2], Double.parseDouble(parts[3]), Integer.parseInt(parts[4]), Boolean.parseBoolean(parts[5])));
                            break;
                        case "CPU":
                            products.add(new CPU(parts[1], parts[2], Double.parseDouble(parts[3]), Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), Integer.parseInt(parts[6])));
                            break;
                    }
                }
                    
            } catch (IOException e) {
                System.out.println("No existing product file found. Starting fresh!");
            }
            
            
        }

            public void saveProducts() {
                try (PrintWriter pw = new PrintWriter(new FileWriter("products.txt"))) {
                    for (Product p : products) {
                        pw.println(p.toDataString());
                    }
                    
                } catch (IOException e) {
                    System.out.println("Failed to save products.");
                }
            }       
            
            
    public void showProductOutput(String category, List<Integer> canBeChoice) {
        boolean found = false;

        switch (category.toLowerCase()) {
            case "cpu":
                System.out.printf("\n%-10s | %-35s | %-10s | %-7s | %-5s | %-7s\n", 
                                  "ProductID", "Name", "Price(RM)", "Stock", "Cores", "Threads");
                System.out.println("--------------------------------------------------------------------------------------");
                break;

            case "keyboard":
                System.out.printf("\n%-10s | %-35s | %-10s | %-7s | %-12s\n", 
                                  "ProductID", "Name", "Price(RM)", "Stock", "Mechanical");
                System.out.println("--------------------------------------------------------------------------------------");
                break;

            case "computer":
                System.out.printf("\n%-10s | %-35s | %-10s | %-7s | %-15s | %-10s\n", 
                                  "ProductID", "Name", "Price(RM)", "Stock", "Processor", "RAM");
                System.out.println("--------------------------------------------------------------------------------------");
                break;

            default:
                System.out.println("Unknown category.");
                return;
        }

        for (Product p : products) {
            if (p.getCategory().equalsIgnoreCase(category)) {
                System.out.println(p);
                int id = Integer.parseInt(p.getProductId());
                canBeChoice.add(id);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No products found in this category.");
        }
}

    

    
    
    
    
    
    
}


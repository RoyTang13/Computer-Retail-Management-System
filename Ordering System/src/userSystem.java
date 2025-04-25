import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;


public class userSystem {
    private final ArrayList<User> users = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    private User currentUser = null;
    
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
                showUserMenu();
                return;
            }
        }
        System.out.println("Invalid username or password.");
    }
    //顾客注册账号
    public void registerCustomer() {
        System.out.println("\n=== Customer Registration ===");
        
        String email = getValidInput("Enter customer email: ", 
                                     "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$", 
                                     "Invalid email format");
        String username = getValidInput("Enter customer username (4-20 word): ", 
                                        "^[a-zA-Z0-9]{4,20}$", 
                                        "Username must be 4-20 number or letter");
        String password = getValidInput("Enter new password: ", 
                                        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$",
                                        "Password must contain at least 8 characters, " +
                                        "including uppercase, lowercase, numbers and special characters (!@#$%^&*)");
        String phone = getValidInput("Enter customer phone (e.g 012-3456789): ", 
                                     "^\\+?[0-9][-. ]?([0-9][-. ]?){8,14}$", 
                                     "Invalid phone number");
        
        System.out.print("Enter age: ");
        int age = getIntInput(1, 150);
        
        
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        
        
        customerDetail newCustomer = new customerDetail(email, username, password, phone, 
                                                      generateNextID(false), age, address);
        users.add(newCustomer);
        
        System.out.println("\nRegistration successful! Your user ID is: " + newCustomer.getUserID());
    }
    //注册员工账号
    public void registerStaff() {
        System.out.println("\n=== Staff Registration ===");
        
        String email = getValidInput("Enter staff email: ", 
                                     "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$", 
                                     "Invalid email format");
        String username = getValidInput("Enter staff username (4-20 word): ", 
                                        "^[a-zA-Z0-9]{4,20}$", 
                                        "Username must be 4-20 number or letter");
        String password = getValidInput("Enter new password:", 
                                        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$",
                                        "Password must contain at least 8 characters, " +
                                        "including uppercase, lowercase, numbers and special characters (!@#$%^&*)");
        String phone = getValidInput("Enter staff phone (e.g 012-3456789): ", 
                                     "^\\+?[0-9][-. ]?([0-9][-. ]?){8,14}$", 
                                     "Invalid phone number");
        
        System.out.print("Enter age (18-100): ");
        int age = getIntInput(18, 100);
        
        
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        
        
        staffDetail newStaff = new staffDetail(email, username, password, phone, 
                                              generateNextID(true), age, address);
        users.add(newStaff);
        
        System.out.println("\nNew staff member registered successfully!");
        saveUsersToFile();
    }
    //用户菜单
    public void showUserMenu() {
        while (currentUser != null) {
            System.out.println("\n=== User Menu ===");
            System.out.println("1. View Profile");
            System.out.println("2. Menu");
            System.out.println("3. Cart");
            System.out.println("4. Make Payment");
            if (currentUser.isStaff()) {
                System.out.println("5. Points System");
                System.out.println("6. View All Users");
                System.out.println("7. Register New Staff");
                System.out.println("8. Manage Customer Points");
            }
            
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");
            
            int maxChoice = currentUser.isStaff() ? 8 : 4;
            int choice = getIntInput(0, maxChoice);

            switch (choice) {
                case 1 -> viewProfile();
                case 2 -> editProfile();//change this
                case 3 -> editProfile();//change this
                case 4 -> editProfile();//change this
                case 5 ->{
                    if (currentUser.isStaff()) {
                        managePoints();
                    }
                    
                    }
                case 6 -> {
                    if (currentUser.isStaff()) {
                        viewAllUsers();
                    }
                }
                case 7 -> {
                    if (currentUser.isStaff()) {
                        registerStaff();
                    }
                }
                case 8 -> {
                    if (currentUser.isStaff()) {
                        manageCustomerPoints();
                    }
                }
                case 0 -> logout();
                default -> {
                System.out.println("Invalid input, Please try again");
            }
            }
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
        System.out.println("Points: " + currentUser.getPoints());
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
        
        int choice = getIntInput(1, 6);
        
        switch (choice) {
            case 1 -> {
                String newEmail = getValidInput("Enter new email: ", 
                                                "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$", 
                                                "Invalid email format");
                currentUser.setEmail(newEmail);
                System.out.println("Email updated successfully.");
                saveUsersToFile();
            }
            case 2 -> {
                String newPassword = getValidInput("Enter new password:", 
                                        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$",
                                        "Password must contain at least 8 characters, " +
                                        "including uppercase, lowercase, numbers and special characters (!@#$%^&*)");
                currentUser.setPassword(newPassword);
                System.out.println("Password updated successfully.");
                saveUsersToFile();
            }
            case 3 -> {
                String newPhone = getValidInput("Enter new phone: ", 
                                                "^\\+?[0-9][-. ]?([0-9][-. ]?){8,14}$", 
                                                "Invalid phone number");
                currentUser.setPhone(newPhone);
                System.out.println("Phone updated successfully.");
                saveUsersToFile();
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
                saveUsersToFile();
            }
            case 5 -> {
                System.out.print("Enter new address: ");
                String newAddress = scanner.nextLine();
                currentUser.setAddress(newAddress);
                System.out.println("Address updated successfully.");
                saveUsersToFile();
            }
            case 0 -> {
            }
            default -> {
                System.out.println("Invalid input, Please try again");
            }
            
        }
        
            saveUsersToFile();   
    }
    //编辑个人POINT（让员工测试）
    public void managePoints() {
        System.out.println("\n=== Points System ===");
        System.out.println("Current Points: " + currentUser.getPoints());
        System.out.println("1. Earn Points");
        System.out.println("2. Redeem Points");
        System.out.println("0. Back to Menu");
        System.out.print("Enter your choice: ");
        
        int choice = getIntInput(1, 3);
        
        switch (choice) {
            case 1 -> {
                System.out.print("Enter points to earn: ");
                int earnPoints = getIntInput(1, Integer.MAX_VALUE);
                currentUser.addPoints(earnPoints);
                System.out.println(earnPoints + " points added. Total: " + currentUser.getPoints());
                saveUsersToFile();
            }
                
            case 2 -> {
                System.out.print("Enter points to redeem: ");
                int redeemPoints = getIntInput(1, Integer.MAX_VALUE);
                if (currentUser.deductPoints(redeemPoints)) {
                    System.out.println(redeemPoints + " points redeemed. Remaining: " + currentUser.getPoints());
                    saveUsersToFile();
                } else {
                    System.out.println("Invalid points. You only have " + currentUser.getPoints() + " points.");
                }
            }
            case 0 -> {
            }
            default -> {
                System.out.println("Invalid input, Please try again");
            }
        }
        // 返回菜单
    }
    //编辑顾客POINT
    public void manageCustomerPoints() {
        
        //Check customer count
        if(getCustomerCount() == 0){
            System.out.println("No customer found");
            return;
        }
        
        System.out.println("\n=== Manage Customer Points ===");
        System.out.println("1. Add Points to Customer");
        System.out.println("2. Deduct Points from Customer");
        System.out.println("0. Back to Menu");
        System.out.print("Enter your choice: ");
        
        int choice = getIntInput(0, 2);
        
        switch (choice) {
            case 1 -> addPointsToCustomer();
            case 2 -> deductPointsFromCustomer();
            case 0 -> {
               
            }
            default -> {
                System.out.println("Invalid input, Please try again");
            }
        }
        
    }
    //选择顾客
    public User findCustomerByID(int customerID){
        for (User user : users){
            if (user.getUserID() == customerID && !user.isStaff()){
                return user;
            }
        }
        return null;
    }
    //增加顾客的POINT（员工用）
    public void addPointsToCustomer() {
        System.out.println("\n=== Add Points to Customer ===");
        viewAllCustomers();
        
        System.out.print("\nEnter customer ID to add points: ");
        int customerId = getIntInput(1, Integer.MAX_VALUE);
        
        User customer = findCustomerByID(customerId);
        if (customer == null || customer.isStaff()) {
            System.out.println("Invalid customer ID or ID belongs to a staff member.");
            return;
        }
        
        System.out.print("Enter points to add: ");
        int pointsToAdd = getIntInput(1, Integer.MAX_VALUE);
        
        customer.addPoints(pointsToAdd);
        saveUsersToFile();
        System.out.println(pointsToAdd + " points added to customer " + customer.getUserName() + 
                          ". New total: " + customer.getPoints());
    }
    //减少顾客的POINT（员工用）
    public void deductPointsFromCustomer() {
        System.out.println("\n=== Deduct Points from Customer ===");
        viewAllCustomers();
        
        System.out.print("\nEnter customer ID to deduct points: ");
        int customerId = getIntInput(1, Integer.MAX_VALUE);
        
        User customer = findCustomerByID(customerId);
        if (customer == null || customer.isStaff()) {
            System.out.println("Invalid customer ID or ID belongs to a staff member.");
            return;
        }
        
        System.out.print("Enter points to deduct: ");
        int pointsToDeduct = getIntInput(1, Integer.MAX_VALUE);
        
        if (customer.deductPoints(pointsToDeduct)) {
            saveUsersToFile();
            System.out.println(pointsToDeduct + " points deducted from customer " + 
                              customer.getUserName() + ". Remaining: " + customer.getPoints());
        } else {
            System.out.println("Failed to deduct points. Customer only has " + 
                              customer.getPoints() + " points.");
        }
    }
    //显示顾客（只有顾客）
    private void viewAllCustomers() {
        System.out.println("\n=== All Customers ===");
        System.out.printf("%-10s %-15s %-25s %-10s%n", 
                          "ID", "Username", "Email", "Points");
        
        for (User user : users) {
            if (!user.isStaff()) {
                System.out.printf("%-10d %-15s %-25s %-10d%n",
                                 user.getUserID(), user.getUserName(), 
                                 user.getEmail(), user.getPoints());
            }
        }
    }

    public void viewAllUsers() {
    System.out.println("\n=== All Users ===");
    
    // 显示工作人员(S开头)，按ID数字部分排序
    System.out.println("\n[Staff Members]");
    System.out.printf("%-10s %-15s %-25s %-15s %-5s %-70s %-10s%n",
                      "ID", "Username", "Email", "Phone", "Age", "Address", "Points");
    
    users.stream()
         .filter(User::isStaff)//筛选是员工的账号
         .sorted(Comparator.comparingInt(User::getUserID)) // 按数字部分排序
         .forEach(user -> printUserRow(user));

    // 显示顾客(C开头)，按ID数字部分排序
    System.out.println("\n[Customers]");
    System.out.printf("%-10s %-15s %-25s %-15s %-5s %-70s %-10s%n",
                    "ID", "Username", "Email", "Phone", "Age", "Address", "Points");
    
    users.stream()
         .filter(user -> !user.isStaff())//筛选是顾客的账号
         .sorted(Comparator.comparingInt(User::getUserID)) // 按数字部分排序
         .forEach(user -> printUserRow(user));
}

    //显示用户账号资料
    public void printUserRow(User user) {
    System.out.printf("%-10s %-15s %-25s %-15s %-5d %-70s %-10d%n",
                    user.getFormattedID(), // 显示格式化ID (S001/C001)
                    user.getUserName(),
                    user.getEmail(),
                    user.getPhone(),
                    user.getAge(),
                    user.getAddress(),
                    user.getPoints());
    }

    private void logout() {
        
        currentUser = null;
        System.out.println("Logged out successfully.");
    }

    // 捕抓一切需要输入“选择”的地方；如果输入错误的int，会跳出错误讯息
    public static int getIntInput(int min, int max) {
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
    //捕抓一切需要输入“讯息”的地方；如果输入错误的String，会跳出错误讯息
    public static String getValidInput(String prompt, String regex, String errorMsg) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
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
                
                System.out.println("User data saved to staff.txt and customer.txt");
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
}



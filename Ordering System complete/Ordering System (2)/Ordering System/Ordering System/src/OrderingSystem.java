
//import java.util.Scanner;

// Main Class for Testing
public class OrderingSystem {
    public static void main(String[] args) {

          UserSystem startUser = new UserSystem();
          startUser.startUserSystem();
          
//          
//          ProductManager pm = new ProductManager();
//        Scanner sc = new Scanner(System.in);
//        pm.loadProducts();
//
//        while (true) {
//            System.out.println("\n=== Main Menu ===");
//            System.out.println("1. View Products");
//            System.out.println("2. Search Product");
//            System.out.println("3. Admin Login");
//            System.out.println("4. Exit");
//            System.out.print("Enter your choice: ");
//            int choice = sc.nextInt();
//            sc.nextLine(); // Consume newline
//
//            switch (choice) {
//                case 1:
//                    System.out.println("\n=== All Products ===");
//                    for (Product p : pm.getProducts()) {
//                        System.out.println(p);
//                    }
//                    break;
//                case 2:
//                    System.out.print("Enter Product ID to search: ");
//                    String id = sc.nextLine();
//                    Product p = pm.findProductById(id);
//                    if (p != null) {
//                        System.out.println("Found: " + p);
//                    } else {
//                        System.out.println("Product not found.");
//                    }
//                    break;
//                case 3:
//                    System.out.print("Enter admin password: ");
//                    String pass = sc.nextLine();
//                    if (pass.equals("admin123")) {
//                        adminMenu(pm, sc);
//                    } else {
//                        System.out.println("Invalid password!");
//                    }
//                    break;
//                case 4:
//                    pm.saveProducts();
//                    System.out.println("Goodbye!");
//                    return;
//                default:
//                    System.out.println("Invalid choice!");
//            }
//        }
//    }
//
//    private static void adminMenu(ProductManager pm, Scanner sc) {
//        while (true) {
//            System.out.println("\n=== Admin Menu ===");
//            System.out.println("1. Add Product");
//            System.out.println("2. Remove Product");
//            System.out.println("3. Save & Return");
//            System.out.print("Enter your choice: ");
//            int choice = sc.nextInt();
//            sc.nextLine(); // consume newline
//
//            switch (choice) {
//                case 1:
//                    System.out.println("Select Product Type:");
//                    System.out.println("1. Computer");
//                    System.out.println("2. Keyboard");
//                    System.out.println("3. CPU");
//                    int type = sc.nextInt();
//                    sc.nextLine();
//
//                    System.out.print("Product ID: ");
//                    String id = sc.nextLine();
//                    System.out.print("Name: ");
//                    String name = sc.nextLine();
//                    System.out.print("Price: ");
//                    double price = sc.nextDouble();
//                    System.out.print("Quantity: ");
//                    int qty = sc.nextInt();
//                    sc.nextLine();
//
//                    switch (type) {
//                        case 1:
//                            System.out.print("Processor: ");
//                            String processor = sc.nextLine();
//                            System.out.print("RAM Size (GB): ");
//                            int ram = sc.nextInt();
//                            pm.addProduct(new Computer(id, name, price, qty, processor, ram));
//                            break;
//                        case 2:
//                            System.out.print("Is Mechanical (true/false): ");
//                            boolean mech = sc.nextBoolean();
//                            pm.addProduct(new Keyboard(id, name, price, qty, mech));
//                            break;
//                        case 3:
//                            System.out.print("Cores: ");
//                            int cores = sc.nextInt();
//                            System.out.print("Threads: ");
//                            int threads = sc.nextInt();
//                            pm.addProduct(new CPU(id, name, price, qty, cores, threads));
//                            break;
//                        default:
//                            System.out.println("Invalid type!");
//                    }
//                    break;
//                case 2:
//                    System.out.print("Enter Product ID to remove: ");
//                    String removeId = sc.nextLine();
//                    pm.removeProduct(removeId);
//                    break;
//                case 3:
//                    pm.saveProducts();
//                    return;
//                default:
//                    System.out.println("Invalid choice!");
//            }
//        }
//
}
}
    
 
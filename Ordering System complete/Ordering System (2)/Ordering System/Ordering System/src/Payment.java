
                                } else {

                                    System.out.println("\u001B[31mInvalid card number. Must be 16 digits.\u001B[0m");

                                }

                            } while (!isValid);

                            System.out.println("Enter Expiry Date MM/YY: ");

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

                            paymentMethod = new BankPayment(cardNumber, expiryDate, cvv);

                            break;

                        case 2:

                            paymentMethod = new PointPayment(customer);

                            break;

                        case 3:

                            paymentMethod = new TopUpCreditPayment(customer, system);

                            break;

                            

                        case 0:

                           

                           return false;

                        default:

                            System.out.println("\u001B[31mInvalid choice. Please try again.\u001B[0m");

                            continue;

                    }

                }



                // 🛠 Try processing

                if (paymentMethod.processPayment(amount)) {

                    if (!(paymentMethod instanceof PointPayment)) {

                        customer.addPoints((int) amount);

                    }

                    saveOrder();

                    generateInvoice();

                    break; // ✅ Success

                } else {

                    System.out.println("\u001B[31mPayment failed. Please try another method.\u001B[0m");

                    paymentMethod = null; // ❗ Reset payment to force re-choose

                }

            }

            return true;

        }



        public Payment getPaymentMethod() {

            return paymentMethod;

        }



        private void generateInvoice() {

        // 📅 Show date

        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formattedDateTime = now.format(formatter);

        

        

        

              System.out.println();

        System.out.println("=".repeat(80));

        System.out.printf("%40s\n", "RECEIPT");

        System.out.println("=".repeat(80));

        System.out.printf("%-20s %s\n", "Company:", "CompuMart");

        System.out.printf("%-20s %s\n", "Customer Name:", customer.getUserName());

        System.out.printf("%-20s %s\n", "Purchase Date:", formattedDateTime);

        System.out.println("-".repeat(80));

        System.out.printf("%-30s %10s %15s %15s\n", "Product Name", "Quantity", "Unit Price", "Total Price");

        System.out.println("-".repeat(80));



        for (CartItem item : products) {

            System.out.printf("%-30s %10d %15.2f %15.2f\n",

                    item.getProductName(),

                    item.getQuantity(),

                    item.getUnitPrice(),

                    item.getTotalAmount());

        }



        System.out.println("-".repeat(80));

        System.out.printf("%-20s %s\n", "Payment Method:", paymentMethod.getPaymentMethod());

        System.out.printf("%-20s RM%.2f\n", "Total Price:", amount);



        // Optional: If Payment is PointPayment, show remaining points

        if (paymentMethod instanceof PointPayment) {

            PointPayment pointPayment = (PointPayment) paymentMethod;

            System.out.printf("%-20s %d points\n", "Point Balance:", customer.getPoints());

        }

        

        // Optional: If Payment is TopUpCreditPayment, show remaining balance

        if (paymentMethod instanceof TopUpCreditPayment) {

            TopUpCreditPayment creditBalance = (TopUpCreditPayment) paymentMethod;

            System.out.printf("%-20s RM%.2f \n", "Credit Balance:", customer.getCreditBalance());

        }



        System.out.println("=".repeat(80));

        System.out.printf("%40s\n", "Thank you for shopping with us!");

        System.out.println("=".repeat(80));  



            

        }



        

        public double getAmount() {

        return amount;

        }

        

        private double calculateTotalAmount() {

        double total = 0.0;

         for (CartItem item : products) {

        total += item.getTotalAmount();

        }

        return total;

}



    private void saveOrder() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("OrderHistory.txt", true))) {

        writer.write("OrderID: " + this.orderId);

        writer.newLine();

        writer.write("Customer: " + customer.getUserName());

        writer.newLine();

        writer.write("Payment Method: " + paymentMethod.getPaymentMethod()); 

        writer.newLine();

        writer.write("Total Amount: " + this.amount);

        writer.newLine();

        writer.write("Products:");

        writer.newLine();

        for (CartItem item : products) {

            writer.write("- " + item.getProductName() + ", Qty: " + item.getQuantity() + ", UnitPrice: " + item.getUnitPrice());

            writer.newLine();

        }

        writer.write("-----");

        writer.newLine();

    } catch (IOException e) {

        System.out.println("Error saving order: " + e.getMessage());

    }



    // Remove from cart.txt

    try {

        // Read all lines from cart

        List<String> cartLines = Files.readAllLines(Paths.get("cart.txt"));

        

        // Filter out lines for this customer's purchased products

        List<String> newCartLines = new ArrayList<>();

        for (String line : cartLines) {

            String[] parts = line.split(",");

            if (parts.length >= 2) {

                String cartCustomer = parts[0].trim();

                String cartProduct = parts[1].trim();

                

                // Keep line if:

                // 1. Not our customer OR

                // 2. Our customer but product not in this order

                if (!cartCustomer.equals(customer.getUserName()) || 

                    products.stream().noneMatch(p -> p.getProductName().equals(cartProduct))) {

                    newCartLines.add(line);

                }

            }

        }

        

        // Write back to cart.txt

        Files.write(Paths.get("cart.txt"), newCartLines);

        

    } catch (IOException e) {

        System.out.println("Error updating cart: " + e.getMessage());

    }

    }

        

        }

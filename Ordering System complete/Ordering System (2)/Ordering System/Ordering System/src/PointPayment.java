
class PointPayment implements Payment {
    private User customer;

    public PointPayment(User customer) {
        this.customer = customer;
    }
    @Override
    public boolean processPayment(double amount) {
        if (customer.redeemPoints(amount)) {
            System.out.println("Processing point payment of " + (int) amount + " points.");
            return true;
        } else {
            System.out.println("\u001B[31mNot enough points. You have " + customer.getPoints() + " points.\u001B[0m");
            return false;
        }
    }
    @Override
    public String getPaymentMethod() {
        return "Point Payment";
    }
}


class Customer {
    private String name;
    private int points;

    public Customer(String name) {
        this.name = name;
        this.points = 200;
    }

    public String getName() {
        return name;
    }
    
    public void setPoints(int newPoints) {
    if (newPoints >= 0) {
        this.points = newPoints;
    } else {
        System.out.println("Point balance cannot be negative.");
    }
}

    public int getPoints() {
        return points;
    }

    public void addPoints(int earned) {
        points += earned;
    }

    public boolean redeemPoints(double amount) {
        int requiredPoints = (int) amount;
        if (points >= requiredPoints) {
            points -= requiredPoints;
            return true;
        }
        return false;
    }
    @Override
    public String toString() {
        return name;
    }
}
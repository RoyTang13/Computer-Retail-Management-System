public class User{
    private String email;
    private String userName;
    private String password;
    private String phone;
    private final int userID;
    private int age;
    private String address;
    private int points;
    private double creditBalance;
    private final boolean isStaff;

    public User(String email, String userName, String password, String phone, 
               int userId, int age, String address, boolean isStaff) {
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.userID = userId;
        this.age = age;
        this.address = address;
        this.isStaff = isStaff;
        this.points = 0;
        this.creditBalance = 0.0;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getUserID() {
        return userID;
    }
    //在顾客和员工ID前加上（S/C），用在显示所有用户上
    public String getFormattedID() {
        return(isStaff ? "S" : "C") + String.format("%03d", userID);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPoints() {
        return points;
    }

    private User setPoints(int points) {
        this.points = points;
        return this;
    }
    
    public boolean isStaff() {
        return isStaff;
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

    public double getCreditBalance() {
        return creditBalance;
    }

    public void addCredit(double amount) {
        this.creditBalance += amount;
    }

    public boolean deductCredit(double amount) {
        if (creditBalance >= amount) {
            creditBalance -= amount;
            return true;
        } else {
            return false;
        }
    }
    
    //防止存入资料时因为“,”,“\\","\n"，出现问题
    private String escapeField(String field){
        if (field == null) return "";
        return field.replace("\\","\\\\")
                    .replace(",","\\,")
                    .replace("\n", "\\n");
    }
    //更改存入的资料样式
    public String toTextFormat() {

    return String.join(",",
        escapeField(email),
        escapeField(userName),
        escapeField(password),
        escapeField(phone),
        String.valueOf(userID),
        String.valueOf(age),
        escapeField(address),
        String.valueOf(points),
        String.valueOf(creditBalance),
        String.valueOf(isStaff)
        );
    }
    //读取资料时把“,”,“\\","\n"换回来
    private static String unescapeField(String field){
        if (field == null) return "";
        return field.replace("\\\\","\\")
                    .replace("\\n","\n")
                    .replace("\\,", ",");
    }
    //更改读取资料时样式
    public static User fromTextFormat(String textLine) {
    String[] parts = textLine.split("(?<!\\\\),");
    
    return new User(
        unescapeField(parts[0]),  // email
        unescapeField(parts[1]),  // username
        unescapeField(parts[2]),  // password
        unescapeField(parts[3]),  // phone
        Integer.parseInt(parts[4]),  // userID
        Integer.parseInt(parts[5]),  // age
        unescapeField(parts[6]),  // address
        Boolean.parseBoolean(parts[9])  // isStaff
        ).setCustomerData(
          Integer.parseInt(parts[7]),   //points   
          Double.parseDouble(parts[8])  //credit  
          );
         
    }
    public User setCustomerData(int points, double credit){
        if(!isStaff){
            this.points = points;
            this.creditBalance = credit;
        }
        return this;
    }
    
    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%d,%d,%s,%d,%b",
                email, userName, password, phone, userID, age, address, points, isStaff);
    }

  

    
    
    
}

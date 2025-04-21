public class customerDetail extends User {
    public customerDetail(String email, String userName, String password, 
                         String phone, int userId, int age, String address) {
        super(email, userName, password, phone, userId, age, address, false);
    }
}
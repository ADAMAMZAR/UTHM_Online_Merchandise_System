import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String email;
    private String address;
    private String password;
    private List<Order> orderHistory;
    private String username;

    // Constructor
    public User(String name, String email, String address, String password) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.password = password;
        // this.username = username;  // Remove this line
        this.orderHistory = new ArrayList<>();
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addOrder(Order order) {
        orderHistory.add(order);
    }

    public String getUsername() {
        return username;
    }
    public void addOrderToHistory(Order order) {
        orderHistory.add(order);
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getTotalPrice() {
        List<Order> orderHistory = getOrderHistory();
        double totalPrice = 0.0;

        for (Order order : orderHistory) {
            totalPrice += order.getTotalPrice();
        }

        return totalPrice;

    }
}

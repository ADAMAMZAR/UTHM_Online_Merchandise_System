import java.util.List;

public class Order {
    private int orderID;
    private String productName;
    private double price;
    private int quantity;

    private static int orderIdCounter = 1;

    private int orderId;
    private List<Product> products;

    // Constructor
    public Order(int orderID, String productName, double price, int quantity) {
        this.orderID = orderID;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public Order(List<Product> products, double v) {
        this.orderId = orderIdCounter++;
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    // Getter methods
    public int getOrderID() {
        return orderID;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return price * quantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}

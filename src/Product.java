import java.util.List;
import java.util.ArrayList;

public class Product {
    private String name;
    private String description;
    private double price;
    private String size; // Use the Size enum
    private String category;
    private int quantity;
    private List<User> addedToCarts;

    // Constructor
    public Product(String name, String description, double price, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.addedToCarts = new ArrayList<>();
    }

    // Getter and Setter methods...

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSize() {
        return size;
    }

    // Setter method for Size
    public void setSize(String sizeChoice) {
        this.size = convertStringToSize(sizeChoice);
    }

    // Helper method to convert a String to Size
    private String convertStringToSize(String sizeChoice) {
        switch (sizeChoice.toUpperCase()) {
            case "S":
                return Size.SMALL;
            case "M":
                return Size.MEDIUM;
            case "L":
                return Size.LARGE;
            case "XL":
                return Size.EXTRA_LARGE;
            default:
                // Handle invalid size choices here, you might want to throw an exception or return a default value
                throw new IllegalArgumentException("Invalid size choice: " + sizeChoice);
        }
    }

    // Setter method for Quantity
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Getter method for Quantity
    public int getQuantity() {
        return quantity;
    }

    // Getter method for Total Price
    public double getTotalPrice() {
        return price * quantity;
    }

    public User[] getAddedToCarts() {
        return addedToCarts.toArray(new User[0]);
    }
}

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Product> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public List<Product> getItems() {
        return items;
    }

    public void addItem(Product item) {
        items.add(item);
    }

    public void removeItem(Product product) {
        items.remove(product);
    }

    public void clearCart() {
        items.clear();
    }

    public double calculateTotalPrice() {
        double total = 0;
        for (Product item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }
}

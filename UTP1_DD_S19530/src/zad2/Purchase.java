/**
 *
 *  @author Diedov Denys S19530
 *
 */

package zad2;


public class Purchase {

    private String id;
    private String name;
    private String product;
    private double price;
    private double quantity;

    public Purchase(String id, String name, String product, double price, double quantity) {
        this.id = id;
        this.name = name;
        this.product = product;
        this.price = price;
        this.quantity = quantity;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProduct() {
        return product;
    }

    public double getPrice() {
        return price;
    }

    public double getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return id + ';' + name + ';' + product + ';' + price + ';' + quantity;
    }
}

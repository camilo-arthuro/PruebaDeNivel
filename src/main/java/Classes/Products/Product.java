package Classes.Products;

public class Product {

    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String burritoGift(){
        return " Pin(s) receives the customer for the purchase";
    }

    public String hamburgerGift(){
        return " Cap(s) receives the customer for the purchase";
    }
}

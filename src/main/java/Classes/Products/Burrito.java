package Classes.Products;

import Classes.Interfaces.ProductInterface;

public class Burrito extends Product implements ProductInterface {

    public Burrito(String name, double price) {
        super(name, price);
    }

    @Override
    public String gift() {
        return " Pin(s) receives the customer for the purchase";
    }
}

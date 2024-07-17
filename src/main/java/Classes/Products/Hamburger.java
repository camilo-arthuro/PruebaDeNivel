package Classes.Products;

import Classes.Interfaces.ProductInterface;

public class Hamburger extends Product implements ProductInterface {

    public Hamburger(String name, double price) {
        super(name, price);
    }

    @Override
    public String gift() {
        return " Cap(s) receives the customer for the purchase";
    }
}

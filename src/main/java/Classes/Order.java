package Classes;

import java.util.ArrayList;

public class Order {
    private int orderId;
    private static int nextId = 1;
    private Client client;
    private ArrayList<Product> productsList;
    private Distributor distributor;

    public Order(Client client, Distributor distributor, ArrayList<Product> products) {
        this.orderId = nextId++;
        this.client = client;
        this.distributor = distributor;
        this.productsList = products;
    }

    public int getOrderId() {
        return orderId;
    }

    public Client getClient() {
        return client;
    }

    public ArrayList<Product> getProductsList() {
        return productsList;
    }

    public Distributor getDistributor() {
        return distributor;
    }
}

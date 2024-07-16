package Classes.Distributors;

public class BicycleDistributor extends Distributor {

    public BicycleDistributor(String name) {
        super(name);
    }

    @Override
    public double additional(double price) {
        return (price * 1) / 100;
    }
}

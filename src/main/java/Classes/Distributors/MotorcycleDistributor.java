package Classes.Distributors;

public class MotorcycleDistributor extends Distributor {

    public MotorcycleDistributor(String name) {
        super(name);
    }

    @Override
    public double additional(double price) {
        return (price * 2) / 100;
    }
}

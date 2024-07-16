package Classes.Distributors;

public class WalkingDistributor extends Distributor {

    public WalkingDistributor(String name) {
        super(name);
    }

    @Override
    public double additional(double price) {
        return 0;
    }
}

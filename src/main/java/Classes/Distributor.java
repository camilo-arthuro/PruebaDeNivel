package Classes;

public class Distributor {

    private String name;

    public Distributor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double bicycleDelivery(double price){
        double additional = (price * 1) /100;

        return additional;
    }

    public double motorcycleDelivery(double price){
        double additional = (price * 2) / 100;

        return additional;
    }

    public void walkingDelivery(){
        System.out.println("Additional : 0 €");
    }
}

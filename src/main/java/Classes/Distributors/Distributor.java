package Classes.Distributors;

public abstract class Distributor {

    private String name;
    private boolean availability;

    public Distributor(String name) {
        this.name = name;
        this.availability = true;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public abstract double additional(double price);
}

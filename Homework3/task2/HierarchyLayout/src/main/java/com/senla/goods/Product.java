package H3T2.goods;

public abstract class Product {
    private final String name;

    protected Product(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract double getWeight();

    @Override
    public String toString() {
        return name + " (" + String.format("%.2f", getWeight()) + " кг)";
    }
}

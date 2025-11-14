package H3T2.goods;


public class BulkProduct extends Product {
    private final double weightKg;

    public BulkProduct(String name, double weightKg) {
        super(name);
        this.weightKg = weightKg;
    }

    @Override
    public double getWeight() {
        return weightKg;
    }

    @Override
    public String toString() {
        return String.format("%s: %.2f кг", getName(), getWeight());
    }
}

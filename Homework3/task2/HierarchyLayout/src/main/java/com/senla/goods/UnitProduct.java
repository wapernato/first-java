package H3T2.goods;



public class UnitProduct extends Product {
    private final double unitWeightKg;
    private final int quantity;

    public UnitProduct(String name, double unitWeightKg, int quantity) {
        super(name);
        this.unitWeightKg = unitWeightKg;
        this.quantity = quantity;
    }

    public double getUnitWeightKg() {
        return unitWeightKg;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public double getWeight() {
        return unitWeightKg * quantity;
    }

    @Override
    public String toString() {
        return String.format("%s: %d шт × %.2f кг = %.2f кг",
                getName(), quantity, unitWeightKg, getWeight());
    }
}

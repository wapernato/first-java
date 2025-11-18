package H3T2;

import H3T2.goods.Product;

import java.util.List;

public class Warehouse {

    private static final double MAX_CAPACITY_KG = 200.0;

    public static void main(String[] args) {

        // Заполняем склад
        List<Product> storedProducts = WarehouseUtils.fillWarehouse(MAX_CAPACITY_KG);

        // Считаем общий вес
        double totalWeight = WarehouseUtils.getTotalWeight(storedProducts);

        // Печатаем отчёт
        WarehouseUtils.printReport(storedProducts, totalWeight, MAX_CAPACITY_KG);
    }
}

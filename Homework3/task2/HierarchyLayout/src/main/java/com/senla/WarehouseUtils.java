package H3T2;

import H3T2.goods.BulkProduct;
import H3T2.goods.Product;
import H3T2.goods.UnitProduct;

import java.util.ArrayList;
import java.util.List;

public class WarehouseUtils {


    public static List<Product> fillWarehouse(double maxCapacityKg) {
        List<Product> products = new ArrayList<>();
        double totalWeight = 0.0;


        List<Product> incoming = List.of(
                new UnitProduct("Гвозди 100 мм", 0.02, 100),   // 2.00 кг
                new UnitProduct("Молоток", 1.0, 3),            // 3.00 кг
                new BulkProduct("Мешок цемента", 25.0),        // 25.00 кг
                new BulkProduct("Биг-бэг песка", 50.0),        // 50.00 кг
                new UnitProduct("Кирпич", 4.0, 20),            // 80.00 кг
                new BulkProduct("Щебень", 40.0)                // 40.00 кг
        );


        for (Product p : incoming) {
            double newWeight = totalWeight + p.getWeight();
            if (newWeight <= maxCapacityKg) {
                products.add(p);
                totalWeight = newWeight;
            } else {
                System.out.printf(
                        "Товар \"%s\" НЕ поместился на склад (превышение веса).%n",
                        p.getName()
                );
            }
        }

        return products;
    }


    public static double getTotalWeight(List<Product> products) {
        double sum = 0.0;
        for (Product p : products) {
            sum += p.getWeight();
        }
        return sum;
    }


    public static void printReport(List<Product> products,
                                   double totalWeight,
                                   double maxCapacityKg) {

        System.out.println();
        System.out.println("========== СОДЕРЖИМОЕ СКЛАДА ==========");
        System.out.printf("Максимальная грузоподъёмность: %.2f кг%n", maxCapacityKg);
        System.out.println();



        int i = 1;
        for (Product p : products) {
            System.out.printf("%-3d %-35s %12.2f%n",
                    i++, p.toString(), p.getWeight());
        }

        System.out.println("---------------------------------------------------------------");
        System.out.printf("ИТОГО вес: %.2f кг%n", totalWeight);
        double percent = totalWeight / maxCapacityKg * 100.0;
        System.out.printf("Заполнено: %.1f%%%n", percent);
        System.out.println("===========================================");
    }
}



public class Main {
    public static void main(String[] args) {
        // Создаем и запускаем сборочную линию
        service.IAssemblyLine line = new service.impl.AssemblyLine(
                new service.impl.BodyStep(),
                new service.impl.EngineStep(),
                new service.impl.TowerStep()
        );

        // Собираем танк
        service.IProduct tank = new model.Product();
        line.assembleProduct(tank);
    }
}
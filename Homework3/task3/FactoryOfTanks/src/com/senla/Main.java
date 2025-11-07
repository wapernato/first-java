package com.senla;

public class Main {
    public static void main(String[] args) {
        // Создаем и запускаем сборочную линию
        com.senla.service.IAssemblyLine line = new com.senla.service.impl.AssemblyLine(
                new com.senla.service.impl.BodyStep(),
                new com.senla.service.impl.EngineStep(),  // Исправлено: Engineering -> EngineStep
                new com.senla.service.impl.TowerStep()
        );

        // Собираем танк
        com.senla.service.IProduct tank = new com.senla.model.Product();
        line.assembleProduct(tank);
    }
}
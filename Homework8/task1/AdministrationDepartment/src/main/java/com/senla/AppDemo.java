package com.senla;

import com.senla.annotation.AutoConfigurer;
import com.senla.annotation.DiContainer;
import com.senla.controller.ControllersMenu;
import com.senla.deserialization.AllDeserialization;
import com.senla.resources.AppConfig;
import com.senla.serialization.AllSerialization;
import com.senla.service.*;
import com.senla.service.impl.*;
import com.senla.view.AppDemoView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;

import java.util.Properties;
import java.util.Scanner;

public class AppDemo {

    public static void main(String[] args) throws IOException, IllegalAccessException {



        Path configFile = Paths.get("config", "config.properties");
        Files.createDirectories(configFile.getParent());

        if (Files.notExists(configFile)) {
            try (InputStream is = AppDemo.class.getClassLoader()
                    .getResourceAsStream("config/config.properties")) {

                if (is != null) {
                    Files.copy(is, configFile);
                    System.out.println("Дефолтный config.properties скопирован на диск: " + configFile.toAbsolutePath());
                } else {
                    Files.writeString(
                            configFile,
                            "rooms.status.change=false\nrooms.history.limit=2\n",
                            StandardOpenOption.CREATE
                    );
                    System.out.println("Создан новый config.properties: " + configFile.toAbsolutePath());
                }
            }
        }



        DiContainer di = new DiContainer();


        AppConfig config = new AppConfig();
        new AutoConfigurer().configure(config);

        di.registerInstance(AppConfig.class, config);

//        System.out.println(config.getPort());
//        System.out.println(config.getDebug());
//        System.out.println(config.getName());
//        System.out.println(Arrays.toString(config.getRatios()));
//        System.out.println(config.getTags());

        // 3) bindings
        di.bind(Rooms.class, InMemoryRooms.class);
        di.bind(GuestRegistry.class, InMemoryGuestRegistry.class);
        di.bind(ServiceCatalog.class, InMemoryServiceCatalog.class);
        di.bind(ServiceUsageRegistry.class, InMemoryServiceUsageRegistry.class);

        di.bind(SortStats.class, CheckInSortStatus.class);
        di.bind(WorksWithFilesImport.class, ImportFiles.class);
        di.bind(WorksWithFilesExport.class, ExportFiles.class);

        // 4) получаем корневые объекты
        AllDeserialization allDeserialization = di.get(AllDeserialization.class);
        AllSerialization allSerialization = di.get(AllSerialization.class);
        ControllersMenu controllersMenu = di.get(ControllersMenu.class);
        AppDemoView view = di.get(AppDemoView.class);

        // стартовая загрузка
        allDeserialization.allDeserialization();

        // 5) один цикл управления (без controllersMenu.run(), если он тоже цикл)
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                view.help();
                System.out.print("> (help - список команд) ");
                if (!sc.hasNextLine()) break;

                String cmd = sc.nextLine().trim().toLowerCase();
                if (cmd.isEmpty()) continue;

                switch (cmd) {
                    case "1", "работа с комнатами" -> controllersMenu.menuRooms();
                    case "2", "работа с гостями" -> controllersMenu.menuGuest();
                    case "3", "работа с сервисом" -> controllersMenu.menuService();
                    case "4", "работа с деталями комнат" -> controllersMenu.menuDetails();
                    case "5", "импорт" -> controllersMenu.menuImport();
                    case "6", "экспорт" -> controllersMenu.menuExport();
                    case "7", "сериализация" -> allSerialization.serializationAll();
                    case "8", "десериализация" -> allDeserialization.allDeserialization();
                    case "help", "помощь" -> view.help();
                    case "выход", "exit", "quit", "0" -> {
                        System.out.println("Программа завершена.");
                        allSerialization.serializationAll();
                        return;
                    }
                    default -> System.out.println("Неизвестная команда, попробуйте ещё раз");
                }
            }
        }
    }
}

package com.senla;

import com.senla.controller.*;
import com.senla.deserialization.AllDeserialization;
import com.senla.deserialization.DeserializationGuestRegistry;
import com.senla.deserialization.DeserializationRooms;
import com.senla.deserialization.DeserializationServiceCatalog;
import com.senla.model.Room;
import com.senla.serialization.AllSerialization;
import com.senla.service.*;
import com.senla.service.impl.*;
import com.senla.view.AppDemoView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Scanner;


public class AppDemo {


    public static void main(String[] args) throws IOException {
        Properties props = new Properties();

        try (InputStream is = AppDemo.class
                .getClassLoader()
                .getResourceAsStream("config/config.properties")) {

            if (is != null) {
                props.load(is);
                System.out.println("config.properties успешно загружен из resources");
                return;
            }

        } catch (IOException e) {
            System.out.println("Ошибка чтения config.properties: " + e.getMessage());
        }


        System.out.println("config.properties не найден, создаю новый");

        Path configDir = Paths.get("config");
        Files.createDirectories(configDir);

        Path configFile = configDir.resolve("config.properties");

        props.setProperty("rooms.status.change", "true");
        props.setProperty("rooms.history.limit", "100");

        try (OutputStream os = Files.newOutputStream(configFile)) {
            props.store(os, "Auto-generated config");
        }

        String changeStatusProperty = props.getProperty("rooms.status.change");
        boolean changeStatus = Boolean.parseBoolean(changeStatusProperty);

        String roomsHistoryLimitProperty = props.getProperty("rooms.history.limit");
        Integer roomsHistoryLimit = Integer.parseInt(roomsHistoryLimitProperty);

        // ====== Инициализация сервисов ======
        Room room = new Room();
        InMemoryRooms rooms = new InMemoryRooms();

        //InMemoryGuestRegistry guest = new InMemoryGuestRegistry(rooms);

        GuestRegistry guests = new InMemoryGuestRegistry(rooms);

        SortStats sorter = new CheckInSortStatus();
        ServiceCatalog catalog = new InMemoryServiceCatalog();
        ServiceUsageRegistry usage = new InMemoryServiceUsageRegistry();
        AppDemoView view = new AppDemoView();
        WorksWithFilesImport importt = new ImportFiles(rooms, guests, catalog);
        WorksWithFilesExport export = new ExportFiles(rooms, guests, catalog);

        AllSerialization allSerialization = new AllSerialization(guests, rooms, catalog, usage);
        AllDeserialization allDeserialization = new AllDeserialization(guests, rooms, catalog, usage);

        AppDemoControllerExport exportController = new AppDemoControllerExport(rooms, guests, sorter, catalog, usage, view, export);
        AppDemoControllerImport importController = new AppDemoControllerImport(rooms, guests, sorter, catalog, usage, view, importt);
        AppDemoControllerRooms controllerRooms = new AppDemoControllerRooms(rooms, guests, sorter, catalog, usage, view, importt, changeStatus);
        AppDemoControllerGuests controllerGuests = new AppDemoControllerGuests(rooms, guests, sorter, catalog, usage, view, importt);
        AppDemoControllerService controllerService = new AppDemoControllerService(rooms, guests, sorter, catalog, usage, view);
        AppDemoControllerSorter controllerSorter = new AppDemoControllerSorter(rooms, guests, sorter, catalog, usage, view, roomsHistoryLimit);

        ControllersMenu controllersMenu = new ControllersMenu(controllerRooms, controllerGuests, controllerService, controllerSorter,importController, exportController, view);

        allDeserialization.allDeserialization();
        try ( Scanner sc = new Scanner(System.in) ){

            while (true){
                view.help();
                System.out.print("> (help - список команд, выбор подгруппы) ");
                if(!sc.hasNextLine()) break;
                String line = sc.nextLine().trim();
                if(line.isEmpty()) continue;

                String cmd = line.toLowerCase();

                switch (cmd){
                    case "1", "работа с комнатами" -> controllersMenu.menuRooms();
                    case "2", "работа с гостями" -> controllersMenu.menuGuest();
                    case "3", "работа с сервисом" -> controllersMenu.menuService();
                    case "4", "работа с деталями комнат" -> controllersMenu.menuDetails();
                    case "5", "импорт" ->  controllersMenu.menuImport();
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


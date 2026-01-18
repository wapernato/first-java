package com.senla;

import com.senla.annotation.AutoConfigurer;
import com.senla.controller.*;
import com.senla.deserialization.AllDeserialization;
import com.senla.deserialization.DeserializationGuestRegistry;
import com.senla.deserialization.DeserializationRooms;
import com.senla.deserialization.DeserializationServiceCatalog;
import com.senla.model.Room;
import com.senla.resources.AppConfig;
import com.senla.serialization.AllSerialization;
import com.senla.service.*;
import com.senla.service.impl.*;
import com.senla.view.AppDemoView;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;


public class AppDemo {


    public static void main(String[] args) throws IllegalAccessException {


        AutoConfigurer autoConfigurer = new AutoConfigurer();
        // ==================================================================
        AppConfig cfg = new AppConfig();
        new AutoConfigurer().configure(cfg);

        boolean changeStatus = cfg.isChangeStatus();
        int roomsHistoryLimit = cfg.getRoomsHistoryLimit();
        // ==================================================================


        // ====== Инициализация сервисов ======
        Room room = new Room();
        InMemoryRooms rooms = new InMemoryRooms();


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
                    case "test" -> autoConfigurer.configure(guests);

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


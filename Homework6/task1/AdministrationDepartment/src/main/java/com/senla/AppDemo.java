package com.senla;

import com.senla.controller.*;
import com.senla.service.*;
import com.senla.service.impl.*;
import com.senla.view.AppDemoView;

import java.util.Scanner;


public class AppDemo {


    public static void main(String[] args) {
        // ====== Инициализация сервисов ======

        Rooms rooms = new InMemoryRooms();
        GuestRegistry guests = new InMemoryGuestRegistry(rooms);
        SortStats sorter = new CheckInSortStatus();
        ServiceCatalog catalog = new InMemoryServiceCatalog();
        ServiceUsageRegistry usage = new InMemoryServiceUsageRegistry();
        AppDemoView view = new AppDemoView();
        WorksWithFilesImport importt = new ImportFiles(rooms, guests);
        WorksWithFilesExport export = new ExportFiles(rooms, guests, catalog);

        AppDemoControllerExport exportController = new AppDemoControllerExport(rooms, guests, sorter, catalog, usage, view, export);
        AppDemoControllerImport importController = new AppDemoControllerImport(rooms, guests, sorter, catalog, usage, view, importt);
        AppDemoControllerRooms controllerRooms = new AppDemoControllerRooms(rooms, guests, sorter, catalog, usage, view, importt);
        AppDemoControllerGuests controllerGuests = new AppDemoControllerGuests(rooms, guests, sorter, catalog, usage, view, importt);
        AppDemoControllerService controllerService = new AppDemoControllerService(rooms, guests, sorter, catalog, usage, view);
        AppDemoControllerSorter controllerSorter = new AppDemoControllerSorter(rooms, guests, sorter, catalog, usage, view);

        ControllersMenu controllersMenu = new ControllersMenu(controllerRooms, controllerGuests, controllerService, controllerSorter,importController, exportController, view);

        try ( Scanner sc = new Scanner(System.in) ){

            while (true){
                view.help();
                System.out.print("> (help - список команд, выбор подгруппы) ");
                if(!sc.hasNextLine()) break;
                String line = sc.nextLine().trim();
                if(line.isEmpty()) continue;

                String cmd = line.toLowerCase();
                // добавить для каждой команды экспорта универсальную проверку


                switch (cmd){
                    case "1", "работа с комнатами" -> controllersMenu.menuRooms();
                    case "2", "работа с гостями" -> controllersMenu.menuGuest();
                    case "3", "работа с сервисом" -> controllersMenu.menuService();
                    case "4", "работа с деталями комнат" -> controllersMenu.menuDetails();
                    case "5", "импорт" ->  controllersMenu.menuImport();
                    case "6", "экспорт" -> controllersMenu.menuExport();

                    //                        case "123" -> guests.getRoomId();
                    //                        case "импортировать список гостей" -> controllerGuests.guestImport(); // 20
                    //                        case "импортировать список комнат" -> controllerRooms.roomsImport(); // 21

                    case "help", "помощь" -> view.help();

                    case "выход","exit", "quit", "0" -> {
                        System.out.println("Программа завершена.");
                        return;
                    }
                    default -> System.out.println("Неизвестная команда, попробуйте ещё раз");

                }
            }
        }
    }
}

// ====== Добавление номеров и изменение цен/вместимости/звёзд/ нельзя забывать, что max - capacity = 3,a stars = 5 ======
//        rooms.addRoom("101", 2, 3);  // number, stars, capacity
//        rooms.addRoom("102", 3, 4);
//        rooms.addRoom("103", 3, 2);
//        rooms.addRoom("104", 1, 1);
//        rooms.addRoom("105", 2, 3);
//
//        rooms.setRoomPrice("101", 1200);
//        rooms.setRoomPrice("102", 1600);
//        rooms.setRoomPrice("103", 4500);
//        rooms.setRoomPrice("104", 800);
//        rooms.setRoomPrice("105", 1500);
//
//        // Изменение цены номера (пример)
//        rooms.setRoomPrice("104", 900);
//
//        // ====== Добавление услуг ====== // просто для галочки, способ ниже больше импонирует
//        catalog.addService("Завтрак", 300);
//        catalog.addService("Спа", 1500);
//        catalog.addService("Прачечная", 500);
//        // Изменить цену услуги
//        catalog.setServicePrice("Прачечная", 550);
//
//        // ====== Бронирования (поселение) ===========================================================================
//        // Будущее
//        LocalDate nextWeek = LocalDate.now().plusDays(7);
//        guests.addHuman("101", "Иван", nextWeek, 3);   // [t+7, t+10)
//        guests.addHuman("101", "Анна", nextWeek, 3);   // вместимость 3 -> ок
//        guests.addHuman("102", "Мария", nextWeek, 2);
//
//        // На сегодня
//        LocalDate today = LocalDate.now();
//        guests.addHuman("103", "Олег", today, 2);      // [t, t+2)
//        guests.addHuman("105", "Павел", today, 1);     // [t, t+1)
//
//        // Пример использования услуг гостем // думаю в будущем лучше оставить только таким образом добавление услуг
//        usage.addUsageFromCatalog("Олег", "Завтрак", today, catalog);
//        usage.addUsageFromCatalog("Олег", "Прачечная", today.plusDays(3), catalog);
//        usage.addUsage("Олег", "Мини-бар", today.plusDays(2), 900); - отказываюсь пока что
//
//        // ====== 1) Список номеров (сортировки по цене / вместимости / звёздам) ======
//
//        divider("1) Список номеров (через SortStats)");
//        sorter.sortRoomByPrice(rooms);
//        //sorter.sortRoomByCapacity(rooms);
//        //sorter.sortRoomByStars(rooms);
//
//        // ====== 2) Список свободных номеров (сортировки) ======
//        divider("2) Свободные на сегодня (сортировки)");
//        sorter.freeSortRoomByPrice(rooms);
//        // ====== 3) Список постояльцев и их номеров (алфавит, дата выезда) ======
//        divider("3) Постояльцы и их номера (имя ↑, дата выезда ↑)");
//        sorter.sortGuestsByAlphabetThenCheckout(guests);
//
//        // ====== 4) Общее число свободных номеров ======
//        divider("4) Общее число свободных номеров");
//        long freeCount = rooms.freeRoomsNumber().size();
//        System.out.println("Свободно номеров сейчас: " + freeCount);
//
//        // ====== 5) Общее число постояльцев (сегодня) ======
//        divider("5) Общее число постояльцев");
//        System.out.println("Сейчас проживает: " + guests.countActiveGuestsToday()); //!!!!!!!!!!!!!!!!!!!!!!!!!!
//
//        // ====== 7) Сумма за проживание, которую должен оплатить постоялец ======
//        divider("7) Сумма к оплате за проживание");
//
//        System.out.printf("Иван (101) должен за проживание: %.2f%n",
//                guests.computeRoomCharge("101", "Иван"));
//        System.out.printf("Олег (103) должен за проживание: %.2f%n",
//                guests.computeRoomCharge("103", "Олег"));
//
//        double olehServices = usage.listByGuest("Олег").stream().mapToDouble(u -> u.price).sum();
//        System.out.printf("Олег (услуги): %.2f, ИТОГО (проживание+услуги): %.2f%n",
//                olehServices, guests.computeRoomCharge("103", "Олег") + olehServices);
//
//        // ====== 8) 3 последних постояльца комнаты ======
//        divider("8) Последние 3 постояльца комнаты 101");
//        sorter.printLast3GuestsOfRoom("101", guests);
//
//        // ====== 9) Список услуг постояльца и их цену (сортировки) ======
//        divider("9) Услуги Олега (по дате ↑)");
//        sorter.printGuestServices("Олег", usage, false); //
//        divider("9a) Услуги Олега (по цене ↑)");
//        sorter.printGuestServices("Олег", usage, true);
//
//        // ====== 10) Цены услуг и номеров (по разделу, цене) ======
//        divider("10) Цены услуг и номеров");
//        sorter.printPrices(rooms, catalog);
//
//        // ====== 11) Детали отдельного номера ======
//        divider("11) Детали номера 103");
//        sorter.printRoomDetails(rooms, "103", guests);
//
//        divider("Операции: выселение, обслуживание, изменение цен, добавление");
//        // Выселить из номера 103 (сегодняшних постояльцев этой комнаты)
//        guests.removePeopleFromRoom("103");
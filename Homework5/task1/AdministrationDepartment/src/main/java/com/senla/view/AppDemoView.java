package com.senla.view;

import com.senla.model.RoomStatus;
import com.senla.service.*;
import com.senla.service.impl.CheckInSortStatus;

import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

import static java.lang.Integer.parseInt;


public class AppDemoView {
    SortStats sorter = new CheckInSortStatus();
    private final Scanner in = new Scanner(System.in); // не закрываем System.in

    public void showMessage(String msg) {
        System.out.println(msg);
    }

    public void showError(String msg) {
        System.err.println(msg);
    }

    public String askString(String prompt) {
        System.out.print(prompt + " : ");
        return in.nextLine().trim();
    }



    public int askInt(String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            String s = in.nextLine().trim();
            try {
                return parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Введите целое число.");
            }
        }
    }

    public LocalDate askDate(String promt) {
        System.out.print(promt + ": ");
        String strDate = in.nextLine().trim();
        return LocalDate.parse(strDate);
    }

    public boolean askBool(String prompt) {
        while (true) {
            System.out.print(prompt + " (да/нет): ");
            String line = in.nextLine().trim().toLowerCase();

            if (line.equals("да") || line.equals("y") || line.equals("true")) {
                return true;
            }
            if (line.equals("нет") || line.equals("n") || line.equals("false")) {
                return false;
            }

            System.out.println("Некорректный ввод. Введите да/нет.");
        }
    }
    public void showGuests(List<GuestRegistry.GuestEntry> guests) {
        System.out.println("Список гостей (по алфавиту, затем по дате выезда):");
        for (GuestRegistry.GuestEntry g : guests) {
            System.out.println(g);
        }
    }

    public void freeRoomsNumber(long freeCount) {
        System.out.println("Свободно номеров сейчас: " + freeCount);
    }

    public void printActiveGuestsToday(int countActive){
        System.out.println("Количество постояльцев: " + countActive);
    }

    public void printGuestServices(String name, ServiceUsageRegistry usage, boolean sortByPrice){
        sorter.printGuestServices(name, usage, sortByPrice);
    }

    public void printPrices(Rooms rooms, ServiceCatalog catalog){
        sorter.printPrices(rooms, catalog);
    }

    public void  printRoomDetails(Rooms rooms, String name,GuestRegistry guests){
        sorter.printRoomDetails(rooms, name, guests);
    }

    public RoomStatus askRoomStatus(String prompt) {
        while (true) {
            System.out.println(prompt);
            System.out.println("Доступные статусы:");
            System.out.println("1 - AVAILABLE (свободен)");
            System.out.println("2 - MAINTENANCE (на ремонте)");
            System.out.println("3 - SERVICE (уборка / обслуживание)");

            String input = in.nextLine().trim(); // или askString, если он есть

            switch (input) {
                case "1":
                case "available":
                case "AVAILABLE":
                case "свободен":
                    return RoomStatus.AVAILABLE;

                case "2":
                case "maintenance":
                case "MAINTENANCE":
                case "ремонт":
                case "на ремонте":
                    return RoomStatus.MAINTENANCE;

                case "3":
                case "service":
                case "SERVICE":
                case "уборка":
                    return RoomStatus.SERVICE;

                default:
                    System.out.println("Неизвестный статус, попробуйте ещё раз.");
            }
        }
    }

    public void helpRooms(){
        System.out.println();
        System.out.println("========= ДОСТУПНЫЕ КОМАНДЫ =========");
        System.out.println("1) Добавить комнату");
        System.out.println("2) изменить цену комнаты");
        System.out.println("3) изменить статус комнаты");
        System.out.println("4) общее число свободных номеров");
        System.out.println("5) свободные номера на определенную дату");
        System.out.println("======================================");
        System.out.println();
    }

    public void helpDetails(){
        System.out.println();
        System.out.println("========= ДОСТУПНЫЕ КОМАНДЫ =========");
        System.out.println("1) Сортировать комнаты по цена/вместимость/звезды");
        System.out.println("2) Сортировка свободных номеров");
        System.out.println("3) Список постояльцев и их номеров");
        System.out.println("4) Список услуг постояльца и их цену");
        System.out.println("5) Цены услуг и номеров");
        System.out.println("6) Последние 3 жильца");
        System.out.println("7) Узнать детали комнаты");
        System.out.println("======================================");
        System.out.println();
    }
    public void helpGuest(){
        System.out.println();
        System.out.println("========= ДОСТУПНЫЕ КОМАНДЫ =========");
        System.out.println("1) Добавить гостя");
        System.out.println("2) Общее число постояльцев");
        System.out.println("3) Сумма, которую должен постоялец");
        System.out.println("4) Удалить человека из номера");
        System.out.println("======================================");
        System.out.println();
    }
    public void helpService(){
        System.out.println();
        System.out.println("========= ДОСТУПНЫЕ КОМАНДЫ =========");
        System.out.println("1) Добавить услугу");
        System.out.println("2) Изменить цену услуги");
        System.out.println("3) Записать услугу на гостя");
        System.out.println("======================================");
        System.out.println();
    }
    public void help() {
        System.out.println();
        System.out.println("========= ДОСТУПНЫЕ КОМАНДЫ =========");
        System.out.println("1) Работа с комнатами");
        System.out.println("2) Работа с гостями");
        System.out.println("3) Работа с сервисами");
        System.out.println("4) Работа с деталями комнаты");
        System.out.println("======================================");
        System.out.println();
    }
}





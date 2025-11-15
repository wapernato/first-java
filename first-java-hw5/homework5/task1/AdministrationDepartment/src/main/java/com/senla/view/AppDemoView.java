package com.senla.view;

import com.senla.model.RoomStatus;
import com.senla.service.*;
import com.senla.service.impl.CheckInSortStatus;

import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;


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
        System.out.print(prompt + " (или 'назад' для возврата): ");
        return in.nextLine().trim();
    }


    public int askInt(String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            String s = in.nextLine().trim();
            try {
                return Integer.parseInt(s);
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

    public boolean isBackCommand(String s) {
        return s != null && s.equalsIgnoreCase("назад");
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
}





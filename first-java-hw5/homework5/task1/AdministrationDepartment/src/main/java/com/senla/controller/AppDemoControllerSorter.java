package com.senla.controller;

import com.senla.service.*;
import com.senla.view.AppDemoView;

import java.util.InputMismatchException;
import java.util.List;

public class AppDemoControllerSorter {

    private final Rooms rooms;
    private final GuestRegistry guests;
    private final SortStats sorter;
    private final ServiceCatalog catalog;
    private final ServiceUsageRegistry usage;
    private final AppDemoView view;


    public AppDemoControllerSorter(Rooms rooms, GuestRegistry guests, SortStats sorter, ServiceCatalog catalog, ServiceUsageRegistry usage, AppDemoView view){
        this.rooms = rooms;
        this.guests = guests;
        this.sorter = sorter;
        this.catalog = catalog;
        this.usage = usage;
        this.view = view;
    }

    public void sortByStats() {
        try {
            while (true) {
                String sortType = view.askString(
                        "По какому типу будет сортировка: звезды/вместимость/цена. Введите текст"
                );

                if (sortType == null || sortType.isBlank()) {
                    System.out.println("Параметр не может быть пустым, попробуйте ещё раз");
                    continue;
                }

                String key = sortType.toLowerCase();

                switch (key) {
                    case "звезды" -> {
                        sorter.sortRoomByStars(rooms);
                        return;
                    }
                    case "вместимость" -> {
                        sorter.sortRoomByCapacity(rooms);
                        return;
                    }
                    case "цена" -> {
                        sorter.sortRoomByPrice(rooms);
                        return;
                    }
                    default -> System.out.println("Нет такого параметра, попробуйте ещё раз");
                }
            }
        } catch (Exception e) {
            view.showError("Произошла непредвиденная ошибка: " + e.getMessage());
        }
    }

    public void freeSortRoomByStats(){
        try {
            while (true) {
                String sortType = view.askString(
                        "По какому типу будет сортировка: звезды/вместимость/цена. Введите текст"
                );

                if (sortType == null || sortType.isBlank()) {
                    System.out.println("Параметр не может быть пустым, попробуйте ещё раз");
                    continue;
                }

                String key = sortType.toLowerCase();

                switch (key) {
                    case "звезды" -> {
                        sorter.freeSortRoomByStars(rooms);
                        return;
                    }
                    case "вместимость" -> {
                        sorter.freeSortRoomByCapacity(rooms);
                        return;
                    }
                    case "цена" -> {
                        sorter.freeSortRoomByPrice(rooms);
                        return;
                    }
                    default -> System.out.println("Нет такого параметра, попробуйте ещё раз");
                }
            }
        } catch (Exception e) {
            view.showError("Произошла непредвиденная ошибка: " + e.getMessage());
        }
    }

    public void sortGuestsByAlphabetThenCheckout(){
        List<GuestRegistry.GuestEntry> sorted = sorter.sortGuestsByAlphabetThenCheckout(guests);
        view.showGuests(sorted);
    }

    public void last3GuestsOfRoom(){
        try {
            String number;
            while (true) {
                number = view.askString("Введите номер комнаты, чтобы узнать последних 3-ех жильцов");
                if(rooms.getRoomsNumbers().contains(number)){
                    break;
                }
                else {
                    view.showMessage("Комнаты нету в отеле");
                }
                sorter.printLast3GuestsOfRoom(number, guests);
            }
        }
        catch (Exception e) {
            view.showError("Произошла непредвиденная ошибка: " + e.getMessage());
        }
    }

    public void guestServices(){
        try{
            String name;
            while (true) {
                name = view.askString("Введите имя человека, у которого нужно узнать услуги");
                if (guests.getAllGuestEntries().contains(name)){
                    view.showMessage("Гость есть в отеле");
                    break;
                }
            }

            boolean sortByPrice = view.askBool("Хотите ли сортировку по цене?");
            view.printGuestServices(name, usage, sortByPrice);
        }
        catch (Exception e) {
            view.showError("Произошла непредвиденная ошибка: " + e.getMessage());
        }
    }

    public void prices(){
        view.printPrices(rooms, catalog);
    }

    public void roomDetails(){
        try {
            String number;
            while (true){
                number = view.askString("Введите номер комнаты, чтобы узнать детали");
                if(rooms.getRoomsNumbers().contains(number)){
                    break;
                }
                else {
                    view.showMessage("Комнаты нету в отеле");
                }
            }
            view.printRoomDetails(rooms, number, guests);
        }
        catch (Exception e) {
            view.showError("Произошла непредвиденная ошибка: " + e.getMessage());
        }

    }
}

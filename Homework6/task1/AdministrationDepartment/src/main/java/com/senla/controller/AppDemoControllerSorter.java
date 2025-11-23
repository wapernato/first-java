package com.senla.controller;

import com.senla.service.*;
import com.senla.view.AppDemoView;

import java.util.List;

public class AppDemoControllerSorter {

    private final Rooms rooms;
    private final GuestRegistry guests;
    private final SortStats sorter;
    private final ServiceCatalog catalog;
    private final ServiceUsageRegistry usage;
    private final AppDemoView view;

    public AppDemoControllerSorter(Rooms rooms,
                                   GuestRegistry guests,
                                   SortStats sorter,
                                   ServiceCatalog catalog,
                                   ServiceUsageRegistry usage,
                                   AppDemoView view) {
        this.rooms = rooms;
        this.guests = guests;
        this.sorter = sorter;
        this.catalog = catalog;
        this.usage = usage;
        this.view = view;
    }


    public void sortByStats() {
        view.showMessage("\n=== Сортировка всех номеров ===");
        try {
            while (true) {
                String sortType = view.askString(
                        "Выберите критерий сортировки (введите слово)\n" +
                                " - звезды\n" +
                                " - вместимость\n" +
                                " - цена\n" +
                                " - 0 для выхода\n" +
                                "Ваш выбор"
                );
                if(view.commandBack(sortType)){
                    view.showMessage("Вы вернулись назад");
                    return;
                }
                if (sortType == null || sortType.isBlank()) {
                    view.showMessage("Критерий сортировки не может быть пустым. Попробуйте ещё раз.");
                    continue;
                }

                String key = sortType.toLowerCase();

                switch (key) {
                    case "звезды","1" -> {
                        sorter.sortRoomByStars(rooms);
                        view.showMessage("Номера отсортированы по количеству звёзд.");
                        return;
                    }
                    case "вместимость","2" -> {
                        sorter.sortRoomByCapacity(rooms);
                        view.showMessage("Номера отсортированы по вместимости.");
                        return;
                    }
                    case "цена","3" -> {
                        sorter.sortRoomByPrice(rooms);
                        view.showMessage("Номера отсортированы по цене.");
                        return;
                    }
                    default -> view.showMessage("Неизвестный критерий. Введите: звезды, вместимость или цена.");
                }
            }
        } catch (Exception e) {
            view.showError("При сортировке номеров произошла непредвиденная ошибка. " +
                    "Попробуйте ещё раз.\nТехнические детали " + e.getMessage());
        }
    }


    public void freeSortRoomByStats() {
        view.showMessage("\n=== Сортировка свободных номеров ===");
        try {
            while (true) {
                String sortType = view.askString(
                        "Выберите критерий сортировки свободных номеров (введите слово)\n" +
                                " - Звезды\n" +
                                " - Вместимость\n" +
                                " - Цена\n" +
                                " - 0 для выхода" +
                                "Ваш выбор:"
                );
                if(view.commandBack(sortType)){
                    view.showMessage("Вы вернулись назад");
                    return;
                }
                if (sortType == null || sortType.isBlank()) {
                    view.showMessage("Критерий сортировки не может быть пустым. Попробуйте ещё раз.");
                    continue;
                }

                String key = sortType.toLowerCase();

                switch (key) {
                    case "звезды","1" -> {
                        sorter.freeSortRoomByStars(rooms);
                        view.showMessage("Свободные номера отсортированы по количеству звёзд.");
                        return;
                    }
                    case "вместимость","2" -> {
                        sorter.freeSortRoomByCapacity(rooms);
                        view.showMessage("Свободные номера отсортированы по вместимости.");
                        return;
                    }
                    case "цена","3" -> {
                        sorter.freeSortRoomByPrice(rooms);
                        view.showMessage("Свободные номера отсортированы по цене.");
                        return;
                    }
                    default -> view.showMessage("Неизвестный критерий. Введите: звезды, вместимость или цена.");
                }
            }
        } catch (Exception e) {
            view.showError("При сортировке свободных номеров произошла непредвиденная ошибка. " +
                    "Попробуйте ещё раз.\nТехнические детали " + e.getMessage());
        }
    }

    public void sortGuestsByAlphabetThenCheckout() {
        view.showMessage("\n=== Список гостей (по алфавиту и дате выезда) ===");
        try {
            List<GuestRegistry.GuestEntry> sorted = sorter.sortGuestsByAlphabetThenCheckout(guests);
            view.showGuests(sorted);
        } catch (Exception e) {
            view.showError("Не удалось вывести список гостей. " +
                    "Попробуйте ещё раз.\nТехнические детали " + e.getMessage());
        }
    }


    public void last3GuestsOfRoom() {
        view.showMessage("\n=== Последние 3 гостя комнаты ===");
        try {
            String number;
            while (true) {
                number = view.askString("Введите номер комнаты, чтобы увидеть последних 3 гостей или 0 для выхода");
                if (view.commandBack(number)){
                    view.showMessage("Команда назад выполнена");
                    return;
                }
                if (rooms.getRoomsNumbers().contains(number)) {
                    break;
                } else {
                    view.showMessage("Комнаты с номером \"" + number +
                            "\" нет в отеле. Проверьте номер и попробуйте ещё раз.");
                }
            }

            sorter.printLast3GuestsOfRoom(number, guests);

        } catch (Exception e) {
            view.showError("Не удалось получить информацию о последних гостях комнаты. " +
                    "Попробуйте ещё раз.\nТехнические детали " + e.getMessage());
        }
    }

    public void guestServices() {
        view.showMessage("\n=== Список услуг гостя ===");
        try {
            // НЕ РАБОТАЕТ ===============================================================================================
            String number;
            while (true){
                number = view.askString("Введите номер, в котором проживает нужный гость или 0 для выхода");
                if (view.commandBack(number)){
                    view.showMessage("Команда назад выполнена");
                    return;
                }
                if(rooms.getRoomsNumbers().contains(number)){
                    view.showMessage("Номер есть в отеле");
                    break;
                }
                else{
                    view.showMessage("Номера нету в отеле. Попробуйте еще раз");
                }
            }

            String name;
            while (true) {

                name = view.askString("Введите имя гостя, для которого нужно показать услуги или 0 для выхода");
                if (view.commandBack(name)){
                    view.showMessage("Команда назад выполнена");
                    return;
                }
                if (guests.getListOfPeople(number).contains(name)) {
                    view.showMessage("Гость \"" + name + "\" найден в отеле.");
                    break;
                } else {
                    view.showMessage("Гостя с именем \"" + name +
                            "\" сейчас нет в отеле. Проверьте имя и попробуйте ещё раз.");
                }
            }

            boolean sortByPrice = view.askBool(
                    "Отсортировать услуги по цене? (да/нет)"
            );

            view.printGuestServices(name, usage, sortByPrice);

        } catch (Exception e) {
            view.showError("Не удалось вывести список услуг гостя. " +
                    "Попробуйте ещё раз.\nТехнические детали " + e.getMessage());
        }
    }


    public void prices() {
        view.showMessage("\n=== Цены на номера и услуги ===");
        try {
            view.printPrices(rooms, catalog);
        } catch (Exception e) {
            view.showError("Не удалось вывести цены. Попробуйте ещё раз.\n" +
                    "Технические детали " + e.getMessage());
        }
    }


    public void roomDetails() {
        view.showMessage("\n=== Детальная информация о комнате ===");
        try {
            String number;
            while (true) {
                number = view.askString("Введите номер комнаты, чтобы узнать детали или 0 для выхода");
                if(view.commandBack(number)){
                    view.showMessage("Вы вернулись назад");
                    return;
                }
                if (rooms.getRoomsNumbers().contains(number)) {
                    break;
                } else {
                    view.showMessage("Комнаты с номером \"" + number +
                            "\" нет в отеле. Проверьте номер и попробуйте ещё раз.");
                }
            }

            view.printRoomDetails(rooms, number, guests);

        } catch (Exception e) {
            view.showError("Не удалось вывести детали комнаты. " +
                    "Попробуйте ещё раз.\nТехнические детали " + e.getMessage());
        }
    }
}
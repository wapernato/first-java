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
                        "Выберите критерий сортировки (введите слово):\n" +
                                " - звезды\n" +
                                " - вместимость\n" +
                                " - цена\n" +
                                "Ваш выбор:"
                );

                if (sortType == null || sortType.isBlank()) {
                    view.showMessage("Критерий сортировки не может быть пустым. Попробуйте ещё раз.");
                    continue;
                }

                String key = sortType.toLowerCase();

                switch (key) {
                    case "звезды" -> {
                        sorter.sortRoomByStars(rooms);
                        view.showMessage("Номера отсортированы по количеству звёзд.");
                        return;
                    }
                    case "вместимость" -> {
                        sorter.sortRoomByCapacity(rooms);
                        view.showMessage("Номера отсортированы по вместимости.");
                        return;
                    }
                    case "цена" -> {
                        sorter.sortRoomByPrice(rooms);
                        view.showMessage("Номера отсортированы по цене.");
                        return;
                    }
                    default -> view.showMessage("Неизвестный критерий. Введите: звезды, вместимость или цена.");
                }
            }
        } catch (Exception e) {
            view.showError("При сортировке номеров произошла непредвиденная ошибка. " +
                    "Попробуйте ещё раз.\nТехнические детали: " + e.getMessage());
        }
    }


    public void freeSortRoomByStats() {
        view.showMessage("\n=== Сортировка свободных номеров ===");
        try {
            while (true) {
                String sortType = view.askString(
                        "Выберите критерий сортировки свободных номеров (введите слово):\n" +
                                " - звезды\n" +
                                " - вместимость\n" +
                                " - цена\n" +
                                "Ваш выбор:"
                );

                if (sortType == null || sortType.isBlank()) {
                    view.showMessage("Критерий сортировки не может быть пустым. Попробуйте ещё раз.");
                    continue;
                }

                String key = sortType.toLowerCase();

                switch (key) {
                    case "звезды" -> {
                        sorter.freeSortRoomByStars(rooms);
                        view.showMessage("Свободные номера отсортированы по количеству звёзд.");
                        return;
                    }
                    case "вместимость" -> {
                        sorter.freeSortRoomByCapacity(rooms);
                        view.showMessage("Свободные номера отсортированы по вместимости.");
                        return;
                    }
                    case "цена" -> {
                        sorter.freeSortRoomByPrice(rooms);
                        view.showMessage("Свободные номера отсортированы по цене.");
                        return;
                    }
                    default -> view.showMessage("Неизвестный критерий. Введите: звезды, вместимость или цена.");
                }
            }
        } catch (Exception e) {
            view.showError("При сортировке свободных номеров произошла непредвиденная ошибка. " +
                    "Попробуйте ещё раз.\nТехнические детали: " + e.getMessage());
        }
    }

    public void sortGuestsByAlphabetThenCheckout() {
        view.showMessage("\n=== Список гостей (по алфавиту и дате выезда) ===");
        try {
            List<GuestRegistry.GuestEntry> sorted = sorter.sortGuestsByAlphabetThenCheckout(guests);
            view.showGuests(sorted);
        } catch (Exception e) {
            view.showError("Не удалось вывести список гостей. " +
                    "Попробуйте ещё раз.\nТехнические детали: " + e.getMessage());
        }
    }


    public void last3GuestsOfRoom() {
        view.showMessage("\n=== Последние 3 гостя комнаты ===");
        try {
            String number;
            while (true) {
                number = view.askString("Введите номер комнаты, чтобы увидеть последних 3 гостей:");
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
                    "Попробуйте ещё раз.\nТехнические детали: " + e.getMessage());
        }
    }

    public void guestServices() {
        view.showMessage("\n=== Список услуг гостя ===");
        try {
            String name;
            while (true) {
                name = view.askString("Введите имя гостя, для которого нужно показать услуги:");
                if (guests.getAllGuestEntries().contains(name)) {
                    view.showMessage("Гость \"" + name + "\" найден в отеле.");
                    break;
                } else {
                    view.showMessage("Гостя с именем \"" + name +
                            "\" сейчас нет в отеле. Проверьте имя и попробуйте ещё раз.");
                }
            }

            boolean sortByPrice = view.askBool(
                    "Отсортировать услуги по цене? (да/нет):"
            );

            view.printGuestServices(name, usage, sortByPrice);

        } catch (Exception e) {
            view.showError("Не удалось вывести список услуг гостя. " +
                    "Попробуйте ещё раз.\nТехнические детали: " + e.getMessage());
        }
    }


    public void prices() {
        view.showMessage("\n=== Цены на номера и услуги ===");
        try {
            view.printPrices(rooms, catalog);
        } catch (Exception e) {
            view.showError("Не удалось вывести цены. Попробуйте ещё раз.\n" +
                    "Технические детали: " + e.getMessage());
        }
    }


    public void roomDetails() {
        view.showMessage("\n=== Детальная информация о комнате ===");
        try {
            String number;
            while (true) {
                number = view.askString("Введите номер комнаты, чтобы узнать детали:");
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
                    "Попробуйте ещё раз.\nТехнические детали: " + e.getMessage());
        }
    }
}

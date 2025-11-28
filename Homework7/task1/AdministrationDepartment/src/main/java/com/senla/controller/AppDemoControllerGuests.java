package com.senla.controller;

import com.senla.service.*;
import com.senla.view.AppDemoView;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class AppDemoControllerGuests {

    private final Rooms rooms;
    private final GuestRegistry guests;
    private final SortStats sorter;
    private final ServiceCatalog catalog;
    private final ServiceUsageRegistry usage;
    private final AppDemoView view;
    private final WorksWithFilesImport importt;

    public AppDemoControllerGuests(Rooms rooms,
                                   GuestRegistry guests,
                                   SortStats sorter,
                                   ServiceCatalog catalog,
                                   ServiceUsageRegistry usage,
                                   AppDemoView view,
                                   WorksWithFilesImport importt) {
        this.rooms = rooms;
        this.guests = guests;
        this.sorter = sorter;
        this.catalog = catalog;
        this.usage = usage;
        this.view = view;
        this.importt = importt;
    }


    public void addHuman() {
        view.showMessage("\n=== Добавление гостя / бронирование номера ===");
        try {

            String number;
            while (true) {
                number = view.askString(
                        "Введите номер комнаты, в которую нужно заселить гостя или 0 для возврата"
                );
                if(view.commandBack(number)){
                    view.showMessage("Вы вернулись назад");
                    return;
                }

                if (rooms.getRoomsNumbers().contains(number)) {
                    view.showMessage("Комната №" + number + " найдена в отеле.");
                    break;
                } else {
                    view.showMessage("Комнаты с номером \"" + number +
                            "\" нет в отеле. Проверьте номер и попробуйте ещё раз.");
                }
            }


            String name = view.askString("Введите имя гостя");


            LocalDate date;
            while (true) {
                try {
                    date = view.askDate(
                            "Укажите дату заезда гостя (формат ГГГГ-ММ-ДД, например 2024-10-12)"
                    );
                } catch (DateTimeParseException e) {
                    view.showMessage("Неверный формат даты. Используйте формат ГГГГ-ММ-ДД. Попробуйте ещё раз.");
                    continue;
                }

                if (date.isAfter(LocalDate.now()) || date.isEqual(LocalDate.now())) {
                    view.showMessage("Дата заезда: " + date);
                    break;
                } else {
                    view.showMessage("Дата не может быть в прошлом. Выберите сегодняшнюю или будущую дату.");
                }
            }


            int night;
            while (true) {
                night = view.askInt(
                        "Укажите на сколько ночей бронируется проживание (целое число > 0)"
                );

                if (night > 0) {
                    break;
                } else {
                    view.showMessage("Количество ночей должно быть больше 0. Попробуйте ещё раз.");
                }
            }


            guests.addHuman(number, name, date, night);
            LocalDate exit = date.plusDays(night);
            if(guests.getListOfPeople(number).contains(name)) {
                String message = """
                        Гость %s
                        Забронировал комнату №%s
                        С %s по %s
                        (ночей: %d).
                        """.formatted(name, number, date, exit, night);

                view.showMessage(message);
            }
            else {
                view.showMessage("Не получилось добавить. Причина в комнате нету свободных мест.");
            }
        } catch (Exception e) {
            view.showError("При добавлении гостя произошла непредвиденная ошибка. " +
                    "Попробуйте ещё раз или обратитесь к администратору.\n" +
                    "Технические детали " + e.getMessage());
        }
    }


    public void countActiveGuestsToday() {
        view.showMessage("\n=== Количество гостей, проживающих сегодня ===");
        int count = guests.countActiveGuestsToday();
        view.printActiveGuestsToday(count);
    }

    public void computeRoomCharge() {
        view.showMessage("\n=== Расчёт стоимости проживания гостя ===");
        try {
            // НЕ РАБОТАЕТ ===============================================================================================

            String number;
            while (true) {
                number = view.askString(
                        "Введите номер комнаты, в которой проживает этот гость"
                );

                if (rooms.getRoomsNumbers().contains(number)) {
                    break;
                } else {
                    view.showMessage("Комнаты с номером \"" + number +
                            "\" нет в отеле. Проверьте номер и попробуйте ещё раз.");
                }
            }

            String name;
            while (true) {
                name = view.askString(
                        "Введите имя гостя, для которого нужно посчитать стоимость проживания или 0 для возврата"
                );
                if(view.commandBack(name)){
                    view.showMessage("Вы вернулись назад");
                    return;
                }
                if (guests.getListOfPeople(number).contains(name)) { // guests.getAllGuestEntries().contains(name)
                    view.showMessage("Гость \"" + name + "\" найден в списке проживающих.");
                    break;
                } else {
                    view.showMessage("Гостя с именем \"" + name +
                            "\" сейчас нет в отеле. Проверьте имя и попробуйте ещё раз.");
                }
            }





            double guestService = usage.listByGuest(name)
                    .stream()
                    .mapToDouble(u -> u.price)
                    .sum();

            double roomCharge = guests.computeRoomCharge(number, name);
            double total = roomCharge + guestService;

            view.showMessage(
                    String.format(
                            "Гость: %s%n" +
                                    "Стоимость проживания (номер): %.2f у.е.%n" +
                                    "Стоимость услуг: %.2f у.е.%n" +
                                    "ИТОГО (проживание + услуги): %.2f у.е.",
                            name,
                            roomCharge,
                            guestService,
                            total
                    )
            );

        } catch (Exception e) {
            view.showError("При расчёте стоимости проживания произошла ошибка. " +
                    "Попробуйте ещё раз.\nТехнические детали " + e.getMessage());
        }
    }

    public void removePeopleFromRoom() {
        view.showMessage("\n=== Освобождение комнаты ===");
        try {
            String number;
            while (true) {
                number = view.askString(
                        "Введите номер комнаты, которую нужно освободить или 0 для возврата"
                );
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

            guests.removePeopleFromRoom(number);
            view.showMessage("Комната №" + number + " успешно освобождена. Все гости выселены из этой комнаты.");

        } catch (Exception e) {
            view.showError("Не удалось освободить комнату. " +
                    "Попробуйте ещё раз или обратитесь к администратору.\n" +
                    "Технические детали " + e.getMessage());
        }
    }


//    public void guestImport() {
//        view.showMessage("\n=== Импорт списка гостей из файла ===");
//        try {
//            importt.importGuest();
//            view.showMessage("Импорт списка гостей из файла успешно завершён.");
//        } catch (Exception e) {
//            view.showError("Не удалось импортировать список гостей из файла. " +
//                    "Проверьте файл и попробуйте ещё раз.\nТехнические детали " + e.getMessage());
//        }
//    }
}
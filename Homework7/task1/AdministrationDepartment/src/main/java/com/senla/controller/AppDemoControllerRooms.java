package com.senla.controller;

import com.senla.model.RoomStatus;
import com.senla.service.*;
import com.senla.view.AppDemoView;

import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.time.LocalDate;

public class AppDemoControllerRooms {


    private final Rooms rooms;
    private final GuestRegistry guests;
    private final WorksWithFilesImport importt;
    private final AppDemoView view;
    private final boolean changeStatus;


    public AppDemoControllerRooms(Rooms rooms,
                                  GuestRegistry guests,
                                  SortStats sorter,
                                  ServiceCatalog catalog,
                                  ServiceUsageRegistry usage,
                                  AppDemoView view,
                                  WorksWithFilesImport importt,
                                  boolean changeStatus
    ) {
        this.rooms = rooms;
        this.guests = guests;
        this.view = view;
        this.importt = importt;
        this.changeStatus = changeStatus;
    }



    public void addRoom() {
        view.showMessage("\n=== Добавление новой комнаты ===\n");


        try {

            int roomNumber;
            while (true) {
                String numberStr = view.askString(
                        "Введите номер комнаты (целое положительное число, например 101) или 0 для возврата"
                );
                if(view.commandBack(numberStr)){
                    view.showMessage("Вы вернулись назад");
                    return;
                }
                if(!rooms.getRoomsNumbers().contains(numberStr)){
                try {
                    roomNumber = Integer.parseInt(numberStr);
                    if (roomNumber <= 0) {
                        view.showMessage("Номер комнаты должен быть больше 0. Попробуйте ещё раз.");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    view.showMessage("Номер комнаты должен быть числом. Попробуйте ещё раз.");
                }
                }
                else {
                    view.showMessage("Комната уже есть, попробуйте еще раз");
                    return;
                }
            }

            String number = String.valueOf(roomNumber);


            int capacity;
            while (true) {
                capacity = view.askInt(
                        "Введите вместимость комнаты (количество гостей от 1 до 3)"
                );
                if (capacity > 0 && capacity <= 3) {
                    break;
                } else {
                    view.showMessage("Вместимость должна быть от 1 до 3 гостей. Попробуйте ещё раз.");
                }
            }

            int stars;
            while (true) {
                stars = view.askInt(
                        "Введите количество звёзд для комнаты (от 1 до 5)"
                );
                if (stars > 0 && stars <= 5) {
                    break;
                } else {
                    view.showMessage("Количество звёзд должно быть от 1 до 5. Попробуйте ещё раз.");
                }
            }
            int price;
            while (true) {
                price = view.askInt(
                        "Введите цену комнаты: (> 0)"
                );
                if (price > 0) {
                    break;
                } else {
                    view.showMessage("Цена комнаты не должна быть отрицательной или должна быть больше 0");
                }
            }

            rooms.addRoom(number, capacity, stars, price);
            view.showMessage("Комната №" + number +
                    " успешно добавлена. Вместимость: " + capacity +
                    ", звёзд: " + stars + ", цена: " + price + ".");

        }
        catch (Exception e) {
            view.showError("При добавлении комнаты произошла непредвиденная ошибка. " +
                    "Попробуйте ещё раз или обратитесь к администратору.\n" +
                    "Технические детали: " + e.getMessage());
        }
    }

    public void setRoomPrice() {
        view.showMessage("\n=== Изменение цены комнаты ===");
        try {
            String number;
            while (true) {
                number = view.askString(
                        "Введите номер комнаты, для которой нужно изменить цену или 0 для возврата"
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
                            "\" не существует. Проверьте номер и попробуйте ещё раз.");
                }
            }

            int price;
            while (true) {
                price = view.askInt(
                        "Введите новую цену за проживание в этой комнате (целое число больше 0)"
                );
                if (price > 0) {
                    break;
                } else {
                    view.showMessage("Цена должна быть больше нуля. Попробуйте ещё раз.");
                }
            }

            rooms.setRoomPrice(number, price);
            view.showMessage("Цена для комнаты №" + number +
                    " успешно обновлена и теперь составляет " + price + " у.е.");

        } catch (Exception e) {
            view.showError("Не удалось изменить цену комнаты. " +
                    "Попробуйте ещё раз или обратитесь к администратору.\n" +
                    "Технические детали: " + e.getMessage());
        }
    }



    /* ============================================================================================================== */





    public void setStatus() {
        if(changeStatus){
            view.showMessage("\n=== Изменение статуса комнаты ===");
            try {

                String number;
                while (true) {
                    number = view.askString(
                            "Введите номер комнаты, у которой нужно изменить статус или 0 для возврата"
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
                                "\" не найдено. Проверьте номер и попробуйте ещё раз.");
                    }
                }


                view.showMessage("Доступные статусы: AVAILABLE (свободна), " +
                        "MAINTENANCE (на обслуживании), SERVICE (уборка/сервис).");

                RoomStatus status;
                while (true) {
                    status = view.askRoomStatus(
                            "Введите новый статус комнаты (AVAILABLE / MAINTENANCE / SERVICE)"
                    );
                    if (status == RoomStatus.AVAILABLE ||
                            status == RoomStatus.SERVICE ||
                            status == RoomStatus.MAINTENANCE) {
                        break;
                    } else {
                        view.showMessage("Неверный статус. Используйте AVAILABLE, MAINTENANCE или SERVICE.");
                    }
                }

                rooms.setRoomStatus(number, status);
                view.showMessage("Статус комнаты №" + number +
                        " успешно изменён на: " + status + ".");

            } catch (Exception e) {
                view.showError("Не удалось изменить статус комнаты. " +
                        "Попробуйте ещё раз или обратитесь к администратору.\n" +
                        "Технические детали: " + e.getMessage());
            }
        }
        else {
            view.showError(
                    "\nДанное действие запрещено настройками системы.\n" +
                            "Обратитесь к администратору, если необходимо изменить права доступа.\n"
            );
            return;
        }
    }


    public void freeRoomsNumber() {
        view.showMessage("\n=== Общее количество свободных номеров ===");
        long countActive = rooms.freeRoomsNumber().size();

        view.freeRoomsNumber(countActive);
    }


    public void listRoomsFreeOn() {
        view.showMessage("\n=== Свободные номера на выбранную дату ===");
        try {
            LocalDate futureDate = view.askDate(
                    "Введите дату, на которую нужно показать свободные номера " +
                            "(формат ГГГГ-ММ-ДД, например 2024-10-12):"
            );

            if (futureDate.isBefore(LocalDate.now())) {
                view.showMessage("Вы ввели дату в прошлом: " + futureDate +
                        ". Для просмотра свободных номеров выберите сегодняшнюю или будущую дату.");
                return;
            }

            var freeRooms = guests.listRoomsFreeOn(futureDate);
            if (freeRooms == null || freeRooms.isEmpty()) {
                view.showMessage("На дату " + futureDate +
                        " свободных номеров нет.");
            } else {
                view.showMessage("Свободные номера на " + futureDate + ": " + freeRooms);
            }

        } catch (InputMismatchException ime) {
            view.showError("Дата должна быть введена числом. Используйте формат ГГГГ-ММ-ДД.");
        } catch (DateTimeParseException e) {
            view.showError("Неверный формат даты. Используйте формат ГГГГ-ММ-ДД, " +
                    "например 2024-10-12.");
        } catch (Exception e) {
            view.showError("Не удалось получить список свободных номеров. " +
                    "Попробуйте ещё раз.\nТехнические детали: " + e.getMessage());
        }
    }

}
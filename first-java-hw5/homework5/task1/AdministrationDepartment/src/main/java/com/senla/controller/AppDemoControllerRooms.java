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


    public AppDemoControllerRooms(Rooms rooms, GuestRegistry guests, SortStats sorter, ServiceCatalog catalog, ServiceUsageRegistry usage, AppDemoView view, WorksWithFilesImport importt) {
        this.rooms = rooms;
        this.guests = guests;
        this.view = view;
        this.importt = importt;
    }

    public void addRoom() {
        try {

            int roomNumber;
            while (true) {
                String numberStr = view.askString("Введите номер комнаты");
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
            String number = String.valueOf(roomNumber);


            int capacity;
            while (true) {

                capacity = view.askInt("Введите вместимость (1–3)");
                if (capacity > 0 && capacity <= 3) {
                    break;
                } else {
                    view.showMessage("Вместимость должна быть от 1 до 3. Попробуйте ещё раз.");
                }
            }


            int stars;
            while (true) {
                stars = view.askInt("Введите количество звёзд (1–5)");
                if (stars > 0 && stars <= 5) {
                    break;
                } else {
                    view.showMessage("Количество звёзд должно быть от 1 до 5. Попробуйте ещё раз.");
                }
            }

            rooms.addRoom(number, capacity, stars);
            view.showMessage("Комната добавлена: №" + number);

        }
        catch (Exception e) {
            view.showError("Произошла непредвиденная ошибка: " + e.getMessage());
        }
    }

    public void setRoomPrice() {
        try {
            String number;
            while (true){
                number = view.askString("Введите номер комнаты");
                if (rooms.getRoomsNumbers().contains(number)) {
                    view.showMessage("Номер есть в отеле");
                    break;
                }
                else {
                    view.showMessage("Такого номера нету в отеле. Попробуйте ещё раз.");
                }
            }

            int price;
            while (true) {
                price = view.askInt("Введите цену комнаты");
                    if (price > 0) {
                        break;
                    }
                    else {
                        view.showMessage("Цена не может быть отрицательной");
                    }
                }
            rooms.setRoomPrice(number, price);
            view.showMessage("Цена "+ number + " номера - " + price);
            }

        catch (Exception e) {
                view.showError("Произошла непредвиденная ошибка: " + e.getMessage());
        }
    }

    public void setStatus() {
        try {
            String number;
            while (true) {
                number = view.askString("Введите номер комнаты, у который будет изменен статус");
                if(rooms.getRoomsNumbers().contains(number)){
                    view.showMessage("Номер есть в отеле");
                    break;
                }
            }

            RoomStatus status;
            while (true) {
                status = view.askRoomStatus("Введите новый статус комнаты: AVAILABLE, MAINTENANCE, SERVICE");
                if (status == RoomStatus.AVAILABLE || status == RoomStatus.SERVICE || status == RoomStatus.MAINTENANCE ){
                    break;
                }
            }

            rooms.setRoomStatus(number, status);
            view.showMessage("Статус комнаты " + number + " изменён на: " + status);

        }
        catch (Exception e) {
            view.showError("Произошла непредвиденная ошибка: " + e.getMessage());
        }
    }

    public void freeRoomsNumber() {
        long countActive = rooms.freeRoomsNumber().size();
        view.freeRoomsNumber(countActive);
    }

    public void listRoomsFreeOn() {
        try {
            LocalDate futureDate = view.askDate("Введите дату на которую будем смотреть свободные номера");
            if (futureDate.isAfter(LocalDate.now())) {
                view.showMessage("Свободные на " + futureDate + ": " + guests.listRoomsFreeOn(futureDate));
            }
        } catch (InputMismatchException ime) {
            System.out.println("Напишите числовое значение, а не строчку");
        }
        catch (DateTimeParseException e){
            System.out.println("Неверный формат даты. Используйте формат гггг-мм-дд, например 2024-10-12");
        }
    }

    public void roomsImport(){
        importt.importRooms();
        view.showMessage("Ввод списка комнат из файла завершен.");
    }
}

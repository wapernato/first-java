package com.senla.controller;

import com.senla.service.*;
import com.senla.view.AppDemoView;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;

public class AppDemoControllerGuests {

    private final Rooms rooms;
    private final GuestRegistry guests;
    private final SortStats sorter;
    private final ServiceCatalog catalog;
    private final ServiceUsageRegistry usage;
    private final AppDemoView view;
    private final WorksWithFilesImport importt;


    public AppDemoControllerGuests(Rooms rooms, GuestRegistry guests, SortStats sorter, ServiceCatalog catalog, ServiceUsageRegistry usage, AppDemoView view, WorksWithFilesImport importt){
        this.rooms = rooms;
        this.guests = guests;
        this.sorter = sorter;
        this.catalog = catalog;
        this.usage = usage;
        this.view = view;
        this.importt = importt;
    }

    public void addHuman(){
        try{

            String number;
            while (true) {

                number = view.askString("Введите номер комнаты");

                if(rooms.getRoomsNumbers().contains(number)){
                    view.showMessage("Комната есть в отеле");
                    break;
                }
                else {
                    view.showMessage("Комната отсутствует в отеле");
                }
            }

            String name = view.askString("Введите имя гостя");

            LocalDate date;
            while (true) {
                date = view.askDate("Укажите дату на которую надо сделать бронь");
                if(date.isAfter(LocalDate.now()) || date.isEqual(LocalDate.now())){
                    view.showMessage(date + " дата заселения гостя");
                    break;
                }
                else {
                    view.showMessage("Дата не может быть прошедшая. Попробуйте еще раз");
                }
            }

            int night;
            while (true) {

                night = view.askInt("Укажите на сколько ночей будет заселение");

                if (night > 0) {
                    break;
                } else {
                    view.showMessage("Количество ночей должно быть > 0");
                }
            }
            guests.addHuman(number, name, date, night);

            LocalDate exit = date.plusDays(night);

            view.showMessage("Гость " + name + " добавлен в комнату " + number + ". Дата брони " + date +
                    ", дата выезда " + exit); // ошибки LocalDate(), если поступает не то значение, то программа ломается - исправить чтобы давала заполнить заново

        }
        catch (DateTimeParseException e){
            System.out.println("Неверный формат даты. Используйте формат гггг-мм-дд, например 2024-10-12");
        }
        catch (Exception e) {
            view.showError("Произошла непредвиденная ошибка: " + e.getMessage());
        }
    }

    public void countActiveGuestsToday(){
        int count = guests.countActiveGuestsToday();
        view.printActiveGuestsToday(count);
    }

    public void computeRoomCharge(){
        try {
            String name;
            while (true) {
                name = view.askString("Введите имя гостя, у которого нужно подсчитать сумму за проживание");
                if(guests.getAllGuestEntries().contains(name)){
                    view.showMessage("Гость есть в отеле");
                    break;
                }
                else {
                    view.showMessage("Гостя нету в отеле. Попробуйте еще раз");
                }
            }

            String number;
            while (true) {
                number = view.askString("Введите номер гостя, у которого нужно подсчитать сумму за проживание");
                if(rooms.getRoomsNumbers().contains(number)){
                    break;
                }
                else {
                    view.showMessage("Комнаты нету в отеле. Попробуйте еще раз");
                }
            }


            double guestService = usage.listByGuest(name).stream().mapToDouble(u -> u.price).sum();
            view.showMessage(
                    String.format(
                            "%s (услуги): %.2f, ИТОГО (проживание+услуги): %.2f",
                            name,
                            guestService,
                            guests.computeRoomCharge(number, name) + guestService
                    )
            );

        }
        catch (Exception e) {
            view.showError("Произошла непредвиденная ошибка: " + e.getMessage());
        }
    }

    public void removePeopleFromRoom(){
        try {
            String number;
            while (true){
                number = view.askString("Введите номер, который нужно освободить");
                if (rooms.getRoomsNumbers().contains(number)){
                    break;
                }
                else{
                    view.showMessage("Комнаты нету в отеле. Попробуйте еще раз");
                }
            }

            guests.removePeopleFromRoom(number);

        }

        catch (Exception e) {
            view.showError("Произошла непредвиденная ошибка: " + e.getMessage());
        }
    }

    public void guestImport(){
        importt.importGuest();
        view.showMessage("Ввод списка гостей из файла завершен.");
    }
}

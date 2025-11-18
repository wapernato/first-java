package com.senla.controller;

import com.senla.service.*;
import com.senla.view.AppDemoView;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;

public class AppDemoControllerService {

    private final Rooms rooms;
    private final GuestRegistry guests;
    private final SortStats sorter;
    private final ServiceCatalog catalog;
    private final ServiceUsageRegistry usage;
    private final AppDemoView view;


    public AppDemoControllerService(Rooms rooms, GuestRegistry guests, SortStats sorter, ServiceCatalog catalog, ServiceUsageRegistry usage, AppDemoView view){
        this.rooms = rooms;
        this.guests = guests;
        this.sorter = sorter;
        this.catalog = catalog;
        this.usage = usage;
        this.view = view;
    }

    public void addService(){
        try{

            String service = view.askString("Введите название услуги");
            view.showMessage("Услуга " + service + " была добавлена в список услуг");

            int price;
            while (true) {
                price = view.askInt("Введите цену услуги");
                if(price > 0){
                    break;
                }
                else {
                    view.showMessage("Цена не может быть отрицательной. Попробуйте еще раз");
                }
            }
            catalog.addService(service, price);
            view.showMessage("Услуга "+ service + " была добавлена. Цена услуги - " + price);
        }
        catch (Exception e) {
            view.showError("Произошла непредвиденная ошибка: " + e.getMessage());
        }
    }

    public void setServicePrice(){
        try{
            String service;
            while (true) {
                service = view.askString("Введите название услуги");
                if(catalog.listServiceNames().contains(service)){
                    view.showMessage("Услуга есть в каталоге");
                    break;
                }
            }

            int price;
            while (true) {
                price = view.askInt("Введите новую цену услуги");
                if(price > 0){
                    break;
                }
                else {
                    view.showMessage("Цена не может быть отрицательной. Попробуйте еще раз");
                }
            }

            catalog.setServicePrice(service, price);
            view.showMessage("Цена " + service + " изменена на " + price);
        }

        catch (Exception e) {
            view.showError("Произошла непредвиденная ошибка: " + e.getMessage());
        }
    }

    public void addUsageFromCatalog(){
        try {
            String name;
            while (true) {
                name = view.askString("Введите имя гостя");
                if(guests.getAllGuestEntries().contains(name)){
                    view.showMessage("Гость присутствует в отеле");
                    break;
                }
                else {
                    view.showMessage("Гостя нету в отеле");
                }
            }

            String service;
            while (true){
                service = view.askString("Введите услугу");
                if(catalog.listServiceNames().contains(service)){
                    view.showMessage("Услуга есть в списке");
                    break;
                }
                else {
                    view.showMessage("Услуги нету в списке");
                }
            }

            LocalDate date;
            while (true) {
                date = view.askDate("Укажите дату использования услуги гостем");
                if(date.isAfter(LocalDate.now()) || date.isEqual(LocalDate.now())){
                    view.showMessage(date + " дата заселения гостя");
                    break;
                }
                else {
                    view.showMessage("Дата не может быть прошедшая. Попробуйте еще раз");
                }
            }
            usage.addUsageFromCatalog(name, service, date, catalog);

        }
        catch (DateTimeParseException e){
            System.out.println("Неверный формат даты. Используйте формат гггг-мм-дд, например 2024-10-12");
        }
        catch (Exception e) {
            view.showError("Произошла непредвиденная ошибка: " + e.getMessage());
        }
    }
}

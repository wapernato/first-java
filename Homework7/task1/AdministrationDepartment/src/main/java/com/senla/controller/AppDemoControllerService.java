package com.senla.controller;

import com.senla.service.*;
import com.senla.view.AppDemoView;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class AppDemoControllerService {

    private final Rooms rooms;
    private final GuestRegistry guests;
    private final SortStats sorter;
    private final ServiceCatalog catalog;
    private final ServiceUsageRegistry usage;
    private final AppDemoView view;

    public AppDemoControllerService(Rooms rooms,
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


    public void addService() {
        view.showMessage("\n=== Добавление новой услуги ===");
        try {

            String service = view.askString(
                    "Введите название услуги (например: Завтрак, СПА, Трансфер) или 0 для выхода"
            );
            if(view.commandBack(service)){
                view.showMessage("Вы вернулись назад");
                return;
            }

            int price;
            while (true) {
                price = view.askInt(
                        "Введите цену услуги (целое число больше 0)"
                );
                if (price > 0) {
                    break;
                } else {
                    view.showMessage("Цена услуги должна быть больше 0. Попробуйте ещё раз.");
                }
            }


            catalog.addService(service.toLowerCase(), price);
            view.showMessage("Услуга \"" + service + "\" добавлена в каталог. Цена: " + price + " у.е.");

        } catch (Exception e) {
            view.showError("При добавлении услуги произошла непредвиденная ошибка. " +
                    "Попробуйте ещё раз или обратитесь к администратору.\n" +
                    "Технические детали " + e.getMessage());
        }
    }



    public void setServicePrice() {
        view.showMessage("\n=== Изменение цены услуги ===");
        try {
            String service;
            while (true) {
                service = view.askString(
                        "Введите название услуги, для которой нужно изменить цену или 0 для выхода"
                );
                if(view.commandBack(service)){
                    view.showMessage("Вы вернулись назад");
                    return;
                }
                if (catalog.listServiceNames().contains(service)) {
                    view.showMessage("Услуга \"" + service + "\" найдена в каталоге.");
                    break;
                } else {
                    view.showMessage("Услуги с названием \"" + service +
                            "\" нет в каталоге. Проверьте название и попробуйте ещё раз.");
                }
            }

            int price;
            while (true) {
                price = view.askInt(
                        "Введите новую цену услуги (целое число больше 0)"
                );
                if (price > 0) {
                    break;
                } else {
                    view.showMessage("Цена услуги должна быть больше 0. Попробуйте ещё раз.");
                }
            }


            catalog.setServicePrice(service, price);
            view.showMessage("Цена услуги \"" + service + "\" успешно изменена на " + price + " у.е.");

        } catch (Exception e) {
            view.showError("Не удалось изменить цену услуги. " +
                    "Попробуйте ещё раз или обратитесь к администратору.\n" +
                    "Технические детали " + e.getMessage());
        }
    }
    public void addUsageFromCatalog() {
        view.showMessage("\n=== Назначение услуги гостю ===");
        try {
            // НЕ РАБОТАЕТ
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
                name = view.askString(
                        "Введите имя гостя, которому нужно назначить услугу или 0 для выхода"
                );
                if(view.commandBack(name)){
                    view.showMessage("Вы вернулись назад");
                    return;
                }
                if (guests.getListOfPeople(number).contains(name)) {
                    view.showMessage("Гость \"" + name + "\" найден в списке проживающих.");
                    break;
                } else {
                    view.showMessage("Гостя с именем \"" + name +
                            "\" сейчас нет в отеле. Проверьте имя и попробуйте ещё раз.");
                }
            }

            String service;
            while (true) {
                service = view.askString(
                        "Введите название услуги (как в каталоге услуг) или 0 для выхода"
                );
                if (view.commandBack(service)){
                    view.showMessage("Команда назад выполнена");
                    return;
                }
                if (catalog.listServiceNames().contains(service.toLowerCase())) {
                    view.showMessage("Услуга \"" + service + "\" есть в каталоге.");
                    break;
                } else {
                    view.showMessage("Услуги \"" + service +
                            "\" нет в каталоге. Проверьте название и попробуйте ещё раз.");
                }
            }


            LocalDate date;
            while (true) {
                try {
                    date = view.askDate(
                            "Укажите дату использования услуги гостем " +
                                    "(формат ГГГГ-ММ-ДД, например 2024-10-12)"
                    );
                } catch (DateTimeParseException e) {
                    view.showMessage("Неверный формат даты. Используйте формат ГГГГ-ММ-ДД. Попробуйте ещё раз.");
                    continue;
                }

                if (date.isAfter(LocalDate.now()) || date.isEqual(LocalDate.now())) {
                    view.showMessage("Дата использования услуги " + date);
                    break;
                } else {
                    view.showMessage("Дата не может быть в прошлом. Выберите сегодняшнюю или будущую дату.");
                }
            }

            usage.addUsageFromCatalog(name, service, date, catalog);
            view.showMessage("Гостю \"" + name + "\" назначена услуга \"" + service +
                    "\" на дату " + date + ".");

        } catch (Exception e) {
            view.showError("Не удалось записать использование услуги. " +
                    "Попробуйте ещё раз или обратитесь к администратору.\n" +
                    "Технические детали " + e.getMessage());
        }
    }
}

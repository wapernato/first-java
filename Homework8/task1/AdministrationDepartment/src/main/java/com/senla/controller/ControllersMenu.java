package com.senla.controller;

import com.senla.AppDemo;
import com.senla.annotation.Inject;
import com.senla.service.*;
import com.senla.service.impl.*;
import com.senla.view.AppDemoView;

import java.util.Scanner;

public class ControllersMenu {
    @Inject
    private AppDemoControllerRooms roomsController;
    @Inject
    private AppDemoControllerGuests guestsController;
    @Inject
    private AppDemoControllerService serviceController;
    @Inject
    private AppDemoControllerSorter sorterController;
    @Inject
    private AppDemoControllerImport importController;
    @Inject
    private AppDemoControllerExport exportController;
    @Inject
    private AppDemoView view;

    public ControllersMenu(){}
    public void menuImport(){
        while (true){
            view.helpImport();
            String line = view.askString("> (help - список команд, подгруппа импорт) ");
            if (line.isEmpty()) {
                continue;
            }

            String cmd = line.toLowerCase();
            switch (cmd){
                case "1","импорт комнат" -> importController.roomsImport();
                case "2","импорт гостей" -> importController.guestImport();
                case "3","импорт сервисов" -> importController.serviceImport();
                case "0","назад" -> {
                    return;
                }
            }
        }
    }

    public void menuExport(){
        while (true){
            view.helpExport();
            String line = view.askString("> (help - список команд, подгруппа экспорт) ");
            if (line.isEmpty()) {
                continue;
            }

            String cmd = line.toLowerCase();
            switch (cmd){
                case "1","экспорт комнат" -> exportController.roomsExport();
                case "2","экспорт гостей" -> exportController.guestExport();
                case "3","экспорт сервисов" -> exportController.serviceExport();
                case "0","назад" -> {
                    return;
                }
            }
        }
    }

    public void menuRooms() {

        while (true) {
            view.helpRooms();
            String line = view.askString("> (help - список команд, подгруппа комнат) ");
            if (line.isEmpty()) {
                continue;
            }

            String cmd = line.toLowerCase();
            switch (cmd) {
                case "1", "добавить комнату" -> roomsController.addRoom();
                case "2", "изменить цену комнаты" -> roomsController.setRoomPrice();
                case "3", "изменить статус комнаты" -> roomsController.setStatus();
                case "4", "общее число свободных номеров" -> roomsController.freeRoomsNumber();
                case "5", "свободные номера на определенную дату" -> roomsController.listRoomsFreeOn();
                case "help" -> view.helpRooms();
                case "0", "назад" -> {
                    return;
                }

            }
        }
    }

    public void menuGuest() {

        while (true) {
            view.helpGuest();
            String line = view.askString("> (help - список команд, подгруппа гости) ");
            if (line.isEmpty()) {
                continue;
            }
            String cmd = line.toLowerCase();
            switch (cmd) {
                case "1", "добавить гостя" -> guestsController.addHuman();
                case "2", "общее число постояльцев сегодня" -> guestsController.countActiveGuestsToday();
                case "3", "сколько должен платить постоялец" -> guestsController.computeRoomCharge();
                case "4", "удалить человека из номера" -> guestsController.removePeopleFromRoom();
                case "help" -> view.helpGuest();
                case "0" -> {
                    return;
                }
            }
        }
    }

    public void menuService() {


        while (true) {
            view.helpService();
            String line = view.askString("> (help - список команд, подгруппа сервисы) ");
            if (line.isEmpty()) {
                continue;
            }

            String cmd = line.toLowerCase();
            switch (cmd) {
                case "1", "добавить услугу" -> serviceController.addService();
                case "2", "изменить цену услуги" -> serviceController.setServicePrice();
                case "3", "записать услугу на гостя" -> serviceController.addUsageFromCatalog();
                case "help" -> view.helpService();
                case "0" -> {
                    return;
                }
            }
        }
    }

    public void menuDetails() {


        while (true) {
            view.helpDetails();
            String line = view.askString("> (help - список команд, подгруппа детали) ");
            if (line.isEmpty()) {
                continue;
            }

            String cmd = line.toLowerCase();
            switch (cmd) {
                case "1", "сортировать комнаты по цена/вместимость/звезды" -> sorterController.sortByStats();
                case "2", "сортировка свободных номеров" -> sorterController.freeSortRoomByStats();
                case "3", "список постояльцев и их номеров" -> sorterController.sortGuestsByAlphabetThenCheckout();
                case "4", "список услуг постояльца и их цену" -> sorterController.guestServices();
                case "5", "цены услуг и номеров" -> sorterController.prices();
                case "6", "последние 3 жильца" -> sorterController.last3GuestsOfRoom();
                case "7", "узнать детали комнаты" -> sorterController.roomDetails();
                case "help" -> view.helpDetails();
                case "0" -> {
                    return;
                }
            }

        }
    }
}
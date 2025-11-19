package com.senla.controller;

import com.senla.service.*;
import com.senla.service.impl.*;
import com.senla.view.AppDemoView;

import java.util.Scanner;

public class ControllersMenu {
    private final AppDemoControllerRooms roomsController;
    private final AppDemoControllerGuests guestsController;
    private final AppDemoControllerService serviceController;
    private final AppDemoControllerSorter sorterController;
    private final AppDemoView view;

    public ControllersMenu(AppDemoControllerRooms roomsController,
                           AppDemoControllerGuests guestsController,
                           AppDemoControllerService serviceController,
                           AppDemoControllerSorter sorterController,
                           AppDemoView view) {
        this.roomsController = roomsController;
        this.guestsController = guestsController;
        this.serviceController = serviceController;
        this.sorterController = sorterController;
        this.view = view;
    }
    public void menuRooms() {
        try (Scanner sc = new Scanner(System.in)) {

            while (true) {
                System.out.print("> (help - список команд) ");
                if (!sc.hasNextLine()) break;
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;

                String cmd = line.toLowerCase();
                switch (cmd){
                    case "1","добавить комнату" -> roomsController.addRoom();
                    case "2","изменить цену комнаты" -> roomsController.setRoomPrice();
                    case "3","изменить статус комнаты" -> roomsController.setStatus();
                    case "4","общее число свободных номеров" -> roomsController.freeRoomsNumber();
                    case "5","свободные номера на определенную дату" -> roomsController.listRoomsFreeOn();


                }
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
    }
   }

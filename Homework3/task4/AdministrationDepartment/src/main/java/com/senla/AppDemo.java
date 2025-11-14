package com.senla;

import com.senla.model.RoomStatus;
import com.senla.service.*;
import com.senla.service.impl.*;

public class AppDemo {

    private static int step = 1;

    private static void log(String message) {
        System.out.println();
        System.out.println("========== ШАГ " + step++ + " ==========");
        System.out.println(message);
    }



    public static void main(String[] args) {


        log("Создаём хранилище комнат...");
        Rooms rooms = new InMemoryRooms();

        log("Добавляем комнату 101");
        rooms.addRoom("101");

        log("Добавляем комнату 102");
        rooms.addRoom("102");

        log("Устанавливаем цену для комнаты 101: 1200");
        rooms.setRoomPrice("101", 1200);

        log("Устанавливаем статус для комнаты 102: MAINTENANCE");
        rooms.setRoomStatus("102", RoomStatus.MAINTENANCE);

        log("Создаём реестр гостей...");
        GuestRegistry guests = new InMemoryGuestRegistry(rooms);

        log("Добавляем гостя: Иван");
        guests.addHuman("Иван");

        log("Добавляем гостя: Мария");
        guests.addHuman("Мария");

        log("Создаём сервис заселения...");
        CheckInService checkIn = new DefaultCheckInService(rooms, guests);

        log("Выполняем попарное заселение гостей в комнаты...");
        checkIn.assignAllPairwise();

        log("Текущие заселения (результаты работы):");
        checkIn.printAssignments();


    }
}

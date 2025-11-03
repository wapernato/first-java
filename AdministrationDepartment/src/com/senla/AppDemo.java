package com.senla;

import com.senla.model.Room;
import com.senla.model.RoomStatus;
import com.senla.service.*;
import com.senla.service.impl.*;

import java.util.List;


public class AppDemo {

    private static void printAllStatsRooms(Rooms rooms){
        for (String num : rooms.getRoomsNumbers()) {
            Room r = rooms.getRoom(num);
            if (r != null) {
                System.out.printf(
                        "Комната %s: %d★, вместимость %d, цена %.2f, статус %s%n",
                        r.number(), r.stars(), r.capacity(), r.price(), r.status()
                );
            }
        }
    }



    public static void main(String[] args) {

        Rooms rooms = new InMemoryRooms(); // работа с комнатами
        GuestRegistry guests = new InMemoryGuestRegistry(rooms); // добавление гостей
        SortStats sorter = new CheckInSortStatus(); // сортировки/фильтры
        CheckInService service = new DefaultCheckInService(rooms, guests);
        // вывод списка людей

        rooms.addRoom("101", 1, 2);
        rooms.addRoom("102", 2, 3);
        rooms.addRoom("103", 3, 5);
        rooms.addRoom("104", 2, 3);
        rooms.addRoom("105", 2, 4);

        rooms.setRoomPrice("101", 800);
        rooms.setRoomPrice("102", 1000);
        rooms.setRoomPrice("103", 4000);
        rooms.setRoomPrice("104", 1000);
        rooms.setRoomPrice("105", 1200);

        rooms.setRoomCapacity("101", 3);

        //guests.addHuman("101","Иван");
        //guests.addHuman("101","Анна");
        //guests.addHuman("102","Мария");
        System.out.println("-----------------------------------------");
        guests.freeRooms();
        System.out.println("-----------------------------------------");
        guests.CountFreeRooms();
        System.out.println("-----------------------------------------");
        sorter.sortRoomByStars(rooms);
        System.out.println("-----------------------------------------");
        //sorter.sortRoomByCapacity(rooms);
        System.out.println(guests. AllWhoLivesInRooms()); // кто в какой комнате живет
        System.out.println("-----------------------------------------");
        guests.removePeopleFromRoom("101"); // Удаление людей их комнат
        System.out.println("-----------------------------------------");
        System.out.println(guests.AllWhoLivesInRooms()); // Провера всех, кто живет в какой-либо комнате
        guests.CountFreeRooms();
        //System.out.println(service.checkIn("101", "Иван")); //  проверка на "правда ли "Иван" живет в комнате"
        //System.out.println(rooms.getRoomsNumbers()); // все комнаты





    }
}
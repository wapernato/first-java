package com.senla.service.impl;


import com.senla.model.Room;
import com.senla.service.Rooms;
import com.senla.service.SortStats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CheckInSortStatus implements SortStats {


    @Override
    public void sortRoomByStars(Rooms rooms) {
        List<Room> snapshot = new ArrayList<>();
        for (String num : rooms.getRoomsNumbers()) {
            Room r = rooms.getRoom(num);
            if (r != null) snapshot.add(r);
        }
        snapshot.sort(
                Comparator.comparingInt(Room::stars).reversed()
        );


        for (Room r : snapshot) {
            System.out.printf("Комната %s: %d★, вместимость %d, цена %.2f, статус %s%n",
                    r.number(), r.stars(), r.capacity(), r.price(), r.status());
        }
    }

    @Override
    public void sortRoomByCapacity(Rooms rooms){
        List<Room> snapshot = new ArrayList<>();
        for (String num : rooms.getRoomsNumbers()) {
            Room r = rooms.getRoom(num);
            if (r != null) snapshot.add(r);
        }
        snapshot.sort(
                Comparator.comparingInt(Room::capacity).reversed()
        );


        for (Room r : snapshot) {
            System.out.printf("Комната %s: %d★, вместимость %d, цена %.2f, статус %s%n",
                    r.number(), r.stars(), r.capacity(), r.price(), r.status());
        }
    }

    @Override
    public void sortRoomByPrice(Rooms rooms){
        List<Room> snapshot = new ArrayList<>();
        for (String num : rooms.getRoomsNumbers()){
            Room r = rooms.getRoom(num);
            if (r != null) snapshot.add(r);
        }

        snapshot.sort(
                Comparator.comparingDouble(Room::price)
        );

        for (Room r : snapshot) {
            System.out.printf("Комната %s: %d★, вместимость %d, цена %.2f, статус %s%n",
                    r.number(), r.stars(), r.capacity(), r.price(), r.status());
        }
    }
}
















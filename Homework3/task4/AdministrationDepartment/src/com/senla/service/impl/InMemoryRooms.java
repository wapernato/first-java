package com.senla.service.impl;


import com.senla.model.Room;
import com.senla.model.RoomStatus;
import com.senla.service.Rooms;


import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


public class InMemoryRooms implements Rooms {
    private final Map<String, Room> rooms = new LinkedHashMap<>();


    @Override
    public Set<String> getRoomsNumbers() {
        return Collections.unmodifiableSet(rooms.keySet());
    }


    @Override
    public void addRoom(String number) {
        if (rooms.containsKey(number)) {
            System.out.println("Номер уже существует: " + number);
            return;
        }
        rooms.put(number, new Room(number));
    }


    @Override
    public int count() {
        return rooms.size();
    }


    @Override
    public void setRoomStatus(String number, RoomStatus status) {
        Room r = rooms.get(number);
        if (r == null) {
            System.out.println("Нет такого номера: " + number);
            return;
        }
        r.setStatus(status);
    }


    @Override
    public void setRoomPrice(String number, double price) {
        if (price < 0) {
            System.out.println("Цена не может быть отрицательной: " + price);
            return;
        }
        Room r = rooms.get(number);
        if (r == null) {
            System.out.println("Нет такого номера: " + number);
            return;
        }
        r.setPrice(price);
    }


    @Override
    public Room getRoom(String number) {
        return rooms.get(number);
    }


    @Override
    public boolean isRoomBookable(String number) {
        Room r = rooms.get(number);
        return r != null && r.status() != RoomStatus.MAINTENANCE;
    }
}
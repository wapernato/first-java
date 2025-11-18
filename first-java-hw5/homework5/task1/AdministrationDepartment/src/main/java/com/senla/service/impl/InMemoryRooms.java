package com.senla.service.impl;


import com.senla.model.OccupancyStatus;
import com.senla.model.Room;
import com.senla.model.RoomStatus;
import com.senla.service.Rooms;


import java.util.*;


public class InMemoryRooms implements Rooms {
    private final Map<String, Room> rooms = new HashMap<>();

    @Override
    public Set<String> getRoomsNumbers() {
        return Collections.unmodifiableSet(rooms.keySet());
    }
    @Override
    public Set<String> freeRoomsNumber(){
        Set<String> out = new HashSet<>();
        for(String num : getRoomsNumbers()){
            Room r = getRoom(num);
            if(r != null && r.occupancyStatus() == OccupancyStatus.VACANT && r.status() == RoomStatus.AVAILABLE){
                out.add(num);
            }
        }
        return out;
    }

    // --------------------check this--------------------------
    @Override
    public void addRoom(String number, int capacity, int stars) {
        if (rooms.containsKey(number)) {
            System.out.println("Номер уже существует: " + number);
            return; 
        }
        if ((capacity > 3) || (capacity < 0)){
            System.out.println("Вместимость в одну комнату должны быть не более 3-ех человек и не меньше 0");
            return;
        }
        if (stars > 5 || stars < 0){
            System.out.println("Количество звезд у номера не должно превышать 5 или быть меньше 0");
            return;
        }
        Room r = new Room(number);
        r.setCapacity(capacity);
        r.setStars(stars);
        rooms.put(number, r);
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
        //rooms.put(number, r);
    }

    @Override
    public void setRoomCapacity(String number, int capacity){
        if ((capacity > 3) || (capacity < 0)){
            System.out.println("Вместимость в одну комнату должны быть не более 3-ех человек и не меньше 0");
            return;
        }
        Room r = rooms.get(number);
        if (r == null){
            System.out.println("Нет такого номера: " + number);
            return;
        }
        r.setCapacity(capacity);
    }


    @Override
    public void setRoomStars(String number, int stars){
        if (stars > 5 || stars < 0){
            System.out.println("Количество звезд у номера не должно превышать 5 или быть меньше 0");
            return;
        }
        Room r = rooms.get(number);
        if (r == null) {
            System.out.println("Нет такого номера: " + number);
            return;
        }
        r.setStars(stars);
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
package com.senla.service.impl;


import com.senla.model.OccupancyStatus;
import com.senla.model.Room;
import com.senla.model.RoomStatus;
import com.senla.service.Rooms;


import java.util.*;
import java.util.stream.Collectors;


public class InMemoryRooms implements Rooms {

    private final Map<Integer, Room> roomsIds = new HashMap<>();
    private int nextId = 1; // максимальному ключу map +1

    public int getNextId(){ return nextId; }

    public Set<Integer> getnumber(){
        return Collections.unmodifiableSet(roomsIds.keySet());
    }

    @Override
    public Set<String> getRoomsNumbers() {
        return roomsIds.values().stream()
                .map(Room::getNumber)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Set<String> freeRoomsNumber() {
        return roomsIds.values().stream()
                .filter(r -> r.getOccupancyStatus() == OccupancyStatus.VACANT)
                .filter(r -> r.getStatus() == RoomStatus.AVAILABLE)
                .map(Room::getNumber)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Map<Integer, RoomsDto> getAllInfoRooms() {
        return roomsIds.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> new RoomsDto(
                                e.getValue().getNumber(),
                                e.getValue().getCapacity(),
                                e.getValue().getStars(),
                                e.getValue().getPrice(),
                                e.getValue().getStatus(),
                                e.getValue().getOccupancyStatus()
                        )
                ));
    }

    @Override
    public Map<Integer, Room> getAllRooms(){
        return Collections.unmodifiableMap(roomsIds);
    }

    @Override
    public void addRoomDes(Map<Integer, Room> rooms){
        roomsIds.putAll(rooms);
    }

    @Override
    public void addRoom(String number, int capacity, int stars, double price) {
        if (getRoomsNumbers().contains(number)) {
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
        if(price < 0){
            System.out.println("Цена комнаты не может быть отрицательна или равна нулю");
            return;
        }

        int id = nextId++;

        Room r = new Room(number, capacity, stars, price);
        roomsIds.put(id, r);

    }

    public int countCapacity(){
        List<Integer> capacity = roomsIds.values().stream()
                .map(Room::getCapacity)
                .toList();

        return capacity.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }


    @Override
    public int count() {
        return roomsIds.size();
    }


    @Override
    public void setRoomStatus(String numberRoom, RoomStatus status) {
        Room r = roomsIds.values().stream()
                .filter(room -> room.getNumber().equals(numberRoom))
                .findFirst()
                .orElse(null);
        if (r == null) {
            System.out.println("Нет такого номера: " + numberRoom);
            return;
        }
        r.setStatus(status);
    }

    @Override
    public void setRoomPrice(String numberRoom, double price) {
        if (price < 0) {
            System.out.println("Цена не может быть отрицательной: " + price);
            return;
        }
        Room r = roomsIds.values().stream()
                .filter(room -> room.getNumber().equals(numberRoom))
                .findFirst()
                .orElse(null);
        if (r == null) {
            System.out.println("Нет такого номера: " + numberRoom);
            return;
        }
        r.setPrice(price);
    }

    @Override
    public void setRoomCapacity(String numberRoom, int capacity){
        if ((capacity > 3) || (capacity < 0)){
            System.out.println("Вместимость в одну комнату должны быть не более 3-ех человек и не меньше 0");
            return;
        }
        Room r = roomsIds.values().stream()
                .filter(room -> room.getNumber().equals(numberRoom))
                .findFirst()
                .orElse(null);
        if (r == null){
            System.out.println("Нет такого номера: " + numberRoom);
            return;
        }
        r.setCapacity(capacity);
    }


    @Override
    public void setRoomStars(String numberRoom, int stars){
        if (stars > 5 || stars < 0){
            System.out.println("Количество звезд у номера не должно превышать 5 или быть меньше 0");
            return;
        }
        Room r = roomsIds.values().stream()
                .filter(room -> room.getNumber().equals(numberRoom))
                .findFirst()
                .orElse(null);
        if (r == null) {
            System.out.println("Нет такого номера: " + numberRoom);
            return;
        }
        r.setStars(stars);
    }
    @Override
    public Room getRoom(String number) {
        return roomsIds.values().stream()
                .filter(r -> r.getNumber().equals(number))
                .findFirst()
                .orElse(null);
    }

    // исправить
    @Override
    public boolean isRoomBookable(int id) {
        Room r = roomsIds.get(id);
        return r != null && r.getStatus() != RoomStatus.MAINTENANCE;
    }

    public void setNextId(int nextId) {
        this.nextId = nextId;
    }

}
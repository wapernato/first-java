package com.senla.service;


import com.senla.model.OccupancyStatus;
import com.senla.model.Room;
import com.senla.model.RoomStatus;


import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;



public interface Rooms {

    public int getNextId();
    Set<String> getRoomsNumbers();
    Set<String> freeRoomsNumber();
    Set<Integer> getnumber();
    Map<Integer, Room> getAllRooms();

    void addRoomDes(Map<Integer, Room> rooms);

    void addRoom(String number, int capacity, int stars, double price);
    int countCapacity();
    int count();


    void setRoomStatus(String number, RoomStatus status);
    void setRoomPrice(String number, double price);
    void setRoomStars(String number, int stars);
    void setNextId(int nextId);
    void setRoomCapacity(String number, int capacity);
    public record RoomsDto(String number, int capacity, int stars, double price, RoomStatus status, OccupancyStatus occupancyStatus){}
    Map<Integer, RoomsDto> getAllInfoRooms();
    Room getRoom(String number);
    boolean isRoomBookable(int id);
}
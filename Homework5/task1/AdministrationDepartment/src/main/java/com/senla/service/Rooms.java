package com.senla.service;


import com.senla.model.Room;
import com.senla.model.RoomStatus;


import java.util.List;
import java.util.Set;



public interface Rooms {
    //int getId();

    Set<String> getRoomsNumbers();
    Set<String> freeRoomsNumber();


    //List<String> freeRooms();
    void addRoom(String number, int capacity, int stars);
    int count();

    void setRoomStatus(String number, RoomStatus status);
    void setRoomPrice(String number, double price);
    void setRoomStars(String number, int stars);
    void setRoomCapacity(String number, int capacity);
    //void getAllStatusRoom(String number);

    Room getRoom(String number);
    boolean isRoomBookable(String number);
}
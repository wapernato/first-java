package com.senla.service;


import com.senla.model.Room;
import com.senla.model.RoomStatus;


import java.util.Set;


/**
 * Контракт для работы с номерами отеля.
 */
public interface Rooms {
    Set<String> getRoomsNumbers();
    void addRoom(String number);
    int count();


    void setRoomStatus(String number, RoomStatus status);
    void setRoomPrice(String number, double price);


    Room getRoom(String number);
    boolean isRoomBookable(String number);
}
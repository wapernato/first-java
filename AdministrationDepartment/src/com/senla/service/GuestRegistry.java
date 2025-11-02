package com.senla.service;


import java.util.List;
import java.util.Map;
import java.util.Set;



public interface GuestRegistry {
    List<String> getListOfPeople(String roomId);
    void addHuman(String roomId,String human3);
    void freeRooms();
    void removePeopleFromRoom(String roomId);
    void CountFreeRooms();
    Map<String, List<String>> AllWhoLivesInRooms();
}
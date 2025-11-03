package com.senla.service;


import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.time.*;



public interface GuestRegistry {
    List<String> getListOfPeople(String roomId);
    void addHuman(String roomId, String human3, LocalDate dateCheckIn, LocalDate dateEvicion);
    void freeRooms();
    void removePeopleFromRoom(String roomId);
    void CountFreeRooms();
    Map<String, List<String>> AllWhoLivesInRooms();
}
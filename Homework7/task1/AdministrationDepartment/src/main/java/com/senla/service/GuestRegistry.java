package com.senla.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface GuestRegistry {

    void CountFreeRooms();

    Map<String, List<String>> AllWhoLivesInRooms();

    class GuestEntry {

        public int id;
        public String guest;
        public String roomId;
        public LocalDate checkIn;
        public LocalDate checkOut;

        public GuestEntry() {}

        public GuestEntry(String guest, String roomId,
                          LocalDate checkIn, LocalDate checkOut, int id) {
            this.id = id;
            this.guest = guest;
            this.roomId = roomId;
            this.checkIn = checkIn;
            this.checkOut = checkOut;

        }

        @Override
        public String toString() {
            return guest + " — комната " + roomId + " — " + checkIn + " → " + checkOut;
        }
    }


    List<String> getListOfPeople(String roomId);

    List<GuestEntry> getAllGuestEntries();

    List<String> listRoomsFreeOn(LocalDate date);

    List<GuestEntry> last3GuestsOfRoom(String roomId);

    void addHuman(String roomId, String guest, LocalDate checkIn, int nights);

    void freeRooms();

    void removePeopleFromRoom(String roomId);

    int countActiveGuestsToday();

    double computeRoomCharge(String roomId, String guestName);

    List<GuestRegistry.GuestEntry> countGuestOfRoom(String roomId, Integer roomsHistoryLimit);

    Set<Integer> getGuestId();

    ArrayList<Object> getRoomId();

    void setGuestStats(String newRoomNumber, String guest,
                       LocalDate start, LocalDate end, int id);


}

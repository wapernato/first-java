package com.senla.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface GuestRegistry {


    List<String> getListOfPeople(String roomId);
    void addHuman(String roomId, String guest, LocalDate checkIn, int nights);
    void freeRooms();
    void removePeopleFromRoom(String roomId);
    void CountFreeRooms();
    Map<String, List<String>> AllWhoLivesInRooms();

    List<GuestEntry> getAllGuestEntries();


    Set<Integer> getGuestId();


    int countActiveGuestsToday();


    List<String> listRoomsFreeOn(LocalDate date);


    ArrayList<Object> getRoomId();

    void setGuestStats(String newRoomNumber, String guest,
                       LocalDate start, LocalDate end, int id);


    List<GuestEntry> last3GuestsOfRoom(String roomId);


    double computeRoomCharge(String roomId, String guestName);



    final class GuestEntry {
        public final int id;
        public final String guest;
        public final String roomId;
        public final LocalDate checkIn;
        public final LocalDate checkOut;

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
}

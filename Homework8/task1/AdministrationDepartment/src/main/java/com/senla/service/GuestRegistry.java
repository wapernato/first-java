package com.senla.service;

import com.senla.service.impl.InMemoryGuestRegistry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface GuestRegistry {

    void CountFreeRooms();

    public int getNextId();
    Map<String, List<String>> AllWhoLivesInRooms();


    class GuestEntry {

        public int id;
        public String guest;
        public String number;
        public LocalDate checkIn;
        public LocalDate checkOut;

        public GuestEntry() {}

        public InMemoryGuestRegistry.Stay toStay() {
            return new InMemoryGuestRegistry.Stay(
                    this.number,
                    this.guest,
                    this.checkIn,
                    this.checkOut,
                    this.id
            );
        }

        public GuestEntry(String guest, String number,
                          LocalDate checkIn, LocalDate checkOut, int id) {
            this.id = id;
            this.guest = guest;
            this.number = number;
            this.checkIn = checkIn;
            this.checkOut = checkOut;

        }

        public String getGuest() { return guest; }
        public String getnumber() { return number; }
        public LocalDate getCheckIn() { return checkIn; }
        public LocalDate getCheckOut() { return checkOut; }
        public int getId() { return id; }

        public void setGuest(String guest) { this.guest = guest; }
        public void setnumber(String number) { this.number = number; }
        public void setCheckIn(LocalDate checkIn) { this.checkIn = checkIn; }
        public void setCheckOut(LocalDate checkOut) { this.checkOut = checkOut; }
        public void setId(int id) { this.id = id; }

        @Override
        public String toString() {
            return guest + " — комната " + number + " — " + checkIn + " → " + checkOut;
        }
    }

    void putAllAfterDes(List<GuestEntry> guests);

    List<String> getListOfPeople(String number);

    List<GuestEntry> getAllGuestEntries();

    List<String> listRoomsFreeOn(LocalDate date);

    List<GuestEntry> last3GuestsOfRoom(String number);

    void addHuman(String number, String guest, LocalDate checkIn, int nights);

    void freeRooms();

    void removePeopleFromRoom(String number);

    int countActiveGuestsToday();

    void setNextId(int nextId);

    double computeRoomCharge(String number, String guestName);

    Set<Integer> getGuestId();

    ArrayList<Object> getnumber();

    void setGuestStats(String newRoomNumber, String guest,
                       LocalDate start, LocalDate end, int id);


}

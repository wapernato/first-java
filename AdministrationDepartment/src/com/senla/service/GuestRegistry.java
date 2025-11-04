package com.senla.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface GuestRegistry {
    List<String> getListOfPeople(String roomId);
    void addHuman(String roomId, String human3, LocalDate checkIn, int nights);
    void freeRooms();
    void removePeopleFromRoom(String roomId);
    void CountFreeRooms();
    Map<String, List<String>> AllWhoLivesInRooms();

    // уже было
    List<GuestEntry> getAllGuestEntries();

    // (2) общее число постояльцев СЕГОДНЯ
    int countActiveGuestsToday();

    // (3) список номеров, свободных на указанную дату (будущее/любая дата)
    List<String> listRoomsFreeOn(LocalDate date);

    // (7) 3 последних постояльца номера
    List<GuestEntry> last3GuestsOfRoom(String roomId);

    // (8) сумма к оплате за номер для постояльца (только проживание: цена номера * ночи)
    double computeRoomCharge(String roomId, String guestName);

    // DTO
    final class GuestEntry {
        public final String guest;
        public final String roomId;
        public final LocalDate checkIn;
        public final LocalDate checkOut;

        public GuestEntry(String guest, String roomId, LocalDate checkIn, LocalDate checkOut) {
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

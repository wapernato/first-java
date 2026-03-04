package com.senla.postgreSQL;

import com.senla.DAO.ReservationDAO;
import com.senla.annotation.Inject;
import com.senla.model.OccupancyStatus;
import com.senla.model.ReservationStatus;
import com.senla.model.forDAO.Reservation;
import com.senla.model.forDAO.Room;
import com.senla.service.GuestRegistry;
import com.senla.service.Rooms;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class DaoGuestRegistry implements GuestRegistry {


    @Override
    public void CountFreeRooms() {

    }

    @Override
    public int getNextId() {
        return 0;
    }

    @Override
    public Map<String, List<String>> AllWhoLivesInRooms() {
        return Map.of();
    }

    @Override
    public void putAllAfterDes(List<GuestEntry> guests) {

    }

    @Override
    public List<String> getListOfPeople(String number) {
        return List.of();
    }

    @Override
    public List<GuestEntry> getAllGuestEntries() {
        return List.of();
    }

    @Override
    public List<String> listRoomsFreeOn(LocalDate date) {
        return List.of();
    }

    @Override
    public List<GuestEntry> last3GuestsOfRoom(String number) {
        return List.of();
    }

    @Override
    public void addHuman(String number, String guest, LocalDate checkIn, int nights) {

    }

    @Override
    public void freeRooms() {

    }

    @Override
    public void removePeopleFromRoom(String number) {

    }

    @Override
    public int countActiveGuestsToday() {
        return 0;
    }

    @Override
    public void setNextId(int nextId) {

    }

    @Override
    public double computeRoomCharge(String number, String guestName) {
        return 0;
    }

    @Override
    public Set<Integer> getGuestId() {
        return Set.of();
    }

    @Override
    public ArrayList<Object> getnumber() {
        return null;
    }

    @Override
    public void setGuestStats(String newRoomNumber, String guest, LocalDate start, LocalDate end, int id) {

    }
}
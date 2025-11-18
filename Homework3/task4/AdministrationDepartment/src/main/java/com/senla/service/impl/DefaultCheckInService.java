package com.senla.service.impl;

import com.senla.model.Assignment;
import com.senla.service.CheckInService;
import com.senla.service.GuestRegistry;
import com.senla.service.Rooms;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class DefaultCheckInService implements CheckInService {
    private final Rooms rooms;
    private final GuestRegistry guests;

    private final Set<Assignment> assignments = new LinkedHashSet<>();

    public DefaultCheckInService(Rooms rooms, GuestRegistry guests) {
        this.rooms = rooms;
        this.guests = guests;
    }

    @Override
    public boolean checkIn(String roomNumber, String guestName) {
        if (!rooms.isRoomBookable(roomNumber)) {
            System.out.println("Номер недоступен: " + roomNumber);
            return false;
        }
        if (isRoomBusy(roomNumber)) {
            System.out.println("Комната уже занята: " + roomNumber);
            return false;
        }
        if (isGuestCheckedIn(guestName)) {
            System.out.println("Гость уже заселён: " + guestName);
            return false;
        }
        assignments.add(new Assignment(roomNumber, guestName));
        return true;
    }

    @Override
    public void assignAllPairwise() {
        Iterator<String> roomIt = rooms.getRoomsNumbers().iterator();
        Iterator<String> peopleIt = guests.getListOfPeople().iterator();
        while (roomIt.hasNext() && peopleIt.hasNext()) {
            String room = roomIt.next();
            String guest = peopleIt.next();
            checkIn(room, guest);
        }
    }

    @Override
    public boolean checkoutByRoom(String roomNumber) {
        Iterator<Assignment> it = assignments.iterator();
        while (it.hasNext()) {
            Assignment a = it.next();
            if (a.roomNumber().equals(roomNumber)) {
                it.remove();
                return true;
            }
        }
        System.out.println("Никто не живёт в комнате: " + roomNumber);
        return false; // <- этого не хватало
    }

    @Override
    public boolean checkoutByGuest(String guestName) {
        Iterator<Assignment> it = assignments.iterator();
        while (it.hasNext()) {
            Assignment a = it.next();
            if (a.guestName().equals(guestName)) {
                it.remove();
                return true;
            }
        }
        System.out.println("Такой гость не заселён: " + guestName);
        return false;
    }

    @Override
    public void printAssignments() {
        for (Assignment a : assignments) {
            System.out.println(a.roomNumber() + " -> " + a.guestName());
        }
    }

    @Override
    public Set<Assignment> viewAssignments() {
        return Collections.unmodifiableSet(assignments);
    }

    // ─── helpers ──────────────────────────────────────────────────────────────

    private boolean isRoomBusy(String roomNumber) {
        return assignments.stream().anyMatch(a -> a.roomNumber().equals(roomNumber));
    }

    private boolean isGuestCheckedIn(String guestName) {
        return assignments.stream().anyMatch(a -> a.guestName().equals(guestName));
    }
}



package com.senla;

import com.senla.model.RoomStatus;
import com.senla.service.*;
import com.senla.service.impl.*;

public class AppDemo {
    public static void main(String[] args) {
        Rooms rooms = new InMemoryRooms();
        rooms.addRoom("101");
        rooms.addRoom("102");
        rooms.setRoomPrice("101", 1200);
        rooms.setRoomStatus("102", RoomStatus.MAINTENANCE);

        GuestRegistry guests = new InMemoryGuestRegistry(rooms);
        guests.addHuman("Иван");
        guests.addHuman("Мария");

        CheckInService checkIn = new DefaultCheckInService(rooms, guests);
        checkIn.assignAllPairwise();
        checkIn.printAssignments();
    }
}


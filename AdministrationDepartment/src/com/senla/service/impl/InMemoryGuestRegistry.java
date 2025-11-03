package com.senla.service.impl;


import com.senla.model.OccupancyStatus;
import com.senla.model.Room;
import com.senla.service.GuestRegistry;
import com.senla.service.Rooms;
import java.time.*;
import java.time.format.DateTimeFormatter;


import java.util.*;


public class InMemoryGuestRegistry implements GuestRegistry {
    private final Map<String, List<String>> peopleByRoom = new HashMap<>();

    private final Rooms rooms;


    public InMemoryGuestRegistry(Rooms rooms) { this.rooms = rooms; }

    @Override
    public List<String> getListOfPeople(String roomId) {
        return Collections.unmodifiableList(peopleByRoom.getOrDefault(roomId, Collections.emptyList()));
    }


    @Override
    public void addHuman(String roomId, String human, LocalDate dateCheckIn, LocalDate dateEvicion) {

        if (rooms.getRoom(roomId) == null) {
            System.out.println("Комнаты " + roomId + " не сущетсвует");
            return;
        }
        if (!rooms.isRoomBookable(roomId)){
            System.out.println("Комната в ремонте или обслуживании " + roomId);
            return;
        }
        List<String> list = peopleByRoom.computeIfAbsent(roomId, k -> new ArrayList<>());
        Room room = rooms.getRoom(roomId);
        if (list.size() >= room.capacity()) {
            System.out.println(roomId + " комната рассчитана на не более (" + room.capacity() + ")");
            return;
        }
        list.add(human);
        if (list.size() == 1) {
            room.setOccupancyStatus(OccupancyStatus.OCCUPIED);
        }
    }
    public Map<String, List<String>> AllWhoLivesInRooms(){
        Map<String, List<String>> allNames = new LinkedHashMap<>();
        for(String num : rooms.getRoomsNumbers()){
            Room r = rooms.getRoom(num);
            if (r != null && r.occupancyStatus() == OccupancyStatus.OCCUPIED){
                List<String> list = peopleByRoom.getOrDefault(num, Collections.emptyList());
                if (!list.isEmpty()) {
                    allNames.put(num, new ArrayList<>(list));
                }
            }
        }
        return allNames;
    }

    public void freeRooms() {
        List<String> freeRooms = new ArrayList<>();
        for (String num : rooms.getRoomsNumbers()) {
            Room r = rooms.getRoom(num);
            if (r != null && r.occupancyStatus() == OccupancyStatus.VACANT) {
                freeRooms.add(num);
            }
        }
        for (int i = 0; i < freeRooms.size(); i++) {
            System.out.println(i + 1 + ": " + freeRooms.get(i));
        }
    }

    public void CountFreeRooms() {
        List<String> freeRooms = new ArrayList<>();
        for (String num : rooms.getRoomsNumbers()) {
            Room r = rooms.getRoom(num);
            if (r != null && r.occupancyStatus() == OccupancyStatus.VACANT) {
                freeRooms.add(num);
            }
        }
        System.out.println("Все свободные комнаты на данный момент: " + freeRooms.size());
    }

    public void removePeopleFromRoom(String roomId){
        //Room r = rooms.getRoom(roomId);
        List<String> list = peopleByRoom.get(roomId);
        if (list.isEmpty()) {
            System.out.println("В комнате никого нету");
            return;
        }
        list.clear();
        Room r = rooms.getRoom(roomId);
        r.setOccupancyStatus(OccupancyStatus.VACANT);
    }
}
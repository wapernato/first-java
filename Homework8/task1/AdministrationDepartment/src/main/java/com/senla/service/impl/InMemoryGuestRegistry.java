
package com.senla.service.impl;

import com.senla.annotation.ConfigProperty;
import com.senla.model.OccupancyStatus;
import com.senla.model.Room;
import com.senla.service.GuestRegistry;
import com.senla.service.Rooms;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryGuestRegistry implements GuestRegistry {


    private final Rooms rooms;

    private final Map<Integer, Stay> staysById = new HashMap<>(); // id - ALLGUESTS
    private final Map<String, List<Integer>> stayIdsByRoom = new HashMap<>();  // number - id

    private int nextId = 1;

    public int getNextId(){
        return nextId;
    }

    @Override
    public void setNextId(int nextId) {this.nextId = nextId; }

    public InMemoryGuestRegistry(Rooms rooms) {
        this.rooms = rooms;
    }

    public static class Stay {
        private final String number;
        private final String guest;
        private final LocalDate start;
        private final LocalDate end;
        private final int id;

        public Stay(String number, String guest, LocalDate start, LocalDate end, int id) {
            this.number = number;
            this.guest = guest;
            this.start = start;
            this.end = end;
            this.id = id;
        }

        public String number() { return number; }
        public String guest() { return guest; }
        public LocalDate start() { return start; }
        public LocalDate end() { return end; }
        public int id() { return id; }
    }



    private boolean overlaps(LocalDate s1, LocalDate e1, LocalDate s2, LocalDate e2) {
        // intersection if NOT (e1 <= s2 OR e2 <= s1)
        return !( !e1.isAfter(s2) || !e2.isAfter(s1) );
    }

    private List<Stay> staysInRoom(String number) {
        return stayIdsByRoom.getOrDefault(number, List.of()).stream()
                .map(staysById::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private void removeStayIdFromRoomIndex(String number, int stayId) {
        List<Integer> ids = stayIdsByRoom.get(number);
        if (ids == null) return;
        ids.remove(Integer.valueOf(stayId));
        if (ids.isEmpty()) stayIdsByRoom.remove(number);
    }

    private void refreshRoomOccupancy(String number) {
        Room room = rooms.getRoom(number);
        if (room == null) return;

        LocalDate today = LocalDate.now();
        boolean occupied = staysInRoom(number).stream()
                .anyMatch(s -> overlaps(s.start, s.end, today, today.plusDays(1)));

        room.setOccupancyStatus(occupied ? OccupancyStatus.OCCUPIED : OccupancyStatus.VACANT);
    }


    @Override
    public List<String> getListOfPeople(String number) {
        refreshRoomOccupancy(number);

        return staysInRoom(number).stream()
                .map(Stay::guest)
                .collect(Collectors.toList());
    }

    @Override
    public void addHuman(String number, String human, LocalDate checkIn, int nights) {
        Room room = rooms.getRoom(number);
        if (room == null) {
            System.out.println("Нет такого номера: " + number);
            return;
        }
        LocalDate today = LocalDate.now();
        if(checkIn.isBefore(today)){
            System.out.println("Дата должны быть позже: " + today);
            return;
        }
        if (nights <= 0) {
            System.out.println("Количество ночей должно быть > 0");
            return;
        }

        LocalDate checkOut = checkIn.plusDays(nights); // [checkIn, checkOut)


        long overlapping = staysInRoom(number).stream()
                .filter(s -> overlaps(s.start, s.end, checkIn, checkOut))
                .count();

        if (overlapping >= room.getCapacity()) {
            System.out.println("Номер " + number + " переполнен на период " +
                    checkIn + "–" + checkOut + ". Вместимость: " + room.getCapacity());
            return;
        }

        int id = nextId++;
        Stay stay = new Stay(number, human, checkIn, checkOut, id);

        staysById.put(id, stay);
        stayIdsByRoom.computeIfAbsent(number, k -> new ArrayList<>()).add(id);

        room.setOccupancyStatus(OccupancyStatus.OCCUPIED);
    }


    // оптимизировать
    @Override
    public void freeRooms() {
        LocalDate today = LocalDate.now();

        Iterator<Map.Entry<Integer, Stay>> it = staysById.entrySet().iterator();
        while (it.hasNext()) {
            Stay s = it.next().getValue();
            if (!s.end.isAfter(today)) {
                it.remove();
                removeStayIdFromRoomIndex(s.number, s.id);
            }
        }

        for (String number : rooms.getRoomsNumbers()) {
            refreshRoomOccupancy(number);
        }
    }


    @Override
    public Map<String, List<String>> AllWhoLivesInRooms() {
        freeRooms();

        Map<String, List<String>> res = new HashMap<>();
        for (String number : stayIdsByRoom.keySet()) {
            List<String> guests = staysInRoom(number).stream()
                    .map(Stay::guest)
                    .collect(Collectors.toList());
            res.put(number, guests);
        }
        return res;
    }



    @Override
    public void CountFreeRooms() {
        freeRooms();

        int free = 0;
        for (String number : rooms.getRoomsNumbers()) {
            Room r = rooms.getRoom(number);
            if (r != null && r.getOccupancyStatus() == OccupancyStatus.VACANT) {
                free++;
            }
        }
        System.out.println("Свободных номеров: " + free);
    }

    @Override
    public List<GuestRegistry.GuestEntry> getAllGuestEntries() {
        return staysById.values().stream()
                .sorted(Comparator.comparing(Stay::start))
                .map(s -> new GuestRegistry.GuestEntry( s.guest,s.number, s.start, s.end, s.id))
                .collect(Collectors.toList());
    }

    @Override
    public int countActiveGuestsToday() {
        LocalDate today = LocalDate.now();
        return (int) staysById.values().stream()
                .filter(s -> overlaps(s.start, s.end, today, today.plusDays(1)))
                .count();
    }

    @Override
    public List<String> listRoomsFreeOn(LocalDate date) {
        List<String> free = new ArrayList<>();
        for (String number : rooms.getRoomsNumbers()) {
            Room room = rooms.getRoom(number);
            if (room == null) continue;

            boolean occupied = staysInRoom(number).stream()
                    .anyMatch(s -> overlaps(s.start, s.end, date, date.plusDays(1)));

            if (!occupied) free.add(number);
        }
        return free;
    }

    @Override
    public List<GuestRegistry.GuestEntry> last3GuestsOfRoom(String number) {
        return staysInRoom(number).stream()
                .sorted(Comparator.comparing(Stay::end).reversed())
                .limit(3)
                .map(s -> new GuestRegistry.GuestEntry( s.guest,s.number, s.start, s.end, s.id))
                .collect(Collectors.toList());
    }


    // улучшить
    @Override
    public double computeRoomCharge(String number, String guestName) {
        Room r = rooms.getRoom(number);
        if (r == null) return 0.0;

        Stay lastStay = staysInRoom(number).stream()
                .filter(s -> Objects.equals(s.guest, guestName))
                .max(Comparator.comparing(Stay::end))
                .orElse(null);

        if (lastStay == null) return 0.0;

        long nights = ChronoUnit.DAYS.between(lastStay.start, lastStay.end);
        return r.getPrice() * nights;
    }

    @Override
    public void removePeopleFromRoom(String number) {
        List<Integer> ids = stayIdsByRoom.remove(number);
        if (ids == null || ids.isEmpty()) {
            System.out.println("Комната пуста: " + number);
            return;
        }

        for (Integer id : ids) {
            staysById.remove(id);
        }

        Room room = rooms.getRoom(number);
        if (room != null) room.setOccupancyStatus(OccupancyStatus.VACANT);
    }

    @Override
    public ArrayList<Object> getnumber() {

        ArrayList<Object> x = new ArrayList<>();
        for (String number : stayIdsByRoom.keySet()) {
            x.add(staysInRoom(number));
        }
        System.out.println(x);
        return x;
    }

    public Set<Integer> getGuestId() {
        return Collections.unmodifiableSet(staysById.keySet());
    }


    @Override
    public void setGuestStats(String newRoomNumber, String guest, LocalDate start, LocalDate end, int id) {
        LocalDate today = LocalDate.now();
        if(start.isBefore(today)){
            System.out.println("Дата должна быть позже: " + today);
            return;
        }
        Stay old = staysById.get(id);
        if (old != null) {
            removeStayIdFromRoomIndex(old.number, id);
        }
        Stay stay = new Stay(newRoomNumber, guest, start, end, id);
        staysById.put(id, stay);
        stayIdsByRoom.computeIfAbsent(newRoomNumber, k -> new ArrayList<>()).add(id);

        refreshRoomOccupancy(newRoomNumber);
        if (old != null) refreshRoomOccupancy(old.number);
    }

    @Override
    public void putAllAfterDes(List<GuestEntry> guests){

        for(GuestEntry e : guests){
            Stay stay = e.toStay();

            staysById.put(stay.id, stay);

            stayIdsByRoom.computeIfAbsent(stay.number, k -> new ArrayList<>()).add(stay.id);
        }

    }

}
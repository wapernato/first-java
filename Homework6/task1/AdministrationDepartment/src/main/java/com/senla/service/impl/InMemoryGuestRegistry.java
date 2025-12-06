
package com.senla.service.impl;

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

    private final Map<Integer, Stay> staysById = new HashMap<>();
    private final Map<String, List<Integer>> stayIdsByRoom = new HashMap<>();

    private int nextId = 1;

    public InMemoryGuestRegistry(Rooms rooms) {
        this.rooms = rooms;
    }


    public static class Stay {
        private final String roomId;
        private final String guest;
        private final LocalDate start;
        private final LocalDate end;
        private final int id;

        public Stay(String roomId, String guest, LocalDate start, LocalDate end, int id) {
            this.roomId = roomId;
            this.guest = guest;
            this.start = start;
            this.end = end;
            this.id = id;
        }

        public String roomId() { return roomId; }
        public String guest() { return guest; }
        public LocalDate start() { return start; }
        public LocalDate end() { return end; }
        public int id() { return id; }
    }



    private boolean overlaps(LocalDate s1, LocalDate e1, LocalDate s2, LocalDate e2) {
        // intersection if NOT (e1 <= s2 OR e2 <= s1)
        return !( !e1.isAfter(s2) || !e2.isAfter(s1) );
    }

    private List<Stay> staysInRoom(String roomId) {
        return stayIdsByRoom.getOrDefault(roomId, List.of()).stream()
                .map(staysById::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private void removeStayIdFromRoomIndex(String roomId, int stayId) {
        List<Integer> ids = stayIdsByRoom.get(roomId);
        if (ids == null) return;
        ids.remove(Integer.valueOf(stayId));
        if (ids.isEmpty()) stayIdsByRoom.remove(roomId);
    }

    private void refreshRoomOccupancy(String roomId) {
        Room room = rooms.getRoom(roomId);
        if (room == null) return;

        LocalDate today = LocalDate.now();
        boolean occupied = staysInRoom(roomId).stream()
                .anyMatch(s -> overlaps(s.start, s.end, today, today.plusDays(1)));

        room.setOccupancyStatus(occupied ? OccupancyStatus.OCCUPIED : OccupancyStatus.VACANT);
    }


    @Override
    public List<String> getListOfPeople(String roomId) {
        refreshRoomOccupancy(roomId);

        return staysInRoom(roomId).stream()
                .map(Stay::guest)
                .collect(Collectors.toList());
    }

    @Override
    public void addHuman(String roomId, String human, LocalDate checkIn, int nights) {
        Room room = rooms.getRoom(roomId);
        if (room == null) {
            System.out.println("Нет такого номера: " + roomId);
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


        long overlapping = staysInRoom(roomId).stream()
                .filter(s -> overlaps(s.start, s.end, checkIn, checkOut))
                .count();

        if (overlapping >= room.capacity()) {
            System.out.println("Номер " + roomId + " переполнен на период " +
                    checkIn + "–" + checkOut + ". Вместимость: " + room.capacity());
            return;
        }

        int id = nextId++;
        Stay stay = new Stay(roomId, human, checkIn, checkOut, id);

        staysById.put(id, stay);
        stayIdsByRoom.computeIfAbsent(roomId, k -> new ArrayList<>()).add(id);

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
                removeStayIdFromRoomIndex(s.roomId, s.id);
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
        for (String roomId : stayIdsByRoom.keySet()) {
            List<String> guests = staysInRoom(roomId).stream()
                    .map(Stay::guest)
                    .collect(Collectors.toList());
            res.put(roomId, guests);
        }
        return res;
    }



    @Override
    public void CountFreeRooms() {
        freeRooms();

        int free = 0;
        for (String number : rooms.getRoomsNumbers()) {
            Room r = rooms.getRoom(number);
            if (r != null && r.occupancyStatus() == OccupancyStatus.VACANT) {
                free++;
            }
        }
        System.out.println("Свободных номеров: " + free);
    }

    @Override
    public List<GuestRegistry.GuestEntry> getAllGuestEntries() {
        return staysById.values().stream()
                .sorted(Comparator.comparing(Stay::start))
                .map(s -> new GuestRegistry.GuestEntry(s.roomId, s.guest, s.start, s.end, s.id))
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
        for (String roomId : rooms.getRoomsNumbers()) {
            Room room = rooms.getRoom(roomId);
            if (room == null) continue;

            boolean occupied = staysInRoom(roomId).stream()
                    .anyMatch(s -> overlaps(s.start, s.end, date, date.plusDays(1)));

            if (!occupied) free.add(roomId);
        }
        return free;
    }

    @Override
    public List<GuestRegistry.GuestEntry> last3GuestsOfRoom(String roomId) {
        return staysInRoom(roomId).stream()
                .sorted(Comparator.comparing(Stay::end).reversed())
                .limit(3)
                .map(s -> new GuestRegistry.GuestEntry(s.roomId, s.guest, s.start, s.end, s.id))
                .collect(Collectors.toList());
    }


    // улучшить
    @Override
    public double computeRoomCharge(String roomId, String guestName) {
        Room r = rooms.getRoom(roomId);
        if (r == null) return 0.0;

        Stay lastStay = staysInRoom(roomId).stream()
                .filter(s -> Objects.equals(s.guest, guestName))
                .max(Comparator.comparing(Stay::end))
                .orElse(null);

        if (lastStay == null) return 0.0;

        long nights = ChronoUnit.DAYS.between(lastStay.start, lastStay.end);
        return r.price() * nights;
    }

    @Override
    public void removePeopleFromRoom(String roomId) {
        List<Integer> ids = stayIdsByRoom.remove(roomId);
        if (ids == null || ids.isEmpty()) {
            System.out.println("Комната пуста: " + roomId);
            return;
        }

        for (Integer id : ids) {
            staysById.remove(id);
        }

        Room room = rooms.getRoom(roomId);
        if (room != null) room.setOccupancyStatus(OccupancyStatus.VACANT);
    }

    @Override
    public ArrayList<Object> getRoomId() {

        ArrayList<Object> x = new ArrayList<>();
        for (String roomId : stayIdsByRoom.keySet()) {
            x.add(staysInRoom(roomId));
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
            System.out.println("Дата должны быть позже: " + today);
            return;
        }
        Stay old = staysById.get(id);
        if (old != null) {
            removeStayIdFromRoomIndex(old.roomId, id);
        }
        Stay stay = new Stay(newRoomNumber, guest, start, end, id);
        staysById.put(id, stay);
        stayIdsByRoom.computeIfAbsent(newRoomNumber, k -> new ArrayList<>()).add(id);

        refreshRoomOccupancy(newRoomNumber);
        if (old != null) refreshRoomOccupancy(old.roomId);
    }

}

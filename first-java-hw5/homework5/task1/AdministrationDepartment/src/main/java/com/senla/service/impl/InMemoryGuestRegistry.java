package com.senla.service.impl;

import com.senla.model.OccupancyStatus;
import com.senla.model.Room;
import com.senla.service.GuestRegistry;
import com.senla.service.Rooms;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;


public class InMemoryGuestRegistry implements GuestRegistry {


    private static final class Stay {
        final String roomId;
        final String guest;
        final LocalDate start;
        final LocalDate end;

        Stay(String roomId, String guest, LocalDate start, LocalDate end) {
            if (!end.isAfter(start)) {
                throw new IllegalArgumentException("end должен быть позже start");
            }
            this.roomId = roomId;
            this.guest = guest;
            this.start = start;
            this.end = end;
        }
        // проверяет живут ли в номере в данный момент
        boolean activeOn(LocalDate date) {
            // start <= date < end
            return !date.isBefore(start) && date.isBefore(end);
        }
    }


    private final Map<String, List<Stay>> staysByRoom = new HashMap<>();

    private final Rooms rooms;

    public InMemoryGuestRegistry(Rooms rooms) {
        this.rooms = rooms;
    }



    private static boolean overlaps(LocalDate s1, LocalDate e1, LocalDate s2, LocalDate e2) {
        // Есть пересечение, если НЕ (e1 <= s2 || e2 <= s1)
        return !( !e1.isAfter(s2) || !e2.isAfter(s1) );
    }

    private LocalDate today() {
        return LocalDate.now();
    }


    private void refreshOccupancyToday(String roomId) {
        Room room = rooms.getRoom(roomId);
        if (room == null) return;
        LocalDate today = today();
        boolean occupied = staysByRoom.getOrDefault(roomId, List.of()).stream()
                .anyMatch(s -> s.activeOn(today));
        room.setOccupancyStatus(occupied ? OccupancyStatus.OCCUPIED : OccupancyStatus.VACANT);
    }


    private void refreshAllOccupancyToday() {
        for (String id : rooms.getRoomsNumbers()) {
            refreshOccupancyToday(id);
        }
    }



    @Override
    public List<String> getListOfPeople(String roomId) {
        LocalDate today = today();
        return staysByRoom.getOrDefault(roomId, List.of()).stream()
                .filter(s -> s.activeOn(today))
                .map(s -> s.guest)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void addHuman(String roomId, String human, LocalDate checkIn, int nights) {
        if (roomId == null || human == null || checkIn == null) {
            System.out.println("Некорректные параметры (roomId/human/checkIn)");
            return;
        }
        Room room = rooms.getRoom(roomId);
        if (room == null) {
            System.out.println("Комнаты " + roomId + " не существует");
            return;
        }
        if (!rooms.isRoomBookable(roomId)) {
            System.out.println("Комната " + roomId + " в ремонте или обслуживании");
            return;
        }
        if (nights <= 0) {
            System.out.println("Длительность проживания (ночей) должна быть > 0");
            return;
        }


        LocalDate checkOut = checkIn.plusDays(nights); // [checkIn, checkOut)
        List<Stay> stays = staysByRoom.computeIfAbsent(roomId, k -> new ArrayList<>());

        // Сколько уже гостей в пересекающемся интервале?
        long overlapping = stays.stream()
                .filter(s -> overlaps(s.start, s.end, checkIn, checkOut))
                .count();

        if (overlapping >= room.capacity()) {
            System.out.println("Номер " + roomId + " переполнен на период " + checkIn + "–" + checkOut +
                    ". Вместимость: " + room.capacity());
            return;
        }

        stays.add(new Stay(roomId, human, checkIn, checkOut));
        // После добавления — обновим статус на сегодня.
        refreshOccupancyToday(roomId);

        System.out.println("Забронировано: " + human + " в " + roomId + " " + checkIn + "–" + checkOut);
    }

    @Override
    public Map<String, List<String>> AllWhoLivesInRooms() {
        LocalDate today = today();
        Map<String, List<String>> res = new LinkedHashMap<>();
        for (String num : rooms.getRoomsNumbers()) {
            List<String> guestsToday = staysByRoom.getOrDefault(num, List.of()).stream()
                    .filter(s -> s.activeOn(today))
                    .map(s -> s.guest)
                    .toList();
            if (!guestsToday.isEmpty()) {
                res.put(num, guestsToday);
            }
        }
        return res;
    }

    @Override
    public void freeRooms() {
        // Сначала синхронизируем статусы на сегодня.
        refreshAllOccupancyToday();

        List<String> freeRooms = new ArrayList<>();
        for (String num : rooms.getRoomsNumbers()) {
            Room r = rooms.getRoom(num);
            if (r != null && r.occupancyStatus() == OccupancyStatus.VACANT && rooms.isRoomBookable(num)) {
                freeRooms.add(num);
            }
        }
        for (int i = 0; i < freeRooms.size(); i++) {
            System.out.println((i + 1) + ": " + freeRooms.get(i));
        }
        if (freeRooms.isEmpty()) {
            System.out.println("Свободных комнат на сегодня нет");
        }
    }

    @Override
    public void CountFreeRooms() {
        refreshAllOccupancyToday();

        long free = rooms.getRoomsNumbers().stream()
                .map(rooms::getRoom)
                .filter(Objects::nonNull)
                .filter(r -> r.occupancyStatus() == OccupancyStatus.VACANT)
                .filter(r -> rooms.isRoomBookable(r.number()))
                .count();

        System.out.println("Все свободные комнаты на данный момент: " + free);
    }

    @Override
    public List<GuestRegistry.GuestEntry> getAllGuestEntries() {
        var all = new java.util.ArrayList<GuestRegistry.GuestEntry>();
        staysByRoom.forEach((roomId, stays) -> {
            for (var s : stays) {
                all.add(new GuestRegistry.GuestEntry(s.guest, roomId, s.start, s.end));
            }
        });
        return java.util.List.copyOf(all);
    }

    @Override
    public void removePeopleFromRoom(String roomId) {
        List<Stay> stays = staysByRoom.get(roomId);
        if (stays == null || stays.isEmpty()) {
            System.out.println("В комнате никого нет");
            refreshOccupancyToday(roomId);
            return;
        }

        LocalDate today = today();
        // Удаляем только тех, кто живёт прямо сейчас; будущие брони не трогаем.
        boolean removedAny = stays.removeIf(s -> s.activeOn(today));

        if (!removedAny) {
            System.out.println("На текущий момент в комнате никто не проживает");
        }

        refreshOccupancyToday(roomId);
    }

    @Override
    public int countActiveGuestsToday() { // ================================================================ //
        LocalDate today = today();
        return (int) staysByRoom.values().stream()
                .flatMap(List::stream)
                .filter(s -> s.activeOn(today))
                .count();
    }


    @Override
    public List<String> listRoomsFreeOn(LocalDate date) {
        List<String> free = new ArrayList<>();
        for (String num : rooms.getRoomsNumbers()) {
            if (!rooms.isRoomBookable(num)) continue;
            boolean occupied = staysByRoom.getOrDefault(num, List.of()).stream()
                    .anyMatch(s -> s.activeOn(date));
            if (!occupied) free.add(num);
        }
        return free;
    }


    @Override
    public List<GuestRegistry.GuestEntry> last3GuestsOfRoom(String roomId) {
        var src = new ArrayList<GuestRegistry.GuestEntry>();
        for (var s : staysByRoom.getOrDefault(roomId, List.of())) {
            src.add(new GuestRegistry.GuestEntry(s.guest, s.roomId, s.start, s.end));
        }
        src.sort(Comparator.comparing((GuestRegistry.GuestEntry e) -> e.checkOut).reversed());
        return src.size() > 3 ? src.subList(0, 3) : src;
    }


    @Override
    public double computeRoomCharge(String roomId, String guestName) {
        Room r = rooms.getRoom(roomId);
        if (r == null) return 0.0;
        // берём его последнюю (самую позднюю по выезду) бронь в этой комнате
        Optional<Stay> opt = staysByRoom.getOrDefault(roomId, List.of()).stream()
                .filter(s -> Objects.equals(s.guest, guestName))
                .max(Comparator.comparing(s -> s.end));
        if (opt.isEmpty()) return 0.0;

        Stay s = opt.get();
        long nights = java.time.temporal.ChronoUnit.DAYS.between(s.start, s.end);
        return r.price() * nights;
    }
}

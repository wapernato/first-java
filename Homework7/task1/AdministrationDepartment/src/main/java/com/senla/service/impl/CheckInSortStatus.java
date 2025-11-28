package com.senla.service.impl;

import com.senla.model.Room;
import com.senla.service.*;

import java.text.Collator;
import java.util.*;

public class CheckInSortStatus implements SortStats {

    @Override
    public void sortRoomByStars(Rooms rooms) {
        List<Room> snapshot = new ArrayList<>();
        for (String num : rooms.getRoomsNumbers()) {
            Room r = rooms.getRoom(num);
            if (r != null) snapshot.add(r);
        }
        snapshot.sort(
                Comparator.comparingInt(Room::stars).reversed()
        );


        for (Room r : snapshot) {
            System.out.printf("Комната %s: %d★, вместимость %d, цена %.2f, статус %s%n",
                    r.number(), r.stars(), r.capacity(), r.price(), r.status());

        }
    }
    @Override
    public void sortRoomByCapacity(Rooms rooms){
        List<Room> snapshot = new ArrayList<>();
        for (String num : rooms.getRoomsNumbers()) {
            Room r = rooms.getRoom(num);
            if (r != null) snapshot.add(r);
        }
        snapshot.sort(
                Comparator.comparingInt(Room::capacity).reversed()
        );


        for (Room r : snapshot) {
            System.out.printf("Комната %s: %d★, вместимость %d, цена %.2f, статус %s%n",
                    r.number(), r.stars(), r.capacity(), r.price(), r.status());
        }
    }


    @Override
    public void sortRoomByPrice(Rooms rooms){
        List<Room> snapshot = new ArrayList<>();
        for (String num : rooms.getRoomsNumbers()){
            Room r = rooms.getRoom(num);
            if (r != null) snapshot.add(r);
        }

        snapshot.sort(
                Comparator.comparingDouble(Room::price)
        );

        for (Room r : snapshot) {
            System.out.printf("Комната %s: %d★, вместимость %d, цена %.2f, статус %s%n",
                    r.number(), r.stars(), r.capacity(), r.price(), r.status());
        }
    }
    @Override
    public void freeSortRoomByStars(Rooms rooms){
        List<Room> snapshot = new ArrayList<>();
        for (String num : rooms.freeRoomsNumber()){
            Room r = rooms.getRoom(num);
            if (r != null) snapshot.add(r);
        }

        snapshot.sort(
                Comparator.comparingDouble(Room::stars).reversed()
        );

        for (Room r : snapshot) {
            System.out.printf("Комната %s: %d★, вместимость %d, цена %.2f, статус %s%n",
                    r.number(), r.stars(), r.capacity(), r.price(), r.status());
        }
    }

    @Override
    public void freeSortRoomByPrice(Rooms rooms){
        List<Room> snapshot = new ArrayList<>();
        for (String num : rooms.freeRoomsNumber()){
            Room r = rooms.getRoom(num);
            if (r != null) snapshot.add(r);
        }

        snapshot.sort(
                Comparator.comparingDouble(Room::price)
        );

        for (Room r : snapshot) {
            System.out.printf("Комната %s: %d★, вместимость %d, цена %.2f, статус %s%n",
                    r.number(), r.stars(), r.capacity(), r.price(), r.status());
        }
    }

    @Override
    public void freeSortRoomByCapacity(Rooms rooms) {
        List<Room> snapshot = new ArrayList<>();
        for (String num : rooms.freeRoomsNumber()) {
            Room r = rooms.getRoom(num);
            if (r != null) snapshot.add(r);
        }
        snapshot.sort(
                Comparator.comparingInt(Room::capacity)
        );

        for (Room r : snapshot) {
            System.out.printf("Комната %s: %d★, вместимость %d, цена %.2f, статус %s%n",
                    r.number(), r.stars(), r.capacity(), r.price(), r.status());

        }
    }

    @Override
    public List<GuestRegistry.GuestEntry> sortGuestsByAlphabetThenCheckout(GuestRegistry guests) {
        List<GuestRegistry.GuestEntry> data = new ArrayList<>(guests.getAllGuestEntries());
        Collator ru = Collator.getInstance(new Locale("ru", "RU"));
        ru.setStrength(Collator.PRIMARY);
        data.sort(Comparator.comparing((GuestRegistry.GuestEntry e) -> e.guest, ru)
                .thenComparing(e -> e.checkOut));
        return data;
    }

    @Override
    public void printRoomDetails(Rooms rooms, String number, GuestRegistry guests, Integer roomsHistoryLimit) {
        Room r = rooms.getRoom(number);
        if (r == null) {
            System.out.println("Комната не найдена: " + number);
            return;
        }
        System.out.printf("Комната %s: %d★, вместимость %d, цена %.2f, статус %s%n",
                r.number(), r.stars(), r.capacity(), r.price(), r.status());

        //узнать последних roomsHistoryLimit гостей

    }

    @Override
    public void printPrices(Rooms rooms, ServiceCatalog services) {
        List<Room> rs = new ArrayList<>();
        for (String n : rooms.getRoomsNumbers()) {
            Room r = rooms.getRoom(n);
            if (r != null) rs.add(r);
        }
        rs.sort(Comparator.comparingDouble(Room::price));
        System.out.println("=== Номера (по цене ↑) ===");
        for (Room r : rs) {
            System.out.printf(" - комната %s — %.2f%n", r.number(), r.price());
        }

        List<String> svc = new ArrayList<>(services.listServiceNames());
        svc.sort(Comparator.comparingDouble(services::getServicePrice));
        System.out.println("=== Услуги (по цене ↑) ===");
        for (String name : svc) {
            System.out.printf(" - %s — %.2f%n", name, services.getServicePrice(name));
        }
    }

    @Override
    public void printGuestServices(String guestName, ServiceUsageRegistry usage, boolean sortByPrice) {
        var list = new ArrayList<>(usage.listByGuest(guestName));
        if (sortByPrice) {
            list.sort(Comparator.comparingDouble(u -> u.price));
            System.out.println("Услуги гостя " + guestName + " (по цене ↑):");
        } else {
            list.sort(Comparator.comparing(u -> u.date));
            System.out.println("Услуги гостя " + guestName + " (по дате ↑):");
        }
        double sum = 0.0;
        for (var u : list) {
            sum += u.price;
            System.out.println(" - " + u.date + " — " + u.serviceName + " — " + u.price);
        }
        System.out.printf("Итого за услуги: %.2f%n", sum);
    }

    @Override
    public void printLast3GuestsOfRoom(String numberRoom, GuestRegistry guests) {
        var last3 = guests.last3GuestsOfRoom(numberRoom);
        System.out.println("Последние 3 постояльца комнаты " + numberRoom + ":");
        if (last3.isEmpty()) {
            System.out.println(" - нет данных");
            return;
        }
        for (var e : last3) {
            System.out.println(" - " + e.guest + " — " + e.checkIn + " → " + e.checkOut);
        }
    }
}

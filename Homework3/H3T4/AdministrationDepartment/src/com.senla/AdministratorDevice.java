package com.senla;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

enum RoomStatus {
    AVAILABLE,
    MAINTENANCE
}

public class AdministratorDevice {


    public static class GuestIntoRoom {
        private final Set<String> people = new LinkedHashSet<>();
        private final Rooms rooms;

        public GuestIntoRoom(Rooms rooms) { this.rooms = rooms; }

        public Set<String> getListOfPeople() {
            return Collections.unmodifiableSet(people);
        }

        public void addHuman(String human) {
            if (rooms.count() <= people.size()) {
                System.out.println("Мест больше нет: " + human);
                return;
            }
            if (!people.add(human)) {
                System.out.println("Гость уже добавлен: " + human);
            }
        }
    }


    public static final class Room {
        private final String number;
        private RoomStatus status;
        private double price;

        private Room(String number) {
            this.number = number;
            this.status = RoomStatus.AVAILABLE;
            this.price = 0.0;
        }

        public String number() { return number; }
        public RoomStatus status() { return status; }
        public double price() { return price; }
        private void setStatus(RoomStatus status) { this.status = status; }
        private void setPrice(double price) { this.price = price; }
    }


    public static class Rooms {
        private final Map<String, Room> rooms = new LinkedHashMap<>();

        public Set<String> getRoomsNumbers() {
            return Collections.unmodifiableSet(rooms.keySet());
        }

        public void addRoom(String number) {
            if (rooms.containsKey(number)) {
                System.out.println("Номер уже существует: " + number);
                return;
            }
            rooms.put(number, new Room(number));
        }

        public int count() { return rooms.size(); }

        public void setRoomStatus(String number, RoomStatus status) {
            Room r = rooms.get(number);
            if (r == null) {
                System.out.println("Нет такого номера: " + number);
                return;
            }
            r.setStatus(status);
        }


        public void setRoomPrice(String number, double price) {
            if (price < 0) {
                System.out.println("Цена не может быть отрицательной: " + price);
                return;
            }
            Room r = rooms.get(number);
            if (r == null) {
                System.out.println("Нет такого номера: " + number);
                return;
            }
            r.setPrice(price);
        }


        public Room getRoom(String number) {
            return rooms.get(number);
        }


        boolean isRoomBookable(String number) {
            Room r = rooms.get(number);
            return r != null && r.status() != RoomStatus.MAINTENANCE;
        }
    }


    public static class ServiceCatalog {
        private static final class Service {
            private final String name;
            private double price;
            private Service(String name, double price) { this.name = name; this.price = price; }
        }

        private final Map<String, Service> services = new LinkedHashMap<>();

        public void addService(String name, double price) {
            if (name == null || name.isBlank()) {
                System.out.println("Название услуги пустое");
                return;
            }
            if (price < 0) {
                System.out.println("Цена услуги не может быть отрицательной: " + price);
                return;
            }
            if (services.containsKey(name)) {
                System.out.println("Услуга уже существует: " + name);
                return;
            }
            services.put(name, new Service(name, price));
        }

        public void setServicePrice(String name, double price) {
            if (price < 0) {
                System.out.println("Цена услуги не может быть отрицательной: " + price);
                return;
            }
            Service s = services.get(name);
            if (s == null) {
                System.out.println("Нет такой услуги: " + name);
                return;
            }
            s.price = price;
        }

        public Set<String> listServiceNames() {
            return Collections.unmodifiableSet(services.keySet());
        }

        public double getServicePrice(String name) {
            Service s = services.get(name);
            return (s == null) ? -1 : s.price;
        }
    }


    public static record Assignment(String roomNumber, String guestName) {}


    public static class CheckIn {
        private final Rooms rooms;
        private final GuestIntoRoom guests;


        private final Set<Assignment> assignments = new LinkedHashSet<>();

        public CheckIn(Rooms rooms, GuestIntoRoom guests) {
            this.rooms = rooms;
            this.guests = guests;
        }


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

        public void assignAllPairwise() {
            Iterator<String> roomIt = rooms.getRoomsNumbers().iterator();
            Iterator<String> peopleIt = guests.getListOfPeople().iterator();
            while (roomIt.hasNext() && peopleIt.hasNext()) {
                String room  = roomIt.next();
                String guest = peopleIt.next();
                checkIn(room, guest);
            }
        }


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
            return false;
        }

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


        public void printAssignments() {
            for (Assignment a : assignments) {
                System.out.println(a.roomNumber() + " -> " + a.guestName());
            }
        }

        public Set<Assignment> viewAssignments() {
            return Collections.unmodifiableSet(assignments);
        }


        private boolean isRoomBusy(String roomNumber) {
            for (Assignment a : assignments) {
                if (a.roomNumber().equals(roomNumber)) return true;
            }
            return false;
        }

        private boolean isGuestCheckedIn(String guestName) {
            for (Assignment a : assignments) {
                if (a.guestName().equals(guestName)) return true;
            }
            return false;
        }
    }
}

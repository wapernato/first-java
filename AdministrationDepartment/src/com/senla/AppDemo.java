package com.senla;

import com.senla.model.OccupancyStatus;
import com.senla.model.Room;
import com.senla.model.RoomStatus;
import com.senla.service.*;
import com.senla.service.impl.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Полноценная демонстрация:
 *
 * 1) Список номеров (сортировать по цене, вместимости, количеству звезд);
 * 2) Список свободных номеров (сортировать по цене, вместимости, количеству звезд);
 * 3) Список постояльцев и их номеров (сортировать по алфавиту, дате освобождения номера);
 * 4) Общее число свободных номеров;
 * 5) Общее число постояльцев;
 * 6) Список номеров которые будут свободны по определенной дате в будущем;
 * 7) Сумму оплаты за номер которую должен оплатить постоялец;
 * 8) Посмотреть 3-х последних постояльцев номера и даты их пребывания;
 * 9) Посмотреть список услуг постояльца и их цену (сортировать по цене, по дате);
 * 10) Цены услуг и номеров (сортировать по разделу, цене);
 * 11) Посмотреть детали отдельного номера.
 *
 * Операции:
 * - Поселить в номер; Выселить из номера;
 * - Изменить статус номера (ремонт/обслуживание);
 * - Изменить цену номера или услуги;
 * - Добавить номер или услугу.
 */
public class AppDemo {

    // ---------- ВСПОМОГАТЕЛЬНОЕ: печать списков свободных номеров с сортировкой ----------
    private static List<Room> freeRoomsSnapshot(Rooms rooms) {
        List<Room> out = new ArrayList<>();
        for (String num : rooms.getRoomsNumbers()) {
            Room r = rooms.getRoom(num);
            if (r != null && r.occupancyStatus() == OccupancyStatus.VACANT && r.status() == RoomStatus.AVAILABLE) {
                out.add(r);
            }
        }
        return out;
    }

    private static void printRoomsSortedByPrice(List<Room> rooms) {
        List<Room> copy = new ArrayList<>(rooms);
        copy.sort(Comparator.comparingDouble(Room::price));
        System.out.println("== Комнаты (по цене ↑) ==");
        for (Room r : copy) {
            System.out.printf(" - %s: %.2f, %d★, вместимость %d, статус %s%n",
                    r.number(), r.price(), r.stars(), r.capacity(), r.status());
        }
    }

    private static void printRoomsSortedByCapacity(List<Room> rooms) {
        List<Room> copy = new ArrayList<>(rooms);
        copy.sort(Comparator.comparingInt(Room::capacity).reversed());
        System.out.println("== Комнаты (по вместимости ↓) ==");
        for (Room r : copy) {
            System.out.printf(" - %s: вместимость %d, цена %.2f, %d★, статус %s%n",
                    r.number(), r.capacity(), r.price(), r.stars(), r.status());
        }
    }

    private static void printRoomsSortedByStars(List<Room> rooms) {
        List<Room> copy = new ArrayList<>(rooms);
        copy.sort(Comparator.comparingInt(Room::stars).reversed());
        System.out.println("== Комнаты (по звёздам ↓) ==");
        for (Room r : copy) {
            System.out.printf(" - %s: %d★, цена %.2f, вместимость %d, статус %s%n",
                    r.number(), r.stars(), r.price(), r.capacity(), r.status());
        }
    }

    private static List<Room> allRoomsSnapshot(Rooms rooms) {
        List<Room> list = new ArrayList<>();
        for (String num : rooms.getRoomsNumbers()) {
            Room r = rooms.getRoom(num);
            if (r != null) list.add(r);
        }
        return list;
    }

    private static void divider(String title) {
        System.out.println();
        System.out.println("--------------------------------------------------");
        System.out.println(title);
        System.out.println("--------------------------------------------------");
    }

    public static void main(String[] args) {

        // ====== Инициализация сервисов ======
        Rooms rooms = new InMemoryRooms();
        GuestRegistry guests = new InMemoryGuestRegistry(rooms);
        SortStats sorter = new CheckInSortStatus();
        ServiceCatalog catalog = new InMemoryServiceCatalog();
        ServiceUsageRegistry usage = new InMemoryServiceUsageRegistry();

        // ====== Добавление номеров и изменение цен/вместимости/звёзд ======
        rooms.addRoom("101", 2, 3);  // number, stars, capacity
        rooms.addRoom("102", 3, 4);
        rooms.addRoom("103", 5, 2);
        rooms.addRoom("104", 1, 1);
        rooms.addRoom("105", 2, 3);

        rooms.setRoomPrice("101", 1200);
        rooms.setRoomPrice("102", 1600);
        rooms.setRoomPrice("103", 4500);
        rooms.setRoomPrice("104", 800);
        rooms.setRoomPrice("105", 1500);

        // Изменение цены номера (пример)
        rooms.setRoomPrice("104", 900);

        // ====== Добавление услуг и изменение цен услуг ======
        catalog.addService("Завтрак", 300);
        catalog.addService("Спа", 1500);
        catalog.addService("Прачечная", 500);
        // Изменить цену услуги
        catalog.setServicePrice("Прачечная", 550);

        // ====== Бронирования (поселение) ======
        // Будущее
        LocalDate nextWeek = LocalDate.now().plusDays(7);
        guests.addHuman("101", "Иван", nextWeek, 3);   // [t+7, t+10)
        guests.addHuman("101", "Анна", nextWeek, 3);   // вместимость 3 -> ок
        guests.addHuman("102", "Мария", nextWeek, 2);

        // На сегодня
        LocalDate today = LocalDate.now();
        guests.addHuman("103", "Олег", today, 2);      // [t, t+2)
        guests.addHuman("105", "Павел", today, 1);     // [t, t+1)

        // Пример использования услуг гостем
        usage.addUsageFromCatalog("Олег", "Завтрак", today, catalog);
        usage.addUsageFromCatalog("Олег", "Прачечная", today.plusDays(1), catalog);
        usage.addUsage("Олег", "Мини-бар", today.plusDays(1), 900);

        // ====== 1) Список номеров (сортировки по цене / вместимости / звёздам) ======
        divider("1) Список номеров (сортировки)");
        List<Room> allRooms = allRoomsSnapshot(rooms);
        printRoomsSortedByPrice(allRooms);
        printRoomsSortedByCapacity(allRooms);
        printRoomsSortedByStars(allRooms);

        // Альтернатива: твоими готовыми методами сортировщика
        divider("1a) Список номеров (через SortStats)");
        sorter.sortRoomByPrice(rooms);
        sorter.sortRoomByCapacity(rooms);
        sorter.sortRoomByStars(rooms);

        // ====== 2) Список свободных номеров (сортировки) ======
        divider("2) Свободные на сегодня (сортировки)");
        List<Room> free = freeRoomsSnapshot(rooms);
        printRoomsSortedByPrice(free);
        printRoomsSortedByCapacity(free);
        printRoomsSortedByStars(free);

        // ====== 3) Список постояльцев и их номеров (алфавит, дата выезда) ======
        divider("3) Постояльцы и их номера (имя ↑, дата выезда ↑)");
        sorter.sortGuestsByAlphabetThenCheckout(guests);

        // ====== 4) Общее число свободных номеров ======
        divider("4) Общее число свободных номеров");
        long freeCount = freeRoomsSnapshot(rooms).size();
        System.out.println("Свободно номеров сейчас: " + freeCount);

        // ====== 5) Общее число постояльцев (сегодня) ======
        divider("5) Общее число постояльцев");
        System.out.println("Сейчас проживает: " + guests.countActiveGuestsToday());

        // ====== 6) Номера, свободные на дату в будущем ======
        divider("6) Свободные на дату в будущем");
        LocalDate futureDate = LocalDate.now().plusDays(10);
        System.out.println("Свободные на " + futureDate + ": " + guests.listRoomsFreeOn(futureDate));

        // ====== 7) Сумма за проживание, которую должен оплатить постоялец ======
        divider("7) Сумма к оплате за проживание");
        System.out.printf("Иван (101) должен за проживание: %.2f%n",
                guests.computeRoomCharge("101", "Иван"));
        System.out.printf("Олег (103) должен за проживание: %.2f%n",
                guests.computeRoomCharge("103", "Олег"));

        // (при желании можно добавить к проживанию стоимость услуг Олега:)
        double olehServices = usage.listByGuest("Олег").stream().mapToDouble(u -> u.price).sum();
        System.out.printf("Олег (услуги): %.2f, ИТОГО (проживание+услуги): %.2f%n",
                olehServices, guests.computeRoomCharge("103", "Олег") + olehServices);

        // ====== 8) 3 последних постояльца комнаты ======
        divider("8) Последние 3 постояльца комнаты 101");
        sorter.printLast3GuestsOfRoom("101", guests);

        // ====== 9) Список услуг постояльца и их цену (сортировки) ======
        divider("9) Услуги Олега (по дате ↑)");
        sorter.printGuestServices("Олег", usage, false);
        divider("9a) Услуги Олега (по цене ↑)");
        sorter.printGuestServices("Олег", usage, true);

        // ====== 10) Цены услуг и номеров (по разделу, цене) ======
        divider("10) Цены услуг и номеров");
        sorter.printPrices(rooms, catalog);

        // ====== 11) Детали отдельного номера ======
        divider("11) Детали номера 103");
        sorter.printRoomDetails(rooms, "103", guests);

        // ====== Операции ======
        divider("Операции: выселение, обслуживание, изменение цен, добавление");
        // Выселить из номера 103 (сегодняшних постояльцев этой комнаты)
        guests.removePeopleFromRoom("103");

        // Изменить статус номера: ремонт/обслуживание
        Room r104 = rooms.getRoom("104");
        r104.setStatus(RoomStatus.SERVICE);
        System.out.println("104 теперь в статусе: " + r104.status());

        // Добавить новый номер
        rooms.addRoom("106", 4, 2);
        rooms.setRoomPrice("106", 2000);

        // Добавить новую услугу и изменить цену
        catalog.addService("Трансфер", 1000);
        catalog.setServicePrice("Трансфер", 1200);

        // Поселить в номер (новое бронирование)
        guests.addHuman("106", "Виктор", LocalDate.now().plusDays(1), 2);

        // Контрольный вывод после операций
        divider("Контрольный вывод после операций");
        System.out.println("Свободные на сегодня: " +
                freeRoomsSnapshot(rooms).stream().map(Room::number).collect(Collectors.toList()));
        sorter.printRoomDetails(rooms, "106", guests);
    }
}

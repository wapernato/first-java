package com.senla.service;

import java.util.List;

public interface SortStats {
    void sortRoomByStars(Rooms rooms);
    void sortRoomByCapacity(Rooms rooms);
    void sortRoomByPrice(Rooms rooms);

    void freeSortRoomByStars(Rooms rooms);
    void freeSortRoomByCapacity(Rooms rooms);
    void freeSortRoomByPrice(Rooms rooms);

    List<GuestRegistry.GuestEntry> sortGuestsByAlphabetThenCheckout(GuestRegistry guests);

    void printRoomDetails(Rooms rooms, String roomId, GuestRegistry guests, Integer roomsHistoryLimit);



    void printPrices(Rooms rooms, ServiceCatalog services);

    void printGuestServices(String guestName, ServiceUsageRegistry usage, boolean sortByPrice);

    void printLast3GuestsOfRoom(String roomId, GuestRegistry guests);
}

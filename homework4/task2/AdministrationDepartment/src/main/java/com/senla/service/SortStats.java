package com.senla.service;

public interface SortStats {
    void sortRoomByStars(Rooms rooms);
    void sortRoomByCapacity(Rooms rooms);
    void sortRoomByPrice(Rooms rooms);

    void freeSortRoomByStars(Rooms rooms);
    void freeSortRoomByCapacity(Rooms rooms);
    void freeSortRoomByPrice(Rooms rooms);

    void sortGuestsByAlphabetThenCheckout(GuestRegistry guests);

    void printRoomDetails(Rooms rooms, String roomId, GuestRegistry guests);

    void printPrices(Rooms rooms, ServiceCatalog services);

    void printGuestServices(String guestName, ServiceUsageRegistry usage, boolean sortByPrice);

    void printLast3GuestsOfRoom(String roomId, GuestRegistry guests);
}

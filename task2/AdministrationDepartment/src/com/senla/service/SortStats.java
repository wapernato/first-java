package com.senla.service;

public interface SortStats {
    void sortRoomByStars(Rooms rooms);
    void sortRoomByCapacity(Rooms rooms);
    void sortRoomByPrice(Rooms rooms);

    void freeSortRoomByStars(Rooms rooms);
    void freeSortRoomByCapacity(Rooms rooms);
    void freeSortRoomByPrice(Rooms rooms);

    // (1) «Список постояльцев и их номеров (сортировка: имя, затем дата выезда)»
    void sortGuestsByAlphabetThenCheckout(GuestRegistry guests);

    // (4) детали отдельного номера
    void printRoomDetails(Rooms rooms, String roomId, GuestRegistry guests);

    // (5) «Цены услуг и номеров (сортировать по разделу, цене)»
    void printPrices(Rooms rooms, ServiceCatalog services);

    // (6) услуги постояльца (sortByPrice=true -> по цене; false -> по дате)
    void printGuestServices(String guestName, ServiceUsageRegistry usage, boolean sortByPrice);

    // (7) 3 последних постояльца номера
    void printLast3GuestsOfRoom(String roomId, GuestRegistry guests);
}

package com.senla;

public class Main {
    public static void main(String[] args) {
        // Создание комнат
        AdministratorDevice.Rooms rooms = new AdministratorDevice.Rooms();
        rooms.addRoom("101");
        rooms.addRoom("102");
        rooms.addRoom("103");
        // Даем цену для каждой комнаты
        rooms.setRoomPrice("101", 3500);
        rooms.setRoomPrice("102", 4200);
        rooms.setRoomPrice("103", 3000);

        // Обслуживание
        rooms.setRoomStatus("102", RoomStatus.MAINTENANCE);

        // Добавляем гостей
        AdministratorDevice.GuestIntoRoom guests = new AdministratorDevice.GuestIntoRoom(rooms);

        guests.addHuman("Alice");
        guests.addHuman("Bob");
        guests.addHuman("Charlie");

        // Добавляем услуги
        AdministratorDevice.ServiceCatalog services = new AdministratorDevice.ServiceCatalog();
        services.addService("Завтрак", 600);
        services.addService("Трансфер", 1200);
        services.setServicePrice("Трансфер", 1500);

        // Заселяем
        AdministratorDevice.CheckIn checkIn = new AdministratorDevice.CheckIn(rooms, guests);


        checkIn.assignAllPairwise();      // Поочередно

        System.out.println("\nТекущие данные");
        checkIn.printAssignments();

        // Убираем обслуживание
        System.out.println("\nМеняем статус комнаты 102");
        rooms.setRoomStatus("102", RoomStatus.AVAILABLE);
        checkIn.checkIn("102", "Bob");
        checkIn.printAssignments();

        // Выселяем
        System.out.println("\nВыселяем 101:");
        checkIn.checkoutByRoom("101");
        checkIn.printAssignments();


    }
}

import java.util.*;

/**
 * Простой консольный "Электронный администратор гостиницы".
 * Сделано на уровне начинающего ООП: базовые классы и меню.
 */
public class HotelApp {
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        Hotel hotel = new Hotel();

        // Немного стартовых данных, чтобы было с чем играться:
        hotel.addRoom(101, 2, 2500);
        hotel.addRoom(102, 1, 1800);
        hotel.addRoom(201, 3, 3400);
        hotel.addService("Завтрак", 500);
        hotel.addService("Трансфер", 1200);

        while (true) {
            printMenu();
            String choice = in.nextLine().trim();
            switch (choice) {
                case "1" -> addRoom(hotel);
                case "2" -> addService(hotel);
                case "3" -> checkIn(hotel);
                case "4" -> checkOut(hotel);
                case "5" -> changeRoomStatus(hotel);
                case "6" -> changeRoomPrice(hotel);
                case "7" -> changeServicePrice(hotel);
                case "8" -> listRooms(hotel);
                case "9" -> listServices(hotel);
                case "0" -> {
                    System.out.println("Выход.");
                    return;
                }
                default -> System.out.println("Неизвестная команда.");
            }
            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("""
                ========= Гостиница =========
                1) Добавить номер
                2) Добавить услугу
                3) Поселить в номер
                4) Выселить из номера
                5) Изменить статус номера (ДОСТУПЕН/ЗАНЯТ/РЕМОНТ/ОБСЛУЖ)
                6) Изменить цену номера
                7) Изменить цену услуги
                8) Показать номера
                9) Показать услуги
                0) Выход
                Выберите пункт: """);
    }

    // === Обёртки для чтения/действий (чтобы main был чище) ===
    private static void addRoom(Hotel hotel) {
        try {
            System.out.print("Номер комнаты (целое): ");
            int number = Integer.parseInt(in.nextLine().trim());
            System.out.print("Вместимость (чел.): ");
            int capacity = Integer.parseInt(in.nextLine().trim());
            System.out.print("Цена за ночь (руб): ");
            double price = Double.parseDouble(in.nextLine().trim());
            if (hotel.addRoom(number, capacity, price)) {
                System.out.println("Номер добавлен.");
            } else {
                System.out.println("Номер с таким номером уже существует.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка ввода.");
        }
    }

    private static void addService(Hotel hotel) {
        System.out.print("Название услуги: ");
        String name = in.nextLine().trim();
        System.out.print("Цена услуги (руб): ");
        try {
            double price = Double.parseDouble(in.nextLine().trim());
            if (hotel.addService(name, price)) {
                System.out.println("Услуга добавлена.");
            } else {
                System.out.println("Такая услуга уже существует.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка ввода.");
        }
    }

    private static void checkIn(Hotel hotel) {
        try {
            System.out.print("Номер комнаты: ");
            int number = Integer.parseInt(in.nextLine().trim());
            System.out.print("Сколько гостей поселить: ");
            int guests = Integer.parseInt(in.nextLine().trim());
            String msg = hotel.checkIn(number, guests);
            System.out.println(msg);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка ввода.");
        }
    }

    private static void checkOut(Hotel hotel) {
        try {
            System.out.print("Номер комнаты: ");
            int number = Integer.parseInt(in.nextLine().trim());
            String msg = hotel.checkOut(number);
            System.out.println(msg);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка ввода.");
        }
    }

    private static void changeRoomStatus(Hotel hotel) {
        try {
            System.out.print("Номер комнаты: ");
            int number = Integer.parseInt(in.nextLine().trim());
            System.out.println("Статусы: AVAILABLE, OCCUPIED, MAINTENANCE, SERVICE");
            System.out.print("Новый статус: ");
            String s = in.nextLine().trim().toUpperCase(Locale.ROOT);
            RoomStatus status = RoomStatus.valueOf(s);
            String msg = hotel.setRoomStatus(number, status);
            System.out.println(msg);
        } catch (IllegalArgumentException e) {
            System.out.println("Неверный статус.");
        } catch (NumberFormatException e) {
            System.out.println("Ошибка ввода.");
        }
    }

    private static void changeRoomPrice(Hotel hotel) {
        try {
            System.out.print("Номер комнаты: ");
            int number = Integer.parseInt(in.nextLine().trim());
            System.out.print("Новая цена (руб): ");
            double price = Double.parseDouble(in.nextLine().trim());
            String msg = hotel.changeRoomPrice(number, price);
            System.out.println(msg);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка ввода.");
        }
    }

    private static void changeServicePrice(Hotel hotel) {
        System.out.print("Название услуги: ");
        String name = in.nextLine().trim();
        System.out.print("Новая цена (руб): ");
        try {
            double price = Double.parseDouble(in.nextLine().trim());
            String msg = hotel.changeServicePrice(name, price);
            System.out.println(msg);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка ввода.");
        }
    }

    private static void listRooms(Hotel hotel) {
        System.out.println("Список номеров:");
        hotel.getRooms().values().stream()
                .sorted(Comparator.comparingInt(Room::getNumber))
                .forEach(System.out::println);
    }

    private static void listServices(Hotel hotel) {
        System.out.println("Список услуг:");
        hotel.getServices().values().stream()
                .sorted(Comparator.comparing(Service::getName, String.CASE_INSENSITIVE_ORDER))
                .forEach(System.out::println);
    }
}

/** Статус номера (минимально необходимый набор). */
enum RoomStatus {
    AVAILABLE,     // доступен для заселения
    OCCUPIED,      // занят
    MAINTENANCE,   // ремонт
    SERVICE        // обслуживание/уборка
}

/** Номер в гостинице. */
class Room {
    private final int number;
    private final int capacity;
    private double pricePerNight;
    private RoomStatus status = RoomStatus.AVAILABLE;
    private int guestsNow = 0;

    public Room(int number, int capacity, double pricePerNight) {
        this.number = number;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
    }

    public int getNumber() { return number; }
    public int getCapacity() { return capacity; }
    public double getPricePerNight() { return pricePerNight; }
    public RoomStatus getStatus() { return status; }
    public int getGuestsNow() { return guestsNow; }

    public void setPricePerNight(double pricePerNight) {
        if (pricePerNight > 0) this.pricePerNight = pricePerNight;
    }

    public void setStatus(RoomStatus status) { this.status = status; }

    public String checkIn(int guests) {
        if (status != RoomStatus.AVAILABLE) {
            return "Номер недоступен: статус " + status;
        }
        if (guests <= 0 || guests > capacity) {
            return "Ошибка: гостей должно быть от 1 до " + capacity;
        }
        this.guestsNow = guests;
        this.status = RoomStatus.OCCUPIED;
        return "Заселение успешно. Гостей: " + guests;
    }

    public String checkOut() {
        if (status != RoomStatus.OCCUPIED) {
            return "Номер не занят.";
        }
        this.guestsNow = 0;
        this.status = RoomStatus.AVAILABLE;
        return "Выселение выполнено. Номер свободен.";
    }

    @Override
    public String toString() {
        return "№" + number +
                ", мест: " + capacity +
                ", цена: " + pricePerNight +
                ", статус: " + status +
                (status == RoomStatus.OCCUPIED ? (", гостей сейчас: " + guestsNow) : "");
    }
}

/** Платная услуга гостиницы. */
class Service {
    private final String name;
    private double price;

    public Service(String name, double price) {
        this.name = name;
        this.price = Math.max(price, 0);
    }

    public String getName() { return name; }
    public double getPrice() { return price; }

    public void setPrice(double price) {
        if (price >= 0) this.price = price;
    }

    @Override
    public String toString() {
        return name + " — " + price + " руб.";
    }
}

/** Гостиница: хранит номера и услуги, предоставляет операции. */
class Hotel {
    private final Map<Integer, Room> rooms = new HashMap<>();
    private final Map<String, Service> services = new HashMap<>();

    public boolean addRoom(int number, int capacity, double price) {
        if (rooms.containsKey(number)) return false;
        rooms.put(number, new Room(number, capacity, price));
        return true;
    }

    public boolean addService(String name, double price) {
        String key = key(name);
        if (services.containsKey(key)) return false;
        services.put(key, new Service(name, price));
        return true;
    }

    public String checkIn(int number, int guests) {
        Room r = rooms.get(number);
        if (r == null) return "Такого номера нет.";
        return r.checkIn(guests);
    }

    public String checkOut(int number) {
        Room r = rooms.get(number);
        if (r == null) return "Такого номера нет.";
        return r.checkOut();
    }

    public String setRoomStatus(int number, RoomStatus status) {
        Room r = rooms.get(number);
        if (r == null) return "Такого номера нет.";
        r.setStatus(status);
        return "Статус обновлён: " + status;
    }

    public String changeRoomPrice(int number, double newPrice) {
        Room r = rooms.get(number);
        if (r == null) return "Такого номера нет.";
        if (newPrice <= 0) return "Цена должна быть > 0.";
        r.setPricePerNight(newPrice);
        return "Цена номера обновлена.";
    }

    public String changeServicePrice(String name, double newPrice) {
        Service s = services.get(key(name));
        if (s == null) return "Такой услуги нет.";
        if (newPrice < 0) return "Цена не может быть отрицательной.";
        s.setPrice(newPrice);
        return "Цена услуги обновлена.";
    }

    public Map<Integer, Room> getRooms() { return rooms; }
    public Map<String, Service> getServices() { return services; }

    private String key(String name) {
        return name.toLowerCase(Locale.ROOT);
    }
}

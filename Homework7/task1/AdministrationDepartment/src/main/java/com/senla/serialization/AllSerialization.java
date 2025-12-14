package com.senla.serialization;

import com.senla.service.GuestRegistry;
import com.senla.service.Rooms;
import com.senla.service.ServiceCatalog;
import com.senla.service.ServiceUsageRegistry;

public class AllSerialization {

    private final GuestRegistry guests;
    private final Rooms rooms;
    private final ServiceCatalog catalog;
    private final ServiceUsageRegistry usage;

    private final SerializationRooms serializationRooms;
    private final SerializationGuestRegistry serializationGuestRegistry;
    private final SerializationServiceCatalog serializationServiceCatalog;
    private final SerializationServiceUsageRegistry serializationServiceUsageRegistry;

    public AllSerialization(GuestRegistry guests,
                            Rooms rooms,
                            ServiceCatalog catalog,
                            ServiceUsageRegistry usage) {
        this.guests = guests;
        this.rooms = rooms;
        this.catalog = catalog;
        this.usage = usage;

        this.serializationRooms = new SerializationRooms(rooms);
        this.serializationGuestRegistry = new SerializationGuestRegistry(guests);
        this.serializationServiceCatalog = new SerializationServiceCatalog(catalog);
        this.serializationServiceUsageRegistry = new SerializationServiceUsageRegistry(usage);
    }

    public void serializationAll() {
        serializationRooms.serializationAllRooms();
        serializationRooms.serializationAllRoomsNextId();
        serializationGuestRegistry.serializeAllGuestEntries();
        serializationGuestRegistry.serializeGuestNextId();
        serializationServiceCatalog.serializationGetService();
        serializationServiceCatalog.serializationGetServiceNextId();
        serializationServiceUsageRegistry.serializationAllServiceUsage();
        serializationServiceUsageRegistry.serializationAllServiceUsageNextId();
        System.out.println("=== СЕРИАЛИЗАЦИЯ ДАННЫХ УСПЕШНА ===");
    }
}

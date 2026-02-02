package com.senla.serialization;

import com.senla.annotation.Inject;
import com.senla.service.GuestRegistry;
import com.senla.service.Rooms;
import com.senla.service.ServiceCatalog;
import com.senla.service.ServiceUsageRegistry;

public class AllSerialization {


    @Inject
    private SerializationRooms serializationRooms;
    @Inject
    private SerializationGuestRegistry serializationGuestRegistry;
    @Inject
    private SerializationServiceCatalog serializationServiceCatalog;
    @Inject
    private SerializationServiceUsageRegistry serializationServiceUsageRegistry;

    public AllSerialization() {}

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

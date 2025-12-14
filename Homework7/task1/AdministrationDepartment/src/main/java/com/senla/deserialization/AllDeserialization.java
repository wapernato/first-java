package com.senla.deserialization;

import com.senla.service.GuestRegistry;
import com.senla.service.ServiceCatalog;
import com.senla.service.ServiceUsageRegistry;
import com.senla.service.impl.InMemoryRooms;

public class AllDeserialization {

    private GuestRegistry guest;
    private InMemoryRooms rooms;
    private ServiceCatalog catalog;
    private ServiceUsageRegistry usage;

    DeserializationGuestRegistry deserializationGuestRegistry = new DeserializationGuestRegistry(guest);
    DeserializationRooms deserializationRooms = new DeserializationRooms(rooms);
    DeserializationServiceCatalog deserializationServiceCatalog = new DeserializationServiceCatalog(catalog);
    DeserializationServiceUsageRegistry deserializationServiceUsageRegistry = new DeserializationServiceUsageRegistry(usage);

    public AllDeserialization(GuestRegistry guest, InMemoryRooms rooms, ServiceCatalog catalog, ServiceUsageRegistry usage){
        this.guest = guest;
        this.rooms = rooms;
        this.catalog = catalog;
        this.usage = usage;

        this.deserializationGuestRegistry = new DeserializationGuestRegistry(this.guest);
        this.deserializationServiceCatalog = new DeserializationServiceCatalog(this.catalog);
        this.deserializationRooms = new DeserializationRooms(this.rooms);
        this.deserializationServiceUsageRegistry = new DeserializationServiceUsageRegistry(this.usage);

    }




    public void allDeserialization(){
        deserializationServiceUsageRegistry.desrializationServiceUsageRegestry();
        deserializationRooms.deserializeRooms();
        deserializationServiceCatalog.deserializeServiceCatalog();
        deserializationGuestRegistry.deserializeGuest();
        System.out.println("=== ДЕСЕРИАЛИЗАЦИЯ ДАННЫХ УСПЕШНА ===");
    }
}

package com.senla.deserialization;

import com.senla.annotation.Inject;

public class AllDeserialization {

    @Inject private DeserializationGuestRegistry guestRegistryDes;
    @Inject private DeserializationRooms roomsDes;
    @Inject private DeserializationServiceCatalog catalogDes;
    @Inject private DeserializationServiceUsageRegistry usageDes;

    public AllDeserialization() {}

    public void allDeserialization() {
        roomsDes.deserializeRooms();
        guestRegistryDes.deserializeGuest();
        catalogDes.deserializeServiceCatalog();
        usageDes.desrializationServiceUsageRegestry();
    }
}

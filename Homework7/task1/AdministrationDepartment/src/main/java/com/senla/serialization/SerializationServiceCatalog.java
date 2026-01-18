package com.senla.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.senla.service.GuestRegistry;
import com.senla.service.ServiceCatalog;

import java.io.FileWriter;
import java.io.IOException;

public class SerializationServiceCatalog {

    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    private final ServiceCatalog catalog;

    public SerializationServiceCatalog(ServiceCatalog catalog) {

        this.catalog = catalog;
    }


    public void serializationGetService(){
        try(FileWriter writer = new FileWriter("GetServicePrice.txt")) {
            String json = mapper.writeValueAsString(catalog.getService());
            writer.write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void serializationGetServiceNextId(){
        try(FileWriter writer = new FileWriter("GetServicePriceNextId.txt")) {
            String json = mapper.writeValueAsString(catalog.getNextId());
            writer.write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

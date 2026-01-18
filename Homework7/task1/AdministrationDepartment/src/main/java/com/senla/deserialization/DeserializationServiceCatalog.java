package com.senla.deserialization;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.senla.service.ServiceCatalog;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class DeserializationServiceCatalog {

    ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    private ServiceCatalog catalog;


    public DeserializationServiceCatalog(ServiceCatalog catalog){
        this.catalog = catalog;
    }

    public void deserializeServiceCatalog() {
        File fileGuest = new File("GetServicePrice.txt");
        File fileIds = new File("GetServicePriceNextId.txt");
        if (fileGuest.exists() && fileGuest.length() > 0 && fileIds.exists() && fileIds.length() > 0) {
            try {
                Map<Integer, ServiceCatalog.ServiceDto> serviceDtoMap = mapper.readValue(fileGuest, new TypeReference<Map<Integer, ServiceCatalog.ServiceDto>>() {
                });
                Integer nextId = mapper.readValue(fileIds, Integer.class);


                catalog.addServiceDes(serviceDtoMap);
                catalog.setNextId(nextId);
            } catch (IOException e) {
                System.out.println("что-то пошло не так при работе с файлами, сетью, потоками");
                System.out.println("Ошибка при чтении файла: " + e.getClass().getName());
                System.out.println("Сообщение: " + e.getMessage());
            }
        }
    }

}

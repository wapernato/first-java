package com.senla.deserialization;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.senla.service.ServiceUsageRegistry;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class DeserializationServiceUsageRegistry {

    ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private ServiceUsageRegistry usage;

    public DeserializationServiceUsageRegistry(ServiceUsageRegistry usage) {
        this.usage = usage;
    }

    public void desrializationServiceUsageRegestry() {
        File fileGuest = new File("src/main/java/com/senla/AllServiceUsage.txt");
        File fileIds = new File("src/main/java/com/senla/AllServiceUsageNextId.txt");
        if (fileGuest.exists() && fileGuest.length() > 0 && fileIds.exists() && fileIds.length() > 0) {
            try {
                Map<Integer, ServiceUsageRegistry.UsageDto> usageDto = mapper.readValue(fileGuest, new TypeReference<Map<Integer, ServiceUsageRegistry.UsageDto>>() {
                });
                Integer id = mapper.readValue(fileIds, Integer.class);

                usage.serviceUsageDes(usageDto);
                usage.setNextId(id);
            } catch (IOException e) {
                System.out.println("что-то пошло не так при работе с файлами, сетью, потоками");
                System.out.println("Ошибка при чтении файла: " + e.getClass().getName());
                System.out.println("Сообщение: " + e.getMessage());
            }
        }
        else {
            return;
        }
    }
}

package com.senla.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.senla.annotation.Inject;
import com.senla.service.GuestRegistry;
import com.senla.service.ServiceUsageRegistry;

import java.io.FileWriter;

public class SerializationServiceUsageRegistry {

    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    @Inject
    private ServiceUsageRegistry usage;

    public SerializationServiceUsageRegistry() {}

    public void serializationAllServiceUsage(){
        try(FileWriter writer = new FileWriter("src/main/java/com/resources/AllServiceUsage.txt")) {
            String json = mapper.writeValueAsString(usage.getAllServiceUsage());
            writer.write(json);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void serializationAllServiceUsageNextId(){
        try(FileWriter writer = new FileWriter("src/main/java/com/resources/AllServiceUsageNextId.txt")) {
            String json = mapper.writeValueAsString(usage.getNextId());
            writer.write(json);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

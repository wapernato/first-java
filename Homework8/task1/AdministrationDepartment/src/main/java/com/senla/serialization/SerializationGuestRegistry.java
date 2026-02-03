package com.senla.serialization;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.senla.annotation.Inject;
import com.senla.service.*;
import com.senla.service.impl.*;





public class SerializationGuestRegistry {

    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    @Inject
    private GuestRegistry guests;

    public SerializationGuestRegistry() {

    }

    public void serializeAllGuestEntries() {
        try(FileWriter writer = new FileWriter("src/main/resources/GuestEntries.txt")) {
           String json = mapper.writeValueAsString(guests.getAllGuestEntries());
           writer.write(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка сериализации гостей", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void serializeGuestNextId() {
        try(FileWriter writer = new FileWriter("src/main/resources/GuestEntriesNextId.txt")) {
            String json = mapper.writeValueAsString(guests.getNextId());
            writer.write(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка сериализации гостей", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}



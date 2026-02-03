package com.senla.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.senla.annotation.Inject;
import com.senla.service.Rooms;
import com.senla.service.impl.InMemoryRooms;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class SerializationRooms {

    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    @Inject
    private Rooms rooms;

    public SerializationRooms() {}

    public void serializationAllRooms() {
        try (FileWriter writer = new FileWriter("src/main/resources/AllRooms.txt")) {
            String json = mapper.writeValueAsString(((InMemoryRooms) rooms).getAllRooms());
            writer.write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void serializationAllRoomsNextId() {
        try (FileWriter writer = new FileWriter("src/main/resources/AllRoomsNextId.txt")) {
            String json = mapper.writeValueAsString(rooms.getNextId());
            writer.write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

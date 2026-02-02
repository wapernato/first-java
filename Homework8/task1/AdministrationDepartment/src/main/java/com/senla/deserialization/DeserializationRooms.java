package com.senla.deserialization;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.senla.annotation.Inject;
import com.senla.model.Room;
import com.senla.service.impl.InMemoryRooms;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class DeserializationRooms {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private static final Path ROOMS_FILE = Path.of("data", "AllRooms.txt");
    private static final Path IDS_FILE   = Path.of("data", "AllRoomsNextId.txt");

    @Inject
    private InMemoryRooms rooms;

    public void deserializeRooms() {
        if (rooms == null) {
            throw new IllegalStateException("InMemoryRooms не внедрён (@Inject не сработал)");
        }

        if (!Files.exists(ROOMS_FILE) || !Files.exists(IDS_FILE)) return;

        try {
            if (Files.size(ROOMS_FILE) == 0 || Files.size(IDS_FILE) == 0) return;

            Integer loadedIds = MAPPER.readValue(IDS_FILE.toFile(), Integer.class);
            Map<Integer, Room> loadedRooms = MAPPER.readValue(
                    ROOMS_FILE.toFile(),
                    new TypeReference<Map<Integer, Room>>() {}
            );

            rooms.addRoomDes(loadedRooms);
            rooms.setNextId(loadedIds);

        } catch (IOException e) {
            System.out.println("Ошибка при чтении файлов");
            System.out.println("Тип: " + e.getClass().getName());
            System.out.println("Сообщение: " + e.getMessage());
        }
    }
}

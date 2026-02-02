package com.senla.deserialization;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.senla.annotation.Inject;
import com.senla.service.GuestRegistry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DeserializationGuestRegistry {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Inject
    private GuestRegistry guest;

    private static final Path GUEST_FILE = Path.of("data", "GuestEntries.txt");
    private static final Path ID_FILE    = Path.of("data", "GuestEntriesNextId.txt");

    public void deserializeGuest() {
        // если DI не подцепился — иначе NPE на guest.putAllAfterDes(...)
        if (guest == null) {
            throw new IllegalStateException("GuestRegistry не внедрён (@Inject не сработал)");
        }

        if (!Files.exists(GUEST_FILE) || !Files.exists(ID_FILE)) return;

        try {
            if (Files.size(GUEST_FILE) == 0 || Files.size(ID_FILE) == 0) return;

            List<GuestRegistry.GuestEntry> entries = MAPPER.readValue(
                    GUEST_FILE.toFile(),
                    new TypeReference<List<GuestRegistry.GuestEntry>>() {}
            );

            Integer nextId = MAPPER.readValue(ID_FILE.toFile(), Integer.class);

            guest.putAllAfterDes(entries);
            guest.setNextId(nextId);

        } catch (IOException e) {
            System.out.println("Ошибка при чтении файлов");
            System.out.println("Тип: " + e.getClass().getName());
            System.out.println("Сообщение: " + e.getMessage());
        }
    }
}

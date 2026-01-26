package com.senla.deserialization;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.senla.service.GuestRegistry;
import com.senla.service.impl.InMemoryGuestRegistry;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class DeserializationGuestRegistry {

    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    private GuestRegistry guest;

    public DeserializationGuestRegistry(GuestRegistry guest){
        this.guest = guest;
    }

    public void deserializeGuest(){
        File fileGuest = new File("src/main/java/com/senla/GuestEntries.txt");
        File fileIds = new File("src/main/java/com/senla/GuestEntriesNextId.txt");
        if(fileGuest.exists() && fileGuest.length() > 0 && fileIds.exists() && fileIds.length() > 0){
            try {
                List<GuestRegistry.GuestEntry> entries = mapper.readValue(
                        fileGuest,
                        new TypeReference<List<GuestRegistry.GuestEntry>>() {} // добавить некую загрузку что-то типо putAll, который был в Rooms
                );
                Integer entriesIds = mapper.readValue(fileIds, Integer.class); // сетнуть roomsIds, к нему нужно еще добавить проверки

                guest.putAllAfterDes(entries);
                guest.setNextId(entriesIds);

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

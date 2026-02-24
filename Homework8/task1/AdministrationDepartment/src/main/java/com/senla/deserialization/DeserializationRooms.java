package com.senla.deserialization;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.senla.annotation.Inject;
import com.senla.model.Room;
import com.senla.service.impl.InMemoryRooms;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class DeserializationRooms {

    ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Inject
    private InMemoryRooms rooms;

    public DeserializationRooms(){}

    public void deserializeRooms() {
        File fileRooms = new File("src/main/resources/AllRooms.txt");
        File fileIds = new File("src/main/resources/AllRoomsNextId.txt");
        if(fileRooms.exists() && fileRooms.length() > 0 && fileIds.exists() && fileIds.length() > 0) {
            try {
                Integer loadedIds = mapper.readValue(fileIds, Integer.class);
                Map<Integer, Room> loadedRooms = mapper.readValue(fileRooms, new TypeReference<Map<Integer, Room>>() {
                });

                rooms.addRoomDes(loadedRooms);
                rooms.setNextId(loadedIds);
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
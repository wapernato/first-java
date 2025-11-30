package com.senla.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.senla.service.impl.InMemoryRooms;

public class SerializationRooms {
    private static final ObjectMapper MAPPER;

    InMemoryRooms room = new InMemoryRooms();


    static {
        MAPPER = new ObjectMapper();
        MAPPER.registerModule(new JavaTimeModule());
        MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    private SerializationRooms() {}

    public static String toJson(Object value){
        try{
            return MAPPER.writeValueAsString(value);
        }
        catch (Exception e) {
            throw new RuntimeException("Ошибка сериализации", e);
        }
    }


}

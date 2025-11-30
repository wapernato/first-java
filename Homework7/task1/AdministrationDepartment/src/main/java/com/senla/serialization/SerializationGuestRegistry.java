package com.senla.serialization;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.senla.service.*;
import com.senla.service.impl.*;





public class SerializationGuestRegistry {

        private final ObjectMapper mapper;
        private final GuestRegistry guests;

        public  SerializationGuestRegistry(ObjectMapper mapper, GuestRegistry guests) {
            this.mapper = mapper;
            this.guests = guests;
        }


    public String serializeAllGuestEntries() {
        try {
            return mapper.writeValueAsString(guests.getAllGuestEntries());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка сериализации гостей", e);
        }
    }

    public String serializeGuestIds() {
        try {
            return mapper.writeValueAsString(guests.getGuestId());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка сериализации id гостей", e);
        }
    }
}



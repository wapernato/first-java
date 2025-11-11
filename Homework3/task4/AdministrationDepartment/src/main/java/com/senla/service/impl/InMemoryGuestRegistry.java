package com.senla.service.impl;


import com.senla.service.GuestRegistry;
import com.senla.service.Rooms;


import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;


public class InMemoryGuestRegistry implements GuestRegistry {
    private final Set<String> people = new LinkedHashSet<>();
    private final Rooms rooms;


    public InMemoryGuestRegistry(Rooms rooms) { this.rooms = rooms; }


    @Override
    public Set<String> getListOfPeople() {
        return Collections.unmodifiableSet(people);
    }


    @Override
    public void addHuman(String human) {
        if (rooms.count() <= people.size()) {
            System.out.println("Мест больше нет: " + human);
            return;
        }
        if (!people.add(human)) {
            System.out.println("Гость уже добавлен: " + human);
        }
    }
}
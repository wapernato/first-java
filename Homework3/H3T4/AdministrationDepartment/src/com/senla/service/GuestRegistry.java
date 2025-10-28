package com.senla.service;


import java.util.Set;


/**
 * Контракт для управления списком гостей, ожидающих заселения.
 */
public interface GuestRegistry {
    Set<String> getListOfPeople();
    void addHuman(String human);
}
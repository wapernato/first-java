package com.senla.service;


import java.util.Set;



public interface GuestRegistry {
    Set<String> getListOfPeople();
    void addHuman(String human);
}
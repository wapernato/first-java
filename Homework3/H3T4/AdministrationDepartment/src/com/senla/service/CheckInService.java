package com.senla.service;


import com.senla.model.Assignment;


import java.util.Set;


/**
 * Контракт заселения/выселения гостей.
 */
public interface CheckInService {
    boolean checkIn(String roomNumber, String guestName);
    void assignAllPairwise();
    boolean checkoutByRoom(String roomNumber);
    boolean checkoutByGuest(String guestName);
    void printAssignments();
    Set<Assignment> viewAssignments();
}
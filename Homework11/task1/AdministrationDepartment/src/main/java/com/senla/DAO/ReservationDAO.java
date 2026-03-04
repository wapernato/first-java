package com.senla.DAO;

import com.senla.model.forDAO.Reservation;
import com.senla.model.ReservationStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationDAO {
    void save(Reservation reservation);
    Optional<Reservation> findById(int id);
    List<Reservation> findAll();
    List<Reservation> findByHotel(int hotelId);
    List<Reservation> findByGuest(int guestId);
    List<Reservation> findByStatus(ReservationStatus status);
    List<Reservation> findByDateRange(LocalDate start, LocalDate end);
    List<Reservation> findActiveReservationsForRoom(String roomNumber, LocalDate date);
    void update(Reservation reservation);
    void updateStatus(int reservationId, ReservationStatus status);
    void delete(int id);
}
package com.senla.DAO;

import com.senla.model.forDAO.ReservationRoom;
import java.util.List;
import java.util.Optional;

public interface ReservationRoomDAO {
    void save(ReservationRoom reservationRoom);
    Optional<ReservationRoom> findById(int reservationId, int roomId);
    List<ReservationRoom> findAll();
    List<ReservationRoom> findByReservationId(int reservationId);
    List<ReservationRoom> findByRoomId(int roomId);
    void update(ReservationRoom reservationRoom);
    void delete(int reservationId, int roomId);
}
package com.senla.DAO;

import com.senla.model.forDAO.Room;

import java.util.List;

public interface RoomDAO {
    void save(Room room);
    Room findById(int id);
    List<Room> findAll();
    void update(Room room);
    void delete(int id);

}

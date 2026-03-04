package com.senla.DAO;

import com.senla.model.forDAO.RoomType;

import java.util.List;

public interface RoomTypeDAO {
    void save(RoomType roomType);
    RoomType findById(int id);
    List<RoomType> findAll();
    void update(RoomType roomType);
    void delete(int id);


}

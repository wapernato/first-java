package com.senla.DAO;

import com.senla.model.forDAO.Guest;

import java.util.List;

public interface GuestDAO {
    void save(Guest guest);
    Guest findById(int id);
    List<Guest> findAll();
    void update(Guest guest);
    void delete(int id);

}

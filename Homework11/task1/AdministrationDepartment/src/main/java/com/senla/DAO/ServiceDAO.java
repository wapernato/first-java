package com.senla.DAO;

import com.senla.model.forDAO.Service;
import java.util.List;
import java.util.Optional;

public interface ServiceDAO {
    void save(Service service);
    Optional<Service> findById(int id);
    List<Service> findAll();
    List<Service> findByHotelId(int hotelId);
    Optional<Service> findByHotelAndName(int hotelId, String name);
    void update(Service service);
    void delete(int id);
}
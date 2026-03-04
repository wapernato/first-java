package com.senla.DAO;

import com.senla.model.forDAO.Hotel;

public interface HotelDAO {
    Hotel save(Hotel hotel);
    Hotel findById(Long id);
}



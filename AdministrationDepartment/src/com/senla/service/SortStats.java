package com.senla.service;


import com.senla.model.Room;

import java.util.List;


public interface SortStats {
   void sortRoomByStars(Rooms rooms);
   void sortRoomByCapacity(Rooms rooms);
   void sortRoomByPrice(Rooms rooms);
}

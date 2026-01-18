package com.senla.resources;

import com.senla.annotation.ConfigProperty;

public class AppConfig {

    @ConfigProperty(propertyName = "rooms.status.change")
    private boolean changeStatus;

    @ConfigProperty(propertyName = "rooms.history.limit")
    private int roomsHistoryLimit;


    public boolean isChangeStatus() { return changeStatus; }
    public int getRoomsHistoryLimit() { return roomsHistoryLimit; }
}

package com.senla.resources;

import com.senla.annotation.ConfigProperty;

import java.util.List;

public class AppConfig {

    @ConfigProperty(propertyName = "rooms.status.change")
    private boolean changeStatus;

    @ConfigProperty(propertyName = "rooms.history.limit")
    private int roomsHistoryLimit;

    @ConfigProperty(propertyName = "my.pkg.AppConfig.port")
    private int port;

    @ConfigProperty(propertyName = "my.pkg.AppConfig.debug")
    private boolean debug;

    @ConfigProperty(propertyName = "my.pkg.AppConfig.name")
    private String name;

    @ConfigProperty(propertyName = "my.pkg.AppConfig.ratios")
    private int[] ratios;

    @ConfigProperty(propertyName = "my.pkg.AppConfig.tags")
    private List<String> tags;


    public boolean isChangeStatus() { return changeStatus; }
    public int getRoomsHistoryLimit() { return roomsHistoryLimit; }
    public int getPort() {return port; }
    public boolean getDebug() {return debug; }
    public String getName() {return name; }
    public int[] getRatios() {return ratios; }
    public List<String> getTags() {return tags; }

}

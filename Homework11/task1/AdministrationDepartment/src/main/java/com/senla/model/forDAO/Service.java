package com.senla.model.forDAO;

import java.math.BigDecimal;

public class Service {
    private Integer serviceId;
    private Integer hotelId;
    private String name;
    private BigDecimal price;

    public Service() {}

    public Service(Integer hotelId, String name, BigDecimal price) {
        this.hotelId = hotelId;
        this.name = name;
        this.price = price;
    }

    public Integer getServiceId() { return serviceId; }
    public void setServiceId(Integer serviceId) { this.serviceId = serviceId; }

    public Integer getHotelId() { return hotelId; }
    public void setHotelId(Integer hotelId) { this.hotelId = hotelId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}
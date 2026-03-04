package com.senla.model.forDAO;

import java.math.BigDecimal;

public class RoomType {

    private Integer roomTypeId;
    private Integer stars;
    private Integer capacity;
    private BigDecimal basePrice;

    public RoomType() {}

    public RoomType(Integer stars, BigDecimal basePrice, Integer capacity){
        this.stars = stars;
        this.basePrice = basePrice;
        this.capacity = capacity;
    }

    public Integer getRoomTypeId() { return roomTypeId; }
    public int getStars() { return stars; }
    public int getCapacity() { return capacity; }
    public BigDecimal getBasePrice() { return basePrice; }

    public void setId(Integer id) { this.roomTypeId = id; }
    public void setStars(int stars) { this.stars = stars; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setBasePrice(BigDecimal basePrice) { this.basePrice = basePrice; }
}

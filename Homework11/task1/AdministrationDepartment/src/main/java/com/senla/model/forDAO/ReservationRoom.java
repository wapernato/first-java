package com.senla.model.forDAO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReservationRoom {
    private Integer reservationId;
    private Integer roomId;
    private BigDecimal pricePerNight;
    private Integer guestsCount;

    public ReservationRoom() {}

    public ReservationRoom(Integer reservationId, Integer roomId, BigDecimal pricePerNight, Integer guestsCount) {
        this.reservationId = reservationId;
        this.roomId = roomId;
        this.pricePerNight = pricePerNight;
        this.guestsCount = guestsCount;
    }

    public Integer getReservationId() { return reservationId; }
    public void setReservationId(Integer reservationId) { this.reservationId = reservationId; }

    public Integer getRoomId() { return roomId; }
    public void setRoomId(Integer roomId) { this.roomId = roomId; }

    public BigDecimal getPricePerNight() { return pricePerNight; }
    public void setPricePerNight(BigDecimal pricePerNight) { this.pricePerNight = pricePerNight; }

    public Integer getGuestsCount() { return guestsCount; }
    public void setGuestsCount(Integer guestsCount) { this.guestsCount = guestsCount; }
}
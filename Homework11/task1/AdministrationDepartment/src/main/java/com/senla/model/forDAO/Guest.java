package com.senla.model.forDAO;

import java.time.LocalDate;

public class Guest {
    private Integer guestId;
    private String localTag;
    private String number;
    private LocalDate checkIn;
    private LocalDate checkOut;

    public Guest() {};

    public Guest(String localTag, String number, LocalDate checkIn, LocalDate checkOut){
        this.localTag = localTag;
        this.number = number;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public Integer getGuestId() { return guestId; }
    public String getLocalTag() { return localTag; }
    public String getNumber() { return number; }
    public LocalDate getCheckIn() { return checkIn; }
    public LocalDate getCheckOut() { return checkOut; }

    public void setGuestId(Integer guestId) { this.guestId = guestId; }
    public void setLocalTag(String localTag) { this.localTag = localTag; }
    public void setNumber(String number) { this.number = number; }
    public void setCheckIn(LocalDate checkIn) { this.checkIn = checkIn; }
    public void setCheckOut(LocalDate checkOut) { this.checkOut = checkOut; }
}

package com.senla.model;



public class Hotel {
    private Long hotelId;
    private String city;
    private String name;
    private int stars;

    Hotel(String city, String name, int stars){
        this.city = city;
        this.name = name;
        this.stars = stars;
    }

    public String getName() {
        return name;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public String getCity() {
        return city;
    }


    public void setHotelId(long hotelId) {
        this.hotelId = hotelId;
    }

    public int getStars() {
        return stars;
    }


}

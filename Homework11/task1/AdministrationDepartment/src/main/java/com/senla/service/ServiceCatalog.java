package com.senla.service;


import java.util.Map;
import java.util.Set;


public interface ServiceCatalog {

    public int getNextId();
    void setNextId(int nextId);



    class Service {
        private String name;
        private double price;


        public Service(String name, double price) {
            this.name = name;
            this.price = price;
        }



        public String getName() { return name; }
        public double getPrice() { return price; }

        public void setPrice(double price) {
            this.price = price;
        }
        public void setName(String name){
            this.name = name;
        }
    }



    public record ServiceDto(String name, double price) {}
    Map<Integer, ServiceDto> getService();
    void addServiceDes(Map<Integer, ServiceDto> allService);

    void addService(String name, double price);
    void setServicePrice(String name, double price);

    Set<Integer> getServicesId();
    Set<String> listServiceNames();

    double getServicePrice(String name);
}
package com.senla.service;


import java.util.Set;


public interface ServiceCatalog {

    final class Service {
        private String name;
        private double price;

        public Service(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public String getName() { return name; }
        public double price() { return price; }

        public void setPrice(double price) {
            this.price = price;
        }

        public void setName(String name){
            this.name = name;
        }
    }

    void addService(String name, double price);
    void setServicePrice(String name, double price);
    Set<Integer> getServicesId();
    Set<String> listServiceNames();
    double getServicePrice(String name);
}
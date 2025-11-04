package com.senla.service;


import java.util.Set;


public interface ServiceCatalog {
    void addService(String name, double price);
    void setServicePrice(String name, double price);
    Set<String> listServiceNames();
    double getServicePrice(String name);
}
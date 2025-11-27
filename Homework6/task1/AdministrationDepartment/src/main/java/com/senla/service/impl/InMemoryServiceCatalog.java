package com.senla.service.impl;


import com.senla.service.ServiceCatalog;


import java.util.*;


public class InMemoryServiceCatalog implements ServiceCatalog {

    private int nextId = 1;

    public static final class Service {
        private final String name;
        private double price;
        public Service(String name, double price) { this.name = name; this.price = price; }
        public String name() { return name; }
        public double price() { return price; }
    }

    private final Map<Integer, Service> serviceIds = new HashMap<>();
    //private final Map<String, Service> services = new HashMap<>();


    @Override
    public void addService(String nameService, double price) {
        if (nameService == null || nameService.isBlank()) {
            System.out.println("Название услуги пустое");
            return;
        }
        if (price < 0) {
            System.out.println("Цена услуги не может быть отрицательной цены: " + price);
            return;
        }
        if (serviceIds.values().stream().anyMatch(s -> s.name.equalsIgnoreCase(nameService))) {
            System.out.println("Услуга уже существует: " + nameService);
            return;
        }
        int id = nextId++;
        serviceIds.put(id, new Service(nameService, price));
    }


    @Override
    public void setServicePrice(String name, double price) {
        if (price < 0) {
            System.out.println("Цена услуги не может быть отрицательной: " + price);
            return;
        }
        boolean serviceAvailability = serviceIds.values().stream().anyMatch(s -> s.name.contains(name));
        if (serviceAvailability) {
            System.out.println("Нет такой услуги: " + name);
            return;
        }
        serviceIds.values().stream().
    }


    @Override
    public Set<String> listServiceNames() {
        return Collections.unmodifiableSet(services.keySet());
    }


    @Override
    public double getServicePrice(String name) {
        Service s = services.get(name);
        return (s == null) ? -1 : s.price;
    }
}
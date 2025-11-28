package com.senla.service.impl;


import com.senla.service.ServiceCatalog;


import java.util.*;
import java.util.stream.Collectors;


public class InMemoryServiceCatalog implements ServiceCatalog {

    private int nextId = 1;

    public static final class Service {

        private final String name;
        private double price;
        public Service(String name, double price) { this.name = name; this.price = price; }
        public String getName() { return name; }
        public double getPrice() { return price; }
        public double price() { return price; }
        public void setPrice(double price) { this.price = price; }
    }

    private final Map<Integer, Service> serviceIds = new HashMap<>();


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

        Service service = serviceIds.values().stream()
                .filter(s -> s.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);

        if (service == null) {
            System.out.println("Нет такой услуги: " + name);
            return;
        }

        service.setPrice(price);
    }


    @Override
    public Set<String> listServiceNames() {
        return serviceIds.values().stream()
                .map(Service::getName)
                .filter(n -> n != null && !n.isEmpty())
                .collect(Collectors.toSet());
    }



    @Override
    public double getServicePrice(String nameService) {
        return serviceIds.values().stream()
                .filter(s -> s.getName() != null && s.getName().equalsIgnoreCase(nameService))
                .mapToDouble(Service::price)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Нет такой услуги: " + nameService));
    }

    public Set<Integer> getServicesId(){
        return serviceIds.keySet();
    }
}
package com.senla.service.impl;


import com.senla.service.ServiceCatalog;


import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


public class InMemoryServiceCatalog implements ServiceCatalog {
    public static final class Service {
        private final String name;
        private double price;
        public Service(String name, double price) { this.name = name; this.price = price; }
        public String name() { return name; }
        public double price() { return price; }
    }


    private final Map<String, Service> services = new LinkedHashMap<>();


    @Override
    public void addService(String name, double price) {
        if (name == null || name.isBlank()) {
            System.out.println("Название услуги пустое");
            return;
        }
        if (price < 0) {
            System.out.println("Цена услуги не может быть отрицательной: " + price);
            return;
        }
        if (services.containsKey(name)) {
            System.out.println("Услуга уже существует: " + name);
            return;
        }
        services.put(name, new Service(name, price));
    }


    @Override
    public void setServicePrice(String name, double price) {
        if (price < 0) {
            System.out.println("Цена услуги не может быть отрицательной: " + price);
            return;
        }
        Service s = services.get(name);
        if (s == null) {
            System.out.println("Нет такой услуги: " + name);
            return;
        }
        s.price = price;
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
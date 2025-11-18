package com.senla.service.impl;

import com.senla.service.ServiceCatalog;
import com.senla.service.ServiceUsageRegistry;

import java.time.LocalDate;
import java.util.*;

public class InMemoryServiceUsageRegistry implements ServiceUsageRegistry {

    private final Map<String, List<Usage>> byGuest = new HashMap<>();

    @Override
    public void addUsage(String guestName, String serviceName, LocalDate date, double priceAtUse) {
        byGuest.computeIfAbsent(guestName, k -> new ArrayList<>())
                .add(new Usage(guestName, serviceName, date, priceAtUse));
    }

    @Override
    public void addUsageFromCatalog(String guestName, String serviceName, LocalDate date, ServiceCatalog catalog) {
        double price = catalog.getServicePrice(serviceName);
        if (price < 0) {
            System.out.println("Услуга не найдена: " + serviceName);
            return;
        }
        addUsage(guestName, serviceName, date, price);
    }

    @Override
    public List<Usage> listByGuest(String guestName) {
        return List.copyOf(byGuest.getOrDefault(guestName, List.of()));
    }
}

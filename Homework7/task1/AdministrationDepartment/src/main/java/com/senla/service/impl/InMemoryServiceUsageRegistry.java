package com.senla.service.impl;

import com.senla.service.ServiceCatalog;
import com.senla.service.ServiceUsageRegistry;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryServiceUsageRegistry implements ServiceUsageRegistry {
    private int nextId = 1;
    private final Map<Integer, Usage> usageById = new HashMap<>();

    public int getNextId() { return nextId; }

    public static class Usage {
        private final String guestName;
        private final String serviceName;
        private final LocalDate date;
        private final double price;

        public Usage(String guestName, String serviceName, LocalDate date, double price){
            this.guestName = guestName;
            this.serviceName = serviceName;
            this.date = date;
            this.price = price;
        }

        public String getGuestName() { return guestName; }
        public String getServiceName() { return serviceName; }
        public LocalDate getDate() { return date; }
        public double getPrice() { return price; }
    }
    public Map<Integer, UsageDto> getAllServiceUsage(){
        return usageById.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> new UsageDto(
                        e.getValue().getGuestName(),
                        e.getValue(). getServiceName(),
                        e.getValue().getDate(),
                        e.getValue().getPrice()
                )
        ));
    }
    // запись услуги на гостя
    @Override
    public void addUsage(String guestName, String serviceName, LocalDate date, double price) {
        Usage usage = new Usage(guestName, serviceName, date, price);
        int id = nextId++;
        usageById.put(id, usage);
    }


    @Override
    public void addUsageFromCatalog(String guestName, String serviceName, LocalDate date, ServiceCatalog catalog) {
        try {
            double price = catalog.getServicePrice(serviceName);
            addUsage(guestName, serviceName, date, price);
        } catch (IllegalArgumentException e) {
            System.out.println("Услуга не найдена: " + serviceName);
        }
    }

    @Override
    public List<String> listByGuest(String guestName) {
        return usageById.values().stream()
                .filter(u -> u.getGuestName().equalsIgnoreCase(guestName))
                .map(u -> u.getDate() + " — " + u.getServiceName() + " — " + u.getPrice())
                .toList();
    }

    @Override
    public void serviceUsageDes(Map<Integer, UsageDto> usageMap){
        for(Map.Entry<Integer, UsageDto> entry : usageMap.entrySet()){
            Integer id = entry.getKey();
            UsageDto usageDto = entry.getValue();

            Usage usage = new Usage(usageDto.guestName(), usageDto.serviceName(), usageDto.date(), usageDto.price());

            usageById.put(id, usage);
        }
    }

    @Override
    public Integer setNextId(Integer nextId){
        return this.nextId = nextId;
    }

}

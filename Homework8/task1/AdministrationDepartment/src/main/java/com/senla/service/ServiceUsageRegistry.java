package com.senla.service;

import com.senla.annotation.ConfigProperty;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ServiceUsageRegistry {

    public int getNextId();
    void addUsage(String guestName, String serviceName, LocalDate date, double priceAtUse);

    void serviceUsageDes(Map<Integer, UsageDto> usageMap);

    void addUsageFromCatalog(String guestName, String serviceName, LocalDate date, ServiceCatalog catalog);

    List<String> listByGuest(String guestName);
    public record UsageDto(String guestName, String serviceName, LocalDate date, double price) {}
    public Map<Integer, UsageDto> getAllServiceUsage();

    final class Usage {
        public final String guestName;
        public final String serviceName;
        public final LocalDate date;
        public final double price;

        public Usage(String guestName, String serviceName, LocalDate date, double price) {
            this.guestName = guestName;
            this.serviceName = serviceName;
            this.date = date;
            this.price = price;
        }
        @Override public String toString() {  return date + " — " + serviceName + " — " + price;  }
    }

    Integer setNextId(Integer nextId);
}

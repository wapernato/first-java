package com.senla.service;

import java.time.LocalDate;
import java.util.List;

public interface ServiceUsageRegistry {
    // зафиксировать, что гость воспользовался услугой name в дату date, по цене priceAtUse
    void addUsage(String guestName, String serviceName, LocalDate date, double priceAtUse);

    // удобный помощник: забрать цену из каталога сейчас
    void addUsageFromCatalog(String guestName, String serviceName, LocalDate date, ServiceCatalog catalog);

    // все услуги гостя
    List<Usage> listByGuest(String guestName);

    // DTO
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
        @Override public String toString() {
            return date + " — " + serviceName + " — " + price;
        }
    }
}

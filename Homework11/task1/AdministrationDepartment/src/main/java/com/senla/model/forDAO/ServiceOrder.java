package com.senla.model.forDAO;

import com.senla.model.ServiceOrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ServiceOrder {
    private Integer orderId;
    private Integer reservationId;
    private Integer serviceId;
    private Integer qty;
    private BigDecimal unitPrice;
    private ServiceOrderStatus status;
    private LocalDateTime orderedAt;

    public ServiceOrder() {}

    public ServiceOrder(Integer reservationId, Integer serviceId, Integer qty,
                        BigDecimal unitPrice, ServiceOrderStatus status, LocalDateTime orderedAt) {
        this.reservationId = reservationId;
        this.serviceId = serviceId;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.status = status;
        this.orderedAt = orderedAt;
    }

    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }

    public Integer getReservationId() { return reservationId; }
    public void setReservationId(Integer reservationId) { this.reservationId = reservationId; }

    public Integer getServiceId() { return serviceId; }
    public void setServiceId(Integer serviceId) { this.serviceId = serviceId; }

    public Integer getQty() { return qty; }
    public void setQty(Integer qty) { this.qty = qty; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

    public ServiceOrderStatus getStatus() { return status; }
    public void setStatus(ServiceOrderStatus status) { this.status = status; }

    public LocalDateTime getOrderedAt() { return orderedAt; }
    public void setOrderedAt(LocalDateTime orderedAt) { this.orderedAt = orderedAt; }
}
package com.senla.DAO;

import com.senla.model.forDAO.ServiceOrder;
import com.senla.model.ServiceOrderStatus;
import java.util.List;
import java.util.Optional;

public interface ServiceOrderDAO {
    void save(ServiceOrder serviceOrder);
    Optional<ServiceOrder> findById(int id);
    List<ServiceOrder> findAll();
    List<ServiceOrder> findByReservationId(int reservationId);
    List<ServiceOrder> findByServiceId(int serviceId);
    List<ServiceOrder> findByStatus(ServiceOrderStatus status);
    void update(ServiceOrder serviceOrder);
    void updateStatus(int orderId, ServiceOrderStatus status);
    void delete(int id);
}
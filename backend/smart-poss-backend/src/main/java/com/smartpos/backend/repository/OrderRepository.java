package com.smartpos.backend.repository;

import com.smartpos.backend.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByOutletIdOrderByOrderDateDesc(String outletId);
}
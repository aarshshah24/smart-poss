package com.smartpos.backend.service;

import com.smartpos.backend.dto.OrderRequest;
import com.smartpos.backend.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    public Order createOrder(OrderRequest request);
    public List<Order> getOrdersByOutlet(String outletId);
    public Optional<Order> getOrderById(String id);
}

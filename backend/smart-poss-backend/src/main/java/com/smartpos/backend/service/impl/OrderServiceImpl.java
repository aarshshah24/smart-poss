package com.smartpos.backend.service.impl;
import com.smartpos.backend.dto.OrderRequest;
import com.smartpos.backend.model.Order;
import com.smartpos.backend.repository.OrderRepository;
import com.smartpos.backend.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(OrderRequest request) {
        Order order = new Order();
        order.setOutletId(request.getOutletId());
        order.setCustomerName(request.getCustomerName());
        order.setContactNo(request.getContactNo());
        order.setItems(request.getItems());
        order.setTotalAmount(request.getTotalAmount());
        order.setTotalQuantity(request.getTotalQuantity());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("COMPLETED");
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersByOutlet(String outletId) {
        return orderRepository.findByOutletIdOrderByOrderDateDesc(outletId);
    }
}
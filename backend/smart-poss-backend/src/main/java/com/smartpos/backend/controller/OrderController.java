package com.smartpos.backend.controller;

import com.smartpos.backend.dto.OrderRequest;
import com.smartpos.backend.model.Order;
import com.smartpos.backend.service.OrderService;
import com.smartpos.backend.service.SmsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;
    private final SmsService smsService;

    public OrderController(OrderService orderService, SmsService smsService) {
        this.orderService = orderService;
        this.smsService = smsService;
    }

    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest request) {
        // 1. Map DTO to Model and Save to MongoDB
        Order savedOrder = orderService.createOrder(request);

        // 2. Trigger the WhatsApp Bill using the DYNAMIC outlet name
        smsService.sendWhatsAppBill(
                request.getContactNo(),
                request.getCustomerName(),
                request.getOutletName(), // UPDATED: Use the name from the request DTO
                savedOrder.getTotalAmount(),
                savedOrder.getId()
        );

        return ResponseEntity.ok("Order created and WhatsApp bill sent! Order ID: " + savedOrder.getId());
    }

    @GetMapping("/outlet/{outletId}")
    public ResponseEntity<List<Order>> getOrders(@PathVariable String outletId) {
        return ResponseEntity.ok(orderService.getOrdersByOutlet(outletId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable String id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
package com.smartpos.backend.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Document(collection = "orders")
public class Order {
    @Id
    private String id;
    private String outletId;
    private String customerName;
    private String contactNo;
    private List<OrderItem> items;
    private double totalAmount;
    private int totalQuantity;
    private LocalDateTime orderDate;
    private String status; // e.g., "COMPLETED", "PENDING"
}
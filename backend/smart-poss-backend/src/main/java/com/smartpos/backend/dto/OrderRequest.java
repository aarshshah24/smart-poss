package com.smartpos.backend.dto;

import com.smartpos.backend.model.OrderItem;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private String outletId;
    private String customerName;
    private String outletName;
    private String contactNo;
    private List<OrderItem> items;
    private double totalAmount;
    private int totalQuantity;
}
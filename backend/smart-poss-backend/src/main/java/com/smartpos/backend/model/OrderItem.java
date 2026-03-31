package com.smartpos.backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItem {
    private String itemName;
    private double price;
    private int quantity;
}
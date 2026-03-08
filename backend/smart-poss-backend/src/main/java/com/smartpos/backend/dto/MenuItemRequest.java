package com.smartpos.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuItemRequest {

    private String outletId;
    private String itemName;
    private double price;
    private String category;

}

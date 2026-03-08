package com.smartpos.backend.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "menu_items")
public class MenuItem {

    @Id
    private String id;
    private String outletId;
    private String itemName;
    private double price;
    private String category;
}
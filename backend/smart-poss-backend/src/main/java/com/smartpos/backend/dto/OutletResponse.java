package com.smartpos.backend.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OutletResponse {
    private String id;

    //user input fields
    private String ownerName;
    private String email;
    private Long phoneNumber;
    private String outletName;
    private String city;
    private String outletType;

    //system fields
    private String status; //APPROVED, PENDING, REJECTED
    private LocalDateTime createdAt;
    private LocalDateTime approvedAt;
}

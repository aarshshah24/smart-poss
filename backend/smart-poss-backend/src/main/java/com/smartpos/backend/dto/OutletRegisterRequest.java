package com.smartpos.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OutletRegisterRequest {
    private String id;

    //user input fields
    private String ownerName;
    private String email;
    private Long phoneNumber;
    private String outletName;
    private String city;
    private String outletType;
    private String password;

}

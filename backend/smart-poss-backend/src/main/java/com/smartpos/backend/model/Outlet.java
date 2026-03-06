package com.smartpos.backend.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "outlets")
public class Outlet {
    @Id
    private String id;

    //user input fields
    private String ownerName;
    private boolean loggedIn;
    private String email;
    private Long phoneNumber;
    private String outletName;
    private String city;
    private String outletType;
    private String password;
    private boolean loggedIn;
    //system fields
    private String status; //APPROVED, PENDING, REJECTED
    private LocalDateTime createdAt;
    private LocalDateTime approvedAt;
}

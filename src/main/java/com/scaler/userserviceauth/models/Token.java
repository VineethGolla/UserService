package com.scaler.userserviceauth.models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
public class Token extends BaseModel {
    private String tokenValue;
    @ManyToOne
    private User user;
    private Date expiryDate;

}

/*
1        1
token   user
m        1
 */
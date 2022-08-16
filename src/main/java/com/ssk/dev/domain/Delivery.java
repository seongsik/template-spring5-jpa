package com.ssk.dev.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by sik371@ktnet.co.kr 2022-08-11 오후 7:00
 */
@Entity
@Getter
@Setter
public class Delivery implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long deliveryId;

    private String city;
    private String street;
    private String zipcode;
    private String status;
}

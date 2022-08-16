package com.ssk.dev.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by sik371@ktnet.co.kr 2022-08-11 오후 7:00
 */
@Entity
@Getter
@Setter
public class Member implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long memberId;

    private String name;
    private String city;
    private String street;
    private String zipcode;
}

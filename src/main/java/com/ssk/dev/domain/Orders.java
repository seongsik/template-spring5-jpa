package com.ssk.dev.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by sik371@ktnet.co.kr 2022-08-11 오후 7:00
 */
@Entity
@Getter
@Setter
public class Orders implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    private LocalDateTime orderDate;
    private String status;
    private Long deliveryId;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}

package com.ssk.dev.api.v1.response;

import com.ssk.dev.domain.Member;
import com.ssk.dev.domain.Orders;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by sik371@ktnet.co.kr 2022-08-12 오전 10:17
 */
@Data
@ApiModel
public class OrderDto {

    @ApiModelProperty(value = "주문ID")
    private Long orderId;

    @ApiModelProperty(value = "주문시각")
    private String orderDate;
    private String status;

    private Long memberId;
    private String memberName;

    private Long deliveryId;
    private String deliveryCity;
    private String deliveryStreet;

    public OrderDto(Orders o) {
        this.orderId = o.getOrderId();
        this.orderDate = o.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.status = o.getStatus();

        // Join - Lazy Loading
        this.memberId = o.getMember().getMemberId();
        this.memberName = o.getMember().getName();

        // Join - Eager Loading
        this.deliveryId = o.getDelivery().getDeliveryId();
        this.deliveryCity = o.getDelivery().getCity();
        this.deliveryStreet = o.getDelivery().getStreet();
    }
}

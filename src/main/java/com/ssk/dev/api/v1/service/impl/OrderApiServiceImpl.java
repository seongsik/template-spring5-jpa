package com.ssk.dev.api.v1.service.impl;

import com.ssk.dev.api.v1.response.OrderDto;
import com.ssk.dev.api.v1.service.OrderApiService;
import com.ssk.dev.domain.Orders;
import com.ssk.dev.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sik371@ktnet.co.kr 2022-08-12 오후 2:59
 */
@Service("orderApiServiceImpl")
@Transactional
@AllArgsConstructor
public class OrderApiServiceImpl implements OrderApiService {

    private final OrderRepository orderRepository;

    @Override
    public List<OrderDto> findAll() {
        List<Orders> data = orderRepository.findAll();
        return data.stream().map(OrderDto::new).collect(Collectors.toList());
    }


}

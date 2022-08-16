package com.ssk.dev.repository;

import com.ssk.dev.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by sik371@ktnet.co.kr 2022-08-12 오전 10:16
 */
public interface OrderRepository extends JpaRepository<Orders, Long> {
}

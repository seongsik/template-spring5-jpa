package com.ssk.dev.api.v1.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by sik371@ktnet.co.kr 2022-08-12 오전 9:55
 */
@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private int count;
    private T data;
}

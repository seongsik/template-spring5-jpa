package com.ssk.dev.api.v1;

import com.ssk.dev.api.v1.response.ApiResponse;
import com.ssk.dev.api.v1.response.OrderDto;
import com.ssk.dev.api.v1.service.OrderApiService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by sik371@ktnet.co.kr 2022-08-12 오전 9:52
 */
@Controller
@AllArgsConstructor
@RequestMapping(value = "/api/v1/orders")
public class OrderApiController {

    private final OrderApiService orderApiService;

    /**
     * @return ApiResponse<List<OrderDto>> 주문 목록
     */
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ApiResponse<List<OrderDto>> members() {
        List<OrderDto> data = orderApiService.findAll();
        return new ApiResponse<>(data.size(), data);
    }
}

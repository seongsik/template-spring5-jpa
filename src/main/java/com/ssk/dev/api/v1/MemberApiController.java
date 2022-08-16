package com.ssk.dev.api.v1;

import com.ssk.dev.api.v1.response.ApiResponse;
import com.ssk.dev.api.v1.response.MemberDto;
import com.ssk.dev.api.v1.service.MemberApiService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
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
@RequestMapping(value = "/api/v1/member")
public class MemberApiController {

    private final MemberApiService memberApiService;

    /**
     * @return ApiResponse<List<MemberDto>> 멤버 목록
     */
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ApiResponse<List<MemberDto>> members() {
        List<MemberDto> data = memberApiService.findAll();
        return new ApiResponse<>(data.size(), data);
    }

    /**
     * @return ApiResponse<List<MemberDto>> 멤버 목록
     */
    @ResponseBody
    @RequestMapping(value = "/pageable", method = RequestMethod.GET)
    public ApiResponse<List<MemberDto>> members(Pageable pageable) {
        List<MemberDto> data = memberApiService.findAllWithPaging(pageable);
        return new ApiResponse<>(data.size(), data);
    }


}

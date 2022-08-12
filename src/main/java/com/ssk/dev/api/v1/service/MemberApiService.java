package com.ssk.dev.api.v1.service;

import com.ssk.dev.api.v1.response.MemberDto;

import java.util.List;

/**
 * Created by sik371@ktnet.co.kr 2022-08-12 오후 2:59
 */
public interface MemberApiService {
    List<MemberDto> findAll();
}

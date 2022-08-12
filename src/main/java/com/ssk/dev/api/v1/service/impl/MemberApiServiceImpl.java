package com.ssk.dev.api.v1.service.impl;

import com.ssk.dev.api.v1.response.MemberDto;
import com.ssk.dev.api.v1.service.MemberApiService;
import com.ssk.dev.domain.Member;
import com.ssk.dev.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sik371@ktnet.co.kr 2022-08-12 오후 2:59
 */
@Service("memberApiServiceImpl")
@Transactional
@AllArgsConstructor
public class MemberApiServiceImpl implements MemberApiService {

    private final MemberRepository memberRepository;

    @Override
    public List<MemberDto> findAll() {
        List<Member> members = memberRepository.findAll();
        return members.stream().map(MemberDto::new).collect(Collectors.toList());

//        return null;
    }
}

package com.ssk.dev.repository;

import com.ssk.dev.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sik371@ktnet.co.kr 2022-08-12 오전 10:16
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
}

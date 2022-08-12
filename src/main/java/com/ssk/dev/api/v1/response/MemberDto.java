package com.ssk.dev.api.v1.response;

import com.ssk.dev.domain.Member;
import lombok.Data;

/**
 * Created by sik371@ktnet.co.kr 2022-08-12 오전 10:17
 */
@Data
public class MemberDto {
    private Long id;

    private String name;
    private String city;
    private String street;
    private String zipcode;

    public MemberDto(Member o) {
        this.id = o.getId();
        this.name = o.getName();
        this.city = o.getCity();
        this.street = o.getStreet();
        this.zipcode = o.getZipcode();
    }
}

package com.ssk.dev.api.v1.response;

import com.ssk.dev.domain.Member;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by sik371@ktnet.co.kr 2022-08-12 오전 10:17
 */
@Data
@ApiModel
public class MemberDto {

    @ApiModelProperty(value = "멤버ID")
    private Long memberId;

    @ApiModelProperty(value = "이름", required = true)
    private String name;
    private String city;
    private String street;
    private String zipcode;

    public MemberDto(Member o) {
        this.memberId = o.getMemberId();
        this.name = o.getName();
        this.city = o.getCity();
        this.street = o.getStreet();
        this.zipcode = o.getZipcode();
    }
}

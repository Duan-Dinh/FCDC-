package com.fpt.myweb.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DoctorRes {
    private Long id;
    private String name;
    private String phone;
}

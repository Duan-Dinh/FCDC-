package com.fpt.myweb.dto.request;

import lombok.*;

@Setter
@Getter
@ToString
public class LoginRequest {
    private Long id;
    private String phone;
    private String password;
}

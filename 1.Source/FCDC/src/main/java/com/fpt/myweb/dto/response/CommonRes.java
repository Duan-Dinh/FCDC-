package com.fpt.myweb.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommonRes {
    private String responseCode;
    private String message;
    private Object data;
}

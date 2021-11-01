package com.fpt.myweb.dto.response;

import com.fpt.myweb.dto.request.UserRequet;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class UserRes {
    private long total;
    private List<UserRequet> userRequets;
}

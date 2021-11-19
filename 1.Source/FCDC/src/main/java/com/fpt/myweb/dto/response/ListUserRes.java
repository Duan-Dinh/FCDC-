package com.fpt.myweb.dto.response;

import com.fpt.myweb.dto.request.ListUserRequest;
import com.fpt.myweb.dto.request.UserRequet;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ListUserRes {
    private long total;
    private List<ListUserRequest> listUserRequests;
}

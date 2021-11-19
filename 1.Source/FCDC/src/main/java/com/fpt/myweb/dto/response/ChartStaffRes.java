package com.fpt.myweb.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChartStaffRes {
    private String Date;
    // private DetailOneDayRes detailOneDayRes;
    private int totalNewF0;
    private int totalCured;
    private int totalCurrentF0;
}

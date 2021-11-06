package com.fpt.myweb.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReportDetail {
    private Long reportId;
    private Long userId;
    private String fullname;
    private String gender;
    private String phone;
    private String dateOfBirth;
    private String address;
    private String statusReport;

}

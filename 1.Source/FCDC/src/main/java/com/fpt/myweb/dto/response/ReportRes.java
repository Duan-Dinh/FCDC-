package com.fpt.myweb.dto.response;

import com.fpt.myweb.dto.request.Report;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReportRes {
    private long total;
    private List<ReportDetail> reportDetails;
}

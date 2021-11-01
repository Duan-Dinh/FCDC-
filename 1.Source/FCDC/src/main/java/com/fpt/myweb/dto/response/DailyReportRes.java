package com.fpt.myweb.dto.response;

import com.fpt.myweb.dto.request.Report;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class DailyReportRes {
    private long total;
    private List<Report> dailyReports;
}

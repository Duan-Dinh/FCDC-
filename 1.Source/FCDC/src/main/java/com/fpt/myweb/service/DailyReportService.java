package com.fpt.myweb.service;

import com.fpt.myweb.dto.request.FeebackReqest;
import com.fpt.myweb.dto.request.Report;
import com.fpt.myweb.dto.response.ReportDetailRes;
import com.fpt.myweb.entity.Daily_Report;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DailyReportService {

    Page<Daily_Report> getReport(Integer page);

    void addReport(Report report);

    Daily_Report getOneReport(Long id);
    List<Daily_Report> getOneByUserID(Long id);
    List<Daily_Report> getByDate(String time);
    void editFeeback(FeebackReqest feebackReqest);



}

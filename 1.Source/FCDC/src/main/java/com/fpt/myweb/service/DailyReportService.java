package com.fpt.myweb.service;

import com.fpt.myweb.dto.request.FeebackReqest;
import com.fpt.myweb.dto.request.Report;
import com.fpt.myweb.entity.Daily_Report;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DailyReportService {

    Page<Daily_Report> getReport(Integer page);

    void addReport(Report report);

    Daily_Report getOneReport(Long id);
    List<Daily_Report> getOneByUserID(Long id,Integer page);
    public int countAllUserID(Long id);

    List<Daily_Report> getByReport(String time,Long villaId,String key,Integer page);
    public int countSentReport(String time,Long villaId,String key);
    void editFeeback(FeebackReqest feebackReqest);



}

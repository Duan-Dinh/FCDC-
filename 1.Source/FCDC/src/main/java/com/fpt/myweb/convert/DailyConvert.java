package com.fpt.myweb.convert;

import com.fpt.myweb.dto.request.NewRequet;
import com.fpt.myweb.dto.request.Report;
import com.fpt.myweb.dto.response.ReportDetail;
import com.fpt.myweb.entity.Daily_Report;
import com.fpt.myweb.entity.New;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

@Component
public class DailyConvert {
    public ReportDetail convertToReportDetail(Daily_Report report){
        ReportDetail item = new ReportDetail();
        item.setReportId(report.getId());
        item.setUserId(report.getUser().getId());
        item.setFullname(report.getUser().getFullname());
        item.setGender(report.getUser().getGender());
        item.setPhone(report.getUser().getPhone());
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        item.setDateOfBirth(dateFormat.format(report.getUser().getBirthOfdate()));
        item.setAddress(report.getUser().getAddress() + " - " + report.getUser().getVillage().getName() + " - " + report.getUser().getVillage().getDistrict().getName() + " - " + report.getUser().getVillage().getDistrict().getProvince().getName());
        item.setStatusReport(report.getStatus());
        item.setFeedback(report.getFeedback());
        return item;
    }

}

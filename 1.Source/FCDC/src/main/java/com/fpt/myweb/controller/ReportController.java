package com.fpt.myweb.controller;

import com.fpt.myweb.dto.request.FeebackReqest;
import com.fpt.myweb.dto.request.Report;
import com.fpt.myweb.dto.response.CommonRes;
import com.fpt.myweb.dto.response.DailyReportRes;
import com.fpt.myweb.dto.response.ReportDetail;
import com.fpt.myweb.dto.response.ReportRes;
import com.fpt.myweb.entity.*;
import com.fpt.myweb.exception.ErrorCode;
import com.fpt.myweb.service.DailyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/report")
public class ReportController {
    @Autowired
    private DailyReportService dailyReportService;

    @PostMapping(value = "/addReport", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> addReport(Report report) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            dailyReportService.addReport(report);
        } catch (Exception e) {
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

    @GetMapping(value = "/getAllReport", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> getAllReport(@PathParam("page") Integer page) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            Page<Daily_Report> newList = dailyReportService.getReport(page);
            List<Daily_Report> daily_reports = newList.getContent();
            List<Report> reports = new ArrayList<>();
            if (!daily_reports.isEmpty()) {
                for (Daily_Report report : daily_reports) {
                    Report item = new Report();
                    item.setId(report.getId());
                    item.setUserId(report.getUser().getId());
                    item.setComment(report.getComment());
                    item.setBodyTemperature(report.getBodyTemperature());
                    item.setOxygenConcentration(report.getOxygenConcentration());
                    item.setListExercise(report.getExercises().stream().map(e -> e.getId()).collect(Collectors.toList()));
                    item.setListMedicine(report.getMedicines().stream().map(e -> e.getId()).collect(Collectors.toList()));
                    item.setListSysptom(report.getSysptoms().stream().map(e -> e.getId()).collect(Collectors.toList()));
                    item.setDateReport(report.getDateTime());
                    item.setStatus(report.getStatus());
                    item.setBreathingRate(report.getBreathingRate());
                    reports.add(item);
                }
            }
            DailyReportRes reportRes = new DailyReportRes();
            reportRes.setTotal(newList.getTotalElements());
            reportRes.setDailyReports(reports);
            commonRes.setData(reportRes);
        } catch (Exception e) {
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }


    // getOne
    @GetMapping(value = "/getById")
    public ResponseEntity<CommonRes> getOneReport(@PathParam("id") Long id) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            Daily_Report report = dailyReportService.getOneReport(id);
            Report item = new Report();
            if (report != null) {
                item.setId(report.getId());
                item.setUserId(report.getUser().getId());
                item.setComment(report.getComment());
                item.setBodyTemperature(report.getBodyTemperature());
                item.setOxygenConcentration(report.getOxygenConcentration());
                item.setListExercise(report.getExercises().stream().map(e -> e.getId()).collect(Collectors.toList()));
                item.setListMedicine(report.getMedicines().stream().map(e -> e.getId()).collect(Collectors.toList()));
                item.setListSysptom(report.getSysptoms().stream().map(e -> e.getId()).collect(Collectors.toList()));
                item.setDateReport(report.getDateTime());
                item.setStatus(report.getStatus());
                item.setBreathingRate(report.getBreathingRate());
                item.setFeedback(report.getFeedback());
            }
            commonRes.setData(item);
        } catch (Exception e) {
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

    // getUserIdReport
    @GetMapping(value = "/getByUserId")
    public ResponseEntity<CommonRes> getUserIdReport(@PathParam("userId") Long userId) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            List<Daily_Report> daily_reports = dailyReportService.getOneByUserID(userId);
            List<Report> reports = new ArrayList<>();
            if (!daily_reports.isEmpty()) {
                for (Daily_Report report : daily_reports) {
                    Report item = new Report();
                    item.setId(report.getId());
                    item.setUserId(report.getUser().getId());
                    item.setComment(report.getComment());
                    item.setBodyTemperature(report.getBodyTemperature());
                    item.setOxygenConcentration(report.getOxygenConcentration());
                    item.setListExercise(report.getExercises().stream().map(e -> e.getId()).collect(Collectors.toList()));
                    item.setListMedicine(report.getMedicines().stream().map(e -> e.getId()).collect(Collectors.toList()));
                    item.setListSysptom(report.getSysptoms().stream().map(e -> e.getId()).collect(Collectors.toList()));
                    item.setDateReport(report.getDateTime());
                    item.setStatus(report.getStatus());
                    item.setBreathingRate(report.getBreathingRate());
                    item.setFeedback(report.getFeedback());
                    reports.add(item);
                }
            }
            DailyReportRes reportRes = new DailyReportRes();
            reportRes.setDailyReports(reports);
            reportRes.setTotal(reports.size());
            commonRes.setData(reportRes);
        } catch (Exception e) {
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

    // truyền vào 1 ngày và trả về nhưng thằng đã gửi report và tình trạng report đó

    @GetMapping(value = "/getAllSentReportOnedate")
    public ResponseEntity<CommonRes> getAllSentReportOnedate(@RequestParam("time") String time , @PathParam("villageId") Long villageId) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            List<Daily_Report> daily_reports = dailyReportService.getByDate(time);
            List<ReportDetail> reports = new ArrayList<>();
            if (!daily_reports.isEmpty()) {
                for (Daily_Report report : daily_reports) {
                    if (report.getUser().getIs_active().equals("1") && report.getUser().getVillage().getId() == villageId.longValue()) {
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
                        reports.add(item);
                    }
                }
            }
            ReportRes reportRes = new ReportRes();
            reportRes.setTotal(reports.size());
            reportRes.setReportDetails(reports);

            commonRes.setData(reportRes);
        } catch (Exception e) {
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }


    // dành cho thằng staff feedback report
    @PutMapping(value = "/editFeeback", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> editFeeback(FeebackReqest feebackReqest) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            dailyReportService.editFeeback(feebackReqest);
        } catch (Exception e) {
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

}

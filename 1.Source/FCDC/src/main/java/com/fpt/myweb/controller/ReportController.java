package com.fpt.myweb.controller;

import com.fpt.myweb.convert.UserConvert;
import com.fpt.myweb.dto.request.FeebackReqest;
import com.fpt.myweb.dto.request.Report;
import com.fpt.myweb.dto.request.UserRequet;
import com.fpt.myweb.dto.response.CommonRes;
import com.fpt.myweb.dto.response.DailyReportRes;
import com.fpt.myweb.dto.response.ReportDetailRes;
import com.fpt.myweb.entity.*;
import com.fpt.myweb.exception.AppException;
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

@RestController
@RequestMapping(value = "/report")
public class ReportController {
    @Autowired
    private DailyReportService dailyReportService;

    @PostMapping(value = "/addReport", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> addReport(Report report){
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            dailyReportService.addReport(report);
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

    @GetMapping(value = "/getAllReport", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> getAllReport(@PathParam("page") Integer page){
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            Page<Daily_Report> newList = dailyReportService.getReport(page);
            List<Daily_Report> daily_reports = newList.getContent();
            List<Report> reports = new ArrayList<>();
            if (!daily_reports.isEmpty()) {
                for (Daily_Report report: daily_reports){
                    Report item = new Report();
                    item.setId(report.getId());
                    item.setUserId(report.getUser().getId());
                    item.setComment(report.getComment());
                    item.setBodyTemperature(report.getBodyTemperature());
                    item.setOxygenConcentration(report.getOxygenConcentration());
                    item.setListExercise(report.getExercises().stream().map(e->e.getId()).collect(Collectors.toList()));
                    item.setListMedicine(report.getMedicines().stream().map(e->e.getId()).collect(Collectors.toList()));
                    item.setListSysptom(report.getSysptoms().stream().map(e->e.getId()).collect(Collectors.toList()));
                    item.setDateReport(report.getDateTime());
                    reports.add(item);
                }
            }
            DailyReportRes reportRes = new DailyReportRes();
            reportRes.setTotal(newList.getTotalElements());
            reportRes.setDailyReports(reports);
            commonRes.setData(reportRes);
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }
//
//    // getOne
//    @GetMapping(value = "/get/{id}")
//    public ResponseEntity<CommonRes> getOneReport(@PathVariable("id") Long id) {
//        CommonRes commonRes = new CommonRes();
//        try {
//            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
//            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
//            Page<Daily_Report> newList = dailyReportService.getReport(id);
//            List<Daily_Report> daily_reports = newList.getContent();
//            List<Report> reports = new ArrayList<>();
//            if (!daily_reports.isEmpty()) {
//                for (Daily_Report report: daily_reports){
//                    Report item = new Report();
//                    item.setId(report.getId());
//                    item.setUserId(report.getUser().getId());
//                    item.setComment(report.getComment());
//                    item.setBodyTemperature(report.getBodyTemperature());
//                    item.setOxygenConcentration(report.getOxygenConcentration());
//                    item.setListExercise(report.getExercises().stream().map(e->e.getId()).collect(Collectors.toList()));
//                    item.setListMedicine(report.getMedicines().stream().map(e->e.getId()).collect(Collectors.toList()));
//                    item.setListSysptom(report.getSysptoms().stream().map(e->e.getId()).collect(Collectors.toList()));
//                    item.setDateReport(report.getDateTime());
//                    reports.add(item);
//                }
//            }
//            DailyReportRes reportRes = new DailyReportRes();
//            reportRes.setTotal(newList.getTotalElements());
//            reportRes.setDailyReports(reports);
//            commonRes.setData(reportRes);
//        } catch (Exception e){
//            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
//            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
//        }
//        return ResponseEntity.ok(commonRes);
//    }
//
//

    // getOne
    @GetMapping(value = "/getById/{id}")
    public ResponseEntity<CommonRes> getOneReport(@PathVariable("id") Long id) {
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
                item.setListExercise(report.getExercises().stream().map(e->e.getId()).collect(Collectors.toList()));
                item.setListMedicine(report.getMedicines().stream().map(e->e.getId()).collect(Collectors.toList()));
                item.setListSysptom(report.getSysptoms().stream().map(e->e.getId()).collect(Collectors.toList()));
                item.setDateReport(report.getDateTime());
            }
            commonRes.setData(item);
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

    // getUserIdReport
    @GetMapping(value = "/getByUserId/{userId}")
    public ResponseEntity<CommonRes> getUserIdReport(@PathVariable("userId") Long userId) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            List< Daily_Report> daily_reports = dailyReportService.getOneByUserID(userId);
            List<Report> reports = new ArrayList<>();
            if (!daily_reports.isEmpty()) {
                for (Daily_Report report: daily_reports){
                    Report item = new Report();
                    item.setId(report.getId());
                    item.setUserId(report.getUser().getId());
                    item.setComment(report.getComment());
                    item.setBodyTemperature(report.getBodyTemperature());
                    item.setOxygenConcentration(report.getOxygenConcentration());
                    item.setListExercise(report.getExercises().stream().map(e->e.getId()).collect(Collectors.toList()));
                    item.setListMedicine(report.getMedicines().stream().map(e->e.getId()).collect(Collectors.toList()));
                    item.setListSysptom(report.getSysptoms().stream().map(e->e.getId()).collect(Collectors.toList()));
                    item.setDateReport(report.getDateTime());
                    reports.add(item);
                }
            }
            DailyReportRes reportRes = new DailyReportRes();

            reportRes.setDailyReports(reports);
            commonRes.setData(reportRes);
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }
    @GetMapping(value = "/getByDateReport")
    public ResponseEntity<CommonRes> getbydate(@RequestParam("time") String time) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            List< Daily_Report> daily_reports = dailyReportService.getByDate(time);
            List<Report> reports = new ArrayList<>();
            if (!daily_reports.isEmpty()) {
                for (Daily_Report report: daily_reports){
                    Report item = new Report();
                    item.setId(report.getId());
                    item.setUserId(report.getUser().getId());
                    item.setComment(report.getComment());
                    item.setBodyTemperature(report.getBodyTemperature());
                    item.setOxygenConcentration(report.getOxygenConcentration());
                    item.setListExercise(report.getExercises().stream().map(e->e.getId()).collect(Collectors.toList()));
                    item.setListMedicine(report.getMedicines().stream().map(e->e.getId()).collect(Collectors.toList()));
                    item.setListSysptom(report.getSysptoms().stream().map(e->e.getId()).collect(Collectors.toList()));
                    item.setDateReport(report.getDateTime());
                    reports.add(item);
                }
            }
            DailyReportRes reportRes = new DailyReportRes();

            reportRes.setDailyReports(reports);
            commonRes.setData(reportRes);
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }
//    @GetMapping(value = "/getByDateReport1")
//    public ResponseEntity<CommonRes> getbydate1(@RequestParam("time") String time) {
//        CommonRes commonRes = new CommonRes();
//        try {
//            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
//            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
//            List<Daily_Report> daily_reports = dailyReportService.getByDate(time);
//            List<ReportDetailRes> reportDetailRes = new ArrayList<>();
//            if (!daily_reports.isEmpty()) {
//                for (Daily_Report report: daily_reports){
//                    ReportDetailRes item = new ReportDetailRes();
//                    item.setUserId(report.getUser().getId());
//                    item.setFullname(report.getUser().getFullname());
//                    item.setGender(report.getUser().getGender());
//                    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//                    item.setDateOfBirth(dateFormat.format(report.getUser().getBirthOfdate()));
//                    item.setPhone(report.getUser().getPhone());
//                    item.setVilaId(report.getUser().getVillage().getName());
//                    reportDetailRes.add(item);
//                }
//            }
//            if(reportDetailRes.size()==0){
//                commonRes.setData("co cl");
//            }else{
//            commonRes.setData(reportDetailRes);}
//        } catch (Exception e){
//            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
//            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
//        }
//        return ResponseEntity.ok(commonRes);
//    }


    @PutMapping(value = "/editFeeback", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> editFeeback(FeebackReqest feebackReqest){
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            dailyReportService.editFeeback(feebackReqest);
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

}

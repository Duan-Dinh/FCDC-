package com.fpt.myweb.controller;

import com.fpt.myweb.common.Contants;
import com.fpt.myweb.dto.request.FeebackReqest;
import com.fpt.myweb.dto.request.ListUserRequest;
import com.fpt.myweb.dto.request.Report;
import com.fpt.myweb.dto.response.*;
import com.fpt.myweb.entity.*;
import com.fpt.myweb.exception.ErrorCode;
import com.fpt.myweb.service.DailyReportService;
import com.fpt.myweb.service.UserService;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/report")
public class ReportController {
    @Autowired
    private DailyReportService dailyReportService;
    @Autowired
    ObjectFactory<HttpSession> httpSessionFactory;
    @PostMapping(value = "/addReport", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> addReport(Report report) {
        CommonRes commonRes = new CommonRes();
        try {
            HttpSession session = httpSessionFactory.getObject();
            User user = (User) session.getAttribute(Contants.USER_SESSION);
            Role role = user.getRole();
            if(role.getId() == 2L ||  role.getId() == 4L){
                commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
                commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
                dailyReportService.addReport(report);
            }
            else{
                commonRes.setResponseCode(ErrorCode.AUTHEN.getKey());
                commonRes.setMessage(ErrorCode.AUTHEN.getValue());
            }

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
            HttpSession session = httpSessionFactory.getObject();
            User user = (User) session.getAttribute(Contants.USER_SESSION);
            Role role = user.getRole();
            if(role.getId() == 2L || role.getId() == 3L || role.getId() == 4L){

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

            }
            else{
                commonRes.setResponseCode(ErrorCode.AUTHEN.getKey());
                commonRes.setMessage(ErrorCode.AUTHEN.getValue());
            }

        } catch (Exception e) {
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

    // getUserIdReport
    @GetMapping(value = "/getByUserId")
    public ResponseEntity<CommonRes> getUserIdReport(@PathParam("userId") Long userId,@PathParam("page") Integer page) {
        CommonRes commonRes = new CommonRes();
        try {
            HttpSession session = httpSessionFactory.getObject();
            User user = (User) session.getAttribute(Contants.USER_SESSION);
            Role role = user.getRole();
            if(role.getId() == 2L || role.getId() == 3L || role.getId() == 4L){
                commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
                commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
                List<Daily_Report> daily_reports = dailyReportService.getOneByUserID(userId,page);
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
                reportRes.setTotal(dailyReportService.countAllUserID(userId));
                commonRes.setData(reportRes);


            }
            else{
                commonRes.setResponseCode(ErrorCode.AUTHEN.getKey());
                commonRes.setMessage(ErrorCode.AUTHEN.getValue());
            }

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
            HttpSession session = httpSessionFactory.getObject();
            User user = (User) session.getAttribute(Contants.USER_SESSION);
            Role role = user.getRole();
            if(role.getId() == 2L){
                commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
                commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
                dailyReportService.editFeeback(feebackReqest);
            }
            else{
                commonRes.setResponseCode(ErrorCode.AUTHEN.getKey());
                commonRes.setMessage(ErrorCode.AUTHEN.getValue());
            }

        } catch (Exception e) {
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

}

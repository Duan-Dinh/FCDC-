package com.fpt.myweb.controller;

import com.fpt.myweb.common.Contants;
import com.fpt.myweb.dto.request.ListUserRequest;
import com.fpt.myweb.dto.request.UserRequet;
import com.fpt.myweb.dto.response.*;
import com.fpt.myweb.entity.Role;
import com.fpt.myweb.entity.User;
import com.fpt.myweb.exception.AppException;
import com.fpt.myweb.exception.ErrorCode;
import com.fpt.myweb.service.DailyReportService;
import com.fpt.myweb.service.UserService;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/staff")
public class StaffController {
    @Autowired
    private DailyReportService dailyReportService;
    @Autowired
    private UserService userService;
    @GetMapping("/allPatientForStaff")// fomat sang DTO trả về dữ liệu
    public ResponseEntity<CommonRes> getAll(@PathParam("villageId") Long villageId,@PathParam("key") String key,@PathParam("result") String result,@PathParam("page") Integer page) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            List<ListUserRequest> listUserRequests = userService.getAllPatientForStaff(villageId,key,result,page);
            ListUserRes listUserRes = new ListUserRes();
            listUserRes.setListUserRequests(listUserRequests);
            listUserRes.setTotal(userService.countByPatientsForStaff(villageId,key,result));
            commonRes.setData(listUserRes);
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

    @GetMapping("/allPatientForStaffAll")// fomat sang DTO trả về dữ liệu
    public ResponseEntity<CommonRes> getAllPatient(@PathParam("villageId") Long villageId,@PathParam("key") String key,@PathParam("result") String result) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            List<ListUserRequest> listUserRequests = userService.getAllPatientForStaffAll(villageId,key,result);
            ListUserRes listUserRes = new ListUserRes();
            listUserRes.setListUserRequests(listUserRequests);
            listUserRes.setTotal(listUserRequests.size());
            commonRes.setData(listUserRes);
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

    @GetMapping("/searchTextForStaff")// fomat sang DTO trả về dữ liệu
    public ResponseEntity<CommonRes> getAllByText(@PathParam("page") Integer page,@PathParam("key") String key, @PathParam("villageId") Long villageId) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            List<ListUserRequest> listUserRequests = userService.searchByTextForStaff(key, villageId,page);
            ListUserRes listUserRes = new ListUserRes();
            listUserRes.setListUserRequests(listUserRequests);
            listUserRes.setTotal(userService.countByTesxtForStaff(key,villageId));
            commonRes.setData(listUserRes);
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

    @GetMapping("/getNewPatientOneDay")// fomat sang DTO trả về dữ liệu
    public ResponseEntity<CommonRes> getNewPatientOneDay(@PathParam("time") String time,@PathParam("villageId") Long villageId) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            List<UserRequet> userRequets = userService.getNewPatientOneDay(time,villageId);
            UserRes userRes = new UserRes();
            userRes.setUserRequets(userRequets);
            userRes.setTotal(userRequets.size());
            commonRes.setData(userRes);
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }
    @GetMapping("/getDetaiOneDay")// fomat sang DTO trả về dữ liệu
    public ResponseEntity<CommonRes> getDetaiOneDay(@PathParam("time") String time,@PathParam("villageId") Long villageId) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            List<UserRequet> newOneday = userService.getNewPatientOneDay(time,villageId);
            List<UserRequet> curedOneday = userService.getCuredPatientOneDay(time,villageId);
            List<UserRequet> current = userService.getAllPatientForStaff(villageId);
            DetailOneDayRes detailOneDayRes = new DetailOneDayRes();
            detailOneDayRes.setTotalNewF0(newOneday.size());
            detailOneDayRes.setTotalCured(curedOneday.size());
            detailOneDayRes.setTotalCurrentF0(current.size());
            commonRes.setData(detailOneDayRes);
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

//    @GetMapping("/allPatientCuredForStaff")// fomat sang DTO trả về dữ liệu
//    public ResponseEntity<CommonRes> getAllCured(@PathParam("page") Integer page,@PathParam("villageId") Long villageId) {
//        CommonRes commonRes = new CommonRes();
//        try {
//            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
//            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
//            List<ListUserRequest> listUserRequests = userService.getAllPatientsCuredForStaff(villageId,page);
//            ListUserRes listUserRes = new ListUserRes();
//            listUserRes.setListUserRequests(listUserRequests);
//            listUserRes.setTotal(userService.countAllPatientCuredForStaff(villageId));
//            commonRes.setData(listUserRes);
//        } catch (Exception e){
//            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
//            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
//        }
//        return ResponseEntity.ok(commonRes);
//    }
    // truyền vào 1 ngày và trả về nhưng thằng đã gửi report và tình trạng report đó


    @GetMapping(value = "/sentReport")
    public ResponseEntity<CommonRes> getAllSentReportOnedate(@RequestParam("time") String time , @PathParam("villageId") Long villageId,@PathParam("key") String key,@PathParam("page") Integer page) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            List<ReportDetail> daily_reports = dailyReportService.getByReport(time,villageId,key,page);
            ReportRes reportRes = new ReportRes();
            reportRes.setReportDetails(daily_reports);
            reportRes.setTotal(dailyReportService.countSentReport(time,villageId,key));
            reportRes.setReportDetails(daily_reports);
            commonRes.setData(reportRes);
        } catch (Exception e) {
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }
    @GetMapping("/notSentReport")// fomat sang DTO trả về dữ liệu
    public ResponseEntity<CommonRes> sentReport(@PathParam("time") String time,@PathParam("villageId") Long villageId,@PathParam("key") String key,@PathParam("page") Integer page) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            List<ListUserRequest> listUserRequests = userService.notSentReport(time,villageId,key,page);
            ListUserRes listUserRes = new ListUserRes();
            listUserRes.setListUserRequests(listUserRequests);
            listUserRes.setTotal(userService.countNotSentReport(time,villageId,key));
            commonRes.setData(listUserRes);
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

    @GetMapping("/searchPatientCuredForStaffByText")// fomat sang DTO trả về dữ liệu
    public ResponseEntity<CommonRes> searchAllByText(@PathParam("page") Integer page,@PathParam("key") String key, @PathParam("villageId") Long villageId) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            List<ListUserRequest> listUserRequests = userService.searchByTextPatientsCuredForStaff(key, villageId,page);
            ListUserRes listUserRes = new ListUserRes();
            listUserRes.setListUserRequests(listUserRequests);
            listUserRes.setTotal(userService.countByTesxtPatientsCuredForStaff(key,villageId));
            commonRes.setData(listUserRes);
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }


    @GetMapping("/chartForStaff")// fomat sang DTO trả về dữ liệu
    public ResponseEntity<CommonRes> chartForStaff(@PathParam("startDate")String startDate, @PathParam("endDate") String endDate,@PathParam("villageId") Long villageId) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            List<ChartStaffRes> chartStaffRes = userService.getChartForStaff(startDate,endDate,villageId);
            commonRes.setData(chartStaffRes);
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

    @GetMapping("/totalCurrentF0")// fomat sang DTO trả về dữ liệu
    public ResponseEntity<CommonRes> totalCurrentF0(@PathParam("villageId") Long villageId) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            commonRes.setData(userService.totalCurrentF0(villageId));
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }
}

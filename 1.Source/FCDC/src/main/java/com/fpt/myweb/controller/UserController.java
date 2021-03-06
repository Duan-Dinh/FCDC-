package com.fpt.myweb.controller;

import com.fpt.myweb.dto.request.ListUserRequest;
import com.fpt.myweb.dto.request.UserRequet;
import com.fpt.myweb.dto.response.CommonRes;
import com.fpt.myweb.dto.response.ListUserRes;
import com.fpt.myweb.dto.response.UserRes;
import com.fpt.myweb.exception.AppException;
import com.fpt.myweb.exception.ErrorCode;
import com.fpt.myweb.repository.UserRepository;
import com.fpt.myweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


    // get all
    @GetMapping("/all")// fomat sang DTO trả về dữ liệu
    public ResponseEntity<CommonRes> getAll(@PathParam("page") Integer page) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            List<UserRequet> userPage = userService.getAllUser(page);
            UserRes userRes = new UserRes();
            userRes.setUserRequets(userPage);
            userRes.setTotal(userPage.size());
            commonRes.setData(userRes);
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

    // get usser by role
    @GetMapping("/getByRoleAndSearchText")// fomat sang DTO trả về dữ liệu
    public ResponseEntity<CommonRes> getAllByRole(@PathParam("roleId") Long roleId,@PathParam("key") String key,@PathParam("page") Integer page) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            List<ListUserRequest> listUserRequests = userService.searchByRole(roleId,key,page);
            ListUserRes listUserRes = new ListUserRes();
            listUserRes.setListUserRequests(listUserRequests);
            listUserRes.setTotal(userService.countSearchByRole(roleId,key));
            commonRes.setData(listUserRes);
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }


    // get usser by text in Username
//    @GetMapping("/searchText")// fomat sang DTO trả về dữ liệu
//    public ResponseEntity<CommonRes> getAllByText(@PathParam("key") String key,@PathParam("page") Integer page) {
//        CommonRes commonRes = new CommonRes();
//        try {
//            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
//            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
//            List<ListUserRequest> listUserRequests = userService.searchByTesxt(key,page);
//            ListUserRes listUserRes = new ListUserRes();
//            listUserRes.setListUserRequests(listUserRequests);
//            listUserRes.setTotal(listUserRequests.size());
//            commonRes.setData(listUserRes);
//        } catch (Exception e){
//            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
//            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
//        }
//        return ResponseEntity.ok(commonRes);
//    }

    //Add new user
    @PostMapping("/add")
    public ResponseEntity<CommonRes> addUsers(UserRequet userRequet, @RequestParam("file") MultipartFile file) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            userService.addUser(userRequet, file);

        } catch (AppException a){
            commonRes.setResponseCode(a.getErrorCode());
            commonRes.setMessage(a.getErrorMessage());
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

    @GetMapping("/checkPhone")
    public ResponseEntity<CommonRes> checkPhone( @PathParam("phone") String phone) {
        CommonRes commonRes = new CommonRes();
        try {
            boolean aBoolean = userService.checkPhone(phone);
            if(!aBoolean){
                commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
                commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            }
            else {
                commonRes.setResponseCode(ErrorCode.AUTHENTICATION_FAILED.getKey());
                commonRes.setMessage(ErrorCode.AUTHENTICATION_FAILED.getValue());
            }
        } catch (AppException a){
            commonRes.setResponseCode(a.getErrorCode());
            commonRes.setMessage(a.getErrorMessage());
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

    // Update
    @PutMapping(value = "/edit")
    public ResponseEntity<CommonRes> edit(UserRequet userRequet, @RequestParam("file") MultipartFile file) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            userService.edit(userRequet,file);

        } catch (AppException a){
            commonRes.setResponseCode(a.getErrorCode());
            commonRes.setMessage(a.getErrorMessage());
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }
    // sửa được ảnh,phone,dateStart
    //edit user by id
    @PutMapping(value = "/editByStaff")
    public ResponseEntity<CommonRes> editByStaff(UserRequet userRequet, @RequestParam("file") MultipartFile file) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            userService.editUserById(userRequet,file);
        } catch (AppException a){
            commonRes.setResponseCode(a.getErrorCode());
            commonRes.setMessage(a.getErrorMessage());
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

    //sửa được ảnh
    @PutMapping(value = "/editByUser")
    public ResponseEntity<CommonRes> editByUser(UserRequet userRequet, @RequestParam("file") MultipartFile file) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            userService.editByUser(userRequet,file);
        } catch (AppException a){
            commonRes.setResponseCode(a.getErrorCode());
            commonRes.setMessage(a.getErrorMessage());
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }


    // Delete
    @PutMapping(value = "delete")
    public ResponseEntity<CommonRes> remove(@PathParam("id") long id) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            userService.deleteUser(id);



        } catch (AppException a){
            commonRes.setResponseCode(a.getErrorCode());
            commonRes.setMessage(a.getErrorMessage());
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

    // getOne
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<CommonRes> getUser(@PathVariable("id") long id) {
        CommonRes commonRes = new CommonRes();
        try {

            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            UserRequet userRequet = userService.getUser(id);
            commonRes.setData(userRequet);

        } catch (AppException a){
            commonRes.setResponseCode(a.getErrorCode());
            commonRes.setMessage(a.getErrorMessage());
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }




    @GetMapping("/allUserForDoctor")// fomat sang DTO trả về dữ liệu
    public ResponseEntity<CommonRes> getAllUserForDoctor(@PathParam("doctorId") String doctorId,@PathParam("key") String key,@PathParam("page") Integer page) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            List<ListUserRequest> listUserRequests = userService.getAllPatientForDoctor(doctorId,key,page);
            ListUserRes listUserRes = new ListUserRes();
            listUserRes.setListUserRequests(listUserRequests);
            listUserRes.setTotal(userService.countAllPatientForDoctor(doctorId,key));
            commonRes.setData(listUserRes);
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }
    @GetMapping("/getDoctorByVillageId")// fomat sang DTO trả về dữ liệu
    public ResponseEntity<CommonRes> getDoctorByVillageId(@PathParam("villageId") Long villageId,@PathParam("page") Integer page) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            List<ListUserRequest> listUserRequests = userService.getAllDoctorByVila(villageId,page);
            ListUserRes listUserRes = new ListUserRes();
            listUserRes.setListUserRequests(listUserRequests);
            listUserRes.setTotal(userService.countAllDoctorByVila(villageId));
            commonRes.setData(listUserRes);
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }
    //export



    @PutMapping(value = "changePass")
    public ResponseEntity<CommonRes> changePass(@PathParam("id") long id,@PathParam("newPass") String newPass) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            userService.changePass(id,newPass);
        } catch (AppException a){
            commonRes.setResponseCode(a.getErrorCode());
            commonRes.setMessage(a.getErrorMessage());
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

//    @GetMapping("/notSentAndSentReport")// fomat sang DTO trả về dữ liệu
//    public ResponseEntity<CommonRes> notSentAndSentReport(@PathParam("time") String time,@PathParam("villageId") Long villageId,@PathParam("key") String key,@PathParam("status") String status,@PathParam("page") Integer page) {
//        CommonRes commonRes = new CommonRes();
//        try {
//            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
//            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
//            List<ListUserRequest> listUserRequests = userService.notSentAndSentReport(time,villageId,key,status,page);
//            ListUserRes listUserRes = new ListUserRes();
//            listUserRes.setListUserRequests(listUserRequests);
//            listUserRes.setTotal(userService.countNotSentAndSentReport(time,villageId,key,status));
//            commonRes.setData(listUserRes);
//        } catch (Exception e){
//            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
//            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
//        }
//        return ResponseEntity.ok(commonRes);
//    }

    @GetMapping("/toTestCovid")// fomat sang DTO trả về dữ liệu
    public ResponseEntity<CommonRes> toTestCovid(@PathParam("time") String time,@PathParam("villageId") Long villageId) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            List<UserRequet> userRequets = userService.toTestCovid(time,villageId);
            UserRes userRes = new UserRes();
            userRes.setUserRequets(userRequets);
            userRes.setTotal(userRequets.size()); //                                                         dk
            commonRes.setData(userRes);
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }
    @GetMapping("/toTestCovidForPatient")// fomat sang DTO trả về dữ liệu
    public ResponseEntity<CommonRes> toTestCovidForPatient(@PathParam("time") String time,@PathParam("villageId") Long villageId,@PathParam("page") Integer page) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            List<UserRequet> userRequetsForPaitent = userService.toTestCovidForPaitent(time,villageId,page);
            List<UserRequet> userRequets = userService.toTestCovid(time,villageId);
            UserRes userRes = new UserRes();
            userRes.setUserRequets(userRequetsForPaitent);
            userRes.setTotal(userRequets.size()); //
            commonRes.setData(userRes);
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

    @PutMapping(value = "editResult")
    public ResponseEntity<CommonRes> editResult(@PathParam("id") long id) {
        CommonRes commonRes = new CommonRes();
        try {
            UserRequet userRequet = userService.editResult(id);
            if(userRequet==null) {
                commonRes.setResponseCode(ErrorCode.CHANGERESULT_FAIL.getKey());
                commonRes.setMessage(ErrorCode.CHANGERESULT_FAIL.getValue());
            }else{
                commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
                commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            }
        } catch (AppException a){
            commonRes.setResponseCode(a.getErrorCode());
            commonRes.setMessage(a.getErrorMessage());
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }


    @PutMapping(value = "changeTypeTakeCare")
    public ResponseEntity<CommonRes> changeTypeTakeCare(@PathParam("id") long id,@PathParam("doctorId") Long doctorId) {
        CommonRes commonRes = new CommonRes();
        try {
            UserRequet userRequet = userService.changeTypeTakeCare(id,doctorId);
            if(userRequet != null){
                commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
                commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());

            }else {
                commonRes.setResponseCode(ErrorCode.BIGGER_100.getKey());
                commonRes.setMessage(ErrorCode.BIGGER_100.getValue());
            }


        } catch (AppException a){
            commonRes.setResponseCode(a.getErrorCode());
            commonRes.setMessage(a.getErrorMessage());
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

    @GetMapping("/exportUer")
    public void exportUer(HttpServletResponse response,@PathParam("time") String time,@PathParam("villaId") Long villaId) throws IOException, ParseException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_HH_mm_ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        userService.exportUserPatient(response,time,villaId);
    }

    @GetMapping("/exportPatientF0AndCured")
    public void exportPatientF0AndCured(HttpServletResponse response,@PathParam("villageId") Long villageId,@PathParam("key") String key,@PathParam("result") String result) throws IOException, ParseException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_HH_mm_ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=PatientF0AndCured_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        userService.exportUserPatientF0AndCured(response, villageId, key, result);
    }


    @PostMapping("/importUer")
    public ResponseEntity<CommonRes> importUer(@PathParam("file") MultipartFile file,@PathParam("type") String type) {

        CommonRes commonRes = new CommonRes();
        try {
            List<UserRequet> userRequets = userService.importUser(file,type);
            if(userRequets==null){
                commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
                commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());}
            else if(userRequets.size()==0){
                commonRes.setResponseCode(ErrorCode.EXCEL_DUPLICATE.getKey());
                commonRes.setMessage(ErrorCode.EXCEL_DUPLICATE.getValue());
            }else{
                commonRes.setResponseCode(ErrorCode.AUTHENTICATION_FAILED_1.getKey());
                commonRes.setMessage(ErrorCode.AUTHENTICATION_FAILED_1.getValue());
                commonRes.setData(userRequets);

            }
        } catch (AppException a){
            commonRes.setResponseCode(a.getErrorCode());
            commonRes.setMessage(a.getErrorMessage());
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.EXCEL_DUPLICATE.getKey());
            commonRes.setMessage(ErrorCode.EXCEL_DUPLICATE.getValue());

        }
        return ResponseEntity.ok(commonRes);
    }
//    @PostMapping("/importStaff")
//    public ResponseEntity<CommonRes> importStaff(@RequestParam("file") MultipartFile file) {
//
//        CommonRes commonRes = new CommonRes();
//        try {
//
//                commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
//                commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
//                userService.importUserStaff(file);
//        } catch (AppException a){
//            commonRes.setResponseCode(a.getErrorCode());
//            commonRes.setMessage(a.getErrorMessage());
//        } catch (Exception e){
//            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
//            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
//        }
//        return ResponseEntity.ok(commonRes);
//    }
//    @PostMapping("/importDoctor")
//    public ResponseEntity<CommonRes> importDoctor(@RequestParam("file") MultipartFile file) {
//
//        CommonRes commonRes = new CommonRes();
//        try {
//
//
//                commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
//                commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
//                userService.importUserDoctor(file);
//
//        } catch (AppException a){
//            commonRes.setResponseCode(a.getErrorCode());
//            commonRes.setMessage(a.getErrorMessage());
//        } catch (Exception e){
//            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
//            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
//        }
//        return ResponseEntity.ok(commonRes);
//    }

}

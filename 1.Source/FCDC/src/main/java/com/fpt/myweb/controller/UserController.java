package com.fpt.myweb.controller;

import com.fpt.myweb.convert.UserConvert;
import com.fpt.myweb.dto.request.UserRequet;
import com.fpt.myweb.dto.response.CommonRes;
import com.fpt.myweb.dto.response.UserRes;
import com.fpt.myweb.entity.User;
import com.fpt.myweb.exception.AppException;
import com.fpt.myweb.exception.ErrorCode;
import com.fpt.myweb.repository.UserRepository;
import com.fpt.myweb.service.UserService;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    ObjectFactory<HttpSession> httpSessionFactory;

    // get all
    @GetMapping("/all")// fomat sang DTO trả về dữ liệu
    public ResponseEntity<CommonRes> getAll(@PathParam("page") Integer page) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            Page<User> userPage = userService.getAllUserByPage(page);
            List<User> users = new ArrayList<User>();
            List<UserRequet> userRequets = new ArrayList<>();
            users = userPage.getContent();

            if (!users.isEmpty()) {
                for (User user: users){
                    userRequets.add(UserConvert.convertToUserRequest(user));
                }
            }
            UserRes userRes = new UserRes();
            userRes.setUserRequets(userRequets);
            userRes.setTotal(userPage.getTotalElements());
            commonRes.setData(userRes);
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

    // get usser by role
    @GetMapping("/searchByRole")// fomat sang DTO trả về dữ liệu
    public ResponseEntity<CommonRes> getAllByRole(@PathParam("roleId") Long roleId, @PathParam("page") Integer page) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            List<UserRequet> userRequets = userService.searchByRole(roleId, page);
            UserRes userRes = new UserRes();
            userRes.setUserRequets(userRequets);
            userRes.setTotal(userService.countByRole(roleId));
            commonRes.setData(userRes);
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

    // get usser by text in Username
    @GetMapping("/searchText")// fomat sang DTO trả về dữ liệu
    public ResponseEntity<CommonRes> getAllByText(@PathParam("key") String key, @PathParam("page") Integer page) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            List<UserRequet> userRequets = userService.searchByTesxt(key, page);
            UserRes userRes = new UserRes();
            userRes.setUserRequets(userRequets);
            userRes.setTotal(userService.countByTesxt(key));
            commonRes.setData(userRes);
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }



    //Add new user
    @PostMapping("/add")
    public ResponseEntity<CommonRes> addUsers(UserRequet userRequet) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            userService.addUser(userRequet);
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
    public ResponseEntity<CommonRes> edit(UserRequet userRequet) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            userService.edit(userRequet);
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
    @DeleteMapping(value = "delete")
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
    public ResponseEntity<CommonRes> getUser1(@PathVariable("id") long id) {
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

    @PostMapping("/importUer")
    public ResponseEntity<CommonRes> importUer(@RequestParam("file") MultipartFile file) {

        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            userService.importUserPatient(file);
        } catch (AppException a){
            commonRes.setResponseCode(a.getErrorCode());
            commonRes.setMessage(a.getErrorMessage());
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }
    @PostMapping("/importStaff")
    public ResponseEntity<CommonRes> importStaff(@RequestParam("file") MultipartFile file) {

        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            userService.importUserStaff(file);
        } catch (AppException a){
            commonRes.setResponseCode(a.getErrorCode());
            commonRes.setMessage(a.getErrorMessage());
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

}

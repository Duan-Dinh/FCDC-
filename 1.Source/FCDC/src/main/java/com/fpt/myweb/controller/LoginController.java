package com.fpt.myweb.controller;

import com.fpt.myweb.common.Contants;
import com.fpt.myweb.dto.request.LoginRequest;
import com.fpt.myweb.dto.response.CommonRes;
import com.fpt.myweb.dto.response.LoginResponse;
import com.fpt.myweb.entity.User;
import com.fpt.myweb.exception.AppException;
import com.fpt.myweb.exception.ErrorCode;
import com.fpt.myweb.repository.UserRepository;
import com.fpt.myweb.service.UserService;
import org.hibernate.annotations.MetaValue;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api")
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    ObjectFactory<HttpSession> httpSessionFactory;

    @PostMapping(value = "/login", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> checkLogin(LoginRequest loginRequest) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            User user = userService.login(loginRequest.getPhone(),loginRequest.getPassword());
            if(user==null){
                commonRes.setResponseCode(ErrorCode.AUTHENTICATION_FAILED.getKey());
                commonRes.setMessage(ErrorCode.AUTHENTICATION_FAILED.getValue());
            }else{
                LoginResponse loginResponse = new LoginResponse();
                if(user.getUsername()!=null) {
                    loginResponse.setUsername(user.getUsername());
                }
                loginResponse.setRole(user.getRole().getName());
                loginResponse.setFullname(user.getFullname()); //
                loginResponse.setId(user.getId());
                commonRes.setData(loginResponse);
                HttpSession session = httpSessionFactory.getObject();
                session.setAttribute(Contants.USER_SESSION, user);
            }
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

    @PostMapping(value = "/logout", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> checkLogout() {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            HttpSession session = httpSessionFactory.getObject();
            session.setAttribute(Contants.USER_SESSION, null);
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }
}

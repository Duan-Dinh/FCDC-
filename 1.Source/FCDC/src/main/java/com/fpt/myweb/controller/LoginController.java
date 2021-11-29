package com.fpt.myweb.controller;

import com.fpt.myweb.common.Contants;
import com.fpt.myweb.dto.request.LoginRequest;
import com.fpt.myweb.dto.response.CommonRes;
import com.fpt.myweb.dto.response.LoginResponse;
import com.fpt.myweb.dto.response.ResetPassRes;
import com.fpt.myweb.entity.Role;
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
import javax.websocket.server.PathParam;
import javax.xml.bind.DatatypeConverter;

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
            HttpSession session = httpSessionFactory.getObject();
            User user = (User) session.getAttribute(Contants.USER_SESSION);
            Role role = user.getRole();
            if(role.getId() == 1L || role.getId() == 2L || role.getId() == 3L || role.getId() == 4L){
                commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
                commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
                 user = userService.login(loginRequest.getPhone(),loginRequest.getPassword());
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
                    loginResponse.setVilaId(user.getVillage().getId());
                    loginResponse.setAddress(user.getAddress());
                    if(user.getFiles() != null){
                        String type ="data:"+ DatatypeConverter.parseAnySimpleType(user.getFiles().getType()) +";base64,"+ DatatypeConverter.printBase64Binary(user.getFiles().getData());
                        loginResponse.setImage(type);
                    }
                    commonRes.setData(loginResponse);
                     session = httpSessionFactory.getObject();
                    session.setAttribute(Contants.USER_SESSION, user);
                }


            }
            else{
                commonRes.setResponseCode(ErrorCode.AUTHEN.getKey());
                commonRes.setMessage(ErrorCode.AUTHEN.getValue());
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
            HttpSession session = httpSessionFactory.getObject();
            User user = (User) session.getAttribute(Contants.USER_SESSION);
            Role role = user.getRole();
            if(role.getId() == 1L || role.getId() == 2L || role.getId() == 3L || role.getId() == 4L){
                commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
                commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
                session = httpSessionFactory.getObject();
                session.setAttribute(Contants.USER_SESSION, null);
            }
            else{
                commonRes.setResponseCode(ErrorCode.AUTHEN.getKey());
                commonRes.setMessage(ErrorCode.AUTHEN.getValue());
            }

        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }
    @PutMapping(value = "/resetPass", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> resetPass(@PathParam("phone") String phone) {
        CommonRes commonRes = new CommonRes();
        try {
            HttpSession session = httpSessionFactory.getObject();
            User user = (User) session.getAttribute(Contants.USER_SESSION);
            Role role = user.getRole();
            if(role.getId() == 1L || role.getId() == 2L || role.getId() == 3L || role.getId() == 4L){
                ResetPassRes resetPassRes = userService.resetPass(phone);
                if(resetPassRes!=null) {
                    commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
                    commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
                    commonRes.setData(resetPassRes);
                }else {
                    commonRes.setResponseCode(ErrorCode.AUTHENTICATION_FAILED.getKey());
                    commonRes.setMessage(ErrorCode.AUTHENTICATION_FAILED.getValue());
                }
            }
            else{
                commonRes.setResponseCode(ErrorCode.AUTHEN.getKey());
                commonRes.setMessage(ErrorCode.AUTHEN.getValue());
            }

        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

}

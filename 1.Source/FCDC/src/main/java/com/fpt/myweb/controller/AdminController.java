package com.fpt.myweb.controller;

import com.fpt.myweb.dto.request.ListUserRequest;
import com.fpt.myweb.dto.request.UserRequet;
import com.fpt.myweb.dto.response.CommonRes;
import com.fpt.myweb.dto.response.ListUserRes;
import com.fpt.myweb.dto.response.UserRes;
import com.fpt.myweb.exception.AppException;
import com.fpt.myweb.exception.ErrorCode;
import com.fpt.myweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    
    @GetMapping("/searchTextWithRole")// fomat sang DTO trả về dữ liệu
    public ResponseEntity<CommonRes> getAllByText(@PathParam("key") String key,@PathParam("page") Integer page, @PathParam("roleId") Long roleId) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            List<UserRequet> listUserRequests = userService.searchByTextWithRole(key, roleId,page);
            long total = userService.countByRole(roleId);
            UserRes listUserRes = new UserRes();
            listUserRes.setUserRequets(listUserRequests);
            listUserRes.setTotal(total);
            commonRes.setData(listUserRes);
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

}

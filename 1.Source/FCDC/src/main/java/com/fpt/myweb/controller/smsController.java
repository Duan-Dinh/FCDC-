package com.fpt.myweb.controller;

import com.fpt.myweb.common.Contants;
import com.fpt.myweb.dto.response.CommonRes;
import com.fpt.myweb.entity.Role;
import com.fpt.myweb.entity.User;
import com.fpt.myweb.exception.ErrorCode;
import com.fpt.myweb.service.SmsService;
import com.fpt.myweb.utils.GetUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/sent")
public class smsController {

    @Autowired
    private SmsService smsService;
    @PostMapping(value = "/Sms")
    public ResponseEntity<CommonRes> sentSms(@RequestParam("phone") String phone, @RequestParam("message") String message){
        CommonRes commonRes = new CommonRes();
        try {
                commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
                commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
                smsService.sendGetJSON(phone, message);
        }
        catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }
    @PostMapping(value = "/SmsList")
    public ResponseEntity<CommonRes> ListsentSms(@RequestParam("phoneList") String phoneList, @RequestParam("message") String message){
        CommonRes commonRes = new CommonRes();
        try {
                commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
                commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
                List<String> phoneL = GetUtils.splipStringTophone(phoneList);
                for (String phone : phoneL) {
                    smsService.sendGetJSON(phone, message);
                }
        }
        catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }
}

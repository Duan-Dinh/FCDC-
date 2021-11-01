package com.fpt.myweb.controller;

import com.fpt.myweb.dto.request.NewRequet;
import com.fpt.myweb.dto.request.UserRequet;
import com.fpt.myweb.dto.response.CommonRes;
import com.fpt.myweb.dto.response.UserRes;
import com.fpt.myweb.entity.New;
import com.fpt.myweb.entity.Province;
import com.fpt.myweb.exception.AppException;
import com.fpt.myweb.exception.ErrorCode;
import com.fpt.myweb.service.FileService;
import com.fpt.myweb.service.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping(value = "/new")
public class NewController {

    @Autowired
    private NewService newService;

    @GetMapping("/get")
    public ResponseEntity<CommonRes> getNew(@PathParam("page") Integer page) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            List<New> newList = newService.getNew(page);
            commonRes.setData(newList);
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

    @PostMapping("/add")
    public ResponseEntity<CommonRes> addNew( @RequestBody NewRequet newRequet) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            New aNew = newService.addNew(newRequet);
            commonRes.setData(aNew);
        } catch (AppException a){
            commonRes.setResponseCode(a.getErrorCode());
            commonRes.setMessage(a.getErrorMessage());
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

    @PutMapping("/edit")
    public ResponseEntity<CommonRes> editNew(@RequestBody NewRequet newRequet) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            New aNew = newService.editNew(newRequet);
            commonRes.setData(aNew);
        } catch (AppException a){
            commonRes.setResponseCode(a.getErrorCode());
            commonRes.setMessage(a.getErrorMessage());
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<CommonRes> deleteNew(@PathParam("id") Integer id) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            newService.deleteNew(id);
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

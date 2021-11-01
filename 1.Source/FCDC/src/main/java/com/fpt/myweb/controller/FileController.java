package com.fpt.myweb.controller;

import com.fpt.myweb.dto.response.CommonRes;
import com.fpt.myweb.exception.ErrorCode;
import com.fpt.myweb.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping(value = "/upload", consumes = { MediaType.ALL_VALUE })
    public ResponseEntity<CommonRes> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("type") String type) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            String fileUrl = fileService.saveFile(file, type);
            commonRes.setData(fileUrl);
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }
}

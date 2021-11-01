package com.fpt.myweb.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SmsService {

    public String sendGetJSON(String phone, String message) throws IOException;

}

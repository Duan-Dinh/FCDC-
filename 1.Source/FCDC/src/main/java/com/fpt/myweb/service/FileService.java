package com.fpt.myweb.service;

import com.fpt.myweb.dto.request.UserRequet;
import com.fpt.myweb.entity.Role;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    public String saveFile( MultipartFile file, String type) throws IOException;

}

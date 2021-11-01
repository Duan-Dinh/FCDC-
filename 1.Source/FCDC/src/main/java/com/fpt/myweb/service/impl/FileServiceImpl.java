package com.fpt.myweb.service.impl;


import com.fpt.myweb.common.Contants;
import com.fpt.myweb.service.FileService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private Environment env;

    @Override
    public String saveFile(MultipartFile file, String type) throws IOException {
        String result = "";
        File fileUpload = null;
        if(type.equalsIgnoreCase(Contants.TYPE_USER)){
            result = env.getProperty("folder.user.images");

        } else if(type.equalsIgnoreCase(Contants.TYPE_NEW)){
            result = env.getProperty("folder.new.images");
        }
        fileUpload = new File(result);
        if(!fileUpload.exists()){
            fileUpload.mkdir();
        }
        if(fileUpload != null){
            InputStream inputStream = null;
            File fileUrl = null;
            inputStream = file.getInputStream();
            String name = file.getResource().getFilename();
            result += System.currentTimeMillis() + "_" + name;
            fileUrl = new File(result);
            FileOutputStream fos = new FileOutputStream(fileUrl);
            byte[] bytes = IOUtils.toByteArray(inputStream);
            fos.write(bytes);
            fos.close();
        }
        return result;
    }
}

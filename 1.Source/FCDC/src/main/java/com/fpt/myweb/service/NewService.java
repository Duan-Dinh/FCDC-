package com.fpt.myweb.service;

import com.fpt.myweb.dto.request.NewRequet;
import com.fpt.myweb.entity.New;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface NewService {

    public New addNew(NewRequet newRequet);

    public New editNew(NewRequet newRequet);

    public void deleteNew(Integer id);

    public List<New> getNew(Integer page);
}

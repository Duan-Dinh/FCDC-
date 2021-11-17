package com.fpt.myweb.service.impl;


import com.fpt.myweb.common.Contants;
import com.fpt.myweb.convert.NewConvert;
import com.fpt.myweb.dto.request.NewRequet;
import com.fpt.myweb.entity.FileDB;
import com.fpt.myweb.entity.New;
import com.fpt.myweb.exception.AppException;
import com.fpt.myweb.exception.ErrorCode;
import com.fpt.myweb.repository.FileDBRepository;
import com.fpt.myweb.repository.NewRepository;
import com.fpt.myweb.service.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class NewServiceImpl implements NewService {

    @Autowired
    private NewRepository newRepository;
    @Autowired
    private FileDBRepository fileDBRepository;
    @Autowired
    private NewConvert newConvert;

    @Override
    public New addNew(NewRequet newRequet,MultipartFile file) throws IOException  {
        New aNew = new New();
        aNew.setTitle(newRequet.getTitle());
        aNew.setDecription(newRequet.getDecription());
        //aNew.setImageUrl(newRequet.getImageUrl());
        aNew.setCreatedDate(new Date());
        aNew.setDelete(true);
        // luu file
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());

        FileDB fileDB = fileDBRepository.save(FileDB);
        aNew.setFilesNew(fileDB);
        newRepository.save(aNew);
        return aNew;
    }

    @Override
    public New editNew(NewRequet newRequet, MultipartFile file) throws IOException {
        New aNew = newRepository.getById(newRequet.getId());
        if(aNew != null){
            aNew.setTitle(newRequet.getTitle());
            aNew.setDecription(newRequet.getDecription());
            //aNew.setImageUrl(newRequet.getImageUrl());
            aNew.setModifiedDate(new Date());
            aNew.setDelete(true);
            // luu file
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());
            FileDB.setId(aNew.getFilesNew().getId());
            FileDB fileDB = fileDBRepository.save(FileDB);

            aNew.setFilesNew(fileDB);
            newRepository.save(aNew);
        }else{
            throw new AppException(ErrorCode.NOT_FOUND_ID.getKey(), ErrorCode.NOT_FOUND_ID.getValue() + newRequet.getId());
        }
        return aNew;
    }

    @Override
    public void deleteNew(Integer id) {
        New aNew = newRepository.getById((long) id);
        if(aNew != null){
            newRepository.delete(aNew);
        }else{
            throw new AppException(ErrorCode.NOT_FOUND_ID.getKey(), ErrorCode.NOT_FOUND_ID.getValue() + id);
        }
    }

    @Override
    public List<NewRequet> getNew(Integer page) {
        if(page == null){
            page = 0;
        }else{
            page--;
        }
        Pageable pageable = PageRequest.of(page, Contants.PAGE_SIZE_New);
       List<New> newList=  newRepository.findAllNewsWithPagination(pageable);
        List<NewRequet> newRequets = new ArrayList<>();
        for (New news : newList) {
            if (news.getFilesNew() != null) {
                NewRequet newRequet = newConvert.convertToNewRequest(news);
                newRequets.add(newRequet);
            }
        }
        return newRequets;

    }


    @Override
    public List<NewRequet> getAllNew() {
        List<New> newList = newRepository.findAll();
        List<NewRequet> newRequets = new ArrayList<>();
        for (New news : newList) {
            if (news.getFilesNew() != null) {
                NewRequet newRequet = newConvert.convertToNewRequest(news);
                newRequets.add(newRequet);
            }
        }
        return newRequets;
    }


}

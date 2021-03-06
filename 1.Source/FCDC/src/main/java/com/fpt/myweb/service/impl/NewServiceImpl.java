package com.fpt.myweb.service.impl;


import com.fpt.myweb.common.Contants;
import com.fpt.myweb.convert.NewConvert;
import com.fpt.myweb.dto.request.NewRequet;
import com.fpt.myweb.dto.request.UserRequet;
import com.fpt.myweb.entity.FileDB;
import com.fpt.myweb.entity.New;
import com.fpt.myweb.entity.User;
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
    public List<NewRequet> getTopNew(Integer page) {
        if(page == null){
            page = 0;
        }else{
            page--;
        }
        Pageable pageable = PageRequest.of(page, Contants.PAGE_SIZE_NEW_TOP);
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
    public List<NewRequet> searchByTitle(String text,Integer page) {
        if(page == null){
            page = 0;
        }else{
            page--;
        }
        Pageable pageable = PageRequest.of(page, Contants.PAGE_SIZE_NEW);
        List<New> searchList = newRepository.findAllNewsByTitle(text,pageable);
        List<NewRequet> newRequets = new ArrayList<>();
        for (New news : searchList) {
                newRequets.add(newConvert.convertToNewRequest(news));
            }
        return newRequets;
    }

    @Override
    public int countsearchByTitle(String text) {
        List<New> searchList = newRepository.findAllNewsByTitle(text);
        if(searchList == null){
            return 0;
        }
        return searchList.size();
    }

    @Override
    public NewRequet getNew(long id) {
        New news = newRepository.findById(id).orElseThrow(()
                -> new AppException(ErrorCode.NOT_FOUND_ID.getKey(), ErrorCode.NOT_FOUND_ID.getValue() + id));
        NewRequet newRequet = newConvert.convertToNewRequest(news);
        return newRequet;
    }


    @Override
    public List<NewRequet> getAllNew(Integer page) {
        if(page == null){
            page = 0;
        }else{
            page--;
        }
        Pageable pageable = PageRequest.of(page, Contants.PAGE_SIZE_NEW);
        List<New> newList=  newRepository.findAllNewsWithPagination(pageable);
        List<NewRequet> newRequets = new ArrayList<>();
        for (New news : newList) {
                newRequets.add(newConvert.convertToNewRequest(news));
            }

        return newRequets;
    }

    @Override
    public int countByTesxt() {
        List<New> searchList = newRepository.findAllNewsWithPagination();
        if(searchList == null){
            return 0;
        }
        return searchList.size();

    }


}

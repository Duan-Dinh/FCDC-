package com.fpt.myweb.service.impl;


import com.fpt.myweb.common.Contants;
import com.fpt.myweb.dto.request.FeebackReqest;
import com.fpt.myweb.dto.request.Report;
import com.fpt.myweb.dto.request.UserRequet;
import com.fpt.myweb.dto.response.CommonRes;
import com.fpt.myweb.dto.response.ReportDetailRes;
import com.fpt.myweb.entity.*;
import com.fpt.myweb.exception.ErrorCode;
import com.fpt.myweb.repository.*;
import com.fpt.myweb.service.DailyReportService;
import com.fpt.myweb.service.FileService;
import com.fpt.myweb.utils.GetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;


@Service
public class DailyReportServiceImpl implements DailyReportService {

    @Autowired
    private Daily_ReportRepository daily_reportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SysptomRepository sysptomRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;


    @Override
    public Page<Daily_Report> getReport(Integer page) {
        if(page == null){
            page = 0;
        }else{
            page--;
        }
        Pageable pageable = PageRequest.of(page, Contants.PAGE_SIZE);
        Page<Daily_Report> searchList = daily_reportRepository.findAll(pageable);
        return searchList;
    }

    @Override
    public void addReport(Report report) {
        Daily_Report daily_report = new Daily_Report();
        User user = userRepository.findById(report.getUserId()).orElse(null);
        daily_report.setUser(user);
        daily_report.setCreatedDate(new Date());
        daily_report.setComment(report.getComment());
        daily_report.setBodyTemperature(report.getBodyTemperature());
        daily_report.setOxygenConcentration(report.getOxygenConcentration());
        if(report.getBodyTemperature()>=38 || report.getOxygenConcentration()>=90){
            daily_report.setStatus("Lưu ý");
        }else{
            daily_report.setStatus("Bình Thường nha");
        }
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String strDate = formatter.format(date);
        daily_report.setDateTime(strDate);

        if(!StringUtils.isEmpty(report.getListSysptomId())){
            List<Long> listSysptomId = GetUtils.convertStringToListLong(report.getListSysptomId());
            for(int i = 0; i< listSysptomId.size() ;i++){
                Sysptom sysptom = sysptomRepository.findById(listSysptomId.get(i)).orElse(null);
                daily_report.getSysptoms().add(sysptom);
            }
        }

        if(!StringUtils.isEmpty(report.getListMedicineId())){
            List<Long> listMedicineId = GetUtils.convertStringToListLong(report.getListMedicineId());
            for(int i = 0; i< listMedicineId.size() ;i++){
                Medicine medicine = medicineRepository.findById(listMedicineId.get(i)).orElse(null);
                daily_report.getMedicines().add(medicine);
            }
        }

        if(!StringUtils.isEmpty(report.getListExerciseId())){
            List<Long> lListExerciseId = GetUtils.convertStringToListLong(report.getListExerciseId());
            for(int i = 0; i< lListExerciseId.size() ;i++){
                Exercise exercise = exerciseRepository.findById(lListExerciseId.get(i)).orElse(null);
                daily_report.getExercises().add(exercise);
            }
        }
        daily_reportRepository.save(daily_report);
    }

    @Override
    public Daily_Report getOneReport(  Long id) {
        return daily_reportRepository.findById(id).orElse(null);
    }

    @Override
    public List<Daily_Report> getOneByUserID(Long id) {
        List<Daily_Report> daily_report = daily_reportRepository.findByUserId(id);
        return daily_report;
    }

    @Override
    public List<Daily_Report> getByDate(String time) {
        List<Daily_Report> daily_report = daily_reportRepository.findByDateTime(time);
        return daily_report;
    }

    @Override
    public void editFeeback(FeebackReqest feebackReqest) {
        Daily_Report daily_report = daily_reportRepository.findById(feebackReqest.getDaily_reportId()).orElse(null);
        daily_report.setFeedback(feebackReqest.getTextFeeback());
        daily_reportRepository.save(daily_report);
    }


}

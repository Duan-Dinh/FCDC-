package com.fpt.myweb.service.impl;


import com.fpt.myweb.common.Contants;
import com.fpt.myweb.entity.*;
import com.fpt.myweb.repository.*;
import com.fpt.myweb.service.FileService;
import com.fpt.myweb.service.MasterDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;
import java.util.List;


@Service
public class MasterDataServiceImpl implements MasterDataService {

    @Autowired
    private VillageRepository villageRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

//    @Autowired
//    private MedicalClinicRepository medicalClinicRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Override
    public List<Village> pickerVillage(String key, Integer districtId) {
        District district = districtRepository.getById((long) districtId);
        return villageRepository.findTop20ByNameContainingAndDistrictOrderById(key, district);
    }

    @Override
    public List<District> pickerDistrict(String key, Integer provinceId) {
        Province province = provinceRepository.getById((long) provinceId);
        return districtRepository.findTop20ByNameContainingAndProvinceOrderById(key, province);
    }

    @Override
    public List<Province> pickerProvince(String key) {
        return provinceRepository.findTop20ByNameContainingOrderById(key);
    }

//    @Override
//    public List<Medical_Clinic> pickerMedical_Clinic(String key, Integer villageId) {
//        Village village = villageRepository.getById((long) villageId);
//        return medicalClinicRepository.findTop20ByNameContainingAndVillageOrderById(key, village);
//    }

    @Override
    public List<Medicine> pickerMedicine(String key) {
        return medicineRepository.findTop20ByNameContainingOrderById(key);
    }
}

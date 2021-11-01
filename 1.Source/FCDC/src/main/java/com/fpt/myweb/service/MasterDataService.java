package com.fpt.myweb.service;

import com.fpt.myweb.entity.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MasterDataService {

    public List<Village> pickerVillage(String key, Integer districtId);

    public List<District> pickerDistrict(String key, Integer provinceId);

    public List<Province> pickerProvince(String key);

    //public List<Medical_Clinic> pickerMedical_Clinic(String key, Integer villageId);

    public List<Medicine> pickerMedicine(String key);

}

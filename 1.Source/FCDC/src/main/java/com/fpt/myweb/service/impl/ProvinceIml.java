package com.fpt.myweb.service.impl;

import com.fpt.myweb.dto.response.AddressRes;
import com.fpt.myweb.entity.Province;
import com.fpt.myweb.repository.ProvinceRepository;
import com.fpt.myweb.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ProvinceIml implements ProvinceService {
    @Autowired
    private ProvinceRepository provinceRepository;
    @Override
    public List<AddressRes> getAllProvince() {
        List<Province> province = provinceRepository.findAll();
        List<AddressRes> provinceRes= new ArrayList<>();
        for(Province provinces : province){
            AddressRes provinceRes1 = new AddressRes();
            provinceRes1.setId(provinces.getId());
            provinceRes1.setName(provinces.getName());
            provinceRes.add(provinceRes1);
        }
        return provinceRes;
    }
}

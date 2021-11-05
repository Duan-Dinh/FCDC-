package com.fpt.myweb.service.impl;

import com.fpt.myweb.dto.response.AddressRes;
import com.fpt.myweb.entity.District;

import com.fpt.myweb.repository.DistrictRepository;

import com.fpt.myweb.service.DistrictsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DistrictsIml implements DistrictsService {
    @Autowired
    private DistrictRepository districtRepository;

    @Override
    public List<AddressRes> getAllDistrictByProvinceId(Long provinceId) {

        List<District> districts = districtRepository.findAllByProvinceId(provinceId);
        List<AddressRes> addressRes = new ArrayList<>();
        for (District district: districts){
            AddressRes addressRes1 = new AddressRes();
            addressRes1.setId(district.getId());
            addressRes1.setName(district.getName());
            addressRes.add(addressRes1);
        }
        return addressRes;
    }
}

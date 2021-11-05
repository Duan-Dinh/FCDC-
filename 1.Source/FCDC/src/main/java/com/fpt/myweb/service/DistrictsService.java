package com.fpt.myweb.service;

import com.fpt.myweb.dto.response.AddressRes;

import java.util.List;

public interface DistrictsService {
    List<AddressRes> getAllDistrictByProvinceId(Long provinceId);
}

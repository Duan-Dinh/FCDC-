package com.fpt.myweb.service.impl;

import com.fpt.myweb.dto.response.AddressRes;
import com.fpt.myweb.entity.District;
import com.fpt.myweb.entity.Village;
import com.fpt.myweb.repository.VillageRepository;
import com.fpt.myweb.service.VillageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class VillageServiceIml implements VillageService {
    @Autowired
    private VillageRepository villageRepository;
    @Override
    public List<AddressRes> getAllVillageBydistrictId(Long id) {
        List<Village> villages = villageRepository.findAllByDistrictId(id);
        List<AddressRes> addressRes = new ArrayList<>();
        for (Village village: villages){
            AddressRes addressRes1 = new AddressRes();
            addressRes1.setId(village.getId());
            addressRes1.setName(village.getName());
            addressRes.add(addressRes1);
        }
        return addressRes;
    }
}

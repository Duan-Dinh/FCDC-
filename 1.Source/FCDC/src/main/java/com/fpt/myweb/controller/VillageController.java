package com.fpt.myweb.controller;

import com.fpt.myweb.common.Contants;
import com.fpt.myweb.dto.response.AddressRes;
import com.fpt.myweb.dto.response.CommonRes;
import com.fpt.myweb.entity.Role;
import com.fpt.myweb.entity.User;
import com.fpt.myweb.exception.ErrorCode;
import com.fpt.myweb.service.VillageService;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping(value = "/village")
public class VillageController {
    @Autowired
    private VillageService villageService;
    @GetMapping(value ="/getAllVillageByDistrictId" )
    public ResponseEntity<List<AddressRes>> getAllDistrictByDistrictId(@PathParam("id") Long id){
        CommonRes commonRes = new CommonRes();
            List<AddressRes> addressRes   = villageService.getAllVillageBydistrictId(id);
        return  new ResponseEntity<List<AddressRes>>(addressRes, HttpStatus.OK);
    }

}

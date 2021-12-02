package com.fpt.myweb.controller;

import com.fpt.myweb.common.Contants;
import com.fpt.myweb.dto.response.AddressRes;
import com.fpt.myweb.dto.response.CommonRes;
import com.fpt.myweb.entity.Role;
import com.fpt.myweb.entity.User;
import com.fpt.myweb.exception.ErrorCode;
import com.fpt.myweb.service.DistrictsService;
import com.fpt.myweb.service.ProvinceService;
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
@RequestMapping(value = "/districts")
public class DistritsController {
    @Autowired
    private DistrictsService districtsService;
    @GetMapping(value ="/getAllDistrictByProvinceId" )
    public ResponseEntity<List<AddressRes>> getAllDistrictByProvinceId(@PathParam("id") Long id){
        CommonRes commonRes = new CommonRes();

            List<AddressRes> addressRes = districtsService.getAllDistrictByProvinceId(id);

        return  new ResponseEntity<List<AddressRes>>(addressRes, HttpStatus.OK);
    }
}

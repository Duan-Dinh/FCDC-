package com.fpt.myweb.controller;

import com.fpt.myweb.common.Contants;
import com.fpt.myweb.dto.response.AddressRes;
import com.fpt.myweb.dto.response.CommonRes;
import com.fpt.myweb.entity.Role;
import com.fpt.myweb.entity.User;
import com.fpt.myweb.exception.ErrorCode;
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
import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping(value = "/province")
public class ProvinveController {
    @Autowired
    private ProvinceService provinceService;
    @Autowired
    ObjectFactory<HttpSession> httpSessionFactory;
    @GetMapping(value ="/getAllProvince" )
    public ResponseEntity<List<AddressRes>> getAllProvince(){
        CommonRes commonRes = new CommonRes();
        List<AddressRes> provinceRes = null;
        HttpSession session = httpSessionFactory.getObject();
        User user = (User) session.getAttribute(Contants.USER_SESSION);
        Role role = user.getRole();
        if(role.getId() == 1L || role.getId() == 2L || role.getId() == 3L || role.getId() == 4L){
            provinceRes = provinceService.getAllProvince();
        }
        else{
            commonRes.setResponseCode(ErrorCode.AUTHEN.getKey());
            commonRes.setMessage(ErrorCode.AUTHEN.getValue());
        }
        return  new ResponseEntity<List<AddressRes>>(provinceRes, HttpStatus.OK);

    }
}

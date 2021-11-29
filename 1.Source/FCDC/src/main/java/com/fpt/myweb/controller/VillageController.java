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
    @Autowired
    ObjectFactory<HttpSession> httpSessionFactory;
    @GetMapping(value ="/getAllVillageByDistrictId" )
    public ResponseEntity<List<AddressRes>> getAllDistrictByDistrictId(@PathParam("id") Long id){
        CommonRes commonRes = new CommonRes();
        List<AddressRes> addressRes = null;
        HttpSession session = httpSessionFactory.getObject();
        User user = (User) session.getAttribute(Contants.USER_SESSION);
        Role role = user.getRole();
        if(role.getId() == 1L || role.getId() == 2L || role.getId() == 3L || role.getId() == 4L){
            addressRes = villageService.getAllVillageBydistrictId(id);
        }
        else{
            commonRes.setResponseCode(ErrorCode.AUTHEN.getKey());
            commonRes.setMessage(ErrorCode.AUTHEN.getValue());
        }
        return  new ResponseEntity<List<AddressRes>>(addressRes, HttpStatus.OK);
    }

}

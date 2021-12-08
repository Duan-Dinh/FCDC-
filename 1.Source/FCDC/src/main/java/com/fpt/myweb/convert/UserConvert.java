package com.fpt.myweb.convert;

import com.fpt.myweb.common.Contants;
import com.fpt.myweb.dto.request.ListUserRequest;
import com.fpt.myweb.dto.request.UserRequet;
import com.fpt.myweb.dto.response.DoctorRes;
import com.fpt.myweb.entity.FileDB;
import com.fpt.myweb.entity.User;
import com.fpt.myweb.entity.Village;
import com.fpt.myweb.exception.AppException;
import com.fpt.myweb.exception.ErrorCode;
import com.fpt.myweb.repository.UserRepository;
import com.fpt.myweb.repository.VillageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
public class UserConvert {
    @Autowired
    VillageRepository villageRepository;
    @Autowired
    private UserRepository userRepository;

    public static User convertToUser(UserRequet userRequet) throws ParseException {
        User user = new User();
        user.setFullname(userRequet.getFullname());
        user.setPassword(userRequet.getPassword());
        user.setGender(userRequet.getGender());
       // user.setEmail(userRequet.getEmail());
        user.setPhone(userRequet.getPhone());
        user.setAddress(userRequet.getAddress());
        Date date = new SimpleDateFormat(Contants.DATE_FORMAT).parse(userRequet.getBirthOfdate());
        user.setBirthOfdate(date);

        return user;
    }

    public UserRequet convertToUserRequest(User user) {
        UserRequet userRequet = new UserRequet();
        userRequet.setId(user.getId());
        userRequet.setFullname(user.getFullname());
        userRequet.setPassword(user.getPassword());
        userRequet.setGender(user.getGender());
       // userRequet.setEmail(user.getEmail());
        userRequet.setPhone(user.getPhone());
        if (user.getAddress() != null) {
            userRequet.setAddress(user.getAddress() + " - " + user.getVillage().getName() + " - " + user.getVillage().getDistrict().getName() + " - " + user.getVillage().getDistrict().getProvince().getName());
        } else {
            userRequet.setAddress(user.getVillage().getName() + " - " + user.getVillage().getDistrict().getName() + " - " + user.getVillage().getDistrict().getProvince().getName());

        }
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        userRequet.setBirthOfdate(dateFormat.format(user.getBirthOfdate()));
        userRequet.setRole_id(user.getRole().getId());
        userRequet.setVillage_id(user.getVillage().getId());
       userRequet.setDistrict_id(user.getVillage().getDistrict().getId());
        userRequet.setProvince_id(user.getVillage().getDistrict().getProvince().getId());
        // userRequet.setId_File(user.getFiles().getId());
        //  FileDB fileDB = new FileDB();
        if (user.getFiles() != null) {
            if (!user.getFiles().getName().isEmpty()) {
                String type = "data:" + DatatypeConverter.parseAnySimpleType(user.getFiles().getType()) + ";base64," + DatatypeConverter.printBase64Binary(user.getFiles().getData());
                userRequet.setImage(type);
            } else {
                userRequet.setImage(null);
            }
        }
        if (user.getDateStart() != null) {
            DateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
            userRequet.setStartOfDate(dateFormat1.format(user.getDateStart()));
        }
        if (user.getResult() != null) {
            userRequet.setResult(user.getResult());
        }
        if (user.getCurrentStatus() != null) {
            userRequet.setCurrentStatus(user.getCurrentStatus());
        }
        if (user.getTypeTakeCare() != null) {
            userRequet.setTypeTakeCare(user.getTypeTakeCare());
        }

        List<User> searchList = userRepository.findAllByVillage(user.getVillage());
        List<DoctorRes> doctorRes = new ArrayList<>();
        for (User user1 : searchList) {
            if (Integer.parseInt(user1.getIs_active()) == 1 && (user1.getRole().getId()) == 3) {
                DoctorRes doctorRes1 = new DoctorRes();
                doctorRes1.setId(user1.getId());
                doctorRes1.setName(user1.getFullname());
                doctorRes1.setPhone(user1.getPhone());
                doctorRes.add(doctorRes1);
            }
        }
        userRequet.setDoctorRes(doctorRes);

        return userRequet;
    }
    public ListUserRequest convertToListUserRequest(User user) {
        ListUserRequest listUserRequest = new ListUserRequest();
        listUserRequest.setId(user.getId());
        listUserRequest.setFullname(user.getFullname());
        listUserRequest.setGender(user.getGender());
        listUserRequest.setPhone(user.getPhone());
        if (user.getAddress() != null) {
            listUserRequest.setAddress(user.getAddress() + " - " + user.getVillage().getName() + " - " + user.getVillage().getDistrict().getName() + " - " + user.getVillage().getDistrict().getProvince().getName());
        } else {
            listUserRequest.setAddress(user.getVillage().getName() + " - " + user.getVillage().getDistrict().getName() + " - " + user.getVillage().getDistrict().getProvince().getName());

        }
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        listUserRequest.setBirthOfdate(dateFormat.format(user.getBirthOfdate()));
        listUserRequest.setRole_id(user.getRole().getId());
        listUserRequest.setVillage_id(user.getVillage().getId());
        listUserRequest.setDistrict_id(user.getVillage().getDistrict().getId());
        listUserRequest.setProvince_id(user.getVillage().getDistrict().getProvince().getId());
        // userRequet.setId_File(user.getFiles().getId());
        //  FileDB fileDB = new FileDB();

        if (user.getDateStart() != null) {
            DateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
            listUserRequest.setStartOfDate(dateFormat1.format(user.getDateStart()));
        }
        if (user.getResult() != null) {
            listUserRequest.setResult(user.getResult());
        }
        if (user.getCurrentStatus() != null) {
            listUserRequest.setCurrentStatus(user.getCurrentStatus());
        }
        if (user.getTypeTakeCare() != null) {
            listUserRequest.setTypeTakeCare(user.getTypeTakeCare());
        }

        List<User> searchList = userRepository.findAllByVillage(user.getVillage());
        List<DoctorRes> doctorRes = new ArrayList<>();
        for (User user1 : searchList) {
            if (Integer.parseInt(user1.getIs_active()) == 1 && (user1.getRole().getId()) == 3) {
                DoctorRes doctorRes1 = new DoctorRes();
                doctorRes1.setId(user1.getId());
                doctorRes1.setName(user1.getFullname());
                doctorRes1.setPhone(user1.getPhone());
                doctorRes.add(doctorRes1);
            }
        }
        listUserRequest.setDoctorRes(doctorRes);

        return listUserRequest;
    }

}

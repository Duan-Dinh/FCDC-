package com.fpt.myweb.convert;

import com.fpt.myweb.common.Contants;
import com.fpt.myweb.dto.request.UserRequet;
import com.fpt.myweb.entity.User;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class UserConvert {
    public static User convertToUser(UserRequet userRequet) throws ParseException {
        User user = new User();
        user.setFullname(userRequet.getFullname());
        user.setPassword(userRequet.getPassword());
        user.setGender(userRequet.getGender());
        user.setEmail(userRequet.getEmail());
        user.setPhone(userRequet.getPhone());
        user.setAddress(userRequet.getAddress());
        Date date = new SimpleDateFormat(Contants.DATE_FORMAT).parse(userRequet.getBirthOfdate());
        user.setBirthOfdate(date);

       return user;
    }
    public static UserRequet convertToUserRequest(User user){
        UserRequet userRequet = new UserRequet();
        userRequet.setId(user.getId());
        userRequet.setFullname(user.getFullname());
        userRequet.setPassword(user.getPassword());
        userRequet.setGender(user.getGender());
        userRequet.setEmail(user.getEmail());
        userRequet.setPhone(user.getPhone());
        userRequet.setAddress(user.getAddress());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        userRequet.setBirthOfdate(dateFormat.format(user.getBirthOfdate()));
        userRequet.setRole_id(user.getRole().getId());
        userRequet.setVillage_id(user.getVillage().getId());
        if(user.getDateStart()!=null){
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        userRequet.setStartOfDate(dateFormat1.format(user.getDateStart()));
        }
        if(user.getResult()!=null){
            userRequet.setResult(user.getResult());
        }

        return userRequet;
    }

}

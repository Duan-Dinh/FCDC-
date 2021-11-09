package com.fpt.myweb.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.springframework.util.StringUtils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class GetUtils {

    public static List<Long> convertStringToListLong(String arr){
        List<Long> result = new ArrayList<>();
        if(StringUtils.isEmpty(arr)){
            return result;
        }
        try {
            String[] strings = arr.split(",");
            for (String s:strings){
                result.add(Long.parseLong(s));
            }
            return result;
        } catch (Exception e){
            return new ArrayList<>();
        }
    }
    public static List<String> splipStringTophone(String arr){
        List<String> result = new ArrayList<>();
        if(StringUtils.isEmpty(arr)){
            return result;
        }
        try {
            String[] strings = arr.split(",");
            for (String s:strings){
                result.add(s);
            }
            return result;
        } catch (Exception e){
            return new ArrayList<>();
        }
    }

    public static String generateRandomPassword(int len)
    {
        // ASCII range – alphanumeric (0-9, a-z, A-Z)
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        // each iteration of the loop randomly chooses a character from the given
        // ASCII range and appends it to the `StringBuilder` instance

        for (int i = 0; i < len; i++)
        {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        return sb.toString();
    }
}

package com.fpt.myweb.service.impl;


import com.fpt.myweb.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private Environment env;

    public Document loadXMLFromString(String xml) throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }


    @Override
    public String sendGetJSON(String phone, String message) throws IOException {
        String APIKey = env.getProperty("sms.apikey");
        String SecretKey = env.getProperty("sms.secret.key");
        String url = "http://rest.esms.vn/MainService.svc/json/SendMultipleMessage_V4_get?ApiKey=" +
                URLEncoder.encode(APIKey, "UTF-8") + "&SecretKey=" + URLEncoder.encode(SecretKey, "UTF-8") +
                "&SmsType=8&Phone=" + URLEncoder.encode(phone, "UTF-8") + "&Content=" + URLEncoder.encode(message, "UTF-8");

        URL obj;
        try {
            obj = new URL(url);

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            //you need to encode ONLY the values of the parameters

            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            if(responseCode==200)//Đã gọi URL thành công, tuy nhiên bạn phải tự kiểm tra CodeResult xem tin nhắn có gửi thành công không, vì có thể tài khoản bạn không đủ tiền thì sẽ thất bại
            {
                //Check CodeResult from response
            }
            //Đọc Response
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            //document.getElementsByTagName("CountRegenerate").item(0).va
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "SUCCESS";
    }
}

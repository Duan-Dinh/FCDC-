package com.fpt.myweb.convert;

import com.fpt.myweb.dto.request.NewRequet;
import com.fpt.myweb.entity.New;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;


@Component
public class NewConvert {
    public NewRequet convertToNewRequest(New news){
        NewRequet newRequet = new NewRequet();
        newRequet.setId(news.getId());
        newRequet.setTitle(news.getTitle());
        newRequet.setDecription(news.getDecription());
        //  FileDB fileDB = new FileDB();

        if(news.getFilesNew() != null){

            String type ="data:"+ DatatypeConverter.parseAnySimpleType(news.getFilesNew().getType()) +";base64,"+ DatatypeConverter.printBase64Binary(news.getFilesNew().getData());
            newRequet.setImage(type);
        }

        return newRequet;
    }

}

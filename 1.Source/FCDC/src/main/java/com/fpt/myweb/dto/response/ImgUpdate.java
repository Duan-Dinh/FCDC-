package com.fpt.myweb.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class ImgUpdate {
    private Long id;
    private MultipartFile newImage;
}

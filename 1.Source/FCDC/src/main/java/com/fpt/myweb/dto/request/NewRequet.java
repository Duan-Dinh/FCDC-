package com.fpt.myweb.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class NewRequet {

    private Long id;

    private String title;

    private String decription;

    private String createDate;

    private String image;


}

package com.fpt.myweb.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Message {
    private Date timestamp;
    private String message;
}

package com.fpt.myweb.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    PROCESS_SUCCESS("00000", "All thing done"),
    CHANGERESULT_FAIL("00005", "Phải lớn hơn hoặc bằng 14 ngày"),
    AUTHENTICATION_FAILED("00002", "Authentication failed"),
    AUTHENTICATION_FAILED_1("00010", "Số điện thoại thêm vào đã tồn tại trong hệ thống"),
    EXCEL_DUPLICATE("00003", "Định dạng dữ liệu không hợp lệ !"),

    NOT_FOUND_ID("U001", "Not found ID = "),
    NOT_FOUND_ROLE_ID("R001", "Not found role ID = "),
    NOT_FOUND_VILLAGE_ID("V001", "Not village ID = "),
    SENT("00006", "Sent"),
    NOT_SENT("00007", "Not Sent"),
    LESS_100("00008", "Total patient < 100"),
    BIGGER_100("00009", "Total patient > 100"),
    INTERNAL_SERVER_ERROR("00001", "Internal server error"),
    AUTHEN("00004", "Authen fails");
    private String key;
    private String value;

    private ErrorCode(String key, String value){
        this.key = key;
        this.value = value;
    }
}

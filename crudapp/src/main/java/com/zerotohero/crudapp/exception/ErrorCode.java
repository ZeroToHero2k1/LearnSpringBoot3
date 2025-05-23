package com.zerotohero.crudapp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    USER_EXISTED(1002,"User Existeddd",HttpStatus.BAD_REQUEST),
    ERRORCODE_INVALID(1009,"None errorcode existed",HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003,"Username invalid, username at least {min} words",HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004,"Password invalid, password at least {min} words",HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(9999,"Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_NOTFOUND(1005,"User not found",HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006,"Unauthenticated",HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007,"You do not have permission",HttpStatus.FORBIDDEN),
    INVALID_DOB(1008,"Chưa đủ tuổi đâu cu, ít nhất phải {min}",HttpStatus.BAD_REQUEST)
    ;

    private int code;
    private String message;
    private HttpStatusCode statusCode;

    ErrorCode(int code, String message,HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode=statusCode;
    }

}

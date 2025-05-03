package com.zerotohero.crudapp.exception;

public enum ErrorCode {
    USER_EXISTED(1002,"User Existeddd"),
    ERRORCODE_INVALID(1000,"None errorcode existed"),
    USERNAME_INVALID(1003,"Username invalid, username at least 3 words"),
    PASSWORD_INVALID(1004,"Password invalid, password at least 8 words"),
    UNCATEGORIZED_EXCEPTION(9999,"Uncategorized error"),
    USER_NOTFOUND(1005,"User not found"),
    UNAUTHENTICATED(1006,"Unauthenticated")
    ;

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

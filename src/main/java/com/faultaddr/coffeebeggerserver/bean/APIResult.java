package com.faultaddr.coffeebeggerserver.bean;

import com.faultaddr.coffeebeggerserver.Constants;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class APIResult {
    private int code;
    private String message;
    private Object data;

    public static APIResult createSuccessMessage(Object data) {
        return createWithCodeAndData(Constants.SUCCESS, null, data);
    }

    public static APIResult createSuccessMessage(String message) {
        return createWithCodeAndData(Constants.SUCCESS, message, null);
    }

    public static APIResult createErrorMessage(String message) {
        return createWithCodeAndData(Constants.FAILED, message, null);
    }

    public static APIResult createWithCodeAndData(int code, String message, Object data) {
        APIResult result = new APIResult();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
}

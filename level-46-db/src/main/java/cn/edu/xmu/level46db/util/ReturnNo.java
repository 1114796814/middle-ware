package cn.edu.xmu.level46db.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/04/26 14:24
 */
public enum ReturnNo {
    //不加说明的状态码为200或201（POST）
    OK(0, "成功"),
    //状态码 500
    INTERNAL_SERVER_ERR(500, "服务器内部错误"),
    //状态码 401
    AUTH_INVALID_ACCOUNT(700, "用户名不存在或者密码错误"),
    NOT_ENOUGH(701, "名额不足"),
    NOT_AT_THE_SPECIFIED_TIME(702, "不存在，或不在规定时间");
    private int code;
    private String message;
    private static final Map<Integer, ReturnNo> returnNoMap;

    static {
        returnNoMap = new HashMap();
        for (ReturnNo enum1 : values()) {
            returnNoMap.put(enum1.code, enum1);
        }
    }

    ReturnNo(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ReturnNo getReturnNoByCode(int code) {
        return returnNoMap.get(code);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

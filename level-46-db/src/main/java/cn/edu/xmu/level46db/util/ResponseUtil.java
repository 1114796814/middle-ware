package cn.edu.xmu.level46db.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/04/26 14:26
 */
public class ResponseUtil {
    public static Object ok() {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("errno", ReturnNo.OK.getCode());
        obj.put("errmsg", ReturnNo.OK.getMessage());
        return obj;
    }

    public static Object ok(Object data) {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("errno", ReturnNo.OK.getCode());
        obj.put("errmsg", ReturnNo.OK.getMessage());
        obj.put("data", data);
        return obj;
    }

    public static Object fail(ReturnNo code) {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("errno", code.getCode());
        obj.put("errmsg", code.getMessage());
        return obj;
    }

    public static Object fail(ReturnNo code, String errmsg) {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("errno", code.getCode());
        obj.put("errmsg", errmsg);
        return obj;
    }

    public static Object fail(ReturnNo code, String errmsg, Object data) {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("errno", code.getCode());
        obj.put("errmsg", errmsg);
        obj.put("data", data);
        return obj;
    }

}


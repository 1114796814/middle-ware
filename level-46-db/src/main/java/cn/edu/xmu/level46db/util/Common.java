package cn.edu.xmu.level46db.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/04/26 14:27
 */
public class Common {
    public static Object decorateReturnObject(ReturnObject returnObject) {
        switch (returnObject.getCode()) {
            case INTERNAL_SERVER_ERR:
                // 500：数据库或其他严重错误
                return new ResponseEntity(
                        ResponseUtil.fail(returnObject.getCode(), returnObject.getErrmsg()),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            case OK:
                // 200: 无错误
                Object data = returnObject.getData();
                if (data != null) {
                    return ResponseUtil.ok(data);
                } else {
                    return ResponseUtil.ok();
                }
            default:
                data = returnObject.getData();
                if (data != null) {
                    return ResponseUtil.fail(returnObject.getCode(), returnObject.getErrmsg(), returnObject.getData());
                } else {
                    return ResponseUtil.fail(returnObject.getCode(), returnObject.getErrmsg());
                }
        }
    }
}

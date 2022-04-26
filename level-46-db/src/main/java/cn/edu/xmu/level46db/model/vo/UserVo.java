package cn.edu.xmu.level46db.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/04/26 10:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserVo {
    private String idCard;
    private String name;
    private String password;
    private String email;
    private String mobile;
    private String university;
    private String address;
}

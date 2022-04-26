package cn.edu.xmu.level46db.model.bo;

import cn.edu.xmu.level46db.model.po.UserPo;
import cn.edu.xmu.level46db.model.vo.LoginVo;
import cn.edu.xmu.level46db.model.vo.UserVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/04/26 12:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private Long id;
    private String idCard;
    private String name;
    private String password;
    private String email;
    private String mobile;
    private String university;
    private String address;

    public User(LoginVo loginVo) {
        this.email = loginVo.getEmail();
        this.password = loginVo.getPassword();
    }

    public UserVo generateVo() {
        return new UserVo(idCard, name, password, email, mobile, university, address);
    }

    public UserPo generatePo() {
        UserPo userPo = new UserPo();
        userPo.setAddress(address);
        userPo.setEmail(email);
        userPo.setMobile(mobile);
        userPo.setPassword(password);
        userPo.setIdCard(idCard);
        userPo.setUniversity(university);
        userPo.setName(name);
        return userPo;
    }
}

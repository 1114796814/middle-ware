package cn.edu.xmu.level46db.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/04/26 14:32
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class LoginVo {
    @NotNull
    @NotBlank
    String email;
    @NotNull
    @NotBlank
    String password;
}

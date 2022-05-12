package cn.edu.xmu.level46db.controller;

import cn.edu.xmu.level46db.model.vo.LoginVo;
import cn.edu.xmu.level46db.service.Level46dbService;
import cn.edu.xmu.level46db.util.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Method;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/04/26 14:29
 */
@RestController
@RequestMapping(value = "/", produces = "application/json;charset=UTF-8")
public class Level46dbController {
    @Autowired
    Level46dbService level46dbService;

    @PostMapping("/login")
    public Object login(@RequestBody LoginVo loginVo) {
        return Common.decorateReturnObject(level46dbService.login(loginVo));
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/rush/{id}")
    public Object rush(@PathVariable @NotNull @NotBlank Long id) {
        return Common.decorateReturnObject(level46dbService.rush(id));
    }
}

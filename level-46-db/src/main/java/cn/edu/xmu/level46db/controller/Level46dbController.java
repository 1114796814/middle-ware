package cn.edu.xmu.level46db.controller;

import cn.edu.xmu.level46db.model.vo.LoginVo;
import cn.edu.xmu.level46db.service.Level46dbService;
import cn.edu.xmu.level46db.util.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/04/26 14:29
 */
@RestController
public class Level46dbController {
    @Autowired
    Level46dbService level46dbService;

    @PostMapping("/login")
    public Object login(@RequestBody LoginVo loginVo) {
        return Common.decorateReturnObject(level46dbService.login(loginVo));
    }

    /**
     * @param type
     * @param year
     * @return
     */
    @GetMapping("/rush")
    public Object rush(@RequestParam(value = "type") @NotNull @NotBlank Byte type,
                       @RequestParam(value = "year") @NotNull @NotBlank Integer year,
                       @RequestParam(value = "month") @NotNull @NotBlank Integer month) {
        return Common.decorateReturnObject(level46dbService.rush(type, year, month));
    }
}

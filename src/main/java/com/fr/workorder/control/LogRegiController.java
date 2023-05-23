package com.fr.workorder.control;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@Api(tags = {"LogRegiController"},description = "登录、注册功能")
public class LogRegiController {

    @Value("${server.servlet.context-path}")
    private String contextPath;


    @ApiOperation(value = "处理登录请求",notes = "需要账号密码进行验证，返回验证结果，如果登录成功还会返回ticket供后续验证身份使用")
    @PostMapping(path = "login")
    public String login(@ApiParam(name="uid",value = "用户id", required = true)String userid,
                        @ApiParam(name="uid",value = "用户密码", required = true)String password,
                        HttpServletResponse response){
        // 检查账号,密码
//        int expiredSeconds = 3600*24*100;
//        Map<String, Object> map = userService.login(userid, password, expiredSeconds);
//        if (map.containsKey("ticket")) {
//            Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
//            cookie.setPath(contextPath);
//            cookie.setMaxAge(expiredSeconds);
//            response.addCookie(cookie);
//            return "redirect:/index";
//        } else {
//            model.addAttribute("usernameMsg", map.get("usernameMsg"));
//            model.addAttribute("passwordMsg", map.get("passwordMsg"));
//            return "/site/login";
//        }
        return null;
    }

}

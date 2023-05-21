package com.fr.workorder.control;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = {"LogRegiController"},description = "登录、注册功能")
public class LogRegiController {
    @ApiOperation(value = "处理登录请求",notes = "需要账号密码进行验证，返回验证结果，如果登录成功还会返回ticket供后续验证身份使用")
    @GetMapping(path = "login")
    public String login(@ApiParam(name="uid",value = "用户id", required = true)String uid){
        return uid;
    }

}

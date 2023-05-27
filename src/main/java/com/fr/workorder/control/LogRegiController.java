package com.fr.workorder.control;


import com.fr.workorder.service.impl.UserServiceImpl;
import com.fr.workorder.utils.Constants;
import com.fr.workorder.utils.GlobalUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(tags = {"LogRegiController"},description = "登录、注册功能")
public class LogRegiController implements Constants {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    UserServiceImpl userService;


    @PostMapping(path = "login")
    public String login(@RequestParam("email") String userEmail,
                        @RequestParam("password")String password,
                        HttpServletResponse response){
        // 检查账号,密码
        int expiredSeconds = DEFAULT_EXPIRED_SECONDS;
        Map<String, Object> map = userService.login(userEmail, password, expiredSeconds);
        if (map.containsKey("ticket")) {
            Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
            cookie.setPath(contextPath);
            cookie.setMaxAge(expiredSeconds);
            response.addCookie(cookie);
            return GlobalUtil.getJSONString(success);
        } else {
            return GlobalUtil.getJSONString(failed, null, map);
        }

    }

    //从路径中获取验证码和用户ID
    // http://localhost:8080/workorder/activation/101/code
    @GetMapping(path = "/activation/{userId}/{code}")
    public String activation(@PathVariable("userId") String userId, @PathVariable("code") String code) {
        int result = userService.activation(userId, code);
        Map<String, Object>map = new HashMap<>();
        if (result == ACTIVATION_SUCCESS) {
            map.put("msg", "激活成功,可使用此账号密码登录!");
            map.put("redirect", "/login");
        } else if (result == ACTIVATION_REPEAT) {
            map.put("msg", "请勿重复激活该账号!");
            map.put("redirect", "/login");
        } else {
            map.put("msg", "激活失败,您提供的激活码不正确!");
            map.put("redirect", "/register");
            return GlobalUtil.getJSONString(failed,null,map);
        }
        return GlobalUtil.getJSONString(success, null, map);
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(@RequestParam("name")String userName, @RequestParam("password")String password,
                           @RequestParam("email")String userEmail, @RequestParam("type")String userType,
                           @RequestParam("company_name")String companyName, @RequestParam("department_id")int departmentId)
            throws MessagingException {

        Map<String, Object> map = userService.register(userName,password,userEmail,userType,companyName,departmentId);

        if (map == null || map.isEmpty()) {
            return GlobalUtil.getJSONString(success);
        } else {
            //注册失败之后应该返回注册页面，并且传回是哪个地方有问题的信息
            return GlobalUtil.getJSONString(failed, null, map);
        }
    }
}

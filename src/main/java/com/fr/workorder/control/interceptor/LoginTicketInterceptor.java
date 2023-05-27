package com.fr.workorder.control.interceptor;

import com.fr.workorder.entity.Loginticket;
import com.fr.workorder.entity.User;
import com.fr.workorder.service.UserService;
import com.fr.workorder.utils.CookieUtil;
import com.fr.workorder.utils.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class LoginTicketInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @Override
    //在调用service之前拦截
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从cookie中获取凭证
        String ticket = CookieUtil.getValue(request, "ticket");

        if (ticket != null) {
            // 查询凭证
            Loginticket loginTicket = userService.findLoginTicket(ticket);
            // 检查凭证是否有效
            if (loginTicket != null && !loginTicket.getStatus() && loginTicket.getExpiredTime().after(new Date())) {
                // 根据凭证查询用户
                User user = userService.getById(loginTicket.getUserId());
                // 在本次请求中持有用户
                hostHolder.setUser(user);

                // 构建用户认证的结果,并存入SecurityContext,以便于Security进行授权.
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        user, user.getPassword(), userService.getAuthorities(user.getUserId()));
                SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
            }
        }
        // 无论怎么样都返回true，是因为我们要允许未登录用户进行访问
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        // 如果是未登录用户在访问就不在模型中添加
//        User user = hostHolder.getUser();
//        if (user != null && modelAndView != null) {
//            modelAndView.addObject("loginUser", user);
//        }
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}

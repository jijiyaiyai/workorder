package com.fr.workorder.config;

import com.fr.workorder.utils.Constants;
import com.fr.workorder.utils.GlobalUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.io.PrintWriter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter implements Constants {
    @Override
    public void configure(WebSecurity web) {
        //web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//         授权:

        http.authorizeRequests()
                .anyRequest().permitAll()
                .and().csrf().disable();

        // 权限不够时的处理
        // 没有登录
        // 权限不足
        http.exceptionHandling()
                .authenticationEntryPoint((request, response, e) -> {
                    String xRequestedWith = request.getHeader("x-requested-with");
                    //如果是异步请求，则返回json
                    if ("XMLHttpRequest".equals(xRequestedWith)) {
                        response.setContentType("application/plain;charset=utf-8");
                        PrintWriter writer = response.getWriter();
                        writer.write(GlobalUtil.getJSONString(403, "您还没有登录，请先登录。"));
                    } else {
                        //打回到登录页面让用户登录
                        response.sendRedirect(request.getContextPath() + "/login");
                    }
                })
                .accessDeniedHandler((request, response, e) -> {
                    String xRequestedWith = request.getHeader("x-requested-with");
                    if ("XMLHttpRequest".equals(xRequestedWith)) {
                        response.setContentType("application/plain;charset=utf-8");
                        PrintWriter writer = response.getWriter();
                        writer.write(GlobalUtil.getJSONString(403, "您没有访问此功能的权限!"));
                    } else {
                        //跳转到拒绝访问
                        response.sendRedirect(request.getContextPath() + "/denied");
                    }
                });

        // Security底层默认会拦截/logout请求,进行退出处理.
        // 这样的话在logout之后的拦截器不会触发
        // 覆盖它默认的逻辑，执行自己的退出逻辑
        http.logout().logoutUrl("/securitylogout");

    }
}

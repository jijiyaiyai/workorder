package com.fr.workorder.control.advice;


import com.fr.workorder.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice(annotations = Controller.class) // 只扫描Controller组件
public class ExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);
    @ExceptionHandler({Exception.class})  // 处理异常的注解
    public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.error("服务器发生异常： "+ e.getMessage());
        for(StackTraceElement element: e.getStackTrace()){
            logger.error(element.toString());
        }
        // 这里统一返回错误信息，由前端决定展示内容
        String xRequestWith = request.getHeader("x-requested-with");
        if("XMLHttpRequest".equals(xRequestWith)){
            response.setContentType("application/plain;charset=utf-8");
            PrintWriter pw = response.getWriter();
            pw.write(StringUtil.getJSONString(1,"服务器发生异常！"));
        }else{
            response.sendRedirect(request.getContextPath()+"/error");
        }
    }


}

package com.zhb.nettychat.web.interceptor;

import com.zhb.nettychat.util.Constant;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserAuthInteceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession();
        //Object userToken = Constant.USERS_TOKEN;
        Object userToken = session.getAttribute(Constant.USER_TOKEN);
        System.out.println("session" + session.getAttribute(Constant.USER_TOKEN));
        if (userToken == null) {
            /*JsonMsgHelper.writeJson(response, new ResponseJson(HttpStatus.FORBIDDEN).setMsg("请登录"),
                    HttpStatus.FORBIDDEN);*/
            //地址重定向
            response.sendRedirect("login");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

}

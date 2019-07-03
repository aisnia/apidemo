package com.xiaoqiang.apidemo.shiro.filter;

import com.alibaba.fastjson.JSON;
import com.xiaoqiang.apidemo.shiro.exception.MyTokenErrorException;
import com.xiaoqiang.apidemo.shiro.exception.MyTokenExpireException;
import com.xiaoqiang.apidemo.shiro.token.JWTToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaoqiang
 * @date 2019/3/28-20:00
 */
//是否是登录尝试
public class JwtFilter extends BasicHttpAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 判断用户是否想要登入。
     * 检测请求是否  包含 accessToken 字段即可
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getParameter("accessToken");
        String contentType = req.getContentType();
        if (contentType!=null&&req.getContentType().contains("multipart/form-data")) {
            return true;
        }
        return token != null;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        String code =  request.getAttribute("status").toString();
        String msg =  request.getAttribute("msg").toString();
        Map<String,Object> map = new HashMap<>(16);
        map.put("code",code);
        map.put("msg",msg);
        String json = JSON.toJSONString(map);
        HttpServletResponse response1 = (HttpServletResponse) response;
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response1.getWriter();
        writer.write(json);
        return false;
    }

    /**
     * 这里我们详细说明下为什么最终返回的都是true，即允许访问
     * 例如我们提供一个地址 GET /article
     * 登入用户和游客看到的内容是不同的
     * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西
     * 所以我们在这里返回true，Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
     * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {

            if (executeLogin(request, response)) {
                return true;
            } else {
               return  false;
            }

        } else {
            //如果请求没有携带token请求头
            request.setAttribute("status","1");
            request.setAttribute("msg","没有携带token请求头");
            return false;
        }
    }
//    最先执行的方法
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        return super.preHandle(request, response);
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response){
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String contentType = request.getContentType();
        if (contentType!=null&&request.getContentType().contains("multipart/form-data")) {
            return true;
        }
        String token = httpServletRequest.getParameter("accessToken");
        JWTToken jwtToken = new JWTToken(token);
        try {
            getSubject(request, response).login(jwtToken);
            return true;
        } catch (MyTokenExpireException e) {
            httpServletRequest.setAttribute("status", "7");
            httpServletRequest.setAttribute("msg", "token过期");
            return false;
        } catch (MyTokenErrorException e) {
            httpServletRequest.setAttribute("status", "1");
            httpServletRequest.setAttribute("msg", "toekn错误");
            return false;
        }


    }
}

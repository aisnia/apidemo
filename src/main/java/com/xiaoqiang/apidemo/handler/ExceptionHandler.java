package com.xiaoqiang.apidemo.handler;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaoqiang
 * @date 2019/3/28-20:57
 * 异常处理类
 */
@RestControllerAdvice
public class ExceptionHandler {
    /**
     * 捕获shiro的无权限异常
     * @param
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(AuthorizationException.class)
    public Map<String,Object> noAuthorizationHandle(){
        HashMap<String, Object> map = new HashMap<>(16);
        map.put("code", "无权限");
        return map;
    }

    /**
     * 捕获token过期异常
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(TokenExpiredException.class)
    public Map<String,Object> tokenExpireHandle(){
        HashMap<String, Object> map = new HashMap<>(16);
        map.put("code", "token过期");
        return map;
    }

    /**
     * token错误的异常
     * @param
     */
    @org.springframework.web.bind.annotation.ExceptionHandler({JWTDecodeException.class, SignatureVerificationException.class})
    public Map<String,Object> tokenErrorHandle(){
        HashMap<String, Object> map = new HashMap<>(16);
        map.put("code", "token错误");
        return map;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ConstraintViolationException.class, BindException.class})
    public Map<String,Object> validationExceptionHandler(){
        HashMap<String, Object> map = new HashMap<>(16);

        map.put("code","参数不合法");
        return map;
    }

}

package com.xiaoqiang.apidemo.shiro.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @author xiaoqiang
 * @date 2019/3/28-20:20
 */
public class MyTokenExpireException extends AuthenticationException {
    public MyTokenExpireException() {
    }

    public MyTokenExpireException(String message) {
        super(message);
    }
}

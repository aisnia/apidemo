package com.xiaoqiang.apidemo.shiro.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @author xiaoqiang
 * @date 2019/3/28-20:21
 */
public class MyTokenErrorException extends AuthenticationException {
    public MyTokenErrorException() {
    }

    public MyTokenErrorException(String message) {
        super(message);
    }
}

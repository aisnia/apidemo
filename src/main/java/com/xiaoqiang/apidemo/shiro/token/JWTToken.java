package com.xiaoqiang.apidemo.shiro.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author xiaoqiang
 * @date 2019/3/28-20:14
 */
public class JWTToken implements AuthenticationToken {

    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    public String getToken(){
        return this.token;
    }

}

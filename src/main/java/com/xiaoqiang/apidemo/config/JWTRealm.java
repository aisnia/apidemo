package com.xiaoqiang.apidemo.config;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.xiaoqiang.apidemo.shiro.exception.MyTokenErrorException;
import com.xiaoqiang.apidemo.shiro.exception.MyTokenExpireException;
import com.xiaoqiang.apidemo.shiro.token.JWTToken;
import com.xiaoqiang.apidemo.utils.JWTUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author xiaoqiang
 * @date 2019/3/29-17:39
 */
public class JWTRealm extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        return (token instanceof JWTToken);
    }
    /**
     * 执行授权逻辑
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("竟然权限认证了");
        return null;
    }


    /**
     * 执行认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JWTToken jwtToken = (JWTToken) authenticationToken;
        String accessToken = jwtToken.getToken();
        try {
            JWTUtil.verifyAccessToken(accessToken);
        } catch (TokenExpiredException e) {
            throw new MyTokenExpireException("token过期");
        } catch (Exception e) {
            throw new MyTokenErrorException("token错误");
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(accessToken, accessToken, "JwtRealm");
        return authenticationInfo;
    }
}

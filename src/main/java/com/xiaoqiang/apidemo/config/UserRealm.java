package com.xiaoqiang.apidemo.config;

import com.xiaoqiang.apidemo.bean.User;
import com.xiaoqiang.apidemo.dao.UserMapper;
import com.xiaoqiang.apidemo.service.UserService;
import com.xiaoqiang.apidemo.shiro.token.JWTToken;
import org.apache.shiro.SecurityUtils;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * @author xiaoqiang
 * @date 2019/6/24-21:12
 */
public class UserRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(UserRealm.class);

    @Override
    public boolean supports(AuthenticationToken token) {
        return (token instanceof UsernamePasswordToken);
    }

    public UserRealm() {
        this.setCredentialsMatcher((token, info) -> {
            UsernamePasswordToken userToken = (UsernamePasswordToken) token;
            //要验证的明文密码
            String plaintext = new String(userToken.getPassword());
            //数据库中的加密后的密文
            String hashed = info.getCredentials().toString();
            return BCrypt.checkpw(plaintext, hashed);
        });
    }

    @Autowired
    private UserMapper userMapper;

    /**
     * 执行授权逻辑
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权逻辑");
        logger.info("执行授权逻辑");

        //        从principalCollection 中获取用户登录的信息
        User principal = (User) principalCollection.getPrimaryPrincipal();

        //        利用用户登录的信息来获取用户的权限 和角色  可能需要查询数据库
        Set<String> roles = new HashSet<>();
        User dbUser = userMapper.findById(principal.getId());
        if (dbUser.getPerms().equals("user")) {
            roles.add("user");
        } else if (dbUser.getPerms().equals("verify")) {
            roles.add("user");
            roles.add("verify");
        } else if (dbUser.getPerms().equals("test")) {
            roles.add("user");
            roles.add("verify");
            roles.add("test");
        }
        //        创建SimpleAuthenticationInfo 并且设置器属性reles 返回这个对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
        return info;
    }


    /**
     * 执行认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行认证逻辑");

        //编写shiro判断逻辑，判断用户名和密码
        UsernamePasswordToken token  =  (UsernamePasswordToken)authenticationToken;
//        从数据库根据 username来获取
        User user = userMapper.findByName(token.getUsername());

        //1、判断用户名
        if(user == null){
            //用户名不存在
            return null; //shiro底层会抛出UnKnowAccountException
        }
        Object principal = user;
        Object credentials= user.getPassword();
        String realmName = getName();
        //2、判断密码, 这里的user是principal
        return new SimpleAuthenticationInfo(principal, credentials, realmName);
    }


}

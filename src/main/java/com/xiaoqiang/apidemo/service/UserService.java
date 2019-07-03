package com.xiaoqiang.apidemo.service;

import com.xiaoqiang.apidemo.bean.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author xiaoqiang
 * @date 2019/3/28-21:19
 */
public interface UserService {
    Map<String, Object> findByName(String name);
    Map<String, Object> findById(String accessToken);

    Map<String, Object> login(String name, String password);

    Map<String, Object> register(HttpServletRequest request);

    Map<String, Object> findAllUsers(String accessToken, Integer pageNum,Integer pageSize);

    Map<String, Object> deleteUser(String accessToken,Integer id);

    Map<String, Object> updateUserInfo(HttpServletRequest request);

    Map<String, Object> authorizeToUser(String accessToken, Integer id, String perms);

    Map<String, Object> findById(Integer id);

    Map<String, Object> confirmPassword(String accessToken, String password);

    Map<String, Object> updatePwd(String accessToken, String uuid,String password);
}

package com.xiaoqiang.apidemo.controller;

import com.xiaoqiang.apidemo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaoqiang
 * @date 2019/3/28-21:19
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;


    //    用户注册
    @ResponseBody
    @RequestMapping(value = "guest/register", method = RequestMethod.POST)
    public Map<String, Object> register(HttpServletRequest request) {
        return userService.register(request);
    }

    //    用户登录
    @ResponseBody
    @RequestMapping(value = "/guest/login",method = RequestMethod.POST)
    public Map<String, Object> login(@RequestParam("name") String name,@RequestParam("password") String password,@RequestParam("token") String token,@RequestParam("Vcode") String Vcode) {
        Map<String, Object> map = null;
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
//用户名密码包装成  UsernamePasswordToken 对象
            map = userService.login(name, password,token,Vcode);
        } else {
            map = new HashMap<>();

        }
        return map;
    }


    //    查找自己的信息 查询用户的详细信息
    @ResponseBody
    @RequestMapping(value = "/user/findInfo", method = RequestMethod.GET)
    public Map<String, Object> findUserInfo(@RequestParam("accessToken") String accessToken) {
        return userService.findById(accessToken);
    }

    //查看其它人的信息
    @ResponseBody
    @RequestMapping(value = "/user/findById", method = RequestMethod.GET)
    public Map<String, Object> findById(@RequestParam("accessToken") String accessToken,@RequestParam("id") Integer id) {
        return userService.findById(id);
    }

    //    修改用户的信息
    @ResponseBody
    @RequestMapping(value = "/user/updateInfo", method = RequestMethod.POST)
    public Map<String, Object> updateUserInfo(HttpServletRequest request) {
        return userService.updateUserInfo(request);
    }
}

package com.xiaoqiang.apidemo.controller;

import com.xiaoqiang.apidemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author xiaoqiang
 * @date 2019/3/30-13:05
 */
@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    //    查找n个用户 昵称 账号 id 等简单信息
    @ResponseBody
    @RequestMapping(value = "/admin/findAll",method = RequestMethod.GET)
        public Map<String, Object> findAllUsers(@RequestParam("accessToken") String accessToken, @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize) {
        return userService.findAllUsers(accessToken, pageNum,pageSize);
    }

    //    删除用户
    @ResponseBody
    @RequestMapping(value = "/admin/deleteUser", method = RequestMethod.GET)
    public Map<String,Object> deleteUser(@RequestParam("accessToken") String accessToken, @RequestParam("id") Integer id) {
        return userService.deleteUser(accessToken,id);
    }

    //    给用户授权，转换角色
    @ResponseBody
    @RequestMapping(value = "/admin/authorizeToUser", method = RequestMethod.POST)
    public Map<String, Object> authorizeToUser(@RequestParam("accessToken") String accessToken, @RequestParam("id") Integer id, @RequestParam("perms") String perms) {
        return userService.authorizeToUser(accessToken, id, perms);
    }
}

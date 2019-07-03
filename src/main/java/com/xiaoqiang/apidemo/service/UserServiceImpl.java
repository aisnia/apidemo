package com.xiaoqiang.apidemo.service;

import com.auth0.jwt.interfaces.Claim;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoqiang.apidemo.bean.User;
import com.xiaoqiang.apidemo.dao.UserMapper;
import com.xiaoqiang.apidemo.utils.JWTUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author xiaoqiang
 * @date 2019/3/28-21:19
 */
@Service
public class UserServiceImpl implements UserService {
    /**
     * redis存放token的前缀
     */
    public static final String USER_TOKEN_PREFIX = "user:token:";

    /**
     * 设置token过期时间一小时
     */
    public static final long TOKEN_EXPIRE_TIME = 60 * 60 * 30;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public Map<String, Object> findByName(String name) {
        Map<String, Object> map = new HashMap<>();
        User user = userMapper.findByName(name);
        user.setSolt(null);
        user.setPassword(null);
        map.put("user", user);
        map.put("code", "0");
        return map;
    }

    @Override
    public Map<String, Object> findById(String accessToken) {
        Map<String, Object> map = new HashMap<>();
        Integer id = getId(accessToken);
        User user = userMapper.findById(id);
        user.setSolt(null);
        user.setPassword(null);
        map.put("user", user);
        map.put("code", "0");
        return map;
    }

    @Override
    public Map<String, Object> login(String name, String password) {
        Map<String, Object> map = new HashMap<>();
        if (name.trim().equals("")) {
            map.put("code", 1);
            map.put("msg", "用户名不能为空");
            return map;
        } else if (password.trim().equals("")) {
            map.put("code", 1);
            map.put("msg", "密码不能为空");
            return map;
        }
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken Usertoken = new UsernamePasswordToken(name, password);
        try {
            subject.login(Usertoken);
            Integer id = userMapper.getIdByName(name);
            String accessToken = JWTUtil.createAccessToken(id);
            String refreshToken = JWTUtil.createRefreshToken(id);
            map.put("accessToken", accessToken);
            map.put("refreshToken", refreshToken);
            redisService.set(USER_TOKEN_PREFIX + id, refreshToken, TOKEN_EXPIRE_TIME);

            map.put("code", "0");
            return map;
        } catch (UnknownAccountException e) {
            map.put("code", "1");

            map.put("msg", "用户不存在");
            return map;
        } catch (IncorrectCredentialsException e) {
            map.put("code", "1");

            map.put("msg", "用户名或密码错误");
            return map;
        } catch (Exception e) {
            map.put("code", "1");
            map.put("msg", "服务器错误");
            e.printStackTrace();
            return map;
        }

    }

    @Override
    public Map<String, Object> register(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String token = request.getParameter("token");
        String vCode = request.getParameter("vCode");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String sex = request.getParameter("sex");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        User user = new User();
        if (token == null) {
            map.put("code", "1");

            map.put("msg", "token不能为空");
            return map;
        }

        Object RealvCode = redisService.get(token);
        if (RealvCode == null) {
            map.put("code", "1");

            map.put("msg", "验证码已过期");
            return map;
        }

        if (vCode == null || vCode.trim().equals("")) {
            map.put("code", "1");

            map.put("msg", "验证码不能为空");
            return map;
        } else if (!RealvCode.toString().toUpperCase().equals(vCode.trim().toUpperCase())) {
            map.put("code", "1");

            map.put("msg", "验证码错误");
            return map;
        } else if (userName == null || userName.trim().equals("")) {
            map.put("code", "1");

            map.put("msg", "用户名不能为空");
            return map;
        } else if (password == null || userName.trim().equals("")) {
            map.put("code", "1");

            map.put("msg", "密码不能为空");
            return map;
        } else if (userName.length() < 6 || userName.length() > 16) {
            map.put("code", "1");

            map.put("msg", "用户名长度小于6或者大于16");
            return map;

        }
        if (userMapper.isExistUser(userName) == 1) {
            map.put("code", "1");
            map.put("msg", "用户名已存在");
            return map;

        }
        user.setUserName(userName);
        String solt = BCrypt.gensalt();
//        加密
        String psd = BCrypt.hashpw(password, solt);
        user.setPassword(psd);
        user.setName(name);
        user.setSex(Integer.parseInt(sex));
        user.setDate(getNowDate());
        user.setPhone(phone);
        user.setEmail(email);
        user.setSolt(solt);
//        注册为普通用户
        user.setPerms("user");
        if (userMapper.addUser(user)) {
            map.put("code", 0);
        } else {
            map.put("code", 1);
            map.put("msg", "注册失败");
        }
        return map;
    }

    private String getNowDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        return sdf.format(date);
    }

    @Override
    public Map<String, Object> findAllUsers(String accessToken, Integer pageNum, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        Integer userId = getId(accessToken);
        User admin = userMapper.findById(userId);
        if (!admin.getPerms().equals("admin")) {
            map.put("code", 1);
            map.put("msg", "没有权限");
            return map;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userMapper.findAllUsers();
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        map.put("users", pageInfo);
        map.put("code", "0");
        return map;
    }

    @Override
    public Map<String, Object> deleteUser(String accessToken, Integer id) {

        Map<String, Object> map = new HashMap<>();
        Integer id1 = getId(accessToken);
        String perms1 = userMapper.findPermsById(id1);
        if (perms1 == null || !perms1.equals("admin")) {
            map.put("code", 1);
            map.put("msg", "没有权限");
            return map;
        }
        String perms2 = userMapper.findPermsById(id);
        if (perms2.equals("admin")) {
            map.put("code", 1);
            map.put("msg", "无法删除管理员");
        }

        if (userMapper.deleteUserById(id)) {
            map.put("code", "0");
        } else {
            map.put("code", 1);
            map.put("msg", "删除失败");
        }
        return map;
    }

    @Override
    public Map<String, Object> updateUserInfo(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String token = request.getParameter("accessToken");
        Integer id = getId(token);
        User user = userMapper.findById(id);
        if (user == null) {
            map.put("code", 1);
            map.put("msg", "没有该用户");
            return map;
        }
        String name = request.getParameter("name");
        String sex = request.getParameter("sex");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");

        user.setName(name);
        user.setEmail(email);
        user.setSex(Integer.parseInt(sex));
        user.setPhone(phone);
        userMapper.updateUser(user);
        map.put("code", "0");
        return map;
    }

    @Override
    public Map<String, Object> authorizeToUser(String accessToken, Integer id, String perms) {
        Map<String, Object> map = new HashMap<>();

        Integer userId = getId(accessToken);
        String perms3 = userMapper.findPermsById(userId);
        if (!perms3.equals("admin")) {
            map.put("code", 1);
            map.put("msg", "没有权限");
            return map;
        }
        String perms1 = userMapper.findPermsById(id);
        if (perms1 == null) {
            map.put("code", 1);
            map.put("msg", "没有该用户");
            return map;
        } else if (perms1.equals("admin")) {
            map.put("code", 1);
            map.put("msg", "无法修改管理员权限");
            return map;
        }
        userMapper.authorizeToUser(id, perms);
        map.put("code", "0");
        return map;
    }

    @Override
    public Map<String, Object> findById(Integer id) {
        Map<String, Object> map = new HashMap<>();

        User user = userMapper.findById(id);
        if (user == null) {
            map.put("code", 1);
            map.put("msg", "找不到该用户");
            return map;
        }
        user.setSolt(null);
        user.setPassword(null);
        map.put("code", "0");
        map.put("user", user);
        return map;
    }

    @Override
    public Map<String, Object> confirmPassword(String accessToken, String password) {
        Map<String, Object> map = new HashMap<>();
        Integer id = getId(accessToken);
        String solt = userMapper.getSoltById(id);
//        加密
        String psd = BCrypt.hashpw(password, solt);
//        System.out.println(psd);
        String uuid = UUID.randomUUID().toString();
        redisService.set(id + "", uuid, TOKEN_EXPIRE_TIME);
        if (userMapper.confirmPassword(id, psd)) {
            map.put("code", 0);
            map.put("uuid", uuid);
        } else {
            map.put("code", 1);
        }
        return map;
    }

    @Override
    public Map<String, Object> updatePwd(String accessToken, String uuid, String password) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        String solt = BCrypt.gensalt();
//        加密
        String psd = BCrypt.hashpw(password, solt);
        Integer id = getId(accessToken);
        Object o = redisService.get(id + "");
        if (o.toString().equals(uuid)) {
            map.put("code", 0);
            userMapper.updatePwd(id, solt, psd);
            return map;
        }
        return map;


    }


    public static  Integer getId(String AccessToken) {
        Map<String, Claim> claimMap = JWTUtil.verifyAccessToken(AccessToken);
        String id = claimMap.get("userId").asString();
        Integer userId = Integer.parseInt(id);
        return userId;
    }
}

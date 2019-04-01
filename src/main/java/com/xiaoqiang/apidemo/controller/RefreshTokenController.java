package com.xiaoqiang.apidemo.controller;

import com.auth0.jwt.interfaces.Claim;
import com.xiaoqiang.apidemo.service.RedisService;
import com.xiaoqiang.apidemo.service.UserServiceImpl;
import com.xiaoqiang.apidemo.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaoqiang
 * @date 2019/3/30-15:56
 */
@Controller
public class RefreshTokenController {
    @Autowired
    private RedisService redisService;
    @ResponseBody
    @RequestMapping(value = "/guest/refreshToken",method = RequestMethod.GET)
    public Map<String, Object> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Claim> claimMap = JWTUtil.verifyRefreshToken(refreshToken);

        int userId = Integer.parseInt(claimMap.get("userId").asString());
        String redisKey = UserServiceImpl.USER_TOKEN_PREFIX + userId;
        if (redisService.exist(redisKey) && redisService.get(redisKey).equals(refreshToken)) {
            String newAccessToken = JWTUtil.createAccessToken(userId);
            String newRefreshToken = JWTUtil.createRefreshToken(userId);
            redisService.set("user:token:" + userId, newRefreshToken, UserServiceImpl.TOKEN_EXPIRE_TIME);
            map.put("code", "0");
            map.put("accessToken", newAccessToken);
            map.put("refreshToken", newRefreshToken);

        } else {
            map.put("code","1");
        }
        return map;
    }
}

package com.xiaoqiang.apidemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author xiaoqiang
 * @date 2019/3/29-9:31
 */
@Service
public class CaptchaService {
//    设置验证码有效时间为 3分钟
    public static final long CAPTCHA_EXPIRE_TIME = 3 * 60 * 1000;
    @Autowired
    RedisService redisService;

    public Map<String, Object> creatToken(String text) {
        Map<String, Object> map = new HashMap<>();
        String uuid = UUID.randomUUID().toString();
        redisService.set(uuid, text, CAPTCHA_EXPIRE_TIME);
        map.put("token", uuid);
        return map;
    }
}

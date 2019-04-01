package com.xiaoqiang.apidemo.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.xiaoqiang.apidemo.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaoqiang
 * @date 2019/3/29-9:13
 */
@Controller
public class CaptchaController {

    @Autowired
    private DefaultKaptcha producer;
    @Autowired
    CaptchaService captchaService;

    @ResponseBody
    @RequestMapping(value = "/guest/captcha", method = RequestMethod.GET)
    public Map<String, Object> captcha(HttpServletResponse response) {
//        生成文字验证码

        String text = producer.createText();
//        生成图片验证码
        ByteArrayOutputStream outputStream = null;
        BufferedImage image = producer.createImage(text);

        outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", outputStream);
        } catch (IOException e) {
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("msg", "图片有误");
            m.put("code", 1);
            return m;

        }
//        对字节数组Base64编码
        Map<String, Object> map = captchaService.creatToken(text);
        map.put("img", Base64Utils.encodeToString(outputStream.toByteArray()));
        map.put("code", 0);
        return map;
    }
}

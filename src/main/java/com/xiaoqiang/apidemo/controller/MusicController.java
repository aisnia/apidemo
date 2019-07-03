package com.xiaoqiang.apidemo.controller;

import com.xiaoqiang.apidemo.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author xiaoqiang
 * @date 2019/6/28-11:24
 */
@Controller
public class MusicController {
    @Autowired
    private MusicService musicService;
//    上传音乐
    @ResponseBody
    @RequestMapping(value = "/user/upload", method = RequestMethod.POST)
    public Map<String, Object> upload(@RequestParam("accessToken") String accessToken,@RequestParam("music") String mName,@RequestParam("singer") String singerName,@RequestParam("album") String album,@RequestParam("file") MultipartFile file) {
        return musicService.upload(accessToken, mName, singerName, album, file);
    }

    //  获取热门的音乐
    @ResponseBody
    @RequestMapping(value = "/user/getHotMusic", method = RequestMethod.GET)
    public Map<String, Object> getHotMusic(@RequestParam("accessToken") String accessToken, @RequestParam("page") Integer page) {
        return musicService.getHotMusic(accessToken,page);
    }

    //  获取热门的音乐
    @ResponseBody
    @RequestMapping(value = "/user/getLoveMusic", method = RequestMethod.GET)
    public Map<String, Object> getLoveMusic(@RequestParam("accessToken") String accessToken, @RequestParam("page") Integer page) {
        return musicService.getLoveMusic(accessToken,page);
    }

    // 根据名字搜索音乐
    @ResponseBody
    @RequestMapping(value = "/user/findMusicByName", method = RequestMethod.GET)
    public Map<String, Object> findMusicByName(@RequestParam("accessToken") String accessToken,  @RequestParam("page") Integer page,@RequestParam("musicName") String musicName) {
        return musicService.findMusicByName(accessToken,page,musicName);
    }


    //  设置我喜欢
    @ResponseBody
    @RequestMapping(value = "/user/setMylove", method = RequestMethod.GET)
    public Map<String, Object> setMylove(@RequestParam("accessToken") String accessToken, @RequestParam("mId") Integer mId) {
        return musicService.setMylove(accessToken, mId);
    }

    //  取消我喜欢
    @ResponseBody
    @RequestMapping(value = "/user/removeMylove", method = RequestMethod.GET)
    public Map<String, Object> removeMylove(@RequestParam("accessToken") String accessToken, @RequestParam("mId") Integer mId) {
        return musicService.removeMylove(accessToken, mId);
    }
}

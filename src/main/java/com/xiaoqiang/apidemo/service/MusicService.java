package com.xiaoqiang.apidemo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaoqiang
 * @date 2019/6/28-11:39
 */
public interface MusicService {
     Map<String, Object> upload(String accessToken, String mName, String singerName, String album, MultipartFile file);

    Map<String, Object> getHotMusic(String accessToken,Integer page);

    Map<String, Object> setMylove(String accessToken, Integer mId);

    Map<String, Object> removeMylove(String accessToken, Integer mId);

    Map<String, Object> getLoveMusic(String accessToken, Integer page);

    Map<String, Object> findMusicByName(String accessToken,Integer page, String musicName);
}

package com.xiaoqiang.apidemo.service;

import com.xiaoqiang.apidemo.bean.Music;
import com.xiaoqiang.apidemo.bean.MusicBean;
import com.xiaoqiang.apidemo.bean.Singer;
import com.xiaoqiang.apidemo.dao.LoveMapper;
import com.xiaoqiang.apidemo.dao.MusicMapper;
import com.xiaoqiang.apidemo.dao.SingerMapper;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * @author xiaoqiang
 * @date 2019/6/28-11:39
 */
@Service
public class MusicServiceImpl implements MusicService {
    @Autowired
    private MusicMapper musicMapper;

    @Autowired
    private SingerMapper singerMapper;

    @Autowired
    private LoveMapper loveMapper;
    //    音乐文件存储路径
    public static final String MUSCI_PATH = "C:/Users/Dell/Desktop/static/";//"D:/Program Files/nginx-1.8.1/static/";;
    //    每页的音乐数
    public static final int PAGE_SIZE = 7;

    @Transactional
    @Override
    public Map<String, Object> upload(String accessToken, String mName, String singerName, String album, MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        Integer id = UserServiceImpl.getId(accessToken);
        map.put("code", 1);
        if (mName == null || mName.equals("")) {
            map.put("msg", "歌曲名不能为空");
            return map;
        } else if (singerName == null || singerName.equals("")) {
            map.put("msg", "歌手名不能为空");
            return map;
        } else if (album == null || album.equals("")) {
            map.put("msg", "专辑名不能为空");
            return map;
        } else if (file.isEmpty() || !file.getOriginalFilename().endsWith(".mp3")) {
            map.put("msg", "文件错误");
            return map;
        }
        try {
            Music music = new Music();
            dealFile(file,music);
            Singer singer = new Singer();
            singer.setSingerName(singerName);
            boolean flag = singerMapper.addSinger(singer);
            System.out.println(flag);
            System.out.println(music);

            if (!flag) {
                singer.setsId(singerMapper.findSIdBySName(singerName));
            }
            System.out.println(music);

            music.setAlbum(album);
            music.setmName(mName);
            music.setsId(singer.getsId());
            music.setUploadTime(getNowDate());
            music.setuId(id);
            musicMapper.addMusic(music);
            map.put("code", 0);
        } catch (Exception e) {
            map.put("msg", "文件错误");
        }

        return map;
    }

    @Transactional
    @Override
    public Map<String, Object> getHotMusic(String accessToken,Integer page) {
        Map<String, Object> map = new HashMap<>();
        Integer uId = UserServiceImpl.getId(accessToken);
//        System.out.println(page);
        try {
            int pageNum = musicMapper.getPageNum();
            int t = page;
            t = t <= 0 ? 0 : t;
            int n =t* PAGE_SIZE;
            if(n>pageNum){
                n = pageNum / PAGE_SIZE;
            }
            List<MusicBean> list = musicMapper.getHotMusic(n, PAGE_SIZE);
            List<Integer> loveMId = loveMapper.findMyLove(uId);

            for (MusicBean musicBean : list) {
                for (Integer integer : loveMId) {
                    if (musicBean.getmId() == integer) {
                        musicBean.setIsLove(1);
                    }
                }
            }


            map.put("code", 0);
            map.put("list", list);
            map.put("pageNum", pageNum);
        } catch (Exception e) {
            map.put("code", 1);
        }finally {
            return map;
        }

    }

    @Transactional
    @Override
    public Map<String, Object> setMylove(String accessToken, Integer mId) {
        Map<String, Object> map = new HashMap<>();
        Integer uId = UserServiceImpl.getId(accessToken);
        if (loveMapper.setMylove(uId, mId)) {
            map.put("code", 0);
        } else {
            map.put("code", 1);
        }
        return map;
    }

    @Transactional
    @Override
    public Map<String, Object> removeMylove(String accessToken, Integer mId) {
        Map<String, Object> map = new HashMap<>();
        Integer uId = UserServiceImpl.getId(accessToken);
        if (loveMapper.removeMylove(uId, mId)) {
            map.put("code", 0);
        } else {
            map.put("code", 1);
        }
        return map;
    }

    @Transactional
    @Override
    public Map<String, Object> getLoveMusic(String accessToken, Integer page) {
        Map<String, Object> map = new HashMap<>();
        Integer id = UserServiceImpl.getId(accessToken);
//        System.out.println(id);
        try {
            int pageNum = musicMapper.getLovePageNum(id);
            int t = page;
            t = t <= 0 ? 0 : t;
            int n =t* PAGE_SIZE;
            if(n>pageNum){
                n = pageNum / PAGE_SIZE;
            }
            List<MusicBean> list = musicMapper.getLoveMusic(n, PAGE_SIZE,id);
            for (MusicBean bean : list) {
                bean.setIsLove(1);
            }

            map.put("code", 0);
            map.put("list", list);
            map.put("pageNum", pageNum);
        } catch (Exception e) {
            map.put("code", 1);
        }finally {
            return map;
        }


    }

    @Override
    public Map<String, Object> findMusicByName(String accessToken,Integer page, String musicName) {
        Map<String, Object> map = new HashMap<>();
        Integer id = UserServiceImpl.getId(accessToken);
        System.out.println(id);
        musicName="%"+musicName+"%";
        try {
            int pageNum = musicMapper.getMusicNumByName(musicName);
            System.out.println(pageNum);
            int t = page;
            t = t <= 0 ? 0 : t;
            int n =t* PAGE_SIZE;
            if(n>pageNum){
                n = pageNum / PAGE_SIZE;
            }
            System.out.println(n);
            List<MusicBean> list = musicMapper.getMusicByName(n, PAGE_SIZE,musicName);
            System.out.println(list);
            List<Integer> loveMId = loveMapper.findMyLove(id);
            System.out.println(loveMId);
            for (MusicBean musicBean : list) {
                for (Integer integer : loveMId) {
                    if (musicBean.getmId() == integer) {
                        musicBean.setIsLove(1);
                    }
                }
            }

            map.put("code", 0);
            map.put("list", list);
            map.put("pageNum", pageNum);
        } catch (Exception e) {
            map.put("code", 1);
        }finally {
            return map;
        }

    }

    private void dealFile(MultipartFile file,Music music) {
        BufferedOutputStream bos = null;
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                //        uuid给图片命名
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//                获取第一个字符 创建文件夹
                String substring = uuid.substring(0, 1);
                String p="music/"+substring;
                String mkdirString = MUSCI_PATH +p;
                File mkdir = new File(mkdirString);
//        目录不存在  就创建
                if (!mkdir.exists()) {
                    mkdir.mkdirs();
                }
//                MP3文件
                String path = mkdirString +"/" + uuid + ".mp3";
                music.setmPath(p + "/" + uuid + ".mp3");
                File musicFile = new File(path);
                 bos = new BufferedOutputStream(new FileOutputStream(musicFile));
                bos.write(bytes);
                bos.flush();
                music.setTime(getMp3TrackLength(musicFile));
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }



    private String getNowDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        return sdf.format(date);
    }

    public static String getMp3TrackLength(File mp3File) {
        try {
            MP3File f = (MP3File) AudioFileIO.read(mp3File);
            MP3AudioHeader audioHeader = (MP3AudioHeader)f.getAudioHeader();
           return audioHeader.getTrackLengthAsString();
        } catch(Exception e) {
            System.out.println("计算MP3的时长错误");
            return null;
        }
    }
}

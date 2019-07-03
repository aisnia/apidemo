package com.xiaoqiang.apidemo.dao;

import com.xiaoqiang.apidemo.bean.Music;
import com.xiaoqiang.apidemo.bean.MusicBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author xiaoqiang
 * @date 2019/6/28-11:40
 */
@Mapper
public interface MusicMapper {

    @Insert("insert into music(sId,album,uploadTime,mPath,mName,uId,time) " +
            "values(#{sId},#{album},#{uploadTime},#{mPath},#{mName},#{uId},#{time})")
    boolean addMusic(Music music);

    @Select("select m.mId,m.mName,m.album,m.mPath,m.time,s.singerName from music m join singer s on m.sId=s.sId " +
            "limit #{page},#{pageSize}")
    List<MusicBean> getHotMusic(Integer page, int pageSize);

    @Select("select count(*) from music")
    int getPageNum();

    @Select("select m.mId,m.mName,m.album,m.mPath,m.time,s.singerName \n" +
            "from music m \n" +
            "join singer s on m.sId=s.sId\n" +
            "join love l on l.uId=#{id} and l.mId=m.mId\n" +
            "limit #{page},#{pageSize}")
    List<MusicBean> getLoveMusic(int page, int pageSize, Integer id);

    @Select("select count(*) from music m join love l on l.uId=#{id} and l.mId=m.mId")
    int getLovePageNum(Integer id);

    @Select("select count(*) from music where mName like #{musicName} ")
    int getMusicNumByName(String musicName);


    @Select("select m.mId,m.mName,m.album,m.mPath,m.time,s.singerName \n" +
            "from music m \n" +
            "join singer s on m.sId=s.sId \n" +
            "where m.mName like #{musicName} or s.singerName like #{musicName} \n" +
            "limit #{n},#{pageSize}")
    List<MusicBean> getMusicByName(int n, int pageSize, String musicName);
}

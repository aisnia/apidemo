package com.xiaoqiang.apidemo.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author xiaoqiang
 * @date 2019/6/29-14:17
 */
@Mapper
public interface LoveMapper {

    @Insert("insert into love(uId,mId) values(#{uId},#{mId})")
    boolean setMylove(Integer uId, Integer mId);

    @Select("select mId from love where uId=#{uId}")
    List<Integer> findMyLove(Integer uId);

    @Delete("delete from love where  uId=#{uId} and mId=#{mId}")
    boolean removeMylove(Integer uId, Integer mId);
}

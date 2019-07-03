package com.xiaoqiang.apidemo.dao;

import com.xiaoqiang.apidemo.bean.Singer;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

/**
 * @author xiaoqiang
 * @date 2019/6/28-14:42
 */
@Mapper
public interface SingerMapper {

    @Options(useGeneratedKeys = true, keyProperty = "sId", keyColumn = "sId")
    @Insert("insert ignore into singer(singerName) values(#{singerName})")
    boolean addSinger(Singer singer);

    @Select("select sId from singer where singerName=#{singerName}")
    int findSIdBySName(String singerName);
}

package com.xiaoqiang.apidemo.dao;

import com.xiaoqiang.apidemo.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author xiaoqiang
 * @date 2019/3/28-21:19
 */
@Mapper
public interface UserMapper {
    @Select("select * from user where userName = #{userName}")
    public User findByName(String userName);

    @Select("select * from user where id = #{id}")
    public User findById(Integer id);

    @Select("select id from user where userName=#{userName}")
    Integer getIdByName(String userName);

    @Select("select count(1) from user where userName=#{userName}")
    Integer isExistUser(String userName);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert into user(userName,name,password,solt,perms,sex,date,phone,email) values(#{userName},#{name},#{password},#{solt},#{perms},#{sex},#{date},#{phone},#{email})")
    boolean addUser(User user);

    @Select("select id,sex,name,perms,phone,email from user")
    List<User> findAllUsers();

    @Delete("delete from user where id=#{id}")
    boolean deleteUserById(Integer id);

    @Update("update user set name=#{name},sex=#{sex},phone=#{phone},email=#{email} where id=#{id}")
    boolean updateUser(User user);

    @Select("select perms from user where id=#{id}")
    String findPermsById(Integer id);

    @Update("update user set perms=#{perms} where id=#{id}")
    boolean authorizeToUser(Integer id, String perms);

    @Select("select count(1) from user where id=#{id} and password=#{password}")
    boolean confirmPassword(Integer id, String password);

    @Select("select solt from user where id=#{id}")
    String getSoltById(Integer id);

    @Update("update user set solt=#{solt},password=#{psd} where id=#{id}")
    boolean updatePwd(Integer id, String solt, String psd);
}

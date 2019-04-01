package com.xiaoqiang.apidemo.bean;

import java.io.Serializable;

/**
 * @功能描述：用户类
 * @创建日期: 2019/2/22 20:21
 */
public class User implements Serializable {
    //    用户id
    private Integer id;
    //    用户的账号
    private String userName;
    //    用户名称
    private String name;
    //    用户密码
    private String password;
    //    盐
    private String solt;
//    用户权限 角色类型
    private String perms;
    //    性别   0  男   1 女
    private Integer sex;

    //    生日
    private String birthday;
    //    电话
    private String phone;
    //    邮箱
    private String email;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", solt='" + solt + '\'' +
                ", perms='" + perms + '\'' +
                ", sex=" + sex +
                ", birthday='" + birthday + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSolt() {
        return solt;
    }

    public void setSolt(String solt) {
        this.solt = solt;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User() {
    }

    public User(Integer id, String userName, String name, String password, String solt, String perms, Integer sex, String birthday, String phone, String email) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.password = password;
        this.solt = solt;
        this.perms = perms;
        this.sex = sex;
        this.birthday = birthday;
        this.phone = phone;
        this.email = email;
    }
}

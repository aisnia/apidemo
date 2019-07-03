package com.xiaoqiang.apidemo.bean;

/**
 * @author xiaoqiang
 * @date 2019/6/28-11:27
 */
public class Singer {
    private int sId;
    private String singerName;
    private String info;


    public Singer() {
    }

    @Override
    public String toString() {
        return "Singer{" +
                "sId=" + sId +
                ", singerName='" + singerName + '\'' +
                ", info='" + info + '\'' +
                '}';
    }

    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Singer(int sId, String singerName, String info) {
        this.sId = sId;
        this.singerName = singerName;
        this.info = info;
    }
}

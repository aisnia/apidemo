package com.xiaoqiang.apidemo.bean;

/**
 * @author xiaoqiang
 * @date 2019/6/28-11:24
 */
public class Music {
    //    歌曲id
    private int mId;
    //    歌曲名
    private String mName;
    //    歌手id
    private int sId;
    //专辑名称
    private String album;
    //    上传时间
    private String time;
    //    歌曲路径
    private String mPath;
    //上传用户的id
    private int uId;

    //    歌曲时长
    private String uploadTime;

    @Override
    public String toString() {
        return "Music{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", sId=" + sId +
                ", album='" + album + '\'' +
                ", time='" + time + '\'' +
                ", mPath='" + mPath + '\'' +
                ", uId=" + uId +
                ", uploadTime='" + uploadTime + '\'' +
                '}';
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Music(int mId, String mName, int sId, String album, String time, String mPath, int uId, String uploadTime) {
        this.mId = mId;
        this.mName = mName;
        this.sId = sId;
        this.album = album;
        this.time = time;
        this.mPath = mPath;
        this.uId = uId;
        this.uploadTime = uploadTime;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public Music() {
    }


    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getmPath() {
        return mPath;
    }

    public void setmPath(String mPath) {
        this.mPath = mPath;
    }


}

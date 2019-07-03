package com.xiaoqiang.apidemo.bean;

/**
 * @author xiaoqiang
 * @date 2019/6/29-11:13
 */
public class MusicBean {
    //    歌曲id
    private int mId;
    //    歌曲名
    private String mName;
    //专辑名称
    private String album;
    //    歌曲路径
    private String mPath;
    //    歌曲时长
    private String time;
    //    歌手名
    private String singerName;
    //    islove
    private int isLove;
    public MusicBean() {
    }

    @Override
    public String toString() {
        return "MusicBean{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", album='" + album + '\'' +
                ", mPath='" + mPath + '\'' +
                ", time='" + time + '\'' +
                ", singerName='" + singerName + '\'' +
                ", isLove=" + isLove +
                '}';
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

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getmPath() {
        return mPath;
    }

    public void setmPath(String mPath) {
        this.mPath = mPath;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public int getIsLove() {
        return isLove;
    }

    public void setIsLove(int isLove) {
        this.isLove = isLove;
    }

    public MusicBean(int mId, String mName, String album, String mPath, String time, String singerName, int isLove) {
        this.mId = mId;
        this.mName = mName;
        this.album = album;
        this.mPath = mPath;
        this.time = time;
        this.singerName = singerName;
        this.isLove = isLove;
    }
}

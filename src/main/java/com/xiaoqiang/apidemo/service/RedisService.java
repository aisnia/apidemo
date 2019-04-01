package com.xiaoqiang.apidemo.service;

/**
 * @author xiaoqiang
 * @date 2019/3/28-21:19
 */
public interface RedisService {

    /**
     * 写入redis缓存（不设置expire存活时间）
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, String value);

    /**
     * 写入redis缓存，设置expire存活时间(以秒为单位)
     *
     * @param key
     * @param value
     * @param expire
     * @return
     */
    public boolean set(final String key, String value, Long expire);


    /**
     * 写入redis缓存，设置expire存活时间(以小时为单位)
     *
     * @param key
     * @param value
     * @param expire
     * @return
     */
    public boolean setByHours(final String key, String value, Long expire);

    /**
     * 读取Redis缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key);

    /**
     * 判断redis缓存中是否有对应的key
     *
     * @param key
     * @return
     */
    public boolean exist(final String key);


    /**
     * redis根据key删除对应的value
     *
     * @param key
     * @return
     */
    public boolean remove(final String key);

    /**
     * Redis根据keys批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys);



}

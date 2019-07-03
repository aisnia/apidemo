package com.xiaoqiang.apidemo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author xiaoqiang
 * @date 2019/3/28-21:34
 */
@Service
public class RedisServiceImpl implements RedisService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean set(String key, String value) {
        boolean result = false;
        try {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set(key, value);
            result = true;
        }catch (Exception e){
            logger.error("写入redis缓存失败！错误信息为: " + e.getMessage());


        }finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
            return result;

        }

    }

    @Override
    public boolean set(String key, String value, Long expire) {
        boolean result = false;
        try{
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.set(key,value);
            redisTemplate.expire(key,expire, TimeUnit.SECONDS);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("写入redis缓存（设置expire存活时间）失败！错误信息为：" + e.getMessage());
        }finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
            return result;

        }
    }

    @Override
    public boolean setByHours(String key, String value, Long expire) {
        boolean result = false;
        try{
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.set(key,value);
            redisTemplate.expire(key,expire, TimeUnit.HOURS);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("写入redis缓存（设置expire存活时间）失败！错误信息为：" + e.getMessage());
        }finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
            return result;

        }

    }

    @Override
    public Object get(String key) {
        Object result = null;
        try{
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            result = operations.get(key);
        }catch (Exception e) {
            logger.error("读取redis缓存失败！错误信息为：" + e.getMessage());
        }finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
            return result;

        }

    }

    @Override
    public boolean exist(String key) {
        boolean result = false;
        try{
            result = redisTemplate.hasKey(key);
        }catch (Exception e){
            logger.error("判断redis缓存中是否有对应的key失败！错误信息为：" + e.getMessage());
        }finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
            return result;

        }

    }

    @Override
    public boolean remove(String key) {
        boolean result = false;
        try{
            if(exist(key)){
                redisTemplate.delete(key);
            }
            result = true;
        }catch (Exception e){
            logger.error("redis根据key删除对应的value失败！错误信息为：" + e.getMessage());
        }finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
            return result;

        }

    }

    @Override
    public void remove(String... keys) {
        for(String key : keys){
            remove(key);
        }
    }



    public long incr(final String key,final long delta){
        if(delta < 0){
            throw new RuntimeException("递增因子必须大于0");
        }
        long result= redisTemplate.opsForValue().increment(key,delta);


            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
            return result;


    }
}

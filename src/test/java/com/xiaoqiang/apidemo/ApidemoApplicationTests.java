package com.xiaoqiang.apidemo;

import com.xiaoqiang.apidemo.dao.MusicMapper;
import com.xiaoqiang.apidemo.service.MusicService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ApidemoApplicationTests {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MusicService musicService;
    @Test
    public void contextLoads() {
    }

}

package com.xiaoqiang.apidemo;

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

    @Test
    public void contextLoads() {
        for (int i = 0; i < 10; i++) {
            logger.trace(i + "日志输出");
        }
    }

}

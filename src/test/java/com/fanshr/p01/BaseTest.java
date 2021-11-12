package com.fanshr.p01;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/6 22:13
 * @date : Modified at 2021/11/6 22:13
 */

// spring 整合 junit,junit启动时家子啊springIOC容器
@RunWith(SpringJUnit4ClassRunner.class)
// 告知JUnit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class BaseTest {


}

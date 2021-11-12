package com.fanshr.p01.dao;

import com.fanshr.p01.BaseTest;
import com.fanshr.p01.entity.Area;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/6 22:16
 * @date : Modified at 2021/11/6 22:16
 */
public class AreaDaoTest extends BaseTest {

    @Autowired
    private AreaDao areaDao;

    @Test
    public void testQueryArea(){

        List<Area> areas = areaDao.queryArea();
        Assert.assertEquals(4,areas.size());

    }


}

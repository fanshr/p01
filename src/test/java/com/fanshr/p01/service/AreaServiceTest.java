package com.fanshr.p01.service;

import com.fanshr.p01.BaseTest;
import com.fanshr.p01.entity.Area;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/6 22:38
 * @date : Modified at 2021/11/6 22:38
 */
public class AreaServiceTest extends BaseTest {
    @Autowired
    private AreaService areaService;

    @Test
    public void testQueryList(){

        List<Area> areaList = areaService.getAreaList();
        Assert.assertEquals(4,areaList.size());
    }
}

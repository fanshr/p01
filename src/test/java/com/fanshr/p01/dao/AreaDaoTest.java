package com.fanshr.p01.dao;

import com.fanshr.p01.BaseTest;
import com.fanshr.p01.entity.Area;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
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

    @Test
    public void testInsertArea(){

        Area area = new Area();
        area.setAreaName("临时区域");
        area.setAreaDesc("描述内容--新增测试");
        area.setPriority(33);
        area.setCreateTime(new Date());
        area.setLastEditTime(new Date());
        int effectedRow = areaDao.insertArea(area);
        Assert.assertEquals(1,effectedRow);
    }

    @Test
    public void testUpdateArea(){
        Area area = new Area();
        area.setAreaId(7L);
        area.setAreaName("临时区域");
        area.setAreaDesc("描述内容--修改测试");
        area.setPriority(39);
        area.setCreateTime(new Date());
        area.setLastEditTime(new Date());
        int effectedRow = areaDao.updateArea(area);
        Assert.assertEquals(1,effectedRow);
    }


    @Test
    public void testDeleteArea(){

        int effectedRow = areaDao.deleteArea(7L);
        Assert.assertEquals(1,effectedRow);

    }

    @Test
    public void testBatchDeleteArea(){
        List<Long> idList = new ArrayList<>();
        idList.add(9L);
        idList.add(10L);
        idList.add(11L);
        int effectedRow = areaDao.batchDeleteArea(idList);
        Assert.assertEquals(0,effectedRow);

    }


}

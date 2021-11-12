package com.fanshr.p01.service.impl;

import com.fanshr.p01.dao.AreaDao;
import com.fanshr.p01.entity.Area;
import com.fanshr.p01.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/6 22:33
 * @date : Modified at 2021/11/6 22:33
 */

@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;
    @Override
    public List<Area> getAreaList() {
        List<Area> areaList = null;

        areaList = areaDao.queryArea();


        return areaList;
    }
}

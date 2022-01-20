package com.fanshr.p01.dao;

import com.fanshr.p01.entity.Area;

import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/6 22:18
 * @date : Modified at 2021/11/6 22:18
 */

public interface AreaDao {

    List<Area> queryArea();

    int insertArea(Area area);

    int updateArea(Area area);

    int deleteArea(long areaId);

    int batchDeleteArea(List<Long> areaIdList);
}

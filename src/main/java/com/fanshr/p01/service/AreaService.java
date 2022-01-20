package com.fanshr.p01.service;

import com.fanshr.p01.dto.AreaExecution;
import com.fanshr.p01.entity.Area;

import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/6 22:33
 * @date : Modified at 2021/11/6 22:33
 */
public interface AreaService {
    List<Area> getAreaList();

    AreaExecution addArea(Area area);

    AreaExecution modifyArea(Area area);

    AreaExecution removeArea(Long areaId);

    AreaExecution removeAreaList(List<Long> areaIdList);
}

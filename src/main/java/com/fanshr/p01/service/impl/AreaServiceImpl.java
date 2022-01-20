package com.fanshr.p01.service.impl;

import com.fanshr.p01.dao.AreaDao;
import com.fanshr.p01.dto.AreaExecution;
import com.fanshr.p01.entity.Area;
import com.fanshr.p01.enums.AreaStateEnum;
import com.fanshr.p01.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    @Transactional
    @Override
    public AreaExecution addArea(Area area) {
        if (area.getAreaName() != null && !"".equals(area.getAreaName())) {
            area.setCreateTime(new Date());
            area.setLastEditTime(new Date());

            int effectedRow = areaDao.insertArea(area);
            if (effectedRow >0){
                return new AreaExecution(AreaStateEnum.SUCCESS,area);
            }else{
                return new AreaExecution(AreaStateEnum.INNER_ERROR);
            }
        }else{
            return  new AreaExecution(AreaStateEnum.EMPTY);
        }
    }

    @Transactional
    @Override
    public AreaExecution modifyArea(Area area) {
        if (area.getAreaId() !=null && area.getAreaId() >0){
            area.setLastEditTime(new Date());
            int effectedRow = areaDao.updateArea(area);
            if (effectedRow >0){
                return new AreaExecution(AreaStateEnum.SUCCESS, area);
            }else{
                return new AreaExecution(AreaStateEnum.INNER_ERROR);
            }
        }else{
            return new AreaExecution(AreaStateEnum.EMPTY);
        }
    }

    @Transactional
    @Override
    public AreaExecution removeArea(Long areaId) {
        if (areaId >0){
            int effectedRows = areaDao.deleteArea(areaId);
            if (effectedRows >0){
                return new AreaExecution(AreaStateEnum.SUCCESS);
            }else{
                return new AreaExecution(AreaStateEnum.INNER_ERROR);
            }
        }else{
            return new AreaExecution(AreaStateEnum.EMPTY);
        }

    }

    @Override
    public AreaExecution removeAreaList(List<Long> areaIdList) {

        if (areaIdList != null && areaIdList.size()>0){
            int effectedRows = areaDao.batchDeleteArea(areaIdList);
            if (effectedRows >0){
                return new AreaExecution(AreaStateEnum.SUCCESS);
            }else{
                return new AreaExecution(AreaStateEnum.INNER_ERROR);

            }
        }else {
            return new AreaExecution(AreaStateEnum.EMPTY);
        }

    }
}

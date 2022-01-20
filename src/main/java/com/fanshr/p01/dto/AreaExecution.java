package com.fanshr.p01.dto;

import com.fanshr.p01.entity.Area;
import com.fanshr.p01.enums.AreaStateEnum;
import lombok.Data;

import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/15 16:22
 * @date : Modified at 2021/11/15 16:22
 */
@Data
public class AreaExecution {
    private int state;
    private String stateInfo;
    private Area area;
    private List<Area> areaList;
    private int count;

    private AreaExecution() {
    }

    public AreaExecution(AreaStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    public AreaExecution(AreaStateEnum stateEnum, Area area) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.area = area;
    }

    public AreaExecution(AreaStateEnum stateEnum, List<Area> areaList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.areaList = areaList;
        this.count = areaList.size();
    }
}

package com.fanshr.p01.service.impl;

import com.fanshr.p01.dao.AwardDao;
import com.fanshr.p01.dto.AwardExecution;
import com.fanshr.p01.entity.Award;
import com.fanshr.p01.enums.AwardStateEnum;
import com.fanshr.p01.service.AwardService;
import com.fanshr.p01.util.FileUtil;
import com.fanshr.p01.util.ImageUtil;
import com.fanshr.p01.util.PageCalculator;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Date;
import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/23 10:25
 * @date : Modified at 2021/11/23 10:25
 */
@Service
public class AwardServiceImpl implements AwardService {
    @Autowired
    private AwardDao awardDao;
    @Override
    public AwardExecution getAwardList(Award awardCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Award> awardList = awardDao.queryAwardList(awardCondition, rowIndex, pageSize);
        int count = awardDao.queryAwardCount(awardCondition);
        AwardExecution ae = new AwardExecution(AwardStateEnum.SUCCESS);
        ae.setAwardList(awardList);
        ae.setCount(count);
        return ae;
    }

    @Override
    public Award getAwardById(long awardId) {
        return awardDao.queryAwardByAwardId(awardId);
    }

    @Override
    public AwardExecution addAward(Award award, CommonsMultipartFile thumbnail) {
        if (award != null && award.getShopId() != null) {
            award.setCreateTime(new Date());
            award.setLastEditTime(new Date());
            award.setEnableStatus(1);
            if (thumbnail != null) {
                addThumbnail(award, thumbnail);
            }

            try {
                int effectedRows = awardDao.insertAward(award);
                if (effectedRows <= 0) {
                    throw new RuntimeException("创建奖品失败");
                }
            } catch (RuntimeException e) {
                throw new RuntimeException("创建奖品失败：" + e.toString());
            }
            return new AwardExecution(AwardStateEnum.SUCCESS, award);
        }else {
            return new AwardExecution(AwardStateEnum.EMPTY);
        }
    }

    private void addThumbnail(Award award, CommonsMultipartFile thumbnail) {
        String dest = FileUtil.getShopImagePath(award.getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        award.setAwardImg(thumbnailAddr);
    }

    @Override
    public AwardExecution modifyAward(Award award, CommonsMultipartFile thumbnail) {
        if (award != null && award.getShopId() != null) {
            award.setLastEditTime(new Date());
            if (thumbnail != null) {
                Award tempAward = awardDao.queryAwardByAwardId(award.getAwardId());
                if (tempAward.getAwardImg() != null) {
                    FileUtil.deleteFile(tempAward.getAwardImg());
                }
                addThumbnail(award,thumbnail);
            }

            try {
                int effectedRows = awardDao.updateAward(award);
                if (effectedRows <= 0) {
                    throw new RuntimeException("更新奖品信息失败");
                }
                return new AwardExecution(AwardStateEnum.SUCCESS, award);
            } catch (RuntimeException e) {
                throw new RuntimeException("更新奖品信息失败：" + e.toString());
            }
        }else {
            return new AwardExecution(AwardStateEnum.EMPTY);
        }
    }
}

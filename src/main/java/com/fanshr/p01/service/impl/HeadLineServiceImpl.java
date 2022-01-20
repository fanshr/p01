package com.fanshr.p01.service.impl;

import com.fanshr.p01.dao.HeadLineDao;
import com.fanshr.p01.dto.HeadLineExecution;
import com.fanshr.p01.entity.HeadLine;
import com.fanshr.p01.enums.HeadLineStateEnum;
import com.fanshr.p01.service.HeadLineService;
import com.fanshr.p01.util.FileUtil;
import com.fanshr.p01.util.ImageUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
public class HeadLineServiceImpl implements HeadLineService {

    @Autowired
    private HeadLineDao headLineDao;




    @Override
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) {

        List<HeadLine> headLines = null;
        if (headLineCondition != null) {
          headLines = headLineDao.queryHeadLine(headLineCondition);
        }
        return headLines;
    }

    @Override
    @Transactional
    public HeadLineExecution addHeadLine(HeadLine headLine, CommonsMultipartFile thumbnail) {

        if (headLine != null) {

            headLine.setCreateTime(new Date());
            headLine.setLastEditTime(new Date());

            if (thumbnail != null){
                addThumbnail(headLine,thumbnail);
            }

            try {
                int effectedRows = headLineDao.insertHeadLine(headLine);

                if (effectedRows>0){
                    return new HeadLineExecution(HeadLineStateEnum.SUCCESS, headLine);
                }else{
                    return new HeadLineExecution(HeadLineStateEnum.INNER_ERROR);
                }
            } catch (Exception e) {
                throw new RuntimeException("添加区域信息失败");
            }

        }else{
            return new HeadLineExecution(HeadLineStateEnum.EMPTY);
        }
    }

    @Override
    @Transactional
    public HeadLineExecution modifyHeadline(HeadLine headLine, CommonsMultipartFile thumbnail) {

        if (headLine != null && headLine.getLineId()!=null && headLine.getLineId()>0) {
            headLine.setLastEditTime(new Date());
            if (thumbnail != null) {
                HeadLine tempHeadLine = headLineDao.queryHeadLineById(headLine.getLineId());
                if (tempHeadLine.getLineImg() != null) {
                    FileUtil.deleteFile(tempHeadLine.getLineImg());
                }
                addThumbnail(headLine,thumbnail);

            }

            try {
                int effectedRows = headLineDao.updateHeadLine(headLine);
                if (effectedRows > 0) {
                    return new HeadLineExecution(HeadLineStateEnum.SUCCESS, headLine);
                }else{

                    return new HeadLineExecution(HeadLineStateEnum.INNER_ERROR);
                }
            } catch (Exception e) {
                throw new RuntimeException("更新头条信息失败：" + e.toString());
            }

        }else{
            return new HeadLineExecution(HeadLineStateEnum.EMPTY);
        }
    }

    @Override
    @Transactional
    public HeadLineExecution removeHeadLine(long headLineId) {
        if (headLineId>0){

            try {
                HeadLine tempHeadLine = headLineDao.queryHeadLineById(headLineId);
                if (tempHeadLine.getLineImg() != null) {
                    FileUtil.deleteFile(tempHeadLine.getLineImg());
                }

                int effectedRows = headLineDao.deleteHeadLine(headLineId);
                if (effectedRows > 0) {
                    return new HeadLineExecution(HeadLineStateEnum.SUCCESS);
                }else{
                    return new HeadLineExecution(HeadLineStateEnum.INNER_ERROR);
                }
            } catch (Exception e) {
                throw new RuntimeException("文件删除失败：" + e.toString());
            }
        }else{
            return new HeadLineExecution(HeadLineStateEnum.EMPTY);
        }
    }

    @Override
    @Transactional
    public HeadLineExecution removeHeadLineList(List<Long> headlineIdList) {
        if (headlineIdList != null && headlineIdList.size() > 0) {
            try {
                List<HeadLine> headLineList = headLineDao.queryHeadLineByIds(headlineIdList);
                for (HeadLine headLine : headLineList) {
                    if (headLine.getLineImg() != null) {
                        FileUtil.deleteFile(headLine.getLineImg());
                    }
                }

                int effectedRows = headLineDao.batchDeleteHeadLine(headlineIdList);
                if (effectedRows > 0) {
                    return new HeadLineExecution(HeadLineStateEnum.SUCCESS);
                }else{
                    return new HeadLineExecution(HeadLineStateEnum.INNER_ERROR);
                }
            } catch (Exception e) {
                throw new RuntimeException("文件删除失败：" + e.toString());
            }


        }else{
            return new HeadLineExecution(HeadLineStateEnum.EMPTY);
        }
    }


    private void addThumbnail(HeadLine headLine, CommonsMultipartFile thumbnail) {
        String dest = FileUtil.getHeadLineImagePath();
        String thumbnailAddr = ImageUtil.generateNomalImg(thumbnail, dest);
        headLine.setLineImg(thumbnailAddr);

    }
}

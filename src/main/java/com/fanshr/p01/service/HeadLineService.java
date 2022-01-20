package com.fanshr.p01.service;

import com.fanshr.p01.dto.HeadLineExecution;
import com.fanshr.p01.entity.HeadLine;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/23 10:19
 * @date : Modified at 2021/11/23 10:19
 */
public interface HeadLineService {

    List<HeadLine> getHeadLineList(HeadLine headLineCondition);

    HeadLineExecution addHeadLine(HeadLine headLine, CommonsMultipartFile thumbnail);

    HeadLineExecution modifyHeadline(HeadLine headLine, CommonsMultipartFile thumbnail);

    HeadLineExecution removeHeadLine(long headLineId);

    HeadLineExecution removeHeadLineList(List<Long> headlineIdList);
}

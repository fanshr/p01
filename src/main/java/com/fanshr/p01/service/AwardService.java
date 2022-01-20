package com.fanshr.p01.service;

import com.fanshr.p01.dto.AwardExecution;
import com.fanshr.p01.entity.Award;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/23 10:19
 * @date : Modified at 2021/11/23 10:19
 */
public interface AwardService {


    /**
     *
     * @param awardCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    AwardExecution getAwardList(Award awardCondition, int pageIndex,
                                int pageSize);

    /**
     *
     * @param awardId
     * @return
     */
    Award getAwardById(long awardId);

    /**
     *
     * @param award
     * @param thumbnail
     * @return
     */
    AwardExecution addAward(Award award, CommonsMultipartFile thumbnail);

    /**
     *
     * @param award
     * @param thumbnail
     * @return
     */
    AwardExecution modifyAward(Award award, CommonsMultipartFile thumbnail);


}

package com.fanshr.p01.service;

import com.fanshr.p01.dto.WechatAuthExecution;
import com.fanshr.p01.entity.WechatAuth;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/23 10:21
 * @date : Modified at 2021/11/23 10:21
 */
public interface WechatAuthService {
    /**
     *
     * @param openId
     * @return
     */
    WechatAuth getWechatAuthByOpenId(String openId);

    /**
     *
     * @param wechatAuth
     * @param profileImg
     * @return
     * @throws RuntimeException
     */
    WechatAuthExecution register(WechatAuth wechatAuth,
                                 CommonsMultipartFile profileImg) throws RuntimeException;
}

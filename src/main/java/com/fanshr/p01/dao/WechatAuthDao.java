package com.fanshr.p01.dao;

import com.fanshr.p01.entity.WechatAuth;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/23 10:24
 * @date : Modified at 2021/11/23 10:24
 */
public interface WechatAuthDao {

    /**
     *
     * @param openId
     * @return
     */
    WechatAuth queryWechatInfoByOpenId(String openId);

    /**
     *
     * @param wechatAuth
     * @return
     */
    int insertWechatAuth(WechatAuth wechatAuth);

    /**
     *
     * @param wechatAuthId
     * @return
     */
    int deleteWechatAuth(Long wechatAuthId);
}

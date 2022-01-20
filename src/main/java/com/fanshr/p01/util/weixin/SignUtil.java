package com.fanshr.p01.util.weixin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/12/29 10:35
 * @date : Modified at 2021/12/29 10:35
 */
public class SignUtil {

    // 与接口信息中的Token保持一致
    private static String token = "p01";
    private static final Logger log = LoggerFactory.getLogger(SignUtil.class);


    /**
     * 验证签名
     * @param signature
     * @param timestamp
     * @return
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] arr = new String[]{token,timestamp,nonce};
        // 按字典顺序排序
        Arrays.sort(arr);

        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);

        }

        MessageDigest md = null;
        String tmpStr = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(content.toString().getBytes(StandardCharsets.UTF_8));
            tmpStr = byteToStr(digest);
            log.debug("临时字符串-->{}",tmpStr);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        content = null;
        return tmpStr !=null ?tmpStr.equals(signature.toUpperCase()):false;

    }

    /**
     * 字节数据转换为十六进制字符串
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest+=byteToHexStr(byteArray[i]);

        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }


}

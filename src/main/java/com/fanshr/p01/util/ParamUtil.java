package com.fanshr.p01.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/12 15:00
 * @date : Modified at 2021/11/12 15:00
 */
public class ParamUtil {

    public static int getInt(HttpServletRequest request,String name){

        try {
            return Integer.decode(request.getParameter(name));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static long getLong(HttpServletRequest request,String name){
        try {
            return Long.valueOf(request.getParameter(name));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static Double getDouble(HttpServletRequest request,String name){
        try {
            return Double.valueOf(request.getParameter(name));
        } catch (NumberFormatException e) {
            return -1d;
        }
    }


    public static Boolean getBoolean(HttpServletRequest request,String name){
        try {
            return Boolean.valueOf(request.getParameter(name));
        } catch (Exception e) {
            return false;
        }
    }

    public static String getString(HttpServletRequest request,String name){
        try {
            String result = request.getParameter(name);
            if (result != null) {
                result= result.trim();
            }

            if ("".equals(result)){
                result = null;
            }

            return  result;
        } catch (Exception e) {
            return null;
        }
    }
}

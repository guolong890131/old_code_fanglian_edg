package com.funi.platform.edg.utils;/**
 * Created by as on 2016/12/12 0012.
 */

import java.util.UUID;

/**
 * @Package: [com.funi.platform.edg.utils]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/12/12 0012 15:12]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2016/12/12 0012 15:12，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */
public class SourceUtils {


    /**
     * 生成32位编码
     * @return string
     */
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }
    /**
     * 处理页面传回 区域参数 返回array
     * @param regList
     * @return
     */
    public static Object dealList(String regList){
        if(null==regList || !regList.contains(",")){
            return null;
        }
        return regList.substring(0,regList.length()-1).split(",");
    }

    /**
     * 处理页面传回 区域参数 返回array
     * @param regList
     * @return
     */
    public static Object dealOneOrList(String regList){
        if(null==regList || !regList.contains(",")){
            return regList;
        }
        return regList.split(",");
    }


    /**
     * 处理中心城区dataSource 都为510100用 返回中文名array
     * @param regList
     * @return
     */
    public static Object dealRegList4Name(String regList){
        if(null==regList || !regList.contains(",") || !regList.contains(";")){
            return null;
        }
        String[] str = regList.split(";");
        if(str.length==1){
            return null;
        }else{
            return dealList(str[1]);
        }
    }

    /**
     * 处理中心城区dataSource 都为510100用 返回 dataSource array
     * @param regList
     * @return
     */
    public static Object dealRegList4DataSource(String regList){
        if(null==regList || !regList.contains(",") || !regList.contains(";")){
            return null;
        }
        String[] str = regList.split(";");
        return dealList(str[0]);
    }

}

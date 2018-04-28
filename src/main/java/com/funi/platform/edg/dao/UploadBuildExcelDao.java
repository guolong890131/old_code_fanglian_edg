package com.funi.platform.edg.dao;


import com.funi.platform.edg.bo.Build;
import com.funi.platform.edg.bo.Field;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/25.
 */
public interface UploadBuildExcelDao {

    void insertErr(Map map);

    int checkBuild(Build build);

    void insertBuildExc(Build build);

    void insertBuildExc_E(Map map);

    List<Build> queryBuildIMP(Map params);

    Long queryBuildIMPTotal(Map params);

    List<Build> queryBuildIMPAll(String uuid);

    List<Build> queryBuildIMPAll_E(String uuid);

    void insertBuild(List<Build> list);

    void insertBuild_E(List<Build> list_e);

    void cleanBuild_E_IMP(String uuid);

    void cleanBuildIMP(String uuid);

    int checkNum(String uuid);

    int checkBuild4Layout_no(String layout_no);
}

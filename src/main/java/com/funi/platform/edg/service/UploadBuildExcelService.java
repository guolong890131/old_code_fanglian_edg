package com.funi.platform.edg.service;

import com.funi.platform.edg.bo.Build;
import com.funi.platform.edg.bo.PageResult;
import com.funi.platform.edg.bo.SearchField;

import java.util.Map;

/**
 * Created by Administrator on 2017/2/25.
 */
public interface UploadBuildExcelService {
    void insertBuildToIMP(Build build) throws Exception;
    void insertBuildErr(Map map);

    PageResult<Build> queryFieldIMP(SearchField searchField, String uuid);

    void transferTable(String uuid);

    void clearAll(String uuid);

    int checkNum(String uuid);
}

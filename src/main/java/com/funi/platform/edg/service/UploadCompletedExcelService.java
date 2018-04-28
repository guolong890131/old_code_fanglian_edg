package com.funi.platform.edg.service;

import com.funi.platform.edg.bo.Completed;
import com.funi.platform.edg.bo.PageResult;
import com.funi.platform.edg.bo.SearchField;

import java.util.Map;

/**
 * Created by Administrator on 2017/2/25.
 */
public interface UploadCompletedExcelService {

    public void insertCompletedToIMP(Completed completed) throws Exception;

    public void insertCompletedErr(Map map) ;

    PageResult<Completed> queryFieldIMP(SearchField searchField, String uuid);

    void transferTable(String uuid);

    void clearAll(String uuid);

    int checkNum(String uuid);
}

package com.funi.platform.edg.dao;

import com.funi.platform.edg.bo.Completed;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/25.
 */
public interface UploadCompletedExcelDao {

    void insertErr(Map map);

    int checkCompleted(Completed completed);

    void insertCompletedExc(Completed completed);

    void insertCompletedExc_E(Map map);

    List<Completed> queryCompletedIMP(Map params);

    Long queryCompletedIMPTotal(Map params);

    List<Completed> queryCompletedIMPAll(String uuid);

    List<Completed> queryCompletedIMPAll_E(String uuid);

    void insertCompleted(List<Completed> list);

    void insertCompleted_E(List<Completed> list_e);

    void cleanCompleted_E_IMP(String uuid);

    void cleanCompletedIMP(String uuid);

    int checkCompleted4Layout_no(String layout_no);

    int checkCompleted4Build_no(String build_no);

    int checkNum(String uuid);
}

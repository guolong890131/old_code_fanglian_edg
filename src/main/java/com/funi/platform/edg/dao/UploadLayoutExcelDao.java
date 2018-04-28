package com.funi.platform.edg.dao;/**
 * Created by as on 2017/2/27 0027.
 */

import com.funi.platform.edg.bo.Layout;

import java.util.List;
import java.util.Map;

/**
 * @Package: [com.funi.platform.edg.dao]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2017/2/27 0027 10:08]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2017/2/27 0027 10:08，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */
public interface UploadLayoutExcelDao {
    void cleanLayout_E_IMP(String uuid);

    void cleanLayoutIMP(String uuid);

    int checkLayout(Layout layout);

    int checkLayout4Fctrno(String fctrno);

    int checkLayout4Ffileno(String ffileno);

    void insertLayoutExc_E(Map map);

    void insertLayoutExc(Layout layout);

    void insertErr(Map map);

    List<Layout> queryLayoutIMP(Map params);

    Long queryLayoutIMPTotal(Map params);

    List<Layout> queryLayoutIMPAll(String uuid);

    List<Layout> queryLayoutIMPAll_E(String uuid);

    void insertLayout(List<Layout> list);

    void insertLayout_E(List<Layout> list_e);

    int checkNum(String uuid);
}

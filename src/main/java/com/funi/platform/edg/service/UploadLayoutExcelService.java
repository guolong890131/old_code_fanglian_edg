package com.funi.platform.edg.service;/**
 * Created by as on 2017/2/27 0027.
 */

import com.funi.platform.edg.bo.Layout;
import com.funi.platform.edg.bo.PageResult;
import com.funi.platform.edg.bo.SearchLayout;

import java.util.Map;

/**
 * @Package: [com.funi.platform.edg.service]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2017/2/27 0027 10:05]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2017/2/27 0027 10:05，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */
public interface UploadLayoutExcelService {
    void cleanAll(String uuid);

    void insertLayoutToIMP(Layout layout) throws Exception;

    void insertLayoutErr(Map map);

    PageResult<Layout> queryLayoutIMP(SearchLayout searchLayout, String uuid);

    void transferTable(String uuid) throws Exception ;

    int checkNum(String uuid);
}

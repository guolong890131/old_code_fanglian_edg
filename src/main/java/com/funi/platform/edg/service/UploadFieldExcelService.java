package com.funi.platform.edg.service;/**
 * Created by as on 2017/2/24 0024.
 */

import com.funi.platform.edg.bo.Field;
import com.funi.platform.edg.bo.FieldVo;
import com.funi.platform.edg.bo.PageResult;
import com.funi.platform.edg.bo.SearchField;

import java.util.Map;

/**
 * @Package: [com.funi.platform.edg.service]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2017/2/24 0024 9:51]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2017/2/24 0024 9:51，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */
public interface UploadFieldExcelService {

    void insertFieldErr(Map map);

    void insertFieldToIMP(FieldVo field) throws Exception;


    PageResult<Field> queryFieldIMP(SearchField searchField, String uuid);

    void cleanAll(String uuid);


    void transferTable(String uuid) throws Exception;

    int checkNum(String uuid);
}

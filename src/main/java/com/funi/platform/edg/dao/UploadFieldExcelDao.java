package com.funi.platform.edg.dao;/**
 * Created by as on 2017/2/24 0024.
 */

import com.funi.platform.edg.bo.Field;
import com.funi.platform.edg.bo.FieldVo;

import java.util.List;
import java.util.Map;

/**
 * @Package: [com.funi.platform.edg.dao]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2017/2/24 0024 9:52]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2017/2/24 0024 9:52，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */
public interface UploadFieldExcelDao {
    
    void insertErr(Map map);

    void insertFieldExc(FieldVo field);

    void insertFieldExc_E(Map map);

    int checkField(Field field);

    List<Field> queryFieldIMP(Map params);

    Long queryFieldIMPTotal(Map params);

    void cleanFieldIMP(String uuid);

    void cleanField_E_IMP(String uuid);

    List<FieldVo> queryFieldIMPAll(String uuid);

    List<Field> queryFieldIMPAll_E(String uuid);


    void insertField(List<FieldVo> list);

    void insertField_E(List<Field> list_e);

    int checkNum(String uuid);
}

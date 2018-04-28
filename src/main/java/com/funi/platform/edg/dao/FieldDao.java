package com.funi.platform.edg.dao;/**
 * Created by as on 2016/12/12 0012.
 */

import com.funi.platform.edg.bo.Field;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Package: [com.funi.platform.edg.dao]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/12/12 0012 11:08]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2016/12/12 0012 11:08，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */
public interface FieldDao {
    List<Field> queryField(Map params);

    Long queryTotal(Map params);

    int deleteField(Map ids);

    int deleteFields(Map<String, Object> map);

    int updateInputFields(Map<String, Object> map);

    int updateInputField(Map<String, Object> map);

    int updateField(Field paramObj);

    int insertField(Field paramObj);

    void insertFieldExtend(Map map);

    void deleteFieldExtend(String id);

    int checkField(String fno_new);

}

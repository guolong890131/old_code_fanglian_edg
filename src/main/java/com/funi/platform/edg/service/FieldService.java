package com.funi.platform.edg.service;/**
 * Created by as on 2016/12/12 0012.
 */

import com.funi.platform.edg.bo.Field;
import com.funi.platform.edg.bo.PageResult;
import com.funi.platform.edg.bo.SearchField;

import java.util.Map;

/**
 * @Package: [com.funi.platform.edg.service]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/12/12 0012 11:06]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2016/12/12 0012 11:06，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */
public interface FieldService {

    PageResult<Field> queryField(SearchField searchField,String uuid);

    int deleteField(Map ids);

    int deleteFields(Map<String, Object> map);

    int updateInputFields(Map<String, Object> map);

    int updateInputField(Map<String, Object> map);

    void updateField(Field paramObj);

    void insertField(Field paramObj);

    int checkField(String fno_new);

}

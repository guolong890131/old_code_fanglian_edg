package com.funi.platform.edg.dao;/**
 * Created by as on 2016/12/26 0026.
 */

import com.funi.platform.edg.bo.SearchLog;
import com.funi.platform.edg.bo.SystemLog;

import java.util.List;
import java.util.Map;

/**
 * @Package: [com.funi.platform.edg.dao]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/12/26 0026 13:34]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2016/12/26 0026 13:34，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */
public interface SystemLogDao {

    int insertLog(SystemLog record);

    List<SystemLog> queryLog(Map<String,String> map);

    Long queryTotal(Map params);
}

package com.funi.platform.edg.service;

import com.funi.platform.edg.bo.Dict;
import com.funi.platform.edg.bo.ParaDate;
import com.funi.platform.edg.query.TimeQuery;

import java.util.List;
import java.util.Map;

/**
 * @Package: [com.funi.platform.edg.service]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/3/22 0022 13:50]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2016/3/22 0022 13:50，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录，增加修改记录)]
 * @Version: [v2.0]
 */

public interface CommonService {

    ParaDate fandTime(TimeQuery timeQuery);

    List<Map> queryRegion();

    List<Dict> findDICT(String str);

    List<Map> findDistinctRole();

    List<Dict> queryRegion4user(String uuid);
}

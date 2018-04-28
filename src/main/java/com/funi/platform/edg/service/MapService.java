package com.funi.platform.edg.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Package: [com.funi.platform.edg.service]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/6/15 0015 13:54]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2016/6/15 0015 13:54，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录，增加修改记录)]
 * @Version: [v2.0]
 */
public interface MapService {
    ArrayList<Map> findRegion(String str);

    List<Map> findCoordinate(Map<String,String> map);

    List<Map> findWin(Map<String, String> paramsMap);

}

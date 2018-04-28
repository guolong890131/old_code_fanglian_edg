package com.funi.platform.edg.service;

import com.funi.platform.edg.bo.Menu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package: [com.funi.platform.edg.service]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/10/10 0010 16:42]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2016/10/10 0010 16:42，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录，增加修改记录)]
 * @Version: [v2.0]
 */
public interface loginService {

    int checkUser(Map<String, String> paramsMap);

    Map<String, String> findRole(Map<String, String> paramsMap);

    List<Menu> findMenu(String role);

    HashMap getUserDate(String uuid);
}

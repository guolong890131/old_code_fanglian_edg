package com.funi.platform.edg.service;/**
 * Created by as on 2016/12/13 0013.
 */

import com.funi.platform.edg.bo.Layout;
import com.funi.platform.edg.bo.PageResult;
import com.funi.platform.edg.bo.SearchLayout;
import com.funi.platform.edg.bo.SearchOne;

import java.util.List;
import java.util.Map;

/**
 * @Package: [com.funi.platform.edg.service]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/12/13 0013 15:26]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2016/12/13 0013 15:26，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */
public interface LayoutService {

    PageResult<Layout> queryLayout(SearchLayout searchLayout,String uuid);

    int updateInputLayouts(Map<String, Object> map);

    int updateInputLayout(Map<String, Object> map);

    int deleteLayouts(Map<String, Object> map);

    int deleteLayout(Map<String, Object> map);

    PageResult<SearchOne> queryBAH(Map<String,String> map);

    void updateLayout(Layout paramObj);

    void insertLayout(Layout paramObj);

    PageResult<SearchOne> queryBDC(Map<String, String> paramsMap);

    int checkLayout(String layout_no);

    int checkField(String fctrno_new);

    int checkFfileno(String ffileno);
}

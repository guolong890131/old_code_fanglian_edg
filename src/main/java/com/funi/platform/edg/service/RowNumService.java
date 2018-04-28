package com.funi.platform.edg.service;/**
 * Created by as on 2017/2/17 0017.
 */


import com.funi.platform.edg.bo.RowNum;

import java.text.ParseException;
import java.util.Map;


/**
 * @Package: [com.funi.platform.edg.service]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2017/2/17 0017 9:16]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2017/2/17 0017 9:16，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */
public interface RowNumService {
    Map queryByRank(String initial, String rankdate) throws ParseException;


}

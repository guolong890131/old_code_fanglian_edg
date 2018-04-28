package com.funi.platform.edg.service.impl;/**
 * Created by as on 2016/12/26 0026.
 */

import com.funi.platform.edg.bo.Field;
import com.funi.platform.edg.bo.PageResult;
import com.funi.platform.edg.bo.SearchLog;
import com.funi.platform.edg.bo.SystemLog;
import com.funi.platform.edg.dao.SystemLogDao;
import com.funi.platform.edg.service.SystemLogService;
import com.funi.platform.edg.utils.SourceUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package: [com.funi.platform.edg.service.impl]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/12/26 0026 11:06]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2016/12/26 0026 11:06，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */
@Service
public class SystemLogServiceImpl implements SystemLogService {

    @Resource
    private SystemLogDao systemLogDao;

    @Override
    public int insert(SystemLog record) {
        return systemLogDao.insertLog(record);
    }

    @Override
    public PageResult<SystemLog> queryLog(SearchLog searchLog) {

        PageResult<SystemLog> pageResult = new PageResult<>();
        Map params = new HashMap();
        if (searchLog.getRegList()!=null && !searchLog.getRegList().equals("全域成都")){
            params.put("data_source", SourceUtils.dealRegList4DataSource(searchLog.getRegList()));
        }
        if (searchLog.getRole()!=null && searchLog.getRole()!=""){
            params.put("role", searchLog.getRole());
        }
        if (searchLog.getName()!=null && searchLog.getName()!=""){
            params.put("name", searchLog.getName());
        }
        if (searchLog.getStartFtr()!=null && searchLog.getStartFtr()!=""){
            params.put("startFtr", searchLog.getStartFtr());
        }
        if (searchLog.getEndFtr()!=null && searchLog.getEndFtr()!=""){
            params.put("endFtr", searchLog.getEndFtr());
        }
        params.put("start",searchLog.getStart());
        params.put("end",searchLog.getEnd());
        List<SystemLog> list =systemLogDao.queryLog(params);
        pageResult.setRows(list);
        Long total = systemLogDao.queryTotal(params);
        pageResult.setTotal(total);
        return pageResult;

    }
}

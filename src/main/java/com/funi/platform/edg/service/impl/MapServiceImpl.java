package com.funi.platform.edg.service.impl;

import com.funi.platform.edg.dao.MapDao;
import com.funi.platform.edg.service.MapService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Package: [com.funi.platform.edg.service.impl]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/6/15 0015 13:55]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2016/6/15 0015 13:55，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录，增加修改记录)]
 * @Version: [v2.0]
 */
@Service
public class MapServiceImpl implements MapService {

    @Resource
    private MapDao mapDao;

    @Override
    public ArrayList<Map> findRegion(String str) {
        return mapDao.findRegion(str);
    }

    @Override
    public List<Map> findCoordinate(Map<String,String> map) {
        return mapDao.findCoordinate(map);
    }

    @Override
    public List<Map> findWin(Map<String, String> paramsMap) {
        return mapDao.findWin(paramsMap);
    }
}

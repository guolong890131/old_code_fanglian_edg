package com.funi.platform.edg.service.impl;

import com.funi.platform.edg.bo.Dict;
import com.funi.platform.edg.bo.ParaDate;
import com.funi.platform.edg.dao.CommonDao;
import com.funi.platform.edg.query.TimeQuery;
import com.funi.platform.edg.service.CommonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package: [com.funi.platform.edg.service.impl]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/4/6 0006 10:44]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2016/4/6 0006 10:44，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录，增加修改记录)]
 * @Version: [v2.0]
 */
@Service
public class CommonServiceImpl implements CommonService {
    @Resource
    private CommonDao commonDao;
    @Override
    public ParaDate fandTime(TimeQuery timeQuery) {
        return commonDao.fandTime(timeQuery);
    }


    @Override
    public List<Dict> queryRegion4user(String uuid) {
        Dict dict = commonDao.queryRegion4user(uuid);
        if(dict==null){
            return null;
        }
        List<Dict> list = new ArrayList<>();
        String[] name = dict.getName().split(",");
        String[] code = dict.getCode().split(",");
        for (int i = 0;i<name.length;i++) {
            Dict d = new Dict(name[i],code[i]);
            list.add(d);
        }

        return list;
    }

    @Override
    public List<Map> queryRegion() {
        List<Map> list = new ArrayList<Map>();

        Map oneMap = new HashMap();
        oneMap.put("circle","主城区");
        List one = commonDao.findRegion("主城区");
        oneMap.put("circlelist",one);
        list.add(oneMap);

        Map twoMap = new HashMap();
        twoMap.put("circle","二圈层");
        List two = commonDao.findRegion("二圈层");
        twoMap.put("circlelist",two);
        list.add(twoMap);

        Map thereMap = new HashMap();
        thereMap.put("circle","三圈层");
        List there = commonDao.findRegion("三圈层");
        thereMap.put("circlelist",there);
        list.add(thereMap);

        return list;
    }

    @Override
    public List<Dict> findDICT(String str) {
        return commonDao.findDICT(str);
    }

    @Override
    public List<Map> findDistinctRole() {
        return commonDao.findDistinctRole();
    }



}

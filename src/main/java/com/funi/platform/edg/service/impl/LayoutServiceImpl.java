package com.funi.platform.edg.service.impl;/**
 * Created by as on 2016/12/13 0013.
 */

import com.funi.platform.edg.annotation.OperLog;
import com.funi.platform.edg.bo.*;
import com.funi.platform.edg.dao.CommonDao;
import com.funi.platform.edg.dao.LayoutDao;
import com.funi.platform.edg.service.LayoutService;
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
 * @CreateDate: [2016/12/13 0013 15:27]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2016/12/13 0013 15:27，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */
@Service
public class LayoutServiceImpl implements LayoutService {

    @Resource
    private LayoutDao dao;

    @Resource
    private CommonDao commonDao;

    public Object queryUserRegion(String uuid) {
        Dict dict = commonDao.queryRegion4user(uuid);
        if(dict==null){
            return null;
        }
//        String[] name = dict.getName().split(",");
        String[] code = dict.getCode().split(",");
        return code;
    }

    @Override
    public PageResult<Layout> queryLayout(SearchLayout searchLayout,String uuid) {

        PageResult<Layout> pageResult = new PageResult<>();
        Map params = new HashMap();
        if (searchLayout.getRegList()!=null && !searchLayout.getRegList().equals("全域成都")){
            params.put("region_code", SourceUtils.dealRegList4DataSource(searchLayout.getRegList()));
        }else {
            params.put("region_code", queryUserRegion(uuid));
        }
        if (searchLayout.getSearchValue()!=null && searchLayout.getSearchValue()!=""){
            params.put(searchLayout.getSearchType(), "%"+searchLayout.getSearchValue()+"%");
        }
        if (searchLayout.getStartTime()!=null && searchLayout.getStartTime()!=""){
            params.put("startTime", searchLayout.getStartTime());
        }
        if (searchLayout.getEndTime()!=null && searchLayout.getEndTime()!=""){
            params.put("endTime", searchLayout.getEndTime());
        }
        if (searchLayout.getTimeType()!=null && searchLayout.getTimeType()!=""){
            params.put("timeType", searchLayout.getTimeType());
        }
        params.put("start",searchLayout.getStart());
        params.put("end",searchLayout.getEnd());
        List<Layout> list =dao.queryLayout(params);
        pageResult.setRows(list);
        Long total = dao.queryTotal(params);
        pageResult.setTotal(total);
        return pageResult;
    }

    @Override
    public int updateInputLayouts(Map<String, Object> map) {
        return dao.updateInputLayouts(map);
    }

    @Override
    public int updateInputLayout(Map<String, Object> map) {
        return dao.updateInputLayout(map);
    }

    @Override
    public int deleteLayouts(Map<String, Object> map) {
        return dao.deleteLayouts(map);
    }

    @Override
    public int deleteLayout(Map<String, Object> map) {
        return dao.deleteLayout(map);
    }

    @Override
    public PageResult<SearchOne> queryBAH(Map<String,String> map) {
        PageResult<SearchOne> pageResult = new PageResult<>();

        List<SearchOne> list =dao.queryBAH(map);
        pageResult.setRows(list);

        return pageResult;
    }

    @Override
    public PageResult<SearchOne> queryBDC(Map<String, String> paramsMap) {
        PageResult<SearchOne> pageResult = new PageResult<>();

        List<SearchOne> list =dao.queryBDC(paramsMap);
        pageResult.setRows(list);

        return pageResult;
    }

    @Override
    public int checkLayout(String layout_no) {
        return dao.checkLayout(layout_no);
    }

    @Override
    public int checkField(String fctrno_new) {
        return dao.checkField(fctrno_new);
    }

    @Override
    public int checkFfileno(String ffileno) {
        return dao.checkFfileno(ffileno);
    }


    @Override
    @OperLog(operationType="规划",operationName="编辑")
    public void updateLayout(Layout paramObj) {

        if(paramObj.getFile_status().equals("未提交")){
            paramObj.setFile_status("0");
        }else if(paramObj.getFile_status().equals("已提交")){
            paramObj.setFile_status("1");
        }
        dao.updateLayout(paramObj);
        //维护关系拓展表
        //删除旧关系
        dao.deleteLayoutExtend(paramObj.getId());

        //建立新的
        Map map = new HashMap<>();
        map.put("xh",paramObj.getId());
        map.put("region_code",paramObj.getRegion_code());
        map.put("layout_no",paramObj.getLayout_no());
        map.put("print_date",paramObj.getPrint_date());

        map.put("ffileno","");
        //土地合同编号
        Object fctrno_new = SourceUtils.dealOneOrList(paramObj.getFctrno());
        if(null!=fctrno_new && ""!=fctrno_new){
            if(fctrno_new instanceof String){
                map.put("fctrno",paramObj.getFctrno());
                dao.insertLayoutExtend(map);
            }else {
                for (Object o : (String[])fctrno_new) {
                    map.put("fctrno",o.toString());
                    dao.insertLayoutExtend(map);
                }
            }
        }

        map.put("fctrno_new","");
        //不动产证书号，也叫国土证号,土地证号
        Object ffileno = SourceUtils.dealOneOrList(paramObj.getFfileno());
        if(null!=ffileno && ""!=ffileno){
            if(ffileno instanceof String){
                map.put("ffileno",paramObj.getFfileno());
                dao.insertLayoutExtend(map);
            }else {
                for (Object o : (String[])ffileno) {
                    map.put("ffileno",o.toString());
                    dao.insertLayoutExtend(map);
                }
            }
        }


    }

    @Override
    @OperLog(operationType="规划",operationName="新增")
    public void insertLayout(Layout paramObj) {

        if(paramObj.getFctrno()==null ){
            paramObj.setFctrno("/");
        }
        if(paramObj.getFctrno()!=null && paramObj.getFctrno().endsWith(",")){
            paramObj.setFctrno( paramObj.getFctrno().substring(0,paramObj.getFctrno().length()-1));
        }
        if(paramObj.getFfileno()==null){
            paramObj.setFfileno("/");
        }
        if(paramObj.getFfileno()!=null && paramObj.getFfileno().endsWith(",")){
            paramObj.setFfileno( paramObj.getFfileno().substring(0,paramObj.getFfileno().length()-1));
        }

        if(paramObj.getRegion_code().equals("全域成都")) {
            paramObj.setRegion_code("");
        }

        //获取流水号序列
        Integer xh = dao.findXh();
        paramObj.setId(xh.toString());
        //插入数据
        dao.insertLayout(paramObj);
        //维护关系拓展表
        Map map = new HashMap<>();
        map.put("xh",xh);
        map.put("region_code",paramObj.getRegion_code());
        map.put("layout_no",paramObj.getLayout_no());
        map.put("print_date",paramObj.getPrint_date());

        map.put("ffileno","");
        //土地合同编号
        Object fctrno_new = SourceUtils.dealOneOrList(paramObj.getFctrno());
        if(null != fctrno_new && "" != fctrno_new){
            if(fctrno_new instanceof String){
                map.put("fctrno",paramObj.getFctrno());
                dao.insertLayoutExtend(map);
            }else {
                for (Object o : (String[])fctrno_new) {
                    map.put("fctrno",o.toString());
                    dao.insertLayoutExtend(map);
                }
            }
        }


        map.put("fctrno","");
        //不动产证书号，也叫国土证号,土地证号
        Object ffileno = SourceUtils.dealOneOrList(paramObj.getFfileno());
        if(null != ffileno && "" != ffileno){
            if(ffileno instanceof String){
                map.put("ffileno",paramObj.getFfileno());
                dao.insertLayoutExtend(map);
            }else {
                for (Object o : (String[])ffileno) {
                    map.put("ffileno",o.toString());
                    dao.insertLayoutExtend(map);
                }
            }
        }

    }


}

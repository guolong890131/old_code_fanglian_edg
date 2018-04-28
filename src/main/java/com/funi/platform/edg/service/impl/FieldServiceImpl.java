package com.funi.platform.edg.service.impl;/**
 * Created by as on 2016/12/12 0012.
 */

import com.funi.platform.edg.annotation.OperLog;
import com.funi.platform.edg.bo.Dict;
import com.funi.platform.edg.bo.Field;
import com.funi.platform.edg.bo.PageResult;
import com.funi.platform.edg.bo.SearchField;
import com.funi.platform.edg.dao.CommonDao;
import com.funi.platform.edg.dao.FieldDao;
import com.funi.platform.edg.service.FieldService;
import com.funi.platform.edg.utils.SourceUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package: [com.funi.platform.edg.service.impl]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/12/12 0012 11:06]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2016/12/12 0012 11:06，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */
@Service
public class FieldServiceImpl implements FieldService {

    @Resource
    private FieldDao dao;
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
    public PageResult<Field> queryField(SearchField searchField,String uuid) {

        PageResult<Field> pageResult = new PageResult<>();
        Map params = new HashMap();
        if (searchField.getRegList()!=null && !searchField.getRegList().equals("全域成都")){
            params.put("data_source", SourceUtils.dealRegList4DataSource(searchField.getRegList()));
        }else {
            params.put("data_source", queryUserRegion(uuid));
        }
        if (searchField.getFtradetype()!=null && searchField.getFtradetype()!=""){
            params.put("ftradetype", searchField.getFtradetype());
        }
        if (searchField.getSearchValue()!=null && searchField.getSearchValue()!=""){
            params.put(searchField.getSearchType(), "%"+searchField.getSearchValue()+"%");
        }
        if (searchField.getStartFtr()!=null && searchField.getStartFtr()!=""){
            params.put("startFtr", searchField.getStartFtr());
        }
        if (searchField.getEndFtr()!=null && searchField.getEndFtr()!=""){
            params.put("endFtr", searchField.getEndFtr());
        }
        if (searchField.getTimeType()!=null && searchField.getTimeType()!=""){
            params.put("timeType", searchField.getTimeType());
        }
        params.put("start",searchField.getStart());
        params.put("end",searchField.getEnd());
        List<Field> list =dao.queryField(params);
        pageResult.setRows(list);
        Long total = dao.queryTotal(params);
        pageResult.setTotal(total);
        return pageResult;
    }

    @Override
    public int deleteField(Map ids) {
        //删除关联
        dao.deleteFieldExtend(ids.get("ids").toString());
        return dao.deleteField(ids);
    }

    @Override
    public int deleteFields(Map<String, Object> map) {
        String[] ids = (String[])map.get("ids");
        for (String id : ids) {
            //删除关联
            dao.deleteFieldExtend(id);
        }
        return dao.deleteFields(map);
    }

    @Override
    public int updateInputFields(Map<String, Object> map) {
        return dao.updateInputFields(map);
    }

    @Override
    public int updateInputField(Map<String, Object> map) {
        return dao.updateInputField(map);
    }

    @Override
    @OperLog(operationType="土地",operationName="编辑")
    public void updateField(Field paramObj) {
        if(paramObj.getFile_status().equals("未提交")){
            paramObj.setFile_status("0");
        }else  if(paramObj.getFile_status().equals("已提交")) {
            paramObj.setFile_status("1");
        }
        dao.updateField(paramObj);
        //维护关系拓展表
        //删除旧关系
        dao.deleteFieldExtend(paramObj.getId());

        //建立新的
        Map map = new HashMap<>();
        map.put("fid",paramObj.getId());
        map.put("data_source",paramObj.getData_source());
        map.put("fno_new",paramObj.getFno_new());

        map.put("ffileno","");
        //土地合同编号
        Object fctrno_new = SourceUtils.dealOneOrList(paramObj.getFctrno_new());
        if(null!=fctrno_new && ""!=fctrno_new){
            if(fctrno_new instanceof String){
                map.put("fctrno_new",paramObj.getFctrno_new());
                dao.insertFieldExtend(map);
            }else {
                for (Object o : (String[])fctrno_new) {
                    map.put("fctrno_new",o.toString());
                    dao.insertFieldExtend(map);
                }
            }
        }


        map.put("fctrno_new","");
        //不动产证书号，也叫国土证号,土地证号
        Object ffileno = SourceUtils.dealOneOrList(paramObj.getFfileno());
        if(null!=ffileno && ""!=ffileno){
            if(ffileno instanceof String){
                map.put("ffileno",paramObj.getFfileno());
                dao.insertFieldExtend(map);
            }else {
                for (Object o : (String[])ffileno) {
                    map.put("ffileno",o.toString());
                    dao.insertFieldExtend(map);
                }
            }
        }

    }

    @Override
    @OperLog(operationType="土地",operationName="新增")
    public void insertField(Field paramObj) {
       if(paramObj.getData_source().equals("全域成都")) {
           paramObj.setData_source("");
       }
        dao.insertField(paramObj);
        //维护关系拓展表
        Map map = new HashMap<>();
        map.put("fid",paramObj.getId());
        map.put("data_source",paramObj.getData_source());
        map.put("fno_new",paramObj.getFno_new());

        map.put("ffileno","");
        //土地合同编号
        Object fctrno_new = SourceUtils.dealOneOrList(paramObj.getFctrno_new());
        if(null!=fctrno_new && ""!=fctrno_new){
            if(fctrno_new instanceof String){
                map.put("fctrno_new",paramObj.getFctrno_new());
                dao.insertFieldExtend(map);
            }else {
                for (Object o : (String[])fctrno_new) {
                    map.put("fctrno_new",o.toString());
                    dao.insertFieldExtend(map);
                }
            }
        }


        map.put("fctrno_new","");
        //不动产证书号，也叫国土证号,土地证号
        Object ffileno = SourceUtils.dealOneOrList(paramObj.getFfileno());
        if(null!=ffileno && ""!=ffileno){
            if(ffileno instanceof String){
                map.put("ffileno",paramObj.getFfileno());
                dao.insertFieldExtend(map);
            }else {
                for (Object o : (String[])ffileno) {
                    map.put("ffileno",o.toString());
                    dao.insertFieldExtend(map);
                }
            }
        }

    }

    @Override
    public int checkField(String fno_new) {
        return dao.checkField(fno_new);
    }

}

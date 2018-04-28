package com.funi.platform.edg.service.impl;

import com.funi.platform.edg.annotation.OperLog;
import com.funi.platform.edg.bo.*;
import com.funi.platform.edg.dao.*;
import com.funi.platform.edg.service.UploadCompletedExcelService;
import com.funi.platform.edg.utils.SourceUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/25.
 */
public class UploadCompletedExcelServiceImpl implements UploadCompletedExcelService {
    @Resource
    private UploadCompletedExcelDao uploadCompletedExcelDao;
    @Resource
    private BuildDao buildDao;
    @Resource
    private CommonDao commonDao;


    @Override
    public void insertCompletedErr(Map map) {
        uploadCompletedExcelDao.insertErr(map);
    }



    /**
     * 单条数据写入竣工IMP表
     */
    @Override
    public void insertCompletedToIMP(Completed completed) throws Exception {
        //从竣工正式B表 验证竣工编号是否重复
        int i = buildDao.checkCompleted(completed.getCompleted_no());
        if(i!=0){
            //竣工编号已存在
            //抛出异常，结束流程
            throw new Exception("已存在该竣工编号;"+completed.getCompleted_no());
        }
        //从竣工导入B_IMP表  验证竣工编号是否重复
        int j = uploadCompletedExcelDao.checkCompleted(completed);
        if(j!=0){
            //竣工好已导入
            //抛出异常，结束流程
            throw new Exception("本次已导入该宗地信息;"+completed.getCompleted_no());
        }

        //建设工程规划许可证号:Layout_no 施工许可证书编号:build_no  处理用户中文输入逗号
        if (completed.getLayout_no() != null && completed.getLayout_no() != ""){
            completed.setLayout_no(completed.getLayout_no().replaceAll("，", ","));
        }
        if(completed.getBuild_no()  != null && completed.getBuild_no() != ""){
            completed.setBuild_no(completed.getBuild_no().replaceAll("，",","));
        }

        //验证上级关联证号号是否存在
        //验证规划证号
        Object layout_no = SourceUtils.dealOneOrList(completed.getLayout_no());
        int layout_Num = 0;
        if(null!=layout_no && ""!=layout_no){
            if(layout_no instanceof String){
                layout_Num = uploadCompletedExcelDao.checkCompleted4Layout_no(completed.getLayout_no());
                if(layout_Num==0){
                    //规划证号不存在
                    //抛出异常，结束流程
                    throw new Exception("此建设工程规划许可证号不存在："+completed.getLayout_no()+";"+completed.getCompleted_no());
                }
            }else {
                for (Object o : (String[])layout_no) {
                    layout_Num = uploadCompletedExcelDao.checkCompleted4Layout_no(o.toString());
                    if(layout_Num==0){
                        //规划证号不存在
                        //抛出异常，结束流程
                        throw new Exception("此建设工程规划许可证号不存在："+o.toString()+";"+completed.getCompleted_no());
                    }
                }
            }
        }
        //验证施工证号
        Object build_no = SourceUtils.dealOneOrList(completed.getBuild_no());
        int build_Num = 0;
        if(null!=build_no && ""!=build_no){
            if(build_no instanceof String){
                build_Num = uploadCompletedExcelDao.checkCompleted4Build_no(completed.getBuild_no());
                if(build_Num==0){
                    //验证施工证号 不存在
                    //抛出异常，结束流程
                    throw new Exception("此施工证号不存在："+completed.getBuild_no()+";"+completed.getCompleted_no());
                }
            }else {
                for (Object o : (String[])build_no) {
                    build_Num = uploadCompletedExcelDao.checkCompleted4Build_no(o.toString());
                    if(build_Num==0){
                        //验证施工证号 不存在
                        //抛出异常，结束流程
                        throw new Exception("此施工证号不存在："+o.toString()+";"+completed.getCompleted_no());
                    }
                }
            }
        }

        //根据区域名称查询区域编号 填充编号
        List<Dict> region = commonDao.findDICT("行政区域类");
        boolean regFlag=true;
        String regName = completed.getRegion().trim();
        for (Dict dict : region) {
            if(dict.getName().equals(regName)){
                completed.setRegion_code(dict.getCode());
                regFlag=false;
            };
        }
        if(regFlag){
            //区域匹配失败，抛出异常，结束流程
            throw new Exception("区域匹配失败，区域有误;"+completed.getCompleted_no()+";"+completed.getRegion());
        }
        //处理主键xh
        Integer xh = buildDao.findCompletedXh();
        completed.setXh(xh.toString());


        //数据插入到B_IMP表
        try {
            //设置插入成功状态
            completed.setImport_state("1");
            uploadCompletedExcelDao.insertCompletedExc(completed);
        }catch (Exception e){
            //数据插入失败，抛出异常，结束流程
            throw new Exception("数据填写错误;"+completed.getCompleted_no()+";"+e.getMessage());
        }
        //插入关系到B_E_IMP表
        //维护关系拓展表
        Map map = new HashMap<>();
        map.put("xh",completed.getXh());
        map.put("data_source",completed.getRegion_code());
        map.put("completed_no",completed.getCompleted_no());
        map.put("build_no","");
        try {
            //规划证号
//            Object layout_no = SourceUtils.dealOneOrList(completed.getLayout_no());
            if(null!=layout_no && ""!=layout_no){
                if(layout_no instanceof String){
                    map.put("layout_no",completed.getLayout_no());
                    uploadCompletedExcelDao.insertCompletedExc_E(map);
                }else {
                    for (Object o : (String[])layout_no) {
                        map.put("layout_no",o.toString());
                        uploadCompletedExcelDao.insertCompletedExc_E(map);
                    }
                }
            }
        }catch (Exception e){
            //关联数据插入失败，抛出异常，结束流程
            throw new Exception("建设规划证号填写错误;"+completed.getCompleted_no()+";"+completed.getLayout_no()+";"+e.getMessage());
        }
        map.put("layout_no","");
        try {
            //施工证号
//            Object build_no = SourceUtils.dealOneOrList(completed.getBuild_no());
            if(null!=build_no && ""!=build_no){
                if(build_no instanceof String){
                    map.put("build_no",completed.getBuild_no());
                    uploadCompletedExcelDao.insertCompletedExc_E(map);
                }else {
                    for (Object o : (String[])build_no) {
                        map.put("build_no",o.toString());
                        uploadCompletedExcelDao.insertCompletedExc_E(map);
                    }
                }
            }
        }catch (Exception e){
            //关联数据插入失败，抛出异常，结束流程
            throw new Exception("施工证号填写错误;"+completed.getCompleted_no()+";"+completed.getBuild_no()+";"+e.getMessage());
        }

    }

    @Override
    public PageResult<Completed> queryFieldIMP(SearchField searchField, String uuid) {
        PageResult<Completed> pageResult = new PageResult<>();
        Map params = new HashMap();
        params.put("uuid",uuid);
        params.put("start",searchField.getStart());
        params.put("end",searchField.getEnd());
        List<Completed> list =uploadCompletedExcelDao.queryCompletedIMP(params);
        pageResult.setRows(list);
        Long total = uploadCompletedExcelDao.queryCompletedIMPTotal(params);
        pageResult.setTotal(total);
        return pageResult;
    }

    @Override
    @OperLog(operationType="竣工",operationName="导入")
    public void transferTable(String uuid) {
        List<Completed> list =uploadCompletedExcelDao.queryCompletedIMPAll(uuid);
        List<Completed> list_e =uploadCompletedExcelDao.queryCompletedIMPAll_E(uuid);
        uploadCompletedExcelDao.insertCompleted(list);
        uploadCompletedExcelDao.insertCompleted_E(list_e);
    }

    @Override
    public void clearAll(String uuid) {
        uploadCompletedExcelDao.cleanCompleted_E_IMP(uuid);
        uploadCompletedExcelDao.cleanCompletedIMP(uuid);
    }

    @Override
    public int checkNum(String uuid) {
        return uploadCompletedExcelDao.checkNum(uuid);
    }
}

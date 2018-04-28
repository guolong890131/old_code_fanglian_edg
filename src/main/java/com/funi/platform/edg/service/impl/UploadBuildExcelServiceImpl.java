package com.funi.platform.edg.service.impl;

import com.funi.platform.edg.annotation.OperLog;
import com.funi.platform.edg.bo.*;
import com.funi.platform.edg.dao.*;
import com.funi.platform.edg.service.UploadBuildExcelService;
import com.funi.platform.edg.utils.SourceUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/25.
 */
public class UploadBuildExcelServiceImpl implements UploadBuildExcelService{
    @Resource
    private UploadBuildExcelDao uploadBuildExcelDao;
    @Resource
    private BuildDao buildDao;
    @Resource
    private CommonDao commonDao;
    @Override
    public void insertBuildToIMP(Build build) throws Exception{
        /**
         * 单条数据写入土地IMP表
         */
        //从施工正式B表 验证施工证号是否重复
        int i = buildDao.checkBuild(build.getBuild_no());
        if(i!=0){
            //施工许可证号已存在
            //抛出异常，结束流程
            throw new Exception("已存在施工证号;"+build.getBuild_no());
        }
        //从施工导入B_IMP表 验证施工许可证号是否重复
        int j = uploadBuildExcelDao.checkBuild(build);
        if(j!=0){
            //施工许可证号已导入
            //抛出异常，结束流程
            throw new Exception("本施工许可证号已导入;"+build.getBuild_no());
        }


        //建设工程规划许可证号:ffileno处理用户中文输入逗号
        if(build.getBuild_no() != null &&build.getBuild_no() != ""){
            build.setBuild_no(build.getBuild_no().replaceAll("，", ","));
        }
        //验证上级关联证号(规划证号)号是否存在
        //验证规划证号
        Object layout_no = SourceUtils.dealOneOrList(build.getLayout_no());
        int layout_Num = 0;
        if(null!=layout_no && ""!=layout_no){
            if(layout_no instanceof String){
                layout_Num = uploadBuildExcelDao.checkBuild4Layout_no(build.getLayout_no());
                if(layout_Num==0){
                    //规划证号不存在
                    //抛出异常，结束流程
                    throw new Exception("此建设工程规划许可证号不存在："+build.getLayout_no()+";"+build.getBuild_no());
                }
            }else {
                for (Object o : (String[])layout_no) {
                    layout_Num = uploadBuildExcelDao.checkBuild4Layout_no(o.toString());
                    if(layout_Num==0){
                        //规划证号不存在
                        //抛出异常，结束流程
                        throw new Exception("此建设工程规划许可证号不存在："+o.toString()+";"+build.getBuild_no());
                    }
                }
            }
        }


        //根据区域名称查询区域编号 填充编号
            List<Dict> region = commonDao.findDICT("行政区域类");
            boolean regFlag=true;
            String regName = build.getRegion().trim();
            for (Dict dict : region) {
                if(dict.getName().equals(regName)){
                    build.setData_source(dict.getCode());
                    regFlag=false;
                }
            }
            if(regFlag){
                //区域匹配失败，抛出异常，结束流程
                throw new Exception("区域匹配失败，区域有误;"+build.getBuild_no()+";"+build.getRegion());
            }
            //处理主键UUID ， fid 或 序列 等
            Integer xh = buildDao.queryXhSEQ();
            build.setXh(xh.toString());

            //数据插入到B_IMP表
        try {
            //设置插入成功状态
            build.setImport_state("1");
            uploadBuildExcelDao.insertBuildExc(build);
        }catch (Exception e){
            //数据插入失败，抛出异常，结束流程
            throw new Exception("数据填写错误;"+build.getBuild_no()+";"+e);
        }
        //插入关系到B_E_IMP表
        //维护关系拓展表
        Map map = new HashMap();
        map.put("xh",build.getXh());
        map.put("data_source",build.getData_source());
        map.put("build_no",build.getBuild_no());

        try {
            //规划许可证号
//            Object layout_no = SourceUtils.dealOneOrList(build.getLayout_no());
            if(null!=layout_no && ""!=layout_no){
                if(layout_no instanceof String){
                    map.put("layout_no",build.getLayout_no());
                    uploadBuildExcelDao.insertBuildExc_E(map);
                }else {
                    for (Object o : (String[])layout_no) {
                        map.put("layout_no",o.toString());
                        uploadBuildExcelDao.insertBuildExc_E(map);
                    }
                }
            }
        }catch (Exception e){
            //关联数据插入失败，抛出异常，结束流程
            throw new Exception("建设工程规划许可证号填写错误;"+build.getBuild_no() +";"+build.getLayout_no() +";"+e.getMessage());
        }

        }


    @Override
    public void insertBuildErr(Map map) {
        uploadBuildExcelDao.insertErr(map);
    }

    @Override
    public PageResult<Build> queryFieldIMP(SearchField searchField, String uuid) {
        PageResult<Build> pageResult = new PageResult<>();
        Map params = new HashMap();
        params.put("uuid",uuid);
        params.put("start",searchField.getStart());
        params.put("end",searchField.getEnd());
        List<Build> list =uploadBuildExcelDao.queryBuildIMP(params);
        pageResult.setRows(list);
        Long total = uploadBuildExcelDao.queryBuildIMPTotal(params);
        pageResult.setTotal(total);
        return pageResult;
    }

    @Override
    @OperLog(operationType="施工",operationName="导入")
    public void transferTable(String uuid) {
        List<Build> list =uploadBuildExcelDao.queryBuildIMPAll(uuid);
        List<Build> list_e =uploadBuildExcelDao.queryBuildIMPAll_E(uuid);
        uploadBuildExcelDao.insertBuild(list);
        uploadBuildExcelDao.insertBuild_E(list_e);
    }

    @Override
    public void clearAll(String uuid) {
        uploadBuildExcelDao.cleanBuild_E_IMP(uuid);
        uploadBuildExcelDao.cleanBuildIMP(uuid);
    }

    @Override
    public int checkNum(String uuid) {
        return uploadBuildExcelDao.checkNum(uuid);
    }
}

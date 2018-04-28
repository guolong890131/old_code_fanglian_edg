package com.funi.platform.edg.service.impl;/**
 * Created by as on 2017/2/24 0024.
 */

import com.funi.platform.edg.annotation.OperLog;
import com.funi.platform.edg.bo.*;
import com.funi.platform.edg.dao.CommonDao;
import com.funi.platform.edg.dao.FieldDao;
import com.funi.platform.edg.dao.UploadFieldExcelDao;
import com.funi.platform.edg.service.UploadFieldExcelService;
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
 * @CreateDate: [2017/2/24 0024 9:51]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2017/2/24 0024 9:51，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */
@Service
public class UploadFieldExcelServiceImpl implements UploadFieldExcelService {

    @Resource
    private UploadFieldExcelDao uploadFieldExcelDao;
    @Resource
    private FieldDao fieldDao;
    @Resource
    private CommonDao commonDao;


    @Override
    public void insertFieldErr(Map map) {
        uploadFieldExcelDao.insertErr(map);
    }

    /**
     * 单条数据写入土地IMP表
     */
    @Override
    public void insertFieldToIMP(FieldVo field) throws Exception {
        //从土地正式B表 验证土地宗号是否重复
        int i = fieldDao.checkField(field.getFno_new());
        if (i != 0) {
            //土地宗号已存在
            //抛出异常，结束流程
            throw new Exception("已存在该宗地信息;" + field.getFno_new());
        }
        //从土地导入B_IMP表 验证土地宗号是否重复
        int j = uploadFieldExcelDao.checkField(field);
        if (j != 0) {
            //土地宗号已存在
            //抛出异常，结束流程
            throw new Exception("本次已导入该宗地信息;" + field.getFno_new());
        }
        //验证上级关联证号号是否存在  土地本身无关联证号 无此步验证
        //根据区域名称查询区域编号 填充编号
        List<Dict> region = commonDao.findDICT("行政区域类");
        boolean regFlag = true;

            String regName = field.getRegion().trim();
            for (Dict dict : region) {
                if (dict.getName().equals(regName)) {
                    field.setData_source(dict.getCode());
                    regFlag = false;
                    break;
                }
            }

        if (regFlag) {
            //区域匹配失败，抛出异常，结束流程
            throw new Exception("区域匹配失败，区域有误;" + field.getFno_new() + ";" + field.getRegion());
        }
        //处理主键UUID ， fid 或 序列 等
        field.setFid(SourceUtils.getUUID());
        //国土证号/不动产证书号:ffileno  土地合同编号:fctrno_new  处理用户中文输入逗号
        if (field.getFfileno() != null && field.getFfileno() != "") {
            field.setFfileno(field.getFfileno().replaceAll("，", ","));
        }
        if (field.getFctrno_new() != null && field.getFctrno_new() != "") {
            field.setFctrno_new(field.getFctrno_new().replaceAll("，", ","));
        }
        //数据插入到B_IMP表
        try {
            //设置插入成功状态
            field.setImport_state("1");
            uploadFieldExcelDao.insertFieldExc(field);
        } catch (Exception e) {
            //数据插入失败，抛出异常，结束流程
            throw new Exception("数据填写错误;" + field.getFno_new() + ";" + e.getMessage());
        }
        //插入关系到B_E_IMP表
        //维护关系拓展表
        Map map = new HashMap<>();
        map.put("fid", field.getFid());
        map.put("data_source", field.getData_source());
        map.put("fno_new", field.getFno_new());
        map.put("ffileno", "");
        try {
            //土地合同编号
            Object fctrno_new = SourceUtils.dealOneOrList(field.getFctrno_new());
            if (null != fctrno_new && "" != fctrno_new) {
                if (fctrno_new instanceof String) {
                    map.put("fctrno_new", field.getFctrno_new());
                    uploadFieldExcelDao.insertFieldExc_E(map);
                } else {
                    for (Object o : (String[]) fctrno_new) {
                        map.put("fctrno_new", o.toString());
                        uploadFieldExcelDao.insertFieldExc_E(map);
                    }
                }
            }
        } catch (Exception e) {
            //关联数据插入失败，抛出异常，结束流程
            throw new Exception("土地合同编号填写错误;" + field.getFno_new() + ";" + field.getFctrno_new() + ";" + e.getMessage());
        }
        map.put("fctrno_new", "");
        try {
            //不动产证书号，也叫国土证号,土地证号
            Object ffileno = SourceUtils.dealOneOrList(field.getFfileno());
            if (null != ffileno && "" != ffileno) {
                if (ffileno instanceof String) {
                    map.put("ffileno", field.getFfileno());
                    uploadFieldExcelDao.insertFieldExc_E(map);
                } else {
                    for (Object o : (String[]) ffileno) {
                        map.put("ffileno", o.toString());
                        uploadFieldExcelDao.insertFieldExc_E(map);
                    }
                }
            }
        } catch (Exception e) {
            //关联数据插入失败，抛出异常，结束流程
            throw new Exception("国土证号/不动产证书号填写错误;" + field.getFno_new() + ";" + field.getFfileno() + ";" + e.getMessage());
        }
    }

    @Override
    public PageResult<Field> queryFieldIMP(SearchField searchField, String uuid) {
        PageResult<Field> pageResult = new PageResult<>();
        Map params = new HashMap();
        params.put("uuid", uuid);
        params.put("start", searchField.getStart());
        params.put("end", searchField.getEnd());
        List<Field> list = uploadFieldExcelDao.queryFieldIMP(params);
        pageResult.setRows(list);
        Long total = uploadFieldExcelDao.queryFieldIMPTotal(params);
        pageResult.setTotal(total);
        return pageResult;
    }

    @Override
    public void cleanAll(String uuid) {
        uploadFieldExcelDao.cleanField_E_IMP(uuid);
        uploadFieldExcelDao.cleanFieldIMP(uuid);

    }

    @Override
    @OperLog(operationType = "土地", operationName = "导入")
    public void transferTable(String uuid) throws Exception {
        List<FieldVo> list = uploadFieldExcelDao.queryFieldIMPAll(uuid);
        List<Field> list_e = uploadFieldExcelDao.queryFieldIMPAll_E(uuid);
        uploadFieldExcelDao.insertField(list);
        uploadFieldExcelDao.insertField_E(list_e);

    }

    @Override
    public int checkNum(String uuid) {
        return uploadFieldExcelDao.checkNum(uuid);
    }
}

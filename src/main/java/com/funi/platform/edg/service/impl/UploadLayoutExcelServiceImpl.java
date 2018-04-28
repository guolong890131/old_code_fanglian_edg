package com.funi.platform.edg.service.impl;/**
 * Created by as on 2017/2/27 0027.
 */

import com.funi.platform.edg.annotation.OperLog;
import com.funi.platform.edg.bo.Dict;
import com.funi.platform.edg.bo.Layout;
import com.funi.platform.edg.bo.PageResult;
import com.funi.platform.edg.bo.SearchLayout;
import com.funi.platform.edg.dao.CommonDao;
import com.funi.platform.edg.dao.LayoutDao;
import com.funi.platform.edg.dao.UploadLayoutExcelDao;
import com.funi.platform.edg.service.UploadLayoutExcelService;
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
 * @CreateDate: [2017/2/27 0027 10:05]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2017/2/27 0027 10:05，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */
@Service
public class UploadLayoutExcelServiceImpl implements UploadLayoutExcelService {

    @Resource
    private UploadLayoutExcelDao uploadLayoutExcelDao;

    @Resource
    private LayoutDao layoutDao;

    @Resource
    private CommonDao commonDao;

    @Override
    public void insertLayoutErr(Map map) {
        uploadLayoutExcelDao.insertErr(map);
    }


    @Override
    public void cleanAll(String uuid) {
        uploadLayoutExcelDao.cleanLayout_E_IMP(uuid);
        uploadLayoutExcelDao.cleanLayoutIMP(uuid);
    }

    /**
     * 单条数据写入规划IMP表
     */
    @Override
    public void insertLayoutToIMP(Layout layout) throws Exception {
        //从规划正式B表 验证规划证号是否重复
        int i = layoutDao.checkLayout(layout.getLayout_no());
        if(i!=0){
            //规划证号已存在
            //抛出异常，结束流程
            throw new Exception("已存在该规划信息;"+layout.getLayout_no());
        }
        //从规划导入B_IMP表 验证规划证号是否重复
        int j = uploadLayoutExcelDao.checkLayout(layout);
        if(j!=0){
            //规划号已存在
            //抛出异常，结束流程
            throw new Exception("本次已导入该规划信息;"+layout.getLayout_no());
        }

        //国土证号/不动产证书号:ffileno  土地合同编号:fctrno  处理用户中文输入逗号
        if(layout.getFfileno() != null && layout.getFfileno() != ""){
            layout.setFfileno(layout.getFfileno().replaceAll("，",","));
        }
        if(layout.getFctrno() != null && layout.getFctrno() != ""){
            layout.setFctrno(layout.getFctrno().replaceAll("，",","));
        }

        //验证上级关联证号号是否存在------
        //验证土地合同编号
        Object fctrno = SourceUtils.dealOneOrList(layout.getFctrno());
        int fctrno_no = 0;
        if(null!=fctrno && ""!=fctrno){
            if(fctrno instanceof String){
                fctrno_no = uploadLayoutExcelDao.checkLayout4Fctrno(layout.getFctrno());
                if(fctrno_no==0){
                    //土地合同编号不存在
                    //抛出异常，结束流程
                    throw new Exception("此土地合同编号不存在："+layout.getFctrno()+";"+layout.getLayout_no());
                }
            }else {
                for (Object o : (String[])fctrno) {
                    fctrno_no = uploadLayoutExcelDao.checkLayout4Fctrno(o.toString());
                    if(fctrno_no==0){
                        //土地合同编号不存在
                        //抛出异常，结束流程
                        throw new Exception("此土地合同编号不存在："+o.toString()+";"+layout.getLayout_no());
                    }
                }
            }
        }
        //验证不动产证书号，也叫国土证号,土地证号
        Object ffileno = SourceUtils.dealOneOrList(layout.getFfileno());
        int ffileno_no = 0;
        if(null!=ffileno && ""!=ffileno){
            if(ffileno instanceof String){
                ffileno_no = uploadLayoutExcelDao.checkLayout4Ffileno(layout.getFfileno());
                if(ffileno_no==0){
                    //国土证号/不动产证书号 不存在
                    //抛出异常，结束流程
                    throw new Exception("此国土证号/不动产证书号不存在："+layout.getFfileno()+";"+layout.getLayout_no());
                }
            }else {
                for (Object o : (String[])ffileno) {
                    ffileno_no = uploadLayoutExcelDao.checkLayout4Ffileno(o.toString());
                    if(ffileno_no==0){
                        //国土证号/不动产证书号 不存在
                        //抛出异常，结束流程
                        throw new Exception("此国土证号/不动产证书号不存在："+o.toString()+";"+layout.getLayout_no());
                    }
                }
            }
        }


        //根据区域名称查询区域编号 填充编号
        List<Dict> region = commonDao.findDICT("行政区域类");
        boolean regFlag=true;
        String regName = layout.getRegion().trim();
        for (Dict dict : region) {
            if(dict.getName().equals(regName)){
                layout.setRegion_code(dict.getCode());
                regFlag=false;
                break;
            };
        }
        if(regFlag){
            //区域匹配失败，抛出异常，结束流程
            throw new Exception("区域匹配失败，区域有误;"+layout.getLayout_no()+";"+layout.getRegion());
        }
        //处理主键xh序列 等
        Integer xh = layoutDao.findXh();
        layout.setXh(xh.toString());


        //数据插入到B_IMP表
        try {
            //设置插入成功状态
            layout.setImport_state("1");
            uploadLayoutExcelDao.insertLayoutExc(layout);
        }catch (Exception e){
            //数据插入失败，抛出异常，结束流程
            throw new Exception("数据填写错误;"+layout.getLayout_no()+";"+e.getMessage());
        }
        //插入关系到B_E_IMP表
        //维护关系拓展表
        Map map = new HashMap<>();
        map.put("xh",layout.getXh());
        map.put("region_code",layout.getRegion_code());
        map.put("layout_no",layout.getLayout_no());
        map.put("print_date",layout.getPrint_date());
        map.put("ffileno","");
        try {
            //土地合同编号
//            Object fctrno = SourceUtils.dealOneOrList(layout.getFctrno());
            if(null!=fctrno && ""!=fctrno){
                if(fctrno instanceof String){
                    map.put("fctrno",layout.getFctrno());
                    uploadLayoutExcelDao.insertLayoutExc_E(map);
                }else {
                    for (Object o : (String[])fctrno) {
                        map.put("fctrno",o.toString());
                        uploadLayoutExcelDao.insertLayoutExc_E(map);
                    }
                }
            }
        }catch (Exception e){
            //关联数据插入失败，抛出异常，结束流程
            throw new Exception("土地合同编号填写错误;"+layout.getLayout_no()+";"+layout.getFctrno()+";"+e.getMessage());
        }
        map.put("fctrno","");
        try {
            //不动产证书号，也叫国土证号,土地证号
//            Object ffileno = SourceUtils.dealOneOrList(layout.getFfileno());
            if(null!=ffileno && ""!=ffileno){
                if(ffileno instanceof String){
                    map.put("ffileno",layout.getFfileno());
                    uploadLayoutExcelDao.insertLayoutExc_E(map);
                }else {
                    for (Object o : (String[])ffileno) {
                        map.put("ffileno",o.toString());
                        uploadLayoutExcelDao.insertLayoutExc_E(map);
                    }
                }
            }
        }catch (Exception e){
            //关联数据插入失败，抛出异常，结束流程
            throw new Exception("国土证号/不动产证书号填写错误;"+layout.getLayout_no()+";"+layout.getFfileno()+";"+e.getMessage());
        }
    }



    @Override
    public PageResult<Layout> queryLayoutIMP(SearchLayout searchLayout, String uuid) {
        PageResult<Layout> pageResult = new PageResult<>();
        Map params = new HashMap();
        params.put("uuid",uuid);
        params.put("start",searchLayout.getStart());
        params.put("end",searchLayout.getEnd());
        List<Layout> list =uploadLayoutExcelDao.queryLayoutIMP(params);
        pageResult.setRows(list);
        Long total = uploadLayoutExcelDao.queryLayoutIMPTotal(params);
        pageResult.setTotal(total);
        return pageResult;
    }

    @Override
    @OperLog(operationType="规划",operationName="导入")
    public void transferTable(String uuid)  throws Exception {
        List<Layout> list =uploadLayoutExcelDao.queryLayoutIMPAll(uuid);
        List<Layout> list_e =uploadLayoutExcelDao.queryLayoutIMPAll_E(uuid);
        uploadLayoutExcelDao.insertLayout(list);
        uploadLayoutExcelDao.insertLayout_E(list_e);
    }

    @Override
    public int checkNum(String uuid) {
        return uploadLayoutExcelDao.checkNum(uuid);
    }
}

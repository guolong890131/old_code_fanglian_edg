package com.funi.platform.edg.service.impl;/**
 * Created by as on 2016/12/13 0013.
 */

import com.funi.platform.edg.annotation.OperLog;
import com.funi.platform.edg.bo.*;
import com.funi.platform.edg.dao.BuildDao;
import com.funi.platform.edg.dao.CommonDao;
import com.funi.platform.edg.service.BuildService;
import com.funi.platform.edg.utils.SourceUtils;
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
 * @CreateDate: [2016/12/13 0013 18:41]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2016/12/13 0013 18:41，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */
@Service
public class BuildServiceImpl implements BuildService {
    
    @Resource
    private BuildDao dao;

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
    public PageResult<BuildVo> queryBuild(SearchBuild searchBuild) {
        PageResult<BuildVo> pageResult = new PageResult<>();
        Map params = new HashMap();
        params.put("useruuid", searchBuild.getUseruuid());
        if (searchBuild.getRegList()!=null && !searchBuild.getRegList().equals("全域成都")){
            params.put("region_code", SourceUtils.dealRegList4DataSource(searchBuild.getRegList()));
        }else {
            params.put("region_code", queryUserRegion(searchBuild.getUseruuid()));
        }
        if (searchBuild.getSearchValue()!=null && searchBuild.getSearchValue()!=""){
            params.put(searchBuild.getSearchType(), "%"+searchBuild.getSearchValue()+"%");
        }
        if (searchBuild.getStartTime()!=null && searchBuild.getStartTime()!=""){
            params.put("startTime", searchBuild.getStartTime());
        }
        if (searchBuild.getEndTime()!=null && searchBuild.getEndTime()!=""){
            params.put("endTime", searchBuild.getEndTime());
        }
        if (searchBuild.getTimeType()!=null && searchBuild.getTimeType()!=""){
            params.put("timeType", searchBuild.getTimeType());
        }
        params.put("start",searchBuild.getStart()+1);
        params.put("end",searchBuild.getEnd());
        List<BuildVo> list =dao.queryBuild(params);
        pageResult.setRows(list);
        Long total = dao.queryTotal(params);
        pageResult.setTotal(total);
        return pageResult;
    }

    @Override
    public int updateinputFieldBs(Map<String, Object> map) {
        return dao.updateinputFieldBs(map);
    }

    @Override
    public int updateinputFieldB(Map<String, Object> map) {
        return dao.updateinputFieldB(map);
    }



    @Override
    public PageResult<Completed> queryCompleted(SearchCompleted searchCompleted,String uuid) {
        PageResult<Completed> pageResult = new PageResult<>();
        Map params = new HashMap();
        if (searchCompleted.getRegList()!=null && !searchCompleted.getRegList().equals("全域成都")){
            params.put("region_code", SourceUtils.dealRegList4DataSource(searchCompleted.getRegList()));
        }else {
            params.put("region_code", queryUserRegion(uuid));
        }
        if (searchCompleted.getSearchValue()!=null && searchCompleted.getSearchValue()!=""){
            params.put(searchCompleted.getSearchType(), "%"+searchCompleted.getSearchValue()+"%");
        }
        if (searchCompleted.getStartTime()!=null && searchCompleted.getStartTime()!=""){
            params.put("startTime", searchCompleted.getStartTime());
        }
        if (searchCompleted.getEndTime()!=null && searchCompleted.getEndTime()!=""){
            params.put("endTime", searchCompleted.getEndTime());
        }
        if (searchCompleted.getTimeType()!=null && searchCompleted.getTimeType()!=""){
            params.put("timeType", searchCompleted.getTimeType());
        }
        params.put("start",searchCompleted.getStart());
        params.put("end",searchCompleted.getEnd());
        List<Completed> list =dao.queryCompleted(params);
        pageResult.setRows(list);
        Long total = dao.queryTotalCompleted(params);
        pageResult.setTotal(total);
        return pageResult;
    }

    @Override
    public int updateinputFieldCs(Map<String, Object> map) {
        return dao.updateinputFieldCs(map);
    }

    @Override
    public int updateinputFieldC(Map<String, Object> map) {
        return dao.updateinputFieldC(map);
    }

    @Override
    public List<Map> queryLayoutNo(String name) {
        return dao.queryLayoutNo(name);
    }

    @Override
    @OperLog(operationType="施工",operationName="施工新增")
    public void addBuild(BuildVo buildVo) throws Exception {
        Integer xh = dao.queryXhSEQ();
        buildVo.setXh(xh+"");
        String layoutNo = buildVo.getLayout_no();
        String[] layoutArr = layoutNo.split(",");
        if(layoutArr.length==0){
            throw new Exception("没有有效的规划许可证号");
        }
        buildVo.setLayout_no(layoutNo);
        System.err.println(buildVo);
        //新增基础信息
        dao.insertBuild(buildVo);
        for (String singleLay:layoutArr){
            if(!singleLay.trim().equals("")){
                buildVo.setLayout_no(singleLay);
                //新增关系表
                dao.insertRelation(buildVo);
            }
        }
    }

    @Override
    public BuildVo querySingleBuild(Integer uuid) {
        return dao.querySingleBuild(uuid);
    }

    /**
     * 修改施工信息
     * @param buildVo
     */
    @Override
    @OperLog(operationType="施工",operationName="施工编辑")
    public void updateBuild(BuildVo buildVo) throws Exception {
        //修改基础信息
        dao.updateBuild(buildVo);
        List<Integer> id = new ArrayList<Integer>();
        id.add(buildVo.getUuid());
        //删除关联表的数据
        dao.deleteRelation(id);
        //新增关联表
        String layoutNo = buildVo.getLayout_no();
        String[] layoutArr = layoutNo.split(",");
        if(layoutArr.length==0){
            throw new Exception("没有有效的规划许可证号");
        }
        buildVo.setLayout_no(layoutNo);
        for (String singleLay:layoutArr){
            if(!singleLay.trim().equals("")){
                buildVo.setLayout_no(singleLay);
                //新增关系表
                dao.insertRelation(buildVo);
            }
        }
    }

    @Override
    public void deleteBuild(List id) {
        //先删除关联表数据
        dao.deleteRelation(id);
        //删除基础数据表数据
        dao.deleteBuild(id);
    }


    @Override
    public PageResult<SearchOne> queryBAH(Map<String,String> paramsMap) {
        PageResult<SearchOne> pageResult = new PageResult<>();

        List<SearchOne> list =dao.queryBAH(paramsMap);
        pageResult.setRows(list);

        return pageResult;
    }

    @Override
    public PageResult<SearchOne> queryBSG(Map<String,String> paramsMap) {
        PageResult<SearchOne> pageResult = new PageResult<>();

        List<SearchOne> list =dao.queryBSG(paramsMap);
        pageResult.setRows(list);

        return pageResult;
    }

    @Override
    public int checkCompleted(String completed_no) {
        return dao.checkCompleted(completed_no);
    }

    @Override
    public int checkBuild(String build_no) {
        return dao.checkBuild(build_no);
    }
    //检查规划号是否关联
    @Override
    public int checkLayout(String layout_no) {
        return dao.checkLayout(layout_no);
    }

    @Override
    public int checkCompletedAssociate(String build_no) {
        return dao.checkCompletedAssociate(build_no);
    }


    @Override
    @OperLog(operationType="施工",operationName="竣工编辑")
    public void updateCompleted(Completed paramObj) {

        if(paramObj.getFile_status().equals("未提交")){
            paramObj.setFile_status("0");
        }else if(paramObj.getFile_status().equals("已提交")) {
            paramObj.setFile_status("1");
        }
        dao.updateCompleted(paramObj);
        //维护关系拓展表
        //删除旧关系
        dao.deleteCompletedExtend(paramObj.getId());

        //建立新的
        //维护关系拓展表
        Map map = new HashMap<>();
        map.put("xh",paramObj.getId());
        map.put("region_code",paramObj.getRegion_code());
        map.put("completed_no",paramObj.getCompleted_no());

        map.put("build_no","");
        //layout_no;//规划许可证号
        Object layout_no = SourceUtils.dealOneOrList(paramObj.getLayout_no());
        if(null!=layout_no && ""!=layout_no){
            if(layout_no instanceof String){
                map.put("layout_no",paramObj.getLayout_no());
                dao.insertCompletedExtend(map);
            }else {
                for (Object o : (String[])layout_no) {
                    map.put("layout_no",o.toString());
                    dao.insertCompletedExtend(map);
                }
            }
        }


        map.put("layout_no","");
        //build_no;//施工许可证号
        Object build_no = SourceUtils.dealOneOrList(paramObj.getBuild_no());
        if(null!=build_no && ""!=build_no){
            if(build_no instanceof String){
                map.put("build_no",paramObj.getBuild_no());
                dao.insertCompletedExtend(map);
            }else {
                for (Object o : (String[])build_no) {
                    map.put("build_no",o.toString());
                    dao.insertCompletedExtend(map);
                }
            }
        }

    }

    @Override
    @OperLog(operationType="施工",operationName="竣工新增")
    public void insertCompleted(Completed paramObj) {

        if(paramObj.getLayout_no()==null ){
            paramObj.setLayout_no("/");
        }
        if(paramObj.getLayout_no()!=null && paramObj.getLayout_no().endsWith(",")){
            paramObj.setLayout_no( paramObj.getLayout_no().substring(0,paramObj.getLayout_no().length()-1));
        }
        if(paramObj.getBuild_no()==null){
            paramObj.setBuild_no("/");
        }
        if(paramObj.getBuild_no()!=null && paramObj.getBuild_no().endsWith(",")){
            paramObj.setBuild_no( paramObj.getBuild_no().substring(0,paramObj.getBuild_no().length()-1));
        }

        if(paramObj.getRegion_code().equals("全域成都")) {
            paramObj.setRegion_code("");
        }

        //获取流水号序列
        Integer xh = dao.findCompletedXh();
        paramObj.setId(xh.toString());

        dao.insertCompleted(paramObj);
        //维护关系拓展表
        Map map = new HashMap<>();
        map.put("xh",xh);
        map.put("region_code",paramObj.getRegion_code());
        map.put("completed_no",paramObj.getCompleted_no());

        map.put("build_no","");
        //layout_no;//规划许可证号
        Object layout_no = SourceUtils.dealOneOrList(paramObj.getLayout_no());
        if(null!=layout_no && ""!=layout_no){
            if(layout_no instanceof String){
                map.put("layout_no",paramObj.getLayout_no());
                dao.insertCompletedExtend(map);
            }else {
                for (Object o : (String[])layout_no) {
                    map.put("layout_no",o.toString());
                    dao.insertCompletedExtend(map);
                }
            }
        }


        map.put("layout_no","");
        //build_no;//施工许可证号
        Object build_no = SourceUtils.dealOneOrList(paramObj.getBuild_no());
        if(null!= build_no && ""!=build_no){
            if(build_no instanceof String){
                map.put("build_no",paramObj.getBuild_no());
                dao.insertCompletedExtend(map);
            }else {
                for (Object o : (String[])build_no) {
                    map.put("build_no",o.toString());
                    dao.insertCompletedExtend(map);
                }
            }
        }

    }

    @Override
    public int deleteCompleteds(Map<String, Object> map) {
        return dao.deleteCompleteds(map);
    }

    @Override
    public int deleteCompleted(Map<String, Object> map) {
        return dao.deleteCompleted(map);
    }


}

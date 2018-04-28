package com.funi.platform.edg.service;/**
 * Created by as on 2016/12/13 0013.
 */

import com.funi.platform.edg.bo.*;

import java.util.List;
import java.util.Map;

/**
 * @Package: [com.funi.platform.edg.service]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/12/13 0013 18:41]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2016/12/13 0013 18:41，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */
public interface BuildService {
    PageResult<BuildVo> queryBuild(SearchBuild searchBuild);

    int updateinputFieldBs(Map<String, Object> map);

    int updateinputFieldB(Map<String, Object> map);

    PageResult<Completed> queryCompleted(SearchCompleted searchCompleted,String uuid);

    int updateinputFieldCs(Map<String, Object> map);

    int updateinputFieldC(Map<String, Object> map);

    List<Map> queryLayoutNo(String name);

    void addBuild(BuildVo buildVo) throws Exception;

    BuildVo querySingleBuild(Integer uuid);

    void updateBuild(BuildVo buildVo) throws Exception;

    void deleteBuild(List id);

    PageResult<SearchOne> queryBAH(Map<String,String> paramsMap);

    void updateCompleted(Completed paramObj);

    void insertCompleted(Completed paramObj);

    int deleteCompleteds(Map<String, Object> map);

    int deleteCompleted(Map<String, Object> map);

    PageResult<SearchOne> queryBSG(Map<String,String> paramsMap);

    int checkCompleted(String completed_no);

    int checkBuild(String build_no);

    int checkLayout(String layout_no);

    int checkCompletedAssociate(String build_no);
}

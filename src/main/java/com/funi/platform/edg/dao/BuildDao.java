package com.funi.platform.edg.dao;/**
 * Created by as on 2016/12/13 0013.
 */

import com.funi.platform.edg.bo.Build;
import com.funi.platform.edg.bo.BuildVo;
import com.funi.platform.edg.bo.Completed;
import com.funi.platform.edg.bo.SearchOne;

import java.util.List;
import java.util.Map;

/**
 * @Package: [com.funi.platform.edg.dao]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/12/13 0013 18:42]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2016/12/13 0013 18:42，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */
public interface BuildDao {
    List<BuildVo> queryBuild(Map params);

    Long queryTotal(Map params);

    int updateinputFieldBs(Map<String, Object> map);

    int updateinputFieldB(Map<String, Object> map);

    List<Completed> queryCompleted(Map params);

    Long queryTotalCompleted(Map params);

    int updateinputFieldC(Map<String, Object> map);

    int updateinputFieldCs(Map<String, Object> map);

    List<Map> queryLayoutNo(String name);

    void insertBuild(BuildVo buildVo);

    void insertRelation(BuildVo buildVo);

    void deleteBuild(List id);

    void deleteRelation(List id);

    Integer queryXhSEQ();

    BuildVo querySingleBuild(Integer uuid);

    void updateBuild(BuildVo buildVo);

    List<SearchOne> queryBAH(Map<String,String> paramsMap);

    void insertCompleted(Completed paramObj);

    void insertCompletedExtend(Map map);

    int deleteCompleteds(Map<String, Object> map);

    int deleteCompleted(Map<String, Object> map);

    void updateCompleted(Completed paramObj);

    void deleteCompletedExtend(String id);

    Integer findCompletedXh();

    List<SearchOne> queryBSG(Map<String,String> paramsMap);

    int checkCompleted(String completed_no);

    int checkBuild(String  build_no);

    int checkLayout(String layout_no);

    int checkCompletedAssociate(String build_no);

}

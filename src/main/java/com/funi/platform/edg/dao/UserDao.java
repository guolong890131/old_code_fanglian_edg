package com.funi.platform.edg.dao;

import com.funi.platform.edg.bo.FieldVo;
import com.funi.platform.edg.bo.UserVo;

import java.util.List;
import java.util.Map;

/**
 * @author jin.ran@funi365.com
 * @version V1.0
 * @Title: funi-platform-edg
 * @Package com.funi.platform.edg.dao
 * @date 2016/12/13 0013 19:07
 * @Description: ${todo}(用一句话描述该文件做什么)
 */
public interface UserDao {

    public List<UserVo> getUserList(UserVo userVo);

     Integer getUserListCount(UserVo userVo);

     UserVo getUserInfo(String uuid);

     void updateUser(UserVo userVo);

     void deleteUser(List uuid);

    void insertUser(UserVo userVo);

    void fastEditUser(UserVo userVo);

    String findOldPasswordMD5(String uuid);

    void fastEditPass4Date(Map<String,String> parMap);

    void updateUnique(UserVo userInfo);

    List<FieldVo> getEtlField(UserVo userVo);

    Integer getEtlFieldCount(UserVo userVo);
}

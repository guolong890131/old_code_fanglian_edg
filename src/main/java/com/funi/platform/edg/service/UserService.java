package com.funi.platform.edg.service;

import com.funi.platform.edg.bo.FieldVo;
import com.funi.platform.edg.bo.UserVo;

import java.util.List;
import java.util.Map;

/**
 * @author jin.ran@funi365.com
 * @version V1.0
 * @Title: funi-platform-edg
 * @Package com.funi.platform.edg.service
 * @date 2016/12/13 0013 19:04
 * @Description: ${todo}(用一句话描述该文件做什么)
 */
public interface UserService {

    public List<UserVo> getUserList(UserVo userVo);

    public Integer getUserListCount(UserVo userVo);

    public UserVo getUserInfo(String uuid);

    public void updateUser(UserVo userVo);

    public void deleteUser(List uuid);

    public void addUser(UserVo userVo) throws Exception;

    void fastEditUser(UserVo userVo);

    String findOldPasswordMD5(String uuid);

    void fastEditPass4Date(Map<String,String> parMap);

    void updateUniquOfUser(UserVo userInfo);

    List<FieldVo> getEtlField(UserVo userVo);

    Integer getEtlFieldCount(UserVo userVo);
}

package com.funi.platform.edg.service.impl;

import com.funi.platform.edg.annotation.OperLog;
import com.funi.platform.edg.bo.FieldVo;
import com.funi.platform.edg.bo.UserVo;
import com.funi.platform.edg.dao.UserDao;
import com.funi.platform.edg.service.UserService;
import com.funi.platform.edg.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author jin.ran@funi365.com
 * @version V1.0
 * @Title: funi-platform-edg
 * @Package com.funi.platform.edg.service.impl
 * @date 2016/12/13 0013 19:06
 * @Description: ${todo}(用一句话描述该文件做什么)
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public List<UserVo> getUserList(UserVo userVo) {
        return userDao.getUserList(userVo);
    }

    @Override
    public Integer getUserListCount(UserVo userVo) {
        return userDao.getUserListCount(userVo);
    }

    @Override
    public UserVo getUserInfo(String uuid) {
        return userDao.getUserInfo(uuid);
    }

    @Override
    @OperLog(operationType="用户管理",operationName="修改用户信息")
    public void updateUser(UserVo userVo) {
        userDao.updateUser(userVo);
    }

    @Override
    @OperLog(operationType="登录用户",operationName="快捷修改用户相关信息")
    public void fastEditUser(UserVo userVo) {
        userDao.fastEditUser(userVo);
    }

    @Override
    public String findOldPasswordMD5(String uuid) {
        return userDao.findOldPasswordMD5(uuid);
    }

    @Override
    @OperLog(operationType="密码失效",operationName="修改用户密码")
    public void fastEditPass4Date(Map<String,String> parMap) {
        userDao.fastEditPass4Date(parMap);
    }

    @Override
    public void updateUniquOfUser(UserVo userInfo) {
        userDao.updateUnique(userInfo);
    }


    @Override
    //查询国土中间库的数据
    public List<FieldVo> getEtlField(UserVo userVo) {
        return userDao.getEtlField(userVo);
    }

    @Override
    //查询国土中间库的数据条数
    public Integer getEtlFieldCount(UserVo userVo) {
        return userDao.getEtlFieldCount(userVo);
    }


    @Override
    public void deleteUser(List uuid) {
        userDao.deleteUser(uuid);
    }

    @Override
    @OperLog(operationType="用户管理",operationName="新增用户")
    public void addUser(UserVo userVo) throws Exception {
        UserVo temp = new UserVo();
        temp.setLoginname(userVo.getLoginname());
        List users = this.userDao.getUserList(temp);
        if(users.size()>0){
            throw new Exception("已存在相同的账号，请重试");
        }
        userVo.setUuid(StringUtils.getUuid());
        userDao.insertUser(userVo);
    }



}

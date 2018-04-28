package com.funi.platform.edg.service.impl;

import com.funi.platform.edg.bo.Menu;
import com.funi.platform.edg.dao.LoginDao;
import com.funi.platform.edg.service.loginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package: [com.funi.platform.edg.service.impl]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/10/10 0010 16:44]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2016/10/10 0010 16:44，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录，增加修改记录)]
 * @Version: [v2.0]
 */
@Service
public class LoginServiceImpl implements loginService {

    @Resource
    private LoginDao dao;

    /**
     *
     * @param paramsMap
     * @return
     */
    @Override
    public int checkUser(Map<String, String> paramsMap) {
        int u = dao.findUser(paramsMap);
        if(u==0){
            int n =dao.findUserName(paramsMap);
            if(n==0){
                return 1;//无效用户名
            }else {
                return 2;//无效密码
            }
        }
        return 0;//登录成功
    }

    @Override
    public Map<String, String> findRole(Map<String, String> paramsMap) {
        return dao.findRole(paramsMap);
    }

    @Override
    public List<Menu> findMenu(String role) {
        return dao.findMenu(role);
    }

    @Override
    public HashMap getUserDate(String uuid) {
        return dao.getUserDate(uuid);
    }
}

package com.funi.platform.edg.controller;/**
 * Created by as on 2016/12/10 0010.
 */

import com.funi.platform.edg.bo.*;
import com.funi.platform.edg.service.CommonService;
import com.funi.platform.edg.service.SystemLogService;
import com.funi.platform.edg.service.loginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Package: [com.funi.platform.edg.controller]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/12/10 0010 11:23]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2016/12/10 0010 11:23，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */
@Controller
@RequestMapping("/log")
public class LogController {

    @Autowired
    private loginService service;
    @Resource
    private CommonService commonService;

    @Resource
    private SystemLogService systemLogService;

    @RequestMapping("/index")
    public ModelAndView index(HttpSession session) {
        ModelAndView mv = null;

        String account = (String)session.getAttribute("account");
        String password = (String)session.getAttribute("password");
        String role = (String)session.getAttribute("role");
        String uuid = (String)session.getAttribute("uuid");
        if(account==null && password==null){
            mv = new ModelAndView("redirect:/user/esc");
            return mv;
        }

        List<Menu> listMenu = service.findMenu(role);
        boolean flag = true;
        for (Menu menu : listMenu) {
            if(menu.getMenu_code().equals("log")){
                flag=false;
            }
        }
        if(flag){
            mv = new ModelAndView("redirect:/user/esc");
            return mv;
        }

        mv = new ModelAndView("/pages/log");
        List<Map> regList =  commonService.queryRegion();
        mv.addObject("regList",regList);
        mv.addObject("userid",uuid);
        return mv;

    }

    /**
     * 操作权限检查
     * @param session
     * @return
     */
    public boolean check(HttpSession session){
        String account = (String)session.getAttribute("account");
        String password = (String)session.getAttribute("password");
        String role = (String)session.getAttribute("role");
        if(account==null && password==null){
            return true;
        }
        List<Menu> listMenu = service.findMenu(role);
        boolean flag = true;
        for (Menu menu : listMenu) {
            if(menu.getMenu_code().equals("log")){
                flag=false;
            }
        }
        return flag;
    }

    @RequestMapping("/queryLog")
    @ResponseBody
    public PageResult<SystemLog> queryLog(SearchLog searchLog,HttpSession session) {
        if(check(session)){
//            response.sendRedirect("index");
            return null;
        }
        PageResult<SystemLog> result = systemLogService.queryLog(searchLog);
        return result;
    }
}

package com.funi.platform.edg.controller;/**
 * Created by as on 2016/12/10 0010.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.funi.framework.mvc.core.view.ResultView;
import com.funi.platform.edg.annotation.OperLog;
import com.funi.platform.edg.bo.*;
import com.funi.platform.edg.service.BuildService;
import com.funi.platform.edg.service.CommonService;
import com.funi.platform.edg.service.LayoutService;
import com.funi.platform.edg.service.loginService;
import com.funi.platform.edg.utils.RequestMapUtils;
import com.funi.platform.edg.utils.SourceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package: [com.funi.platform.edg.controller]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/12/10 0010 11:20]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2016/12/10 0010 11:20，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */
@Controller
@RequestMapping("/layout")
public class LayoutController {

    @Autowired
    private loginService service;
    @Resource
    private CommonService commonService;

    @Resource
    private LayoutService layoutService;
    @Resource
    private BuildService buildService;
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
            if(menu.getMenu_code().equals("layout")){
                flag=false;
            }
        }
        if(flag){
            mv = new ModelAndView("redirect:/user/esc");
            return mv;
        }

        mv = new ModelAndView("/pages/layout");
//        List<Map> regList =  commonService.queryRegion();
        List<Dict> regList4user = commonService.queryRegion4user(uuid);
//        mv.addObject("regList",regList);
        mv.addObject("regList4user",regList4user);
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
            if(menu.getMenu_code().equals("layout")){
                flag=false;
            }
        }
        return flag;
    }


    @RequestMapping("/queryLayout")
    @ResponseBody
    public PageResult<Layout> queryLayout(SearchLayout searchLayout,HttpSession session) {
        if(check(session)){
            return null;
        }

        String uuid = (String)session.getAttribute("uuid");
        PageResult<Layout> result = layoutService.queryLayout(searchLayout,uuid);
        return result;
    }

    @RequestMapping("/apply")
    public ModelAndView apply(HttpServletRequest request, HttpSession session) {
        ModelAndView mv = null;

        String account = (String)session.getAttribute("account");
        String password = (String)session.getAttribute("password");
        String role = (String)session.getAttribute("role");
        String uuid = (String)session.getAttribute("uuid");
        List<Dict> regList4user = commonService.queryRegion4user(uuid);
        Dict dict = regList4user.get(0);

        if(account==null && password==null){
            mv = new ModelAndView("redirect:/user/esc");
            return mv;
        }

        List<Menu> listMenu = service.findMenu(role);
        boolean flag = true;
        for (Menu menu : listMenu) {
            if(menu.getMenu_code().equals("layout")){
                flag=false;
            }
        }
        if(flag){
            mv = new ModelAndView("redirect:/user/esc");
            return mv;
        }

        final Map<String,String> paramsMap = RequestMapUtils.getRequestMaps(request);//view,edit,add
        String type= paramsMap.get("type");
        if("view".equals(type)){
            mv = new ModelAndView("/pages/layoutApplyShow");
            mv.addObject("data",paramsMap.get("data"));
            return mv;
        }
        //回撤
        if("rollback".equals(type)){
            mv = new ModelAndView("/pages/layoutRollback");
            mv.addObject("data",paramsMap.get("data"));
            String data = paramsMap.get("data");
            JSONObject jsonObject = JSON.parseObject(data);
            String layout_no = jsonObject.getString("layout_no");
            //查询是否被规划关联
            int count= buildService.checkLayout(layout_no);
            if(count>0) {
                mv.addObject("associate", true);
            }


            return mv;
        }

        mv = new ModelAndView("/pages/layoutApply");
        List<Map> regList =  commonService.queryRegion();
        mv.addObject("regList",regList);
        mv.addObject("userid",uuid);
        mv.addObject("dict",dict);
        if("edit".equals(type)){
            mv.addObject("data",paramsMap.get("data"));
        }

        return mv;
    }

    @RequestMapping("/addContractNo")
    public String addContractNo(HttpSession session) {
        if(check(session)){
            return "redirect:/user/esc";
        }
        return "/pages/addContractNo";
    }


    @RequestMapping("/addTypeNo")
    public String addTypeNo(HttpSession session) {
        if(check(session)){
            return "redirect:/user/esc";
        }
        return "/pages/addTypeNo";
    }



    //删除
    @RequestMapping("/deleteField")
    @OperLog(operationType="规划",operationName="删除")
    @ResponseBody
    public ResultView deleteField(HttpServletRequest request,HttpSession session) {
        if(check(session)){
            return null;
        }

        final Map<String,String> paramsMap = RequestMapUtils.getRequestMaps(request) ;
        String ids =paramsMap.get("ids");
        Map<String,Object> map = new HashMap<>();
        int i = 0;
        if(ids.contains(",")){
            map.put("ids",SourceUtils.dealList(ids));
            i = layoutService.deleteLayouts(map);
        }else {
            map.put("ids",ids);
            i = layoutService.deleteLayout(map);
        }
        return ResultView.newResult(i);
    }


    //归档
    @RequestMapping("/inputField")
    @OperLog(operationType="规划",operationName="提交")
    @ResponseBody
    public ResultView inputField(HttpServletRequest request,HttpSession session) {
        if(check(session)){
            return null;
        }

        final Map<String,String> paramsMap = RequestMapUtils.getRequestMaps(request) ;
        String ids =paramsMap.get("ids");
        Map<String,Object> map = new HashMap<>();
        int i = 0;
        if(ids.contains(",")){
            map.put("ids", SourceUtils.dealList(ids));
            i = layoutService.updateInputLayouts(map);
        }else {
            map.put("ids",ids);
            i = layoutService.updateInputLayout(map);
        }
        return ResultView.newResult(i);
    }


    //查询 土地合同编号
    @RequestMapping("/queryBAH")
    @ResponseBody
    public PageResult<SearchOne> queryBAH(HttpServletRequest request,HttpSession session) {
        if(check(session)){
            return null;
        }

        final Map<String,String> paramsMap = RequestMapUtils.getRequestMaps(request) ;
//        paramsMap.get("fctrno_new")
        PageResult<SearchOne> result = layoutService.queryBAH(paramsMap);
        return result;
    }

    //查询 不动产证号
    @RequestMapping("/queryBDC")
    @ResponseBody
    public PageResult<SearchOne> queryBDC(HttpServletRequest request,HttpSession session) {
        if(check(session)){
            return null;
        }

        final Map<String,String> paramsMap = RequestMapUtils.getRequestMaps(request) ;
//        paramsMap.get("fctrno_new")
        PageResult<SearchOne> result = layoutService.queryBDC(paramsMap);
        return result;
    }



    @RequestMapping("/add")
    @ResponseBody
    public ResultView add(Layout paramObj,HttpSession session) {
        if(check(session)){
            return null;
        }

        ResultView rv = ResultView.newResult("");
        String id = paramObj.getId();
        if(id!=null && id!=""){
            try {
                //编辑
                layoutService.updateLayout(paramObj);
            }catch (Exception e){
                rv.setSuccess(false);
                rv.setMessage(e.getMessage());
            }
        }else {
            try{
                //数据重复录入检查
                int i = layoutService.checkLayout(paramObj.getLayout_no());
                if(i==0){
                    //新增
                    layoutService.insertLayout(paramObj);
                }else {
                    rv.setSuccess(false);
                    rv.setMessage("规划许可证号已存在，请勿重复录入！");
                }
            }catch (Exception e){
                rv.setSuccess(false);
                rv.setMessage(e.getMessage());
            }
        }
        return rv;
    }
}

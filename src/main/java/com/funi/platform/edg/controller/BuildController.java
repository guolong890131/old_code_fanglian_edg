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
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package: [com.funi.platform.edg.controller]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/12/10 0010 11:21]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2016/12/10 0010 11:21，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */
@Controller
@RequestMapping("/build")
public class BuildController {

    @Autowired
    private loginService service;
    @Resource
    private CommonService commonService;
    @Resource
    private BuildService buildService;

    @RequestMapping("/index")
    public ModelAndView index(HttpSession session) {
        ModelAndView mv = null;

        String account = (String)session.getAttribute("account");
        String password = (String)session.getAttribute("password");
        String role = (String)session.getAttribute("role");

        if(account==null && password==null){
            mv = new ModelAndView("redirect:/user/esc");
            return mv;
        }

        List<Menu> listMenu = service.findMenu(role);
        boolean flag = true;
        for (Menu menu : listMenu) {
            if(menu.getMenu_code().equals("build")){
                flag=false;
            }
        }
        if(flag){
            mv = new ModelAndView("redirect:/user/esc");
            return mv;
        }

        mv = new ModelAndView("/pages/buildAndCompleted");

        return mv;
    }

    @RequestMapping("/build")
    public ModelAndView build(HttpSession session) {
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
            if(menu.getMenu_code().equals("build")){
                flag=false;
            }
        }
        if(flag){
            mv = new ModelAndView("redirect:/user/esc");
            return mv;
        }

        mv = new ModelAndView("/pages/build");
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
            if(menu.getMenu_code().equals("build")){
                flag=false;
            }
        }
        return flag;
    }



    @RequestMapping("/queryBuild")
    @ResponseBody
    public PageResult<BuildVo> queryBuild(SearchBuild searchBuild,HttpSession session) {
        if(check(session)){
            return null;
        }

        searchBuild.setUseruuid(session.getAttribute("uuid").toString());
        PageResult<BuildVo> result = buildService.queryBuild(searchBuild);
        return result;
    }


    @RequestMapping("/buildApply")
    public ModelAndView buildApply(HttpServletRequest request, HttpSession session) {

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
            if(menu.getMenu_code().equals("build")){
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
            mv = new ModelAndView("/pages/buildApplyShow");
            mv.addObject("data",paramsMap.get("data"));
            return mv;
        }
        if("rollback".equals(type)){
            mv = new ModelAndView("/pages/buildRollback");
            mv.addObject("data",paramsMap.get("data"));
            String data = paramsMap.get("data");
            JSONObject jsonObject = JSON.parseObject(data);
            String build_no = jsonObject.getString("build_no");
            //查询是否被规划关联
            int count= buildService.checkCompletedAssociate(build_no);
            if(count>0) {
                mv.addObject("associate", true);
            }
            return mv;
        }

        mv = new ModelAndView("/pages/buildApply");
        List<Map> regList =  commonService.queryRegion();
        mv.addObject("regList",regList);
        mv.addObject("userid",uuid);
        mv.addObject("dict",dict);

        if("edit".equals(type)){
            mv.addObject("data",paramsMap.get("data"));
        }

        return mv;
    }

    @RequestMapping("/inputFieldB")
    @OperLog(operationType="施工",operationName="施工提交")
    @ResponseBody
    public ResultView inputFieldB(HttpServletRequest request, HttpSession session) {
        if(check(session)){
            return null;
        }

        final Map<String,String> paramsMap = RequestMapUtils.getRequestMaps(request) ;
        String ids =paramsMap.get("ids");
        Map<String,Object> map = new HashMap<>();
        int i = 0;
        if(ids.contains(",")){
            map.put("ids", SourceUtils.dealList(ids));
            i = buildService.updateinputFieldBs(map);
        }else {
            map.put("ids",ids);
            i = buildService.updateinputFieldB(map);
        }
        return ResultView.newResult(i);
    }


    @RequestMapping("/completed")
    public ModelAndView completed(HttpSession session) {
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
            if(menu.getMenu_code().equals("build")){
                flag=false;
            }
        }
        if(flag){
            mv = new ModelAndView("redirect:/user/esc");
            return mv;
        }

        mv = new ModelAndView("/pages/completed");
//        List<Map> regList =  commonService.queryRegion();
        List<Dict> regList4user = commonService.queryRegion4user(uuid);
//        mv.addObject("regList",regList);
        mv.addObject("regList4user",regList4user);
        mv.addObject("userid",uuid);
        return mv;
    }

    @RequestMapping("/queryCompleted")
    @ResponseBody
    public PageResult<Completed> queryCompleted(SearchCompleted searchCompleted,HttpSession session) {
        if(check(session)){
            return null;
        }

        String uuid = (String)session.getAttribute("uuid");
        PageResult<Completed> result = buildService.queryCompleted(searchCompleted,uuid);
        return result;
    }

    @RequestMapping("/completedApply")
    public ModelAndView completedApply(HttpServletRequest request, HttpSession session) {

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
            if(menu.getMenu_code().equals("build")){
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
            mv = new ModelAndView("/pages/completedApplyShow");
            mv.addObject("data",paramsMap.get("data"));
            return mv;
        }
        if("rollback".equals(type)){
            mv = new ModelAndView("/pages/completedRollback");
            mv.addObject("data",paramsMap.get("data"));
            return mv;
        }

        mv = new ModelAndView("/pages/completedApply");
        List<Map> regList =  commonService.queryRegion();
        mv.addObject("regList",regList);
        mv.addObject("userid",uuid);
        mv.addObject("dict",dict);

        if("edit".equals(type)){
            mv.addObject("data",paramsMap.get("data"));
        }

        return mv;
    }

    @RequestMapping("/inputFieldC")
    @OperLog(operationType="施工",operationName="竣工提交")
    @ResponseBody
    public ResultView inputFieldC(HttpServletRequest request,HttpSession session) {
        if(check(session)){
            return null;
        }
        final Map<String,String> paramsMap = RequestMapUtils.getRequestMaps(request) ;
        String ids =paramsMap.get("ids");
        Map<String,Object> map = new HashMap<>();
        int i = 0;
        if(ids.contains(",")){
            map.put("ids", SourceUtils.dealList(ids));
            i = buildService.updateinputFieldCs(map);
        }else {
            map.put("ids",ids);
            i = buildService.updateinputFieldC(map);
        }
        return ResultView.newResult(i);
    }

    //进入选择许可证页面
    @RequestMapping("/addContractNoWithBuild")
    public String addContractNo(HttpSession session) {
        if(check(session)){
            return "redirect:/user/esc";
        }
        return "/pages/addContractNoWithBuild";
    }

    /**
     * 获取许可证书
     * @param name
     * @return
     */
    @RequestMapping("/getContractNoList")
    @ResponseBody
    public Map getContractNoList(String name,HttpSession session) {
        if(check(session)){
            return null;
        }
        List<Map> list = buildService.queryLayoutNo(name);
        Map _res = new HashMap();
        _res.put("rows",list);
        return _res;
    }

    /**
     * 新增施工证数据填报
     * @param buildVo
     * @return
     */
    @ResponseBody
    @RequestMapping("/addBuild")
    public Map addBuild(BuildVo buildVo,HttpSession session) {
        if(check(session)){
            return null;
        }

        buildVo.setInputer_id(session.getAttribute("uuid").toString());
        Map _res = new HashMap();
        if(buildVo.getUuid()!=null && buildVo.getUuid()!=0){
            try {
                buildService.updateBuild(buildVo);
                _res.put("status", "success");
                _res.put("message", "修改成功");
            } catch (Exception e) {
                _res.put("status", "fail");
                _res.put("message", e.getMessage());
            }
        }else {
            try {
                //数据重复录入检查
                int i = buildService.checkBuild(buildVo.getBuild_no());
                if(i==0){
                    buildService.addBuild(buildVo);
                    _res.put("status", "success");
                    _res.put("message", "新增成功");
                }else {
                    _res.put("status", "fail");
                    _res.put("message", "施工许可证号已存在，请勿重复录入！");
                }

            } catch (Exception e) {
                _res.put("status", "fail");
                _res.put("message", e.getMessage());
            }
        }
        return _res;
    }

    /**
     * 查看施工页面
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("/view")
    public ModelAndView view(HttpServletRequest request, HttpSession session) {

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
            if(menu.getMenu_code().equals("build")){
                flag=false;
            }
        }
        if(flag){
            mv = new ModelAndView("redirect:/user/esc");
            return mv;
        }

        final Map<String,String> paramsMap = RequestMapUtils.getRequestMaps(request);//view,edit,add
        String type= paramsMap.get("type");
        mv = new ModelAndView("/pages/buildApply");
        List<Map> regList =  commonService.queryRegion();
        mv.addObject("regList",regList);
        mv.addObject("userid",uuid);
        mv.addObject("type",type);
        if("edit".equals(type)){
            mv.addObject("data",paramsMap.get("data"));
        }
        return mv;
    }


    /**
     * 删除施工信息
     * @param buildVo
     * @return
     */
    @RequestMapping("/deleteBuild")
    @OperLog(operationType="施工",operationName="施工删除")
    @ResponseBody
    public Map deleteBuild(BuildVo buildVo,HttpSession session) {
        if(check(session)){
            return null;
        }

        Map _res = new HashMap();
        try {
            buildService.deleteBuild(buildVo.getUuidList());
            _res.put("status", "success");
            _res.put("message", "删除成功");
        } catch (Exception e) {
            _res.put("status", "fail");
            _res.put("message", e.getMessage());
        }
        return _res;
    }





    @RequestMapping("/addContractNo4Completed")
    public String addContractNo4Completed(HttpSession session) {
        if(check(session)){
            return "redirect:/user/esc";
        }
        return "/pages/addContractNo4Completed";
    }

    @RequestMapping("/addContractNo4Completed2")
    public String addContractNo4Completed2(HttpSession session) {
        if(check(session)){
            return "redirect:/user/esc";
        }
        return "/pages/addContractNo4Completed2";
    }


    //查询 规划许可证号
    @RequestMapping("/queryBAH")
    @ResponseBody
    public PageResult<SearchOne> queryBAH(HttpServletRequest request,HttpSession session) {
        if(check(session)){
            return null;
        }

        final Map<String,String> paramsMap = RequestMapUtils.getRequestMaps(request) ;

        PageResult<SearchOne> result = buildService.queryBAH(paramsMap);
        return result;
    }


    //查询 施工许可证书编号
    @RequestMapping("/queryBSG")
    @ResponseBody
    public PageResult<SearchOne> queryBSG(HttpServletRequest request,HttpSession session) {
        if(check(session)){
            return null;
        }

        final Map<String,String> paramsMap = RequestMapUtils.getRequestMaps(request) ;

        PageResult<SearchOne> result = buildService.queryBSG(paramsMap);
        return result;
    }


    @RequestMapping("/addCompleted")
    @ResponseBody
    public ResultView addCompleted(Completed paramObj,HttpSession session) {
        if(check(session)){
            return null;
        }

        ResultView rv = ResultView.newResult("");

        String id = paramObj.getId();
        if(id!=null && id!=""){
            try {
                //编辑
                buildService.updateCompleted(paramObj);
            }catch (Exception e){
                rv.setSuccess(false);
                rv.setMessage(e.getMessage());
            }
        }else {
            try{
            //数据重复录入检查
                int i = buildService.checkCompleted(paramObj.getCompleted_no());
                if(i==0){
                    //新增
                    buildService.insertCompleted(paramObj);
                }else {
                    rv.setSuccess(false);
                    rv.setMessage("工程竣工备案表编号已存在，请勿重复录入！");
                }
            }catch (Exception e){
                rv.setSuccess(false);
                rv.setMessage(e.getMessage());
            }
        }
        return rv;
    }


    //删除
    @RequestMapping("/deleteCompleted")
    @OperLog(operationType="施工",operationName="竣工删除")
    @ResponseBody
    public ResultView deleteCompleted(HttpServletRequest request,HttpSession session) {
        if(check(session)){
            return null;
        }

        final Map<String,String> paramsMap = RequestMapUtils.getRequestMaps(request) ;
        String ids =paramsMap.get("ids");
        Map<String,Object> map = new HashMap<>();
        int i = 0;
        if(ids.contains(",")){
            map.put("ids",SourceUtils.dealList(ids));
            i = buildService.deleteCompleteds(map);
        }else {
            map.put("ids",ids);
            i = buildService.deleteCompleted(map);
        }
        return ResultView.newResult(i);
    }
}


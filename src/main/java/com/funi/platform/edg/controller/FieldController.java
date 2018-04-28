package com.funi.platform.edg.controller;/**
 * Created by as on 2016/12/10 0010.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.funi.framework.mvc.core.view.ResultView;
import com.funi.platform.edg.annotation.OperLog;
import com.funi.platform.edg.bo.*;
import com.funi.platform.edg.service.CommonService;
import com.funi.platform.edg.service.FieldService;
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
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package: [com.funi.platform.edg.controller]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/12/10 0010 11:13]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2016/12/10 0010 11:13，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */
@Controller
@RequestMapping("/field")
public class FieldController {

    @Autowired
    private loginService service;
    @Resource
    private CommonService commonService;
    @Resource
    private FieldService fieldService;
    @Resource
    private LayoutService layoutService;

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
            if(menu.getMenu_code().equals("field")){
                flag=false;
            }
        }
        if(flag){
            mv = new ModelAndView("redirect:/user/esc");
            return mv;
        }
        mv=new ModelAndView("/pages/field");
//        List<Map> regList =  commonService.queryRegion();
        List<Dict> regList4user = commonService.queryRegion4user(uuid);
//        mv.addObject("regList",regList);
        mv.addObject("regList4user",regList4user);
        mv.addObject("userid",uuid);
        return mv;
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
            if(menu.getMenu_code().equals("field")){
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
            mv = new ModelAndView("/pages/fieldApplyShow");
            mv.addObject("data",paramsMap.get("data"));
            return mv;
        }
        //回撤
        if("rollback".equals(type)){
            mv = new ModelAndView("/pages/fieldRollback");
            mv.addObject("data",paramsMap.get("data"));
            String data = paramsMap.get("data");
            JSONObject jsonObject = JSON.parseObject(data);
            String fctrno_new = jsonObject.getString("fctrno_new");
            String[] fctrno_news = fctrno_new.split(",");
            //查询是否被规划关联
            String associate="";
            String unassociate="";
            for(String split:fctrno_news){
               int count= layoutService.checkField(split);
                if(count>0){
                    associate=associate+split+",";
                }else{
                    unassociate=unassociate+split+",";
                }

            }
            mv.addObject("associate",associate);
            mv.addObject("unassociate",unassociate);

            String ffileno = jsonObject.getString("ffileno");
            String[] ffilenos = ffileno.split(",");
            String ffileno_associate="";
            String ffileno_unassociate="";
            for(String fs:ffilenos){
                int count= layoutService.checkFfileno(fs);
                if(count>0){
                    ffileno_associate=ffileno_associate+fs+",";
                }else{
                    ffileno_unassociate=ffileno_unassociate+fs+",";
                }

                mv.addObject("ffileno_associate",ffileno_associate);
                mv.addObject("ffileno_unassociate",ffileno_unassociate);
            }
            System.err.println("ffileno_associate"+ffileno_associate);
            System.err.println("ffileno_unassociate"+ffileno_unassociate);
            return mv;
        }

        mv = new ModelAndView("/pages/fieldApply");
        List<Map> regList =  commonService.queryRegion();
        mv.addObject("regList",regList);
        mv.addObject("userid",uuid);
        mv.addObject("dict",dict);

        if("edit".equals(type)){
            mv.addObject("data",paramsMap.get("data"));
        }

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
            if(menu.getMenu_code().equals("field")){
                flag=false;
            }
        }
        return flag;
    }


    @RequestMapping("/queryField")
    @ResponseBody
    public PageResult<Field> queryField(SearchField searchField,HttpSession session)  {
        if(check(session)){
            return null;
        }
        String uuid = (String)session.getAttribute("uuid");
        PageResult<Field> result = fieldService.queryField(searchField,uuid);
        return result;
    }

    @RequestMapping("/queryFtradetype")
    @ResponseBody
    public List<Dict> queryFtradetype(HttpSession session)  {
        if(check(session)){
            return null;
        }
        List<Dict> list = commonService.findDICT("土地出让方式类");
        return list;
    }


    @RequestMapping("/deleteField")
    @OperLog(operationType="土地",operationName="删除")
    @ResponseBody
    public ResultView deleteField(HttpServletRequest request,HttpSession session)  {
        if(check(session)){
            return null;
        }

        final Map<String,String> paramsMap = RequestMapUtils.getRequestMaps(request) ;
        String ids =paramsMap.get("ids");
        Map<String,Object> map = new HashMap<>();
        int i = 0;
        if(ids.contains(",")){
            map.put("ids",SourceUtils.dealList(ids));
            i = fieldService.deleteFields(map);
        }else {
            map.put("ids",ids);
            i = fieldService.deleteField(map);
        }
        return ResultView.newResult(i);
    }

    @RequestMapping("/inputField")
    @OperLog(operationType="土地",operationName="提交")
    @ResponseBody
    public ResultView inputField(HttpServletRequest request,HttpSession session)  {
        if(check(session)){
            return null;
        }

        final Map<String,String> paramsMap = RequestMapUtils.getRequestMaps(request) ;
        String ids =paramsMap.get("ids");
        Map<String,Object> map = new HashMap<>();
        int i = 0;
        if(ids.contains(",")){
            map.put("ids",SourceUtils.dealList(ids));
            i = fieldService.updateInputFields(map);
        }else {
            map.put("ids",ids);
            i = fieldService.updateInputField(map);
        }
        return ResultView.newResult(i);
    }


    @RequestMapping("/add")
    @ResponseBody
    public ResultView add(Field paramObj,HttpSession session)  {
        if(check(session)){
            return null;
        }
        //ffileno  fctrno_new  处理用户中文输入逗号
        if(paramObj.getFfileno() != null && paramObj.getFfileno() != ""){
            paramObj.setFfileno(paramObj.getFfileno().replaceAll("，",","));
        }
        if(paramObj.getFctrno_new() != null && paramObj.getFctrno_new() != ""){
            paramObj.setFctrno_new(paramObj.getFctrno_new().replaceAll("，",","));
        }

        ResultView rv = ResultView.newResult("");

        String id = paramObj.getId();
        if(id!=null && id!=""){
            try {
                //编辑
                fieldService.updateField(paramObj);
            }catch (Exception e){
                rv.setSuccess(false);
                rv.setMessage("编辑失败，请检查录入数据是否正确");
                rv.setResult(e.getMessage());
            }
        }else {
            try{
                //数据重复录入检查
                int i = fieldService.checkField(paramObj.getFno_new());
                if(i==0){
                    //新增
                    paramObj.setId(SourceUtils.getUUID());
                    fieldService.insertField(paramObj);
                }else {
                    rv.setSuccess(false);
                    rv.setMessage("土地宗号已存在，请勿重复录入！");
                }
            }catch (Exception e){
                rv.setSuccess(false);
                rv.setMessage("新增失败，请检查录入数据是否正确");
                rv.setResult(e.getMessage());
            }
        }
        return rv;
    }


    @RequestMapping("/addNum")
    public String addNum(HttpSession session)  {
        if(check(session)){
            return "redirect:/user/esc";
        }
        return "/pages/addNum";
    }

}

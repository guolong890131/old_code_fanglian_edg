package com.funi.platform.edg.controller;/**
 * Created by as on 2016/12/10 0010.
 */

import com.funi.framework.mvc.core.view.ResultView;
import com.funi.platform.edg.annotation.OperLog;
import com.funi.platform.edg.bo.*;
import com.funi.platform.edg.service.CommonService;
import com.funi.platform.edg.service.UserService;
import com.funi.platform.edg.service.loginService;
import com.funi.platform.edg.utils.RequestMapUtils;
import net.sf.json.JSONArray;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Package: [com.funi.platform.edg.controller]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/12/10 0010 11:24]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2016/12/10 0010 11:24，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */
@Controller
@RequestMapping("/users")
public class UserController {

    @Resource
    private loginService service;
    @Resource
    private CommonService commonService;
    @Resource
    private UserService userService;

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
            if(menu.getMenu_code().equals("users")){
                flag=false;
            }
        }
        if(flag){
            mv = new ModelAndView("redirect:/user/esc");
            return mv;
        }

        mv = new ModelAndView("/pages/userAndEtl");

        return mv;
    }

    //跳转用户信心页面
    @RequestMapping("/user")
    public  ModelAndView  userTable(HttpSession session){
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
            if(menu.getMenu_code().equals("users")){
                flag=false;
            }
        }
        if(flag){
            mv = new ModelAndView("redirect:/user/esc");
            return mv;
        }

        mv = new ModelAndView("/pages/user");
        List<Map> regList =  commonService.queryRegion();
        List<Map> roleMap =  commonService.findDistinctRole();
        mv.addObject("regList",regList);
        mv.addObject("roleList",roleMap);
        mv.addObject("userid",uuid);
        return mv;
    }
    //跳转到中间数据导入的页面
    @RequestMapping("/etl")
    public  ModelAndView  etl(HttpSession session){
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
            if(menu.getMenu_code().equals("users")){
                flag=false;
            }
        }
        if(flag){
            mv = new ModelAndView("redirect:/user/esc");
            return mv;
        }

        mv = new ModelAndView("/pages/etl");
        List<Map> regList =  commonService.queryRegion();
        mv.addObject("regList",regList);
        mv.addObject("userid",uuid);
        return mv;
    }

    @RequestMapping("/apply")
    public ModelAndView apply( HttpSession session) {
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
            if(menu.getMenu_code().equals("users")){
                flag=false;
            }
        }
        if(flag){
            mv = new ModelAndView("redirect:/user/esc");
            return mv;
        }

        mv = new ModelAndView("/pages/userApply");
        List<Dict> regList =  commonService.findDICT("行政区域类");
        List<Map> roleMap =  commonService.findDistinctRole();
        mv.addObject("roleList",roleMap);
        mv.addObject("regList",regList);
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
            if(menu.getMenu_code().equals("users")){
                flag=false;
            }
        }
        return flag;
    }


    /**
     * 查询用户列表
     * @param userVo
     * @return
     */
    @RequestMapping("/getUserList")
    @OperLog(operationType="用户管理",operationName="查看用户列表")
    @ResponseBody
    public Map getUserList(UserVo userVo,HttpSession session) {
        if(check(session)){
            return null;
        }

        if(null!=userVo.getRegList() && ""!=userVo.getRegList()){
            List<String> lis = new ArrayList<>();
            String[] regList = userVo.getRegList().split(",");
            for (String s : regList) {
                lis.add(s);
            }
            userVo.setRegionList(lis);
        }

        List<UserVo> list = userService.getUserList(userVo);
        Integer rows = userService.getUserListCount(userVo);
        Map _res = new HashMap();
        _res.put("rows",list);
        _res.put("total",rows);
        return _res;
    }

    @RequestMapping("/getEtlField")
    @OperLog(operationType="中间表查询",operationName="查看中间表的数据")
    @ResponseBody
    public Map getEtlField(UserVo userVo,HttpSession session) {
        if(check(session)){
            return null;
        }

        if(null!=userVo.getRegList() && ""!=userVo.getRegList()){
            List<String> lis = new ArrayList<>();
            String[] regList = userVo.getRegList().split(",");
            for (String s : regList) {
                lis.add(s);
            }
            userVo.setRegionList(lis);
        }

        List<FieldVo> list = userService.getEtlField(userVo);
        Integer rows = userService.getEtlFieldCount(userVo);
        Map _res = new HashMap();
        _res.put("rows",list);
        _res.put("total",rows);
        return _res;
    }


    public boolean validPwdComplex(String password){
        //1：满足密码长度不能小于8位
        if(password.trim().length()<8){
            return false;
        }
        //2：密码复杂度,必须包含数字和字母
//        String reg = "^[A-Za-z].*[0-9]|[0-9].*[A-Za-z]{8,25}$";
        String reg = "^(?!\\d+$)(?![A-Za-z]+$)[a-zA-Z0-9]{8,25}$";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(password);
        boolean b= matcher.matches();
        return b;
    }

    /**
     * 新增用户
     * @param userVo
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Map addUser(UserVo userVo,HttpSession session) {
        if(check(session)){
            return null;
        }

        Map _res = new HashMap();

        String loginpassencode = userVo.getLoginpassencode().trim();
        if(!"********".equals(loginpassencode)){
            if(validPwdComplex(loginpassencode)){
                //MD5加密
                String passwordMD5 =  DigestUtils.md5Hex(loginpassencode).toUpperCase();
                userVo.setLoginpass(passwordMD5);
            }else {
                _res.put("status", "fail");
                _res.put("message", "密码复杂度过低，请输入8位以上字母+数字的组合作为密码");
                return _res;
            }
        }

        if(userVo.getUuid()!=null && !userVo.getUuid().trim().equals("")){
            try {
                userService.updateUser(userVo);
                _res.put("status", "success");
                _res.put("message", "修改成功");
            } catch (Exception e) {
                _res.put("status", "fail");
                _res.put("message", e.getMessage());
            }
        }else {
            try {
                userService.addUser(userVo);
                _res.put("status", "success");
                _res.put("message", "新增成功");
            } catch (Exception e) {
                _res.put("status", "fail");
                _res.put("message", e.getMessage());
            }
        }
        return _res;
    }

    @RequestMapping("/view4DateEdit")
    public ModelAndView view4DateEdit(String uuid) {
        ModelAndView mv = null;
        mv = new ModelAndView("/pages/userPasUp");
        mv.addObject("uuid",uuid);
        return mv;
    }

    /**
     * 密码失效修改
     * @param request
     * @return
     */
    @RequestMapping("/dateEdit")
    @ResponseBody
    public Map dateEdit(HttpServletRequest request) {
        final Map<String,String> paramsMap = RequestMapUtils.getRequestMaps(request) ;
        Map _res = new HashMap();
        String uuid = paramsMap.get("uuid").trim();
        String oldPassen = paramsMap.get("oldPassen").trim();
        String newPassen = paramsMap.get("newPassen").trim();
        String rePassen = paramsMap.get("rePassen").trim();

        if(null==uuid && "".equals(uuid)){
            _res.put("status", "fail");
            _res.put("message", "登录信息有误，请重新登录验证后，再修改密码！");
            return _res;
        }
        if(newPassen.equals(oldPassen)){
            _res.put("status", "fail");
            _res.put("message", "新密码不能与旧密码相同！");
            return _res;
        }
        if(!newPassen.equals(rePassen)){
            _res.put("status", "fail");
            _res.put("message", "两次输入的新密码不相同！");
            return _res;
        }

        String passwordMD5 = null;
        if(validPwdComplex(newPassen)){
            //MD5加密
            passwordMD5 =  DigestUtils.md5Hex(newPassen).toUpperCase();
        }else {
            _res.put("status", "fail");
            _res.put("message", "密码复杂度过低，请输入8位以上字母+数字的组合作为密码");
            return _res;
        }

        String oldPasswordMD5 = userService.findOldPasswordMD5(uuid);
        String oldPassenMD5 = DigestUtils.md5Hex(oldPassen).toUpperCase();
        if(!oldPassenMD5.equals(oldPasswordMD5)){
            _res.put("status", "fail");
            _res.put("message", "旧密码错误！");
            return _res;
        }

        try {
            Map<String,String> parMap = new HashMap<>();
            parMap.put("uuid",uuid);
            parMap.put("passwordMD5",passwordMD5);
            userService.fastEditPass4Date(parMap);
            _res.put("status", "success");
            _res.put("message", "修改成功");
        } catch (Exception e) {
            _res.put("status", "fail");
            _res.put("message", e.getMessage());
        }
        return _res;
    }


    /**
     * 快捷修改用户
     * @param userVo
     * @return
     */
    @RequestMapping("/fastEdit")
    @ResponseBody
    public Map fastEdit(UserVo userVo,HttpSession session) {
        Map _res = new HashMap();

        String account = (String)session.getAttribute("account");
        String password = (String)session.getAttribute("password");
        String role = (String)session.getAttribute("role");
        if(account==null && password==null && role == null){
            _res.put("status", "fail");
            _res.put("message", "登录失效，请重新登录");
            return _res;
        }

        String loginpassencode = userVo.getLoginpassencode().trim();
        if(!"********".equals(loginpassencode)){
            if(validPwdComplex(loginpassencode)){
                //MD5加密
                String passwordMD5 =  DigestUtils.md5Hex(loginpassencode).toUpperCase();
                userVo.setLoginpass(passwordMD5);
            }else {
                _res.put("status", "fail");
                _res.put("message", "密码复杂度过低，请输入8位以上字母+数字的组合作为密码");
                return _res;
            }
        }

        if(userVo.getUuid()!=null && !userVo.getUuid().trim().equals("")){
            try {
                userService.fastEditUser(userVo);
                _res.put("status", "success");
                _res.put("message", "修改成功");
            } catch (Exception e) {
                _res.put("status", "fail");
                _res.put("message", e.getMessage());
            }
        }
        return _res;
    }

    @RequestMapping("/view4fastEdit")
    public ModelAndView view4fastEdit(String uuid,String type,HttpSession session) {
        ModelAndView mv = null;

        String account = (String)session.getAttribute("account");
        String password = (String)session.getAttribute("password");
//        String role = (String)session.getAttribute("role");
        if(account==null && password==null){
            mv = new ModelAndView("redirect:/user/esc");
            return mv;
        }


        mv = new ModelAndView("/pages/userApply");
        //查询当前用户的信息
        UserVo userVo = userService.getUserInfo(uuid);

        String idnum = userVo.getIdnum();
        if(null!=idnum && ""!=idnum){
            if(idnum.length()>10){
                StringBuilder sb = new StringBuilder(idnum);
                int a = (int)(idnum.length()/2);
                sb.replace(a-3, a+3, "******");
                userVo.setIdnum(sb.toString());
            }else {
                userVo.setIdnum("*****");
            }
        }

        List<Dict> regList =  commonService.findDICT("行政区域类");
        List<Map> roleMap =  commonService.findDistinctRole();
        mv.addObject("roleList",roleMap);
        mv.addObject("regList", regList);
        mv.addObject("userinfo",userVo);
        mv.addObject("type",type);
        return mv;
    }

    @RequestMapping("/view")
    public ModelAndView view(String uuid,String type,HttpSession session) {
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
            if(menu.getMenu_code().equals("users")){
                flag=false;
            }
        }
        if(flag){
            mv = new ModelAndView("redirect:/user/esc");
            return mv;
        }

        mv = new ModelAndView("/pages/userApply");
        //查询当前用户的信息
        UserVo userVo = userService.getUserInfo(uuid);

        String idnum = userVo.getIdnum();
        if(null!=idnum && ""!=idnum){
            if(idnum.length()>10){
                StringBuilder sb = new StringBuilder(idnum);
                int a = (int)(idnum.length()/2);
                sb.replace(a-3, a+3, "******");
                userVo.setIdnum(sb.toString());
            }else {
                userVo.setIdnum("*****");
            }
        }

        List<Dict> regList =  commonService.findDICT("行政区域类");
        List<Map> roleMap =  commonService.findDistinctRole();
        mv.addObject("roleList",roleMap);
        mv.addObject("regList", regList);
        mv.addObject("userinfo",userVo);
        mv.addObject("type",type);
        return mv;
    }



    @RequestMapping("/queryRegList")
    @ResponseBody
    public String queryRegList(HttpSession session) {
        if(check(session)){
            return null;
        }

        List<Dict> regList =  commonService.findDICT("行政区域类");
        JSONArray jsonarray = JSONArray.fromObject(regList);
        return jsonarray.toString();
    }


    /**
     * 删除用户
     * @param userVo
     * @return
     */
    @RequestMapping("/deleteUser")
    @OperLog(operationType="用户管理",operationName="删除")
    @ResponseBody
    public Map delete(UserVo userVo,HttpSession session) {
        if(check(session)){
            return null;
        }

        Map _res = new HashMap();
        try {
            userService.deleteUser(userVo.getUuidList());
            _res.put("status", "success");
            _res.put("message", "删除成功");
        } catch (Exception e) {
            _res.put("status", "fail");
            _res.put("message", e.getMessage());
        }
        return _res;
    }
    @RequestMapping(value="checkLogin")
    @ResponseBody
    public ResultView checkLogin(HttpSession session){
        ResultView resultView = ResultView.newResult("检查是否有人登陆");
        String sessionid = (String)session.getAttribute("sessionid");
        String uuid = (String)session.getAttribute("uuid");
        UserVo userInfo = userService.getUserInfo(uuid);
        boolean result = sessionid.equals(userInfo.getSessionId());
        if(!result){
            session.setAttribute("message","此账号在另外的设备登录");
        }
        resultView.addAttribute("result",result);
        return resultView;
    }
}

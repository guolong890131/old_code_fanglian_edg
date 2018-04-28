package com.funi.platform.edg.controller;

import com.funi.framework.mvc.core.view.ResultView;
import com.funi.platform.edg.annotation.OperLog;
import com.funi.platform.edg.bo.Menu;
import com.funi.platform.edg.bo.UserVo;
import com.funi.platform.edg.service.UserService;
import com.funi.platform.edg.service.loginService;
import com.funi.platform.edg.utils.RequestMapUtils;
import com.funi.platform.edg.utils.TimeHelp;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author jinlong.wang
 */
@Controller
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private loginService service;
    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        request.getSession().removeAttribute("message");
        return "/login";
    }

    @RequestMapping("/esc")
    public String esc(HttpServletRequest request) {
        request.getSession().removeAttribute("account");
        request.getSession().removeAttribute("password");
        return "/login";
    }

    @RequestMapping("/loginProcess")
    @ResponseBody
    public ResultView loginProcess(HttpServletRequest request,HttpSession session) {
        final Map<String,String> paramsMap = RequestMapUtils.getRequestMaps(request) ;


        session.removeAttribute("message");
        ResultView rv = ResultView.newResult("0");

        String code = paramsMap.get("code");
        code=code.toLowerCase();
        String validateCode = (String)session.getAttribute("validateCode");
        validateCode=validateCode.toLowerCase();
        if(null == code && "".equals(code)){
            rv.setResult("1");
            rv.setMessage("验证码为空！");
            return rv;
        }
        if(!validateCode.equals(code)){
            rv.setResult("1");
            rv.setMessage("验证码错误！");
            return rv;
        }

        //MD5加密
        String passwordMD5 =  DigestUtils.md5Hex(paramsMap.get("password")).toUpperCase();
        paramsMap.put("password",passwordMD5);
        int flag = service.checkUser(paramsMap);
        if(flag==0) {
            Map<String, String> map = service.findRole(paramsMap);
            //验证密码是否过期
            Map<String, String> dateMap = service.getUserDate(map.get("UUID"));
            String sysdate = dateMap.get("SDATE");
            String editdate = null;
            if(null==dateMap.get("EDITDATE") || "".equals(dateMap.get("EDITDATE"))){
                editdate = dateMap.get("INDATE");
            }else {
                editdate = dateMap.get("EDITDATE");
            }
            long daySub = TimeHelp.getDaySub(editdate,sysdate);
            if(daySub>=90){
                rv.addAttribute("uuid",map.get("UUID"));
                rv.setMessage("密码过期，请更新密码！");
                rv.setResult("2");
                return rv;
            }else {
                rv.setMessage("登录成功");
                request.getSession().setAttribute("account", paramsMap.get("account"));
                request.getSession().setAttribute("password", passwordMD5);
                request.getSession().setAttribute("role",map.get("ROLE_CODE"));
                request.getSession().setAttribute("uuid",map.get("UUID"));
            }
        }
        if(flag==1){
            rv.setResult("1");
            rv.setMessage("用户名错误！");
            return rv;
        }
        if(flag==2){
            rv.setResult("1");
            rv.setMessage("密码错误！");
            return rv;
        }
        return rv;
    }


    @RequestMapping("/main")
    @OperLog(operationType="用户",operationName="登录")
    public ModelAndView mains(HttpSession session) {
        ModelAndView mv = new ModelAndView("/pages/main");

        String account = (String)session.getAttribute("account");
        String password = (String)session.getAttribute("password");
        session.removeAttribute("message");
        if(account==null && password==null){
            mv = new ModelAndView("/login");
            return mv;
        }

        String role = (String)session.getAttribute("role");
        String uuid = (String)session.getAttribute("uuid");
        //更新登录状态的唯一标示unique的值
        String sessionId = session.getId();
        session.setAttribute("sessionid",sessionId);
        UserVo userInfo = userService.getUserInfo(uuid);
        userInfo.setSessionId(sessionId);
        userService.updateUniquOfUser(userInfo);


        List<Menu> listMenu = service.findMenu(role);
        mv.addObject("menu",listMenu);
        mv.addObject("uuid",uuid);
        return mv;
    }




    //全局默认起始查询年设置
    @RequestMapping("/yearTemp")
    @ResponseBody
    public ResultView yearTemp(HttpServletRequest request) {
        ResultView rv = ResultView.newResult("");
        final Map<String,String> paramsMap = RequestMapUtils.getRequestMaps(request);
        request.getSession().setAttribute("checked", paramsMap.get("checked"));
        request.getSession().setAttribute("yearTemp", paramsMap.get("yearTemp"));
        return rv;
    }

    @RequestMapping("/yearChecked")
    @ResponseBody
    public ResultView yearChecked(HttpServletRequest request) {
        ResultView rv = ResultView.newResult(request.getSession().getAttribute("checked"));
        return rv;
    }

    /**
     * 分页直接继承PageableQuery
     *
     * @param userQuery 分页参数对象
     * @return 用户列表
     */
//    @RequestMapping("/find")
//    @ResponseBody
//    public List<User> findUsers(UserQuery userQuery) {
//        return userService.find(userQuery);
//    }


    /**
     * 单个对象返回 ， MVC 对象返回 除了常见的List ，String , Bool 类型，还支持自定义类型
     * 但是自定义类型 必须采用ResultView  包装
     *
     * @param userId 当前用户ID
     * @return 用户列表
     */
//    @RequestMapping("/findById")
//    @ResponseBody
//    public ResultView findUser(String userId) {
//        return ResultView.newResult(userService.findById(userId));
//    }


    /**
     * 视图 必须以/ 开头，存放在WEB-INF/jsp 目录中
     *
     * @return 视图路径
     */
//    @RequestMapping("/index")
//    public String index() {
//        return "/index";
//    }

}

package com.funi.platform.edg.controller;

import com.funi.framework.mvc.core.view.ResultView;
import com.funi.platform.edg.bo.ParaDate;
import com.funi.platform.edg.query.TimeQuery;
import com.funi.platform.edg.service.CommonService;
import com.funi.platform.edg.service.MapService;
import com.funi.platform.edg.utils.RequestMapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package: [com.funi.platform.edg.controller]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/6/3 0003 9:43]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2016/6/3 0003 9:43，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录，增加修改记录)]
 * @Version: [v2.0]
 */
@Controller
@RequestMapping("/map")
public class MapController {

    @Resource
    private MapService mapService;

    @Resource
    private CommonService commonService;

    /**
     * 视图 必须以/ 开头，存放在WEB-INF/jsp 目录中
     *
     * @return 视图路径
     */
    @RequestMapping("/index")
    public String index(ModelMap model,HttpSession session) {
        String account = (String)session.getAttribute("account");
        String password = (String)session.getAttribute("password");
        if(account==null && password==null){
            return "/login";
        }

        TimeQuery timeQuery = new TimeQuery("001");
        try {
            ParaDate date = commonService.fandTime(timeQuery);
            if(null != date && null != date.getDefault_date()){
                model.addAttribute("timeTemp",date.getDefault_date());
            }
        }catch (Exception e){
            model.addAttribute("timeTemp","时间查询异常");
            System.out.println(e.getMessage());
        }

        return "/map";
    }

    /**
     *获取地图选择区域数据
     * 返回数据map中的key : name,code
     * "行政区域类"
     */
    @RequestMapping("/findRegion")
    @ResponseBody
    public ResultView findRegion(HttpServletRequest request) {
        final Map<String,String> paramsMap = RequestMapUtils.getRequestMaps(request) ;
        String type = paramsMap.get("Type");
        ArrayList<Map> list = mapService.findRegion(type);
        Map<String,String> map = new HashMap<>();
        map.put("NAME","全域成都");
        map.put("CODE","");
        list.add(0,map);
        return ResultView.newResult(list);
    }

    /**
     *获取地图柱状坐标
     * 传入参数：types，code
     * types是  '项目','施工','规划','土地'  这4个字符串组成的list
     * code 是 区域code代码
     * 返回数据map中的key : ID,TYPE,AREA,PROJECT_NAME,bdzb,X,Y
     */
    @RequestMapping("/findCoordinate")
    @ResponseBody
    public ResultView findCoordinate(HttpServletRequest request) {
        final Map<String,String> paramsMap = RequestMapUtils.getRequestMaps(request) ;
        String code =paramsMap.get("code");
        String types =paramsMap.get("types");
        if(types.endsWith(",")){
            types = types.substring(0,types.length()-1);
        }
        Map map = new HashMap<>();
        map.put("code",code);
        String[] str = types.split(",");
        map.put("types",str);
        return ResultView.newResult(mapService.findCoordinate(map));
    }

    /**
     *获取详情
     * 传入参数：ID,TYPE
     * 返回数据map中的key : TYPE，PROJECT_NAME，ADDRESS，DEVELOPER，STOCK_AREA，DEAL_DATE，PRINT_DATE，BUILD_DATE，PROJECT_DATE
     */
    @RequestMapping("/findWin")
    @ResponseBody
    public ResultView findWin(HttpServletRequest request) {
        final Map<String,String> paramsMap = RequestMapUtils.getRequestMaps(request);
        return  ResultView.newResult(mapService.findWin(paramsMap));
    }
}

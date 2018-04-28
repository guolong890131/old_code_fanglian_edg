package com.funi.platform.edg.controller;

import com.funi.platform.edg.bo.MarketList;
import com.funi.platform.edg.bo.MarketStockCycle;
import com.funi.platform.edg.bo.MarketSupplyDeal;
import com.funi.platform.edg.bo.ParaDate;
import com.funi.platform.edg.query.MarketQuery;
import com.funi.platform.edg.query.TimeQuery;
import com.funi.platform.edg.service.CommonService;
import com.funi.platform.edg.service.MarketService;
import com.funi.platform.edg.utils.TimeHelp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package: [com.funi.platform.edg.controller]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/3/16 0016 13:03]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录,增加修改记录)]
 * @UpdateDate: [2016/3/16 0016 13:03，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录，增加修改记录)]
 * @Version: [v2.0]
 */
@Controller
@RequestMapping("/market")
public class MarketController {
    @Resource
    private MarketService marketService;
    @Resource
    private CommonService commonService;

    /**
     * 视图 必须以/ 开头，存放在WEB-INF/jsp 目录中
     *
     * 楼市供销监测
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

        timeQuery = new TimeQuery("003");
        try {
            ParaDate date = commonService.fandTime(timeQuery);
            if(null != date && null != date.getDefault_y()){
                model.addAttribute("yearTemp",date.getDefault_y());
            }
        }catch (Exception e){
            model.addAttribute("yearTemp","时间查询异常");
            System.out.println(e.getMessage());
        }

        return "/market";
    }
    @RequestMapping("/pompAllpresell")
    public String pompAllpresell() {
        return "/pompAllpresell";
    }
    @RequestMapping("/pompAllarea")
    public String pompAllarea() {
        return "/pompAllarea";
    }
    @RequestMapping("/pompAllsell")
    public String pompAllsell() {
        return "/pompAllsell";
    }
    /**
     *获取左侧全域数据
     */
    @RequestMapping("/findMarketList")
    @ResponseBody
    public List<MarketList> findMarketList() {
        return marketService.findMarketList();
    }


    /**
     *批准预售面积与成交面积趋势统计
     */
    @RequestMapping("/findSupplyDeal")
    @ResponseBody
    public List findSupplyDeal(MarketQuery marketQuery,HttpServletRequest request) {
        String checked = (String)request.getSession().getAttribute("checked");
        if("true".equals(checked)){
            marketQuery.setYearTemp(request.getSession().getAttribute("yearTemp").toString());
        }
        return marketService.findSupplyDeal(marketQuery);
    }

    /**
     *可销售面积与销售周期(月)趋势统计
     */
    @RequestMapping("/findStockCycle")
    @ResponseBody
    public List findStockCycle(MarketQuery marketQuery,HttpServletRequest request) {
        String checked = (String)request.getSession().getAttribute("checked");
        if("true".equals(checked)){
            marketQuery.setYearTemp(request.getSession().getAttribute("yearTemp").toString());
        }
        return marketService.findStockCycle(marketQuery);
    }

    /**
     *价格趋势统计
     */
    @RequestMapping("/findSupplyPrice")
    @ResponseBody
    public List findSupplyPrice(MarketQuery marketQuery,HttpServletRequest request) {
        String checked = (String)request.getSession().getAttribute("checked");
        if("true".equals(checked)){
            marketQuery.setYearTemp(request.getSession().getAttribute("yearTemp").toString());
        }
        return marketService.findSupplyPrice(marketQuery);
    }

    /**
     *全域本年累计批准预售面积 -弹出层-
     */
    @RequestMapping("/findPompAllpresell")
    @ResponseBody
    public List findPompAllpresell(MarketQuery marketQuery) {
        TimeQuery timeQuery = new TimeQuery("001");
        ParaDate date = commonService.fandTime(timeQuery);
        String flag=marketQuery.getFlagYM();
        String time =date.getDefault_y()+"-"+date.getDefault_m();
        if("本月".equals(flag)){
            List<String> list = new ArrayList<String>();
            list.add(time);
            marketQuery.setTimeList(list);
        }
        if("三月".equals(flag)){
            List<String> list = new ArrayList<String>();
            for(int i =0 ;i<3;i++){
                list.add(TimeHelp.dateFormatToN(time,i));
            }
            marketQuery.setTimeList(list);

        }
        if("半年".equals(flag)){
            List<String> list = new ArrayList<String>();
            for(int i =0 ;i<6;i++){
                list.add(TimeHelp.dateFormatToN(time,i));
            }
            marketQuery.setTimeList(list);
        }
        if("一年".equals(flag)){
            List<String> list = new ArrayList<String>();
            for(int i =0 ;i<12;i++){
                list.add(TimeHelp.dateFormatToN(time,i));
            }
            marketQuery.setTimeList(list);
        }
        return marketService.findPompAllpresell(marketQuery);
    }

    /**
     *全域本年累计成交面积 -弹出层-
     */
    @RequestMapping("/findPompAllArea")
    @ResponseBody
    public List findPompAllArea(MarketQuery marketQuery) {
        TimeQuery timeQuery = new TimeQuery("001");
        ParaDate date = commonService.fandTime(timeQuery);
        String flag=marketQuery.getFlagYM();
        String time =date.getDefault_y()+"-"+date.getDefault_m();
        if("本月".equals(flag)){
            List<String> list = new ArrayList<String>();
            list.add(time);
            marketQuery.setTimeList(list);
        }
        if("三月".equals(flag)){
            List<String> list = new ArrayList<String>();
            for(int i =0 ;i<3;i++){
                list.add(TimeHelp.dateFormatToN(time,i));
            }
            marketQuery.setTimeList(list);

        }
        if("半年".equals(flag)){
            List<String> list = new ArrayList<String>();
            for(int i =0 ;i<6;i++){
                list.add(TimeHelp.dateFormatToN(time,i));
            }
            marketQuery.setTimeList(list);
        }
        if("一年".equals(flag)){
            List<String> list = new ArrayList<String>();
            for(int i =0 ;i<12;i++){
                list.add(TimeHelp.dateFormatToN(time,i));
            }
            marketQuery.setTimeList(list);
        }
        return marketService.findPompAllArea(marketQuery);
    }

    /**
     *全域当前可销售面积 -弹出层-
     */
    @RequestMapping("/findPompAllsell")
    @ResponseBody
    public List findPompAllsell(MarketQuery marketQuery) {
        TimeQuery timeQuery = new TimeQuery("001");
        ParaDate date = commonService.fandTime(timeQuery);
        marketQuery.setTime(date.getDefault_date());
        return marketService.findPompAllsell(marketQuery);
    }
}

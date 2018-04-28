package com.funi.platform.edg.controller;

import com.funi.platform.edg.bo.*;
import com.funi.platform.edg.query.StockQuery;
import com.funi.platform.edg.query.TimeQuery;
import com.funi.platform.edg.service.CommonService;
import com.funi.platform.edg.service.StockService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Package: [com.funi.platform.edg.controller]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/3/16 0016 13:13]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2016/3/16 0016 13:13，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录，增加修改记录)]
 * @Version: [v2.0]
 */
@Controller
@RequestMapping("/stock")
public class StockController {
    @Resource
    private StockService stockService;
    @Resource
    private CommonService commonService;
    /**
     * 视图 必须以/ 开头，存放在WEB-INF/jsp 目录中
     *
     * 可建面积监测
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

        TimeQuery timeQuery = new TimeQuery("002");
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



        return "/stock";
    }



    /**
     * 商业地产监测
     * @return
     */
    @RequestMapping("/allemploy")
    public String allemploy(ModelMap model,HttpSession session) {
        String account = (String)session.getAttribute("account");
        String password = (String)session.getAttribute("password");
        if(account==null && password==null){
            return "/login";
        }

        TimeQuery timeQuery = new TimeQuery("002");
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


        return "/allemploy";
    }


    @RequestMapping("/allstock")
    public String allstock() {
        return "/allstock";
    }
    @RequestMapping("/allland")
    public String allland() {
        return "/allland";
    }
    @RequestMapping("/allplan")
    public String allplan() {
        return "/allplan";
    }
    @RequestMapping("/allconstruct")
    public String allconstruct() {
        return "/allconstruct";
    }
    @RequestMapping("/allsell")
    public String allsell() {
        return "/allsell";
    }




    @RequestMapping("/pompRegion")
    public String pompRegion() {
        return "/pompRegion";
    }
    /**
     *获取左侧全域数据
     */
    @RequestMapping("/findStockList")
    @ResponseBody
    public List<StockList> findStockList() {
        return stockService.findStockList();
    }

    /**
     *获取圈层数据
     */
    @RequestMapping("/findStockLayer")
    @ResponseBody
    public List<StockLayer> findStockLayer(StockQuery stockQuery) {
        return stockService.findStockLayer(stockQuery);
    }

    /**
     *按阶段或业态统计存量及环比 stage_usage_stock
     */
    @RequestMapping("/findStockStageUsage")
    @ResponseBody
    public List<StockStageUsage> findStockStageUsage(StockQuery stockQuery) {
        return stockService.findStockStageUsage(stockQuery);
    }

    /**
     *趋势统计-楼市区域参数
     */
    @RequestMapping("/findStockTrend4lsRegion")
    @ResponseBody
    public List<StockLsRegion>  findStockTrend4lsRegion(StockQuery stockQuery) {
        return stockService.findStockTrend4lsRegion(stockQuery);
    }

    /**
     *趋势统计
     */
    @RequestMapping("/findStockTrend")
    @ResponseBody
    public List findStockTrend(StockQuery stockQuery,HttpServletRequest request) {
        String checked = (String)request.getSession().getAttribute("checked");
        if("true".equals(checked)){
            stockQuery.setYearTemp(request.getSession().getAttribute("yearTemp").toString());
        }
        return stockService.findStockTrend(stockQuery);
    }

    /**
     *full_cycle_region_stock   区域统计（全域全周期库存）
     */
    @RequestMapping("/findFullCycleRegionStock")
    @ResponseBody
    public List<StockFullCycleRegion> findFullCycleRegionStock(StockQuery stockQuery) {
        return stockService.findFullCycleRegionStock(stockQuery);
    }

    /**
     *edg_field_layout_region_stock   区域统计（土地、规划、施工库存）
     */
    @RequestMapping("/findFieldLayoutRegionStock")
    @ResponseBody
    public List<StockFieldLayoutRegion> findFieldLayoutRegionStock(StockQuery stockQuery) {
        return stockService.findFieldLayoutRegionStock(stockQuery);
    }

    /**
     *edg_estate_region_stock  区域统计（全域楼市可售）
     */
    @RequestMapping("/findEstateRegionStock")
    @ResponseBody
    public List<StockEstateRegion> findEstateRegionStock(StockQuery stockQuery) {
        return stockService.findEstateRegionStock(stockQuery);
    }

    /**
     *edg_canuse_stock   区域统计（全域房屋可使用面积）
     */
    @RequestMapping("/findEdgCanuseStock ")
    @ResponseBody
    public List<StockCanuseObj> findEdgCanuseStock(StockQuery stockQuery) {
        return stockService.findEdgCanuseStock(stockQuery);
    }

    /**
     *edg_canuse_rate   全域房屋  使用情况统计
     */
    @RequestMapping("/findEdgCanuseRate ")
    @ResponseBody
    public List<StockCanuseObj> findEdgCanuseRate(StockQuery stockQuery) {
        return stockService.findEdgCanuseRate(stockQuery);
    }

    /**
     *edg_canuse_rate   楼市  区域统计  弹出页面
     */
    @RequestMapping("/findPompRegion ")
    @ResponseBody
    public List<StockPompRegion> findPompRegion(StockQuery stockQuery) {
        String str = stockQuery.getRegionName();
        String val =str.substring(0,str.length()-1);
        stockQuery.setRegionName(val);
        return stockService.findPompRegion(stockQuery);
    }

    /**
     *edg_canuse_rate   楼市  饼图点击查询列表
     */
    @RequestMapping("/findRegionPie ")
    @ResponseBody
    public List<StockStageUsagePie> findRegionPie(StockQuery stockQuery) {
        return stockService.findRegionPie(stockQuery);
    }
}

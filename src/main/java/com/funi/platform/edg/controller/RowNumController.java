package com.funi.platform.edg.controller;/**
 * Created by as on 2017/2/17 0017.
 */

import com.funi.platform.edg.bo.RowNum;
import com.funi.platform.edg.service.RowNumService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @Package: [com.funi.platform.edg.controller]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2017/2/17 0017 9:18]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2017/2/17 0017 9:18，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */
@Controller
@RequestMapping("/rowNum")
public class RowNumController {
    @Resource
    private RowNumService rowNumService;

    @RequestMapping("/rank")
    public ModelAndView rank(String rankno, String rankdate) throws ParseException {
        ModelAndView modelAndView = new ModelAndView("/rowNumber/rowNumber");
        Map map = rowNumService.queryByRank(rankno, rankdate);
        RowNum rn = (RowNum)map.get("rn");
        int waitNo = (int)map.get("waitNo");
        String intervalTime = rn.getIntervalTime();
        int waitTime=waitNo*(Integer.parseInt(intervalTime)) ;
        //拆分日期
        String time = rankdate.substring(0, 4) +"-"+ rankdate.substring(4,6)+"-" + rankdate.substring(6, 8);
        //转化为星期几
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
        String s = dateFormat.format(date);

        modelAndView.addObject("time",time+" "+s);
        modelAndView.addObject("waitNo",String.valueOf(waitNo));
        modelAndView.addObject("waitTime",String.valueOf(waitTime));
        modelAndView.addObject("rankno",rankno);
        modelAndView.addObject("currnetNo",rn.getTopNo());
        return modelAndView;
    }

}

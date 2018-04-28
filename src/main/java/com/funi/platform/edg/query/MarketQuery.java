package com.funi.platform.edg.query;

import java.util.List;

/**
 * @Package: [com.funi.platform.edg.query]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/3/17 0017 11:03]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2016/3/17 0017 11:03，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录，增加修改记录)]
 * @Version: [v2.0]
 */
public class MarketQuery {
    private String flagYM;//年月标志
    private String 	layer_code;//圈层代码,001主城区002二圈层003三圈层 +区域code
    private String layer_name;//	圈层名称
    private String usage_code;//	物业类型代码
    private String usage_name;//物业类型名称

    private String time;//查询时间字符串
    private List<String> timeList;//时间串

    private String region_code;//区域code

    private String yearTemp;//默认起始时间

    public String getYearTemp() {
        return yearTemp;
    }

    public void setYearTemp(String yearTemp) {
        this.yearTemp = yearTemp;
    }

    public String getRegion_code() {
        return region_code;
    }

    public void setRegion_code(String region_code) {
        this.region_code = region_code;
    }

    public List<String> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<String> timeList) {
        this.timeList = timeList;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFlagYM() {
        return flagYM;
    }

    public void setFlagYM(String flagYM) {
        this.flagYM = flagYM;
    }

    public String getLayer_code() {
        //如果选了区域 就传区域code给sql  区域code和圈层code 都在layer_code这个表字段里
        if(null==region_code || "".equals(region_code)){
            return layer_code;
        }else {
            return region_code;
        }

    }

    public void setLayer_code(String layer_code) {
        this.layer_code = layer_code;
    }

    public String getLayer_name() {
        return layer_name;
    }

    public void setLayer_name(String layer_name) {
        this.layer_name = layer_name;
    }

    public String getUsage_code() {
        return usage_code;
    }

    public void setUsage_code(String usage_code) {
        this.usage_code = usage_code;
    }

    public String getUsage_name() {
        return usage_name;
    }

    public void setUsage_name(String usage_name) {
        this.usage_name = usage_name;
    }
}

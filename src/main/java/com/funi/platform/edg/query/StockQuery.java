package com.funi.platform.edg.query;

/**
 * @Package: [com.funi.platform.edg.query]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/3/17 0017 15:54]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2016/3/17 0017 15:54，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录，增加修改记录)]
 * @Version: [v2.0]
 */
public class StockQuery {
    private String stock_code;//存量类型代码,001全周期库存总量,002土地可建面积,003规划可建面积,004施工可建面积,005楼市可销售面积,006房屋可使用面积
    private String stock_name;//存量类型名称

    private String layer_code;//圈层代码,001主城区002二圈层003三圈层
    private String layer_name;//圈层名称

    private String flagYM;//年月标志

    private String type_code;	//001阶段002业态
    private String 	type_name;	//阶段或业态名称

    private String usage_code;//物业类型code
    private String usage_name;//物业名称

    private String regionName;//区域名称
    private int num;//排行数

    private String region_code;//楼市 趋势统计 区域区分用

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

    public String getUsage_name() {
        return usage_name;
    }

    public void setUsage_name(String usage_name) {
        this.usage_name = usage_name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getUsage_code() {
        return usage_code;
    }

    public void setUsage_code(String usage_code) {
        this.usage_code = usage_code;
    }

    public String getStock_code() {
        return stock_code;
    }

    public void setStock_code(String stock_code) {
        this.stock_code = stock_code;
    }

    public String getStock_name() {
        return stock_name;
    }

    public void setStock_name(String stock_name) {
        this.stock_name = stock_name;
    }

    public String getLayer_code() {
        return layer_code;
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

    public String getFlagYM() {
        return flagYM;
    }

    public void setFlagYM(String flagYM) {
        this.flagYM = flagYM;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getType_code() {
        return type_code;
    }

    public void setType_code(String type_code) {
        this.type_code = type_code;
    }
}

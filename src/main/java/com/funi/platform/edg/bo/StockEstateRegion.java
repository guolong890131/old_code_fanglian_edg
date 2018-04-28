package com.funi.platform.edg.bo;

/**
 * @Package: [com.funi.platform.edg.bo]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/3/18 0018 9:48]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2016/3/18 0018 9:48，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录，增加修改记录)]
 * @Version: [v2.0]
 */
public class StockEstateRegion {
    private String create_date;//统计日期
    private String 	region_code;//区域代码
    private String 	region_name;//区域名称
    private Float supply_area;//当月取得销售许可面积(平米)
    private Float supply_rate;//供应环比
    private Float supply_rate_tb;//供应同比
    private Float deal_area;//	当月成交面积
    private Float deal_rate;//	成交环比
    private Float deal_rate_tb;//	成交同比
    private Float stock_area;//	库存总量(平米)
    private Float stock_rate;//	可售环比
    private Float stock_rate_tb;//	可售同比

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getRegion_code() {
        return region_code;
    }

    public void setRegion_code(String region_code) {
        this.region_code = region_code;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public Float getSupply_area() {
        return supply_area;
    }

    public void setSupply_area(Float supply_area) {
        this.supply_area = supply_area;
    }

    public Float getSupply_rate() {
        return supply_rate;
    }

    public void setSupply_rate(Float supply_rate) {
        this.supply_rate = supply_rate;
    }

    public Float getDeal_area() {
        return deal_area;
    }

    public void setDeal_area(Float deal_area) {
        this.deal_area = deal_area;
    }

    public Float getDeal_rate() {
        return deal_rate;
    }

    public void setDeal_rate(Float deal_rate) {
        this.deal_rate = deal_rate;
    }

    public Float getStock_area() {
        return stock_area;
    }

    public void setStock_area(Float stock_area) {
        this.stock_area = stock_area;
    }

    public Float getStock_rate() {
        return stock_rate;
    }

    public void setStock_rate(Float stock_rate) {
        this.stock_rate = stock_rate;
    }

    public Float getSupply_rate_tb() {
        return supply_rate_tb;
    }

    public void setSupply_rate_tb(Float supply_rate_tb) {
        this.supply_rate_tb = supply_rate_tb;
    }

    public Float getDeal_rate_tb() {
        return deal_rate_tb;
    }

    public void setDeal_rate_tb(Float deal_rate_tb) {
        this.deal_rate_tb = deal_rate_tb;
    }

    public Float getStock_rate_tb() {
        return stock_rate_tb;
    }

    public void setStock_rate_tb(Float stock_rate_tb) {
        this.stock_rate_tb = stock_rate_tb;
    }
}

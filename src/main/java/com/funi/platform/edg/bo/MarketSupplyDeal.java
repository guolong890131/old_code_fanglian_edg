package com.funi.platform.edg.bo;

/**
 * @Package: [com.funi.platform.edg.bo]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/3/17 0017 10:37]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2016/3/17 0017 10:37，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录，增加修改记录)]
 * @Version: [v2.0]
 */
public class MarketSupplyDeal {
    private String create_date;//统计日期
    private String y;//年
    private String ym;//年月
    private Float supply_area;//批准预售面积(平米)
    private Float deal_area;//成交面积(平米)
    private Float rate;//供销比;
    private Float average_price;//成交均价

    private Float deal_average_decprice;//申报均价，（成交）

    public Float getDeal_average_decprice() {
        return deal_average_decprice;
    }

    public void setDeal_average_decprice(Float deal_average_decprice) {
        this.deal_average_decprice = deal_average_decprice;
    }

    public Float getAverage_price() {
        return average_price;
    }

    public void setAverage_price(Float average_price) {
        this.average_price = average_price;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getYm() {
        return ym;
    }

    public void setYm(String ym) {
        this.ym = ym;
    }

    public Float getSupply_area() {
        return supply_area;
    }

    public void setSupply_area(Float supply_area) {
        this.supply_area = supply_area;
    }

    public Float getDeal_area() {
        return deal_area;
    }

    public void setDeal_area(Float deal_area) {
        this.deal_area = deal_area;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }
}

package com.funi.platform.edg.bo;

/**
 * @Package: [com.funi.platform.edg.bo]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/3/17 0017 15:48]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2016/3/17 0017 15:48，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录，增加修改记录)]
 * @Version: [v2.0]
 */
public class StockLayer {
    private String create_date;//统计日期
    private String layer_code;//圈层代码,001主城区002二圈层003三圈层
    private String layer_name;//圈层名称
    private String stock_area;//	可售面积(平米)
    private Float rate;//环比

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
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

    public String getStock_area() {
        return stock_area;
    }

    public void setStock_area(String stock_area) {
        this.stock_area = stock_area;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }
}

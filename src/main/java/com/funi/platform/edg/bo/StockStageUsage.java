package com.funi.platform.edg.bo;

/**
 * @Package: [com.funi.platform.edg.bo]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/3/17 0017 19:08]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2016/3/17 0017 19:08，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录，增加修改记录)]
 * @Version: [v2.0]
 */
public class StockStageUsage {
    private String create_date;//统计日期
    private String usage_code;//具体阶段或业态代码,住宅、非住宅、商业、办公、车位等代码
    private String usage_name;//具体阶段或业态名称.住宅、非住宅、商业、办公、车位等名称
    private Float stock_area;//可售面积(平米)
    private Float rate;//环比

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
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

    public Float getStock_area() {
        return stock_area;
    }

    public void setStock_area(Float stock_area) {
        this.stock_area = stock_area;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }
}

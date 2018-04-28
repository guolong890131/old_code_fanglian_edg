package com.funi.platform.edg.bo;

/**
 * @Package: [com.funi.platform.edg.bo]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/3/23 0023 21:12]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2016/3/23 0023 21:12，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录，增加修改记录)]
 * @Version: [v2.0]
 */
public class StockStageUsagePie {
    private String region_code;//区域code
    private String region_name;//区域名称
    private Float stock_area;//可售面积(平米)

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

    public Float getStock_area() {
        return stock_area;
    }

    public void setStock_area(Float stock_area) {
        this.stock_area = stock_area;
    }
}

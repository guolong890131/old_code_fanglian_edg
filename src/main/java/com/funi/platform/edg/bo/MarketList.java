package com.funi.platform.edg.bo;

/**
 * @Package: [com.funi.platform.edg.bo]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/3/16 0016 19:37]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2016/3/16 0016 19:37，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录，增加修改记录)]
 * @Version: [v2.0]
 */
public class MarketList {
    private String create_date;//统计日期
    private String 	market_code;//市场概况统计类型代码
    private String market_name;//市场概况统计类型名称
    private String value;//面积或价格

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getMarket_code() {
        return market_code;
    }

    public void setMarket_code(String market_code) {
        this.market_code = market_code;
    }

    public String getMarket_name() {
        return market_name;
    }

    public void setMarket_name(String market_name) {
        this.market_name = market_name;
    }

    public String getValue() {
        String flag = getMarket_code();
        if (null != value){
            if("1001".equals(flag) || "1002".equals(flag) || "1003".equals(flag) ){
                int v =Math.round(Float.parseFloat(value)/10000);
                return v+"";
            }
        }
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

package com.funi.platform.edg.bo;

/**
 * @Package: [com.funi.platform.edg.bo]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/3/22 0022 10:15]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2016/3/22 0022 10:15，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录，增加修改记录)]
 * @Version: [v2.0]
 */
public class StockCanuseObj {
    private String regin;
    private Float house;
    private Float business;
    private Float office;
    private Float stall;

    public String getRegin() {
        return regin;
    }

    public void setRegin(String regin) {
        this.regin = regin;
    }

    public Float getHouse() {
        return house;
    }

    public void setHouse(Float house) {
        this.house = house;
    }

    public Float getBusiness() {
        return business;
    }

    public void setBusiness(Float business) {
        this.business = business;
    }

    public Float getOffice() {
        return office;
    }

    public void setOffice(Float office) {
        this.office = office;
    }

    public Float getStall() {
        return stall;
    }

    public void setStall(Float stall) {
        this.stall = stall;
    }
}

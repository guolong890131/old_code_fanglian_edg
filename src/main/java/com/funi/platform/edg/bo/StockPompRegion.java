package com.funi.platform.edg.bo;

/**
 * @Package: [com.funi.platform.edg.bo]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/3/23 0023 20:03]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2016/3/23 0023 20:03，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录，增加修改记录)]
 * @Version: [v2.0]
 */
public class StockPompRegion {
    private String project_name;//项目名称
    private Float all_area;//总库存
    private Float house_area;//住宅可售
    private Float business_area;//商业可售
    private Float office_area;//办公可售
    private Float stall;//车位可售
    private Float xszq;//住宅销售周期

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public Float getAll_area() {
        return all_area;
    }

    public void setAll_area(Float all_area) {
        this.all_area = all_area;
    }

    public Float getHouse_area() {
        return house_area;
    }

    public void setHouse_area(Float house_area) {
        this.house_area = house_area;
    }

    public Float getBusiness_area() {
        return business_area;
    }

    public void setBusiness_area(Float business_area) {
        this.business_area = business_area;
    }

    public Float getOffice_area() {
        return office_area;
    }

    public void setOffice_area(Float office_area) {
        this.office_area = office_area;
    }

    public Float getStall() {
        return stall;
    }

    public void setStall(Float stall) {
        this.stall = stall;
    }

    public Float getXszq() {
        return xszq;
    }

    public void setXszq(Float xszq) {
        this.xszq = xszq;
    }
}

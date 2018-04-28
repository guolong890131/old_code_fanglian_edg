package com.funi.platform.edg.bo;

/**
 * @Package: [com.funi.platform.edg.bo]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/3/18 0018 9:13]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2016/3/18 0018 9:13，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录，增加修改记录)]
 * @Version: [v2.0]
 */
public class StockFullCycleRegion {
    private String create_date;//统计日期
    private String 	region_code;//区域代码
    private String 	region_name;//区域名称
    private Float stock_area;//	库存总量(平米)
    private Float field_area;//土地可建面积(平米)
    private Float layout_area;//规划可建面积(平米)
    private Float build_area;//施工可建面积(平米)
    private Float estate_area;//楼市可销售面积(平米)

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

    public Float getStock_area() {
        return stock_area;
    }

    public void setStock_area(Float stock_area) {
        this.stock_area = stock_area;
    }

    public Float getField_area() {
        return field_area;
    }

    public void setField_area(Float field_area) {
        this.field_area = field_area;
    }

    public Float getLayout_area() {
        return layout_area;
    }

    public void setLayout_area(Float layout_area) {
        this.layout_area = layout_area;
    }

    public Float getBuild_area() {
        return build_area;
    }

    public void setBuild_area(Float build_area) {
        this.build_area = build_area;
    }

    public Float getEstate_area() {
        return estate_area;
    }

    public void setEstate_area(Float estate_area) {
        this.estate_area = estate_area;
    }
}

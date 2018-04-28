package com.funi.platform.edg.bo;/**
 * Created by as on 2016/12/13 0013.
 */

import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Package: [com.funi.platform.edg.bo]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/12/13 0013 18:29]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2016/12/13 0013 18:29，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */
public class Build{
    private String create_time;//创建时间

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }


    private String id;//UUID'主键';
    private String xh;//'流水号';
    private String data_source;//'区域';
    private String region_code;//'行政区域代码';
    private String file_status;//'归档状态 0=未归档 1=已归档';
    private String inputer_id;//'录入者id 对应 user表 uuid';
    private String supervisor_unit;//'监理单位';
    private Integer uuid;
    private String import_state;//导入状态 数据库默认为0=失败 1=成功  失败时写入失败原因

    @Excel(name = "建设工程规划许可证号",isImportField="true")
    private String layout_no;//'规划许可证书号';
    @Excel(name = "区域",isImportField="true")
    private String region;//区域名称
    @Excel(name = "施工许可证号",isImportField="true")
    private String build_no;//'施工许可证号';
    @Excel(name = "工程名称",isImportField="true")
    private String build_name;//'工程名称';
    @Excel(name = "建设单位",isImportField="true")
    private String construction_unit;//'建设单位';
    @Excel(name = "施工单位",isImportField="true")
    private String build_unit;//'施工单位';
    @Excel(name = "合同价格（万元）",isImportField="true")
    private Float price;//'合同价（万元）';
    @Excel(name = "建设地址",isImportField="true")
    private String address;//'建设地址';
    @Excel(name = "发证日期",isImportField="true",importFormat ="yyyy/MM/dd")
    private String print_date;//'发证日期';
    @Excel(name = "合同开工日期",isImportField="true",importFormat ="yyyy/MM/dd")
    private String build_date;//'开工日期';
    @Excel(name = "合同竣工日期",isImportField="true",importFormat ="yyyy/MM/dd")
    private String compulete_date;//'竣工日期';
    @Excel(name = "建设规模（㎡）",isImportField="true")
    private Float construction_scale;//'建设规模（平方米）';
    @Excel(name = "建设规模其中住宅施工面积（㎡）",isImportField="true")
    private Float s1;//建设规模其中住宅施工面积（㎡）
    @Excel(name = "建设规模其中商业施工面积（㎡）",isImportField="true")
    private Float s2;//建设规模其中商业施工面积（㎡）
    @Excel(name = "建设规模其中办公施工面积（㎡）",isImportField="true")
    private Float s3;//建设规模其中办公施工面积（㎡）
    @Excel(name = "建设规模其中酒店施工面积（㎡）",isImportField="true")
    private Float s4;//建设规模其中酒店施工面积（㎡）
    @Excel(name = "建设规模其中车位施工面积（㎡）",isImportField="true")
    private Float s5;//建设规模其中车位施工面积（㎡）

    public String getImport_state() {
        return import_state;
    }

    public void setImport_state(String import_state) {
        if(null==import_state){
            this.import_state = import_state;
        }else {
            this.import_state = import_state.trim();
        }
    }

    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public Float getS1() {
        return s1;
    }

    public void setS1(Float s1) {
        this.s1 = s1;
    }

    public Float getS2() {
        return s2;
    }

    public void setS2(Float s2) {
        this.s2 = s2;
    }

    public Float getS3() {
        return s3;
    }

    public void setS3(Float s3) {
        this.s3 = s3;
    }

    public Float getS4() {
        return s4;
    }

    public void setS4(Float s4) {
        this.s4 = s4;
    }

    public Float getS5() {
        return s5;
    }

    public void setS5(Float s5) {
        this.s5 = s5;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        if(null==region){
            this.region = region;
        }else {
            this.region = region.trim();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if(null==id){
            this.id = id;
        }else {
            this.id = id.trim();
        }
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        if(null==xh){
            this.xh = xh;
        }else {
            this.xh = xh.trim();
        }
    }

    public String getBuild_no() {
        return build_no;
    }

    public void setBuild_no(String build_no) {
        if(null==build_no){
            this.build_no = build_no;
        }else {
            this.build_no = build_no.trim();
        }
    }

    public String getLayout_no() {
        return layout_no;
    }

    public void setLayout_no(String layout_no) {
        if(null==layout_no){
            this.layout_no = layout_no;
        }else {
            this.layout_no = layout_no.trim();
        }
    }

    public String getBuild_name() {
        return build_name;
    }

    public void setBuild_name(String build_name) {
        if(null==build_name){
            this.build_name = build_name;
        }else {
            this.build_name = build_name.trim();
        }
    }

    public String getConstruction_unit() {
        return construction_unit;
    }

    public void setConstruction_unit(String construction_unit) {
        if(null==construction_unit){
            this.construction_unit = construction_unit;
        }else {
            this.construction_unit = construction_unit.trim();
        }
    }

    public String getBuild_unit() {
        return build_unit;
    }

    public void setBuild_unit(String build_unit) {
        if(null==build_unit){
            this.build_unit = build_unit;
        }else {
            this.build_unit = build_unit.trim();
        }
    }

    public String getSupervisor_unit() {
        return supervisor_unit;
    }

    public void setSupervisor_unit(String supervisor_unit) {
        if (null == supervisor_unit) {
            this.supervisor_unit = supervisor_unit;
        } else {
            this.supervisor_unit = supervisor_unit.trim();
        }
    }

    public Float getConstruction_scale() {
        return construction_scale;
    }

    public void setConstruction_scale(Float construction_scale) {
        this.construction_scale = construction_scale;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (null == address) {
            this.address = address;
        } else {
            this.address = address.trim();
        }
    }

    public String getPrint_date() {
        return print_date;
    }

    public void setPrint_date(String print_date) {
        if (null == print_date) {
            this.print_date = print_date;
        } else {
            this.print_date = print_date.trim();
        }
    }

    public String getBuild_date() {
        return build_date;
    }

    public void setBuild_date(String build_date) {
        if (null == build_date) {
            this.build_date = build_date;
        } else {
            this.build_date = build_date.trim();
        }
    }

    public String getCompulete_date() {
        return compulete_date;
    }

    public void setCompulete_date(String compulete_date) {
        if (null == compulete_date) {
            this.compulete_date = compulete_date;
        } else {
            this.compulete_date = compulete_date.trim();
        }
    }

    public String getData_source() {
        return data_source;
    }

    public void setData_source(String data_source) {
        if (null == data_source) {
            this.data_source = data_source;
        } else {
            this.data_source = data_source.trim();
        }
    }

    public String getRegion_code() {
        return region_code;
    }

    public void setRegion_code(String region_code) {
        if (null == region_code) {
            this.region_code = region_code;
        } else {
            this.region_code = region_code.trim();
        }
    }

    public String getFile_status() {
        return file_status;
    }

    public void setFile_status(String file_status) {
        if (null == file_status) {
            this.file_status = file_status;
        } else {
            this.file_status = file_status.trim();
        }
    }

    public String getInputer_id() {
        return inputer_id;
    }

    public void setInputer_id(String inputer_id) {
        if (null == inputer_id) {
            this.inputer_id = inputer_id;
        } else {
            this.inputer_id = inputer_id.trim();
        }
    }

    @Override
    public String toString() {
        return "Build{" +
                "id='" + id + '\'' +
                ", xh='" + xh + '\'' +
                ", data_source='" + data_source + '\'' +
                ", region_code='" + region_code + '\'' +
                ", file_status='" + file_status + '\'' +
                ", inputer_id='" + inputer_id + '\'' +
                ", layout_no='" + layout_no + '\'' +
                ", supervisor_unit='" + supervisor_unit + '\'' +
                ", uuid=" + uuid +
                ", import_state='" + import_state + '\'' +
                ", region='" + region + '\'' +
                ", build_no='" + build_no + '\'' +
                ", build_name='" + build_name + '\'' +
                ", construction_unit='" + construction_unit + '\'' +
                ", build_unit='" + build_unit + '\'' +
                ", construction_scale=" + construction_scale +
                ", price=" + price +
                ", address='" + address + '\'' +
                ", print_date='" + print_date + '\'' +
                ", build_date='" + build_date + '\'' +
                ", compulete_date='" + compulete_date + '\'' +
                ", s1=" + s1 +
                ", s2=" + s2 +
                ", s3=" + s3 +
                ", s4=" + s4 +
                ", s5=" + s5 +
                '}';
    }
}

package com.funi.platform.edg.bo;/**
 * Created by as on 2016/12/13 0013.
 */

import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Package: [com.funi.platform.edg.bo]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/12/13 0013 20:41]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2016/12/13 0013 20:41，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */
public class Completed {
    private String create_time;//创建时间

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    private String id;//'主键'UUID;
    private String xh;//'流水号';
    private String region_code;//'行政区域代码';
    private String data_source;//'行政区域代码' 关系拓展表使用;
    private String file_status;//'归档状态 0=未归档 1=已归档';
    private String inputer_id;//'录入者id 对应 user表 uuid';
    private String construction_unit;//'建设单位';
    private String address;//'建设地址';
    private String build_name;//'工程名称';
    private String import_state;//导入状态 数据库默认为0=失败 1=成功  失败时写入失败原因

    @Excel(name = "区域",isImportField="true")
    private String region;//区域
    @Excel(name = "竣工备案表编号",isImportField="true")
    private String completed_no;//'工程竣工备案表编号';
    @Excel(name = "建设工程规划许可证号",isImportField="true")
    private String layout_no;//规划许可证号
    @Excel(name = "施工许可证书编号",isImportField="true")
    private String build_no;//施工许可证号
    @Excel(name = "竣工备案时间",isImportField="true",importFormat ="yyyy/MM/dd")
    private String completed_date;//'工程竣工备案时间';
    @Excel(name = "竣工规模（㎡）",isImportField="true")
    private String completed_scale;//'竣工规模（㎡）';
    @Excel(name = "竣工规模其中住宅竣工面积（㎡）",isImportField="true")
    private String j1;//'竣工规模其中住宅竣工面积（㎡）';
    @Excel(name = "竣工规模其中商业竣工面积（㎡）",isImportField="true")
    private String j2;//'竣工规模其中商业竣工面积（㎡）';
    @Excel(name = "竣工规模其中办公竣工面积（㎡）",isImportField="true")
    private String j3;//'竣工规模其中办公竣工面积（㎡）';
    @Excel(name = "竣工规模其中酒店竣工面积（㎡）",isImportField="true")
    private String j4;//'竣工规模其中酒店竣工面积（㎡）';
    @Excel(name = "竣工规模其中车位竣工面积（㎡）",isImportField="true")
    private String j5;//'竣工规模其中车位竣工面积（㎡）';

    public String getData_source() {
        return data_source;
    }

    public void setData_source(String data_source) {
        this.data_source = data_source;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if(null==address){
            this.address = address;
        }else {
            this.address = address.trim();
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

    public String getRegion_code() {
        return region_code;
    }

    public void setRegion_code(String region_code) {
        if(null==region_code){
            this.region_code = region_code;
        }else {
            this.region_code = region_code.trim();
        }
    }

    public String getCompleted_no() {
        return completed_no;
    }

    public void setCompleted_no(String completed_no) {
        if(null==completed_no){
            this.completed_no = completed_no;
        }else {
            this.completed_no = completed_no.trim();
        }
    }

    public String getCompleted_date() {
        return completed_date;
    }

    public void setCompleted_date(String completed_date) {
        if(null==completed_date){
            this.completed_date = completed_date;
        }else {
            this.completed_date = completed_date.trim();
        }
    }

    public String getCompleted_scale() {
        return completed_scale;
    }

    public void setCompleted_scale(String completed_scale) {
        if(null==completed_scale){
            this.completed_scale = completed_scale;
        }else {
            this.completed_scale = completed_scale.trim();
        }
    }

    public String getJ1() {
        return j1;
    }

    public void setJ1(String j1) {
        if(null==j1){
            this.j1 = j1;
        }else {
            this.j1 = j1.trim();
        }
    }

    public String getJ2() {
        return j2;
    }

    public void setJ2(String j2) {
        if(null==j2){
            this.j2 = j2;
        }else {
            this.j2 = j2.trim();
        }
    }

    public String getJ3() {
        return j3;
    }

    public void setJ3(String j3) {
        if(null==j3){
            this.j3 = j3;
        }else {
            this.j3 = j3.trim();
        }
    }

    public String getJ4() {
        return j4;
    }

    public void setJ4(String j4) {
        if(null==j4){
            this.j4 = j4;
        }else {
            this.j4 = j4.trim();
        }
    }

    public String getJ5() {
        return j5;
    }

    public void setJ5(String j5) {
        if(null==j5){
            this.j5 = j5;
        }else {
            this.j5 = j5.trim();
        }
    }

    public String getFile_status() {
        return file_status;
    }

    public void setFile_status(String file_status) {
        if(null==file_status){
            this.file_status = file_status;
        }else {
            this.file_status = file_status.trim();
        }
    }

    public String getInputer_id() {
        return inputer_id;
    }

    public void setInputer_id(String inputer_id) {
        if(null==inputer_id){
            this.inputer_id = inputer_id;
        }else {
            this.inputer_id = inputer_id.trim();
        }
    }

    @Override
    public String toString() {
        return "Completed{" +
                "id='" + id + '\'' +
                ", xh='" + xh + '\'' +
                ", region_code='" + region_code + '\'' +
                ", file_status='" + file_status + '\'' +
                ", inputer_id='" + inputer_id + '\'' +
                ", construction_unit='" + construction_unit + '\'' +
                ", address='" + address + '\'' +
                ", build_name='" + build_name + '\'' +
                ", import_state='" + import_state + '\'' +
                ", region='" + region + '\'' +
                ", completed_no='" + completed_no + '\'' +
                ", layout_no='" + layout_no + '\'' +
                ", build_no='" + build_no + '\'' +
                ", completed_date='" + completed_date + '\'' +
                ", completed_scale='" + completed_scale + '\'' +
                ", j1='" + j1 + '\'' +
                ", j2='" + j2 + '\'' +
                ", j3='" + j3 + '\'' +
                ", j4='" + j4 + '\'' +
                ", j5='" + j5 + '\'' +
                '}';
    }
}

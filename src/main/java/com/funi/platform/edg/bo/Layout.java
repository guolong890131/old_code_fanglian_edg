package com.funi.platform.edg.bo;/**
 * Created by as on 2016/12/13 0013.
 */

import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Package: [com.funi.platform.edg.bo]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/12/13 0013 14:48]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2016/12/13 0013 14:48，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */
public class Layout {

    private String create_time;//创建时间

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    private Integer uuid;
    private String import_state;//导入状态 数据库默认为0=失败 1=成功  失败时写入失败原因
    private String xh;//序号
    private String id;//'uuid'
    private String project_bh;//'项目编号';
    private String region_code;//'行政区域代码';
    private Float c1;// '其中计入容积率：计容总面积（㎡）';
    private String map_code;// '用地红拨图编号';
    private String file_status;// '归档状态 0=未归档 1=已归档';
    private String inputer_id;// '录入者id 对应 user表 uuid';

    @Excel(name = "区域",isImportField="true")
    private String region;//区域名称
    @Excel(name = "国土证号/不动产证书号",isImportField="true")
    private String ffileno;// '国土证号，也叫土地证号';
    @Excel(name = "土地合同编号",isImportField="true")
    private String fctrno;// '土地合同编号';
    @Excel(name = "建设单位",isImportField="true")
    private String construction_unit;// '建设单位名称';
    @Excel(name = "项目名称",isImportField="true")
    private String construction_project_name;// '项目名称';
    @Excel(name = "项目地址",isImportField="true")
    private String address;// '建设地址';
    @Excel(name = "建设工程规划许可证书号",isImportField="true")
    private String layout_no;// '建设工程规划许可证书号';
    @Excel(name = "发证日期",isImportField="true",importFormat ="yyyy/MM/dd")
    private String print_date;// '发证日期';
    @Excel(name = "规划总建筑面积（㎡）",isImportField="true")
    private Float construction_scale;// '规划总建筑面积';
    @Excel(name = "其中计容积率住宅面积（㎡）",isImportField="true")
    private Float c2;// '其中计入容积率：住宅面积（㎡）';
    @Excel(name = "其中计容商业面积（㎡）",isImportField="true")
    private Float c3;// '其中计入容积率：商业面积（㎡）';
    @Excel(name = "其中计容办公面积（㎡）",isImportField="true")
    private Float c4;// '其中计入容积率：办公面积（㎡）';
    @Excel(name = "其中计容酒店面积（㎡）",isImportField="true")
    private Float c5;// '其中计入容积率：酒店面积（㎡）';
    @Excel(name = "其中计容其他面积（㎡）",isImportField="true")
    private Float c6;// '其中计入容积率：其他面积（㎡）（物管用房等）';
    @Excel(name = "其中计容机动车位面积（㎡）",isImportField="true")
    private Float c7;// '其中计入容积率：机动车位面积（㎡）';
    @Excel(name = "其中不计容机动车位面积（㎡）",isImportField="true")
    private Float c8;// '其中不计入容积率：机动车位面积（㎡）';
    @Excel(name = "其中不计容其他面积（㎡）",isImportField="true")
    private Float c9;// '其中不计入容积率其他面积（㎡）（首层架空、垃圾用房等）';


    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
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


    public String getProject_bh() {
        return project_bh;
    }

    public void setProject_bh(String project_bh) {
        if(null==project_bh){
            this.project_bh = project_bh;
        }else {
            this.project_bh = project_bh.trim();
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

    public String getFfileno() {
        return ffileno;
    }

    public void setFfileno(String ffileno) {
        if(null==ffileno){
            this.ffileno = ffileno;
        }else {
            this.ffileno = ffileno.trim();
        }
    }

    public String getFctrno() {
        return fctrno;
    }

    public void setFctrno(String fctrno) {
        if(null==fctrno){
            this.fctrno = fctrno;
        }else {
            this.fctrno = fctrno.trim();
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

    public String getConstruction_project_name() {
        return construction_project_name;
    }

    public void setConstruction_project_name(String construction_project_name) {
        if(null==construction_project_name){
            this.construction_project_name = construction_project_name;
        }else {
            this.construction_project_name = construction_project_name.trim();
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

    public String getPrint_date() {
        return print_date;
    }

    public void setPrint_date(String print_date) {
        if(null==print_date){
            this.print_date = print_date;
        }else {
            this.print_date = print_date.trim();
        }
    }

    public Float getConstruction_scale() {
        return construction_scale;
    }

    public void setConstruction_scale(Float construction_scale) {
        this.construction_scale = construction_scale;
    }

    public Float getC1() {
        return c1;
    }

    public void setC1(Float c1) {
        this.c1 = c1;
    }

    public Float getC2() {
        return c2;
    }

    public void setC2(Float c2) {
        this.c2 = c2;
    }

    public Float getC3() {
        return c3;
    }

    public void setC3(Float c3) {
        this.c3 = c3;
    }

    public Float getC4() {
        return c4;
    }

    public void setC4(Float c4) {
        this.c4 = c4;
    }

    public Float getC5() {
        return c5;
    }

    public void setC5(Float c5) {
        this.c5 = c5;
    }

    public Float getC6() {
        return c6;
    }

    public void setC6(Float c6) {
        this.c6 = c6;
    }

    public Float getC7() {
        return c7;
    }

    public void setC7(Float c7) {
        this.c7 = c7;
    }

    public Float getC8() {
        return c8;
    }

    public void setC8(Float c8) {
        this.c8 = c8;
    }

    public Float getC9() {
        return c9;
    }

    public void setC9(Float c9) {
        this.c9 = c9;
    }

    public String getMap_code() {
        return map_code;
    }

    public void setMap_code(String map_code) {
        if(null==map_code){
            this.map_code = map_code;
        }else {
            this.map_code = map_code.trim();
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
        return "Layout{" +
                "uuid=" + uuid +
                ", import_state='" + import_state + '\'' +
                ", xh='" + xh + '\'' +
                ", id='" + id + '\'' +
                ", project_bh='" + project_bh + '\'' +
                ", region_code='" + region_code + '\'' +
                ", c1=" + c1 +
                ", map_code='" + map_code + '\'' +
                ", file_status='" + file_status + '\'' +
                ", inputer_id='" + inputer_id + '\'' +
                ", region='" + region + '\'' +
                ", ffileno='" + ffileno + '\'' +
                ", fctrno='" + fctrno + '\'' +
                ", construction_unit='" + construction_unit + '\'' +
                ", construction_project_name='" + construction_project_name + '\'' +
                ", address='" + address + '\'' +
                ", layout_no='" + layout_no + '\'' +
                ", print_date='" + print_date + '\'' +
                ", construction_scale=" + construction_scale +
                ", c2=" + c2 +
                ", c3=" + c3 +
                ", c4=" + c4 +
                ", c5=" + c5 +
                ", c6=" + c6 +
                ", c7=" + c7 +
                ", c8=" + c8 +
                ", c9=" + c9 +
                '}';
    }
}

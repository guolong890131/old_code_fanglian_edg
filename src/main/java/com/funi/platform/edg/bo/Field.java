package com.funi.platform.edg.bo;/**
 * Created by as on 2016/12/12 0012.
 */

import org.jeecgframework.poi.excel.annotation.Excel;

import java.util.Date;

/**
 * @Package: [com.funi.platform.edg.bo]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/12/12 0012 9:47]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2016/12/12 0012 9:47，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */

/**
 *
 * 常见Excel 导出问题
 1 如何定义导入导出列?
 为属性添加Excel 注解即可完成列到属性的映射。
 2 如何控制导出列排序规则?
 设置属性对应Excel 注解中orderNum 参数，即可实现列排序。如： @Excel(name = "NAME",orderNum = "1")
 2 如何控制某列隐藏不显示?
 设置属性对应Excel 注解中width 参数为0。如:@Excel(name = "ID",width = 0)
 3 如何输出数值列
 设置属性对应Excel 注解中cellType 类型为CellType.NUMERIC。如: @Excel(name = "ID",cellType = Excel.CellType.NUMERIC)
 4 默认输出2007及以后的版本 导出时候调用ExcelExporter.as2007()然后执行导出操作
 */
public class Field {
    private String create_time;

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    private String id;//fid
    private String fid;//fid
    private String data_source;//行政区域编号
    private String file_status;//归档状态 0=未归档 1=已归档
    private String inputer_id;//录入者id 对应 user表 uuid
    private String import_state;//导入状态 数据库默认为0=失败 1=成功  失败时写入失败原因

    private String ffileno_new;//仅关系表中使用

    @Excel(name = "区域",isImportField="true")
    private String region;

    @Excel(name = "出让方式",isImportField="true")
    private String ftradetype;

    @Excel(name = "宗地号",isImportField="true")
    private String fno_new;

    @Excel(name = "宗地位置",isImportField="true")
    private String faddress;

    @Excel(name = "竞得者",isImportField="true")
    private String fowner;

    @Excel(name = "评估单价(万元/亩)",isImportField="true")
    private Float faprice_mu;

    @Excel(name = "评估楼面地价（元/㎡）",isImportField="true")
    private Float fabprice;

    @Excel(name = "起拍单价（万元/亩）",isImportField="true")
    private Float fbprice_mu;

    @Excel(name = "起拍楼面地价（元/㎡）",isImportField="true")
    private Float fbbprice;

    @Excel(name = "成交单价（万元/亩）",isImportField="true")
    private Float ftrprice_mu;

    @Excel(name = "成交楼面地价（元/㎡）",isImportField="true")
    private Float ftrbprice;

    @Excel(name = "出让时间",isImportField="true",importFormat ="yyyy/MM/dd")
    private String ftrtime;//成交时间

    @Excel(name = "净用地面积(㎡)",isImportField="true")
    private Float ffarea;

    @Excel(name = "土地用途",isImportField="true")
    private String fusage_type;

    @Excel(name = "规划用地性质",isImportField="true")
    private String fplanusg;

    @Excel(name = "计容总建筑面积下限（㎡）",isImportField="true")
    private Float fbtarea_down;

    @Excel(name = "计容总建筑面积上限（㎡）",isImportField="true")
    private Float fbtarea_up;

    @Excel(name = "计容住宅建筑面积下限（㎡）",isImportField="true")
    private Float fhouse_down;

    @Excel(name = "计容住宅建筑面积上限（㎡）",isImportField="true")
    private Float fhouse_up;

    @Excel(name = "计容商服建筑面积下限（㎡）",isImportField="true")
    private Float fbusiness_down;

    @Excel(name = "计容商服建筑面积上限（㎡）",isImportField="true")
    private Float fbusiness_up;

    @Excel(name = "计容自持商服建筑面积下限（㎡）",isImportField="true")
    private Float fbself_down;

    @Excel(name = "计容自持商服建筑面积上限（㎡）",isImportField="true")
    private Float fbself_up;

    @Excel(name = "计容自用商服建筑面积下限（㎡）",isImportField="true")
    private Float fbuse_down;

    @Excel(name = "计容自用商服建筑面积上限（㎡）",isImportField="true")
    private Float fbuse_up;

    @Excel(name = "容积率下限",isImportField="true")
    private Float frate_dow;

    @Excel(name = "容积率上限",isImportField="true")
    private Float frate_up;

    @Excel(name = "土地合同编号",isImportField="true")
    private String fctrno_new;

    @Excel(name = "国土证号/不动产证书号",isImportField="true")
    private String ffileno;

    public String getFfileno_new() {
        return ffileno_new;
    }

    public void setFfileno_new(String ffileno_new) {
        this.ffileno_new = ffileno_new;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        if(null==fid){
            this.fid = fid;
        }else {
            this.fid = fid.trim();
        }

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

    public String getData_source() {
        return data_source;
    }

    public void setData_source(String data_source) {
        if(null==data_source){
            this.data_source = data_source;
        }else {
            this.data_source = data_source.trim();
        }
    }

    public String getFno_new() {
        return fno_new;
    }

    public void setFno_new(String fno_new) {
        if(null==fno_new){
            this.fno_new = fno_new;
        }else {
            this.fno_new = fno_new.trim();
        }
    }

    public String getFaddress() {
        return faddress;
    }

    public void setFaddress(String faddress) {
        if(null==faddress){
            this.faddress = faddress;
        }else {
            this.faddress = faddress.trim();
        }
    }

    public String getFtradetype() {
        return ftradetype;
    }

    public void setFtradetype(String ftradetype) {
        if(null==ftradetype){
            this.ftradetype = ftradetype;
        }else {
            this.ftradetype = ftradetype.trim();
        }
    }

    public String getFowner() {
        return fowner;
    }

    public void setFowner(String fowner) {
        if(null==fowner){
            this.fowner = fowner;
        }else {
            this.fowner = fowner.trim();
        }
    }

    public Float getFaprice_mu() {
        return faprice_mu;
    }

    public void setFaprice_mu(Float faprice_mu) {
        this.faprice_mu = faprice_mu;
    }

    public Float getFabprice() {
        return fabprice;
    }

    public void setFabprice(Float fabprice) {
        this.fabprice = fabprice;
    }

    public Float getFbprice_mu() {
        return fbprice_mu;
    }

    public void setFbprice_mu(Float fbprice_mu) {
        this.fbprice_mu = fbprice_mu;
    }

    public Float getFbbprice() {
        return fbbprice;
    }

    public void setFbbprice(Float fbbprice) {
        this.fbbprice = fbbprice;
    }

    public Float getFtrprice_mu() {
        return ftrprice_mu;
    }

    public void setFtrprice_mu(Float ftrprice_mu) {
        this.ftrprice_mu = ftrprice_mu;
    }

    public Float getFtrbprice() {
        return ftrbprice;
    }

    public void setFtrbprice(Float ftrbprice) {
        this.ftrbprice = ftrbprice;
    }

    public String getFtrtime() {
        return ftrtime;
    }

    public void setFtrtime(String ftrtime) {
        if(null==ftrtime){
            this.ftrtime = ftrtime;
        }else {
            this.ftrtime = ftrtime.trim();
        }
    }

    public Float getFfarea() {
        return ffarea;
    }

    public void setFfarea(Float ffarea) {
        this.ffarea = ffarea;
    }

    public String getFusage_type() {
        return fusage_type;
    }

    public void setFusage_type(String fusage_type) {
        if(null==fusage_type){
            this.fusage_type = fusage_type;
        }else {
            this.fusage_type = fusage_type.trim();
        }
    }

    public String getFplanusg() {
        return fplanusg;
    }

    public void setFplanusg(String fplanusg) {
        if(null==fplanusg){
            this.fplanusg = fplanusg;
        }else {
            this.fplanusg = fplanusg.trim();
        }
    }


    public Float getFbtarea_up() {
        return fbtarea_up;
    }

    public void setFbtarea_up(Float fbtarea_up) {
        this.fbtarea_up = fbtarea_up;
    }

    public Float getFbtarea_down() {
        return fbtarea_down;
    }

    public void setFbtarea_down(Float fbtarea_down) {
        this.fbtarea_down = fbtarea_down;
    }

    public Float getFhouse_up() {
        return fhouse_up;
    }

    public void setFhouse_up(Float fhouse_up) {
        this.fhouse_up = fhouse_up;
    }

    public Float getFhouse_down() {
        return fhouse_down;
    }

    public void setFhouse_down(Float fhouse_down) {
        this.fhouse_down = fhouse_down;
    }

    public Float getFbusiness_up() {
        return fbusiness_up;
    }

    public void setFbusiness_up(Float fbusiness_up) {
        this.fbusiness_up = fbusiness_up;
    }

    public Float getFbusiness_down() {
        return fbusiness_down;
    }

    public void setFbusiness_down(Float fbusiness_down) {
        this.fbusiness_down = fbusiness_down;
    }

    public Float getFbself_up() {
        return fbself_up;
    }

    public void setFbself_up(Float fbself_up) {
        this.fbself_up = fbself_up;
    }

    public Float getFbself_down() {
        return fbself_down;
    }

    public void setFbself_down(Float fbself_down) {
        this.fbself_down = fbself_down;
    }

    public Float getFbuse_up() {
        return fbuse_up;
    }

    public void setFbuse_up(Float fbuse_up) {
        this.fbuse_up = fbuse_up;
    }

    public Float getFbuse_down() {
        return fbuse_down;
    }

    public void setFbuse_down(Float fbuse_down) {
        this.fbuse_down = fbuse_down;
    }

    public Float getFrate_up() {
        return frate_up;
    }

    public void setFrate_up(Float frate_up) {
        this.frate_up = frate_up;
    }

    public Float getFrate_dow() {
        return frate_dow;
    }

    public void setFrate_dow(Float frate_dow) {
        this.frate_dow = frate_dow;
    }

    public String getFctrno_new() {
        return fctrno_new;
    }

    public void setFctrno_new(String fctrno_new) {
        if(fctrno_new==null){
            this.fctrno_new=fctrno_new;
        }else{
            this.fctrno_new=fctrno_new.trim();
        }
    }

    public String getFfileno() {
        return ffileno;
    }

    public void setFfileno(String ffileno) {
        if(ffileno==null){
            this.ffileno=ffileno;
        }else{
            this.ffileno=ffileno.trim();
        }
    }

    public String getFile_status() {
        return file_status;
    }

    public void setFile_status(String file_status) {
        if(file_status==null){
            this.file_status=file_status;
        }else{
            this.file_status=file_status.trim();
        }
    }

    public String getInputer_id() {
        return inputer_id;
    }

    public void setInputer_id(String inputer_id) {
        if(inputer_id==null){
            this.inputer_id=inputer_id;
        }else{
            this.inputer_id=inputer_id.trim();
        }
    }

    @Override
    public String toString() {
        return "Field{" +

                ", id='" + id + '\'' +
                ", fid='" + fid + '\'' +
                ", data_source='" + data_source + '\'' +
                ", file_status='" + file_status + '\'' +
                ", inputer_id='" + inputer_id + '\'' +
                ", import_state='" + import_state + '\'' +
                ", region='" + region + '\'' +
                ", ftradetype='" + ftradetype + '\'' +
                ", fno_new='" + fno_new + '\'' +
                ", faddress='" + faddress + '\'' +
                ", fowner='" + fowner + '\'' +
                ", faprice_mu=" + faprice_mu +
                ", fabprice=" + fabprice +
                ", fbprice_mu=" + fbprice_mu +
                ", fbbprice=" + fbbprice +
                ", ftrprice_mu=" + ftrprice_mu +
                ", ftrbprice=" + ftrbprice +
                ", ftrtime='" + ftrtime + '\'' +
                ", ffarea=" + ffarea +
                ", fusage_type='" + fusage_type + '\'' +
                ", fplanusg='" + fplanusg + '\'' +
                ", fbtarea_down=" + fbtarea_down +
                ", fbtarea_up=" + fbtarea_up +
                ", fhouse_down=" + fhouse_down +
                ", fhouse_up=" + fhouse_up +
                ", fbusiness_down=" + fbusiness_down +
                ", fbusiness_up=" + fbusiness_up +
                ", fbself_down=" + fbself_down +
                ", fbself_up=" + fbself_up +
                ", fbuse_down=" + fbuse_down +
                ", fbuse_up=" + fbuse_up +
                ", frate_dow=" + frate_dow +
                ", frate_up=" + frate_up +
                ", fctrno_new='" + fctrno_new + '\'' +
                ", ffileno='" + ffileno + '\'' +
                '}';
    }
}

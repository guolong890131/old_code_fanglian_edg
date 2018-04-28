package com.funi.platform.edg.bo;

/**
 * Created by Administrator on 2018/2/27.
 */
public class FieldVo extends Field{
    private String fno  ;
    private String fctrno ;
    private Float Famount         ;
    private String Fusage_id       ;
    private String Fusage_name      ;
    private Float Fbtarea      ;
    private Float Fhouse         ;
    private Float  Fbusiness       ;
    private String create_time       ;
    private String  update_time        ;
    private String delete_time        ;
    private String deleted   ;
    private String starttime;
    private String endtime;

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getFno() {
        return fno;
    }

    public void setFno(String fno) {
        this.fno = fno;
    }

    public String getFctrno() {
        return fctrno;
    }

    public void setFctrno(String fctrno) {
        this.fctrno = fctrno;
    }

    public Float getFamount() {
        return Famount;
    }

    public void setFamount(Float famount) {
        Famount = famount;
    }

    public String getFusage_id() {
        return Fusage_id;
    }

    public void setFusage_id(String fusage_id) {
        Fusage_id = fusage_id;
    }

    public String getFusage_name() {
        return Fusage_name;
    }

    public void setFusage_name(String fusage_name) {
        Fusage_name = fusage_name;
    }

    public Float getFbtarea() {
        return Fbtarea;
    }

    public void setFbtarea(Float fbtarea) {
        Fbtarea = fbtarea;
    }

    public Float getFhouse() {
        return Fhouse;
    }

    public void setFhouse(Float fhouse) {
        Fhouse = fhouse;
    }

    public Float getFbusiness() {
        return Fbusiness;
    }

    public void setFbusiness(Float fbusiness) {
        Fbusiness = fbusiness;
    }

    @Override
    public String getCreate_time() {
        return create_time;
    }

    @Override
    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getDelete_time() {
        return delete_time;
    }

    public void setDelete_time(String delete_time) {
        this.delete_time = delete_time;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "FieldVo{" +
                "fno='" + fno + '\'' +
                ", fctrno='" + fctrno + '\'' +
                ", Famount=" + Famount +
                ", Fusage_id='" + Fusage_id + '\'' +
                ", Fusage_name='" + Fusage_name + '\'' +
                ", Fbtarea=" + Fbtarea +
                ", Fhouse=" + Fhouse +
                ", Fbusiness=" + Fbusiness +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                ", delete_time='" + delete_time + '\'' +
                ", deleted='" + deleted + '\'' +
                '}';
    }
}

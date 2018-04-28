package com.funi.platform.edg.bo;

/**
 * @Package: [com.funi.platform.edg.bo]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/3/22 0022 13:49]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2016/3/22 0022 13:49，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录，增加修改记录)]
 * @Version: [v2.0]
 */
public class ParaDate {
    private String last_date; //'最后更新时间';
    private String default_date; // '默认查询时间';
    private String default_y;// '默认查询年';
    private String default_m;//'默认查询月';
    private String default_d;//'默认查询日';

    public String getLast_date() {
        return last_date;
    }

    public void setLast_date(String last_date) {
        this.last_date = last_date;
    }

    public String getDefault_date() {
        return default_date;
    }

    public void setDefault_date(String default_date) {
        this.default_date = default_date;
    }

    public String getDefault_y() {
        return default_y;
    }

    public void setDefault_y(String default_y) {
        this.default_y = default_y;
    }

    public String getDefault_m() {
        return default_m;
    }

    public void setDefault_m(String default_m) {
        this.default_m = default_m;
    }

    public String getDefault_d() {
        return default_d;
    }

    public void setDefault_d(String default_d) {
        this.default_d = default_d;
    }
}

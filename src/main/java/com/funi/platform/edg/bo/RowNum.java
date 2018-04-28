package com.funi.platform.edg.bo;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/2/21.
 */
public class RowNum {
    //间隔时间
    private String intervalTime;
    //已经叫号后的最大排号号码
    private String topNo;

    public String getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(String intervalTime) {
        this.intervalTime = intervalTime;
    }

    public String getTopNo() {
        return topNo;
    }

    public void setTopNo(String topNo) {
        this.topNo = topNo;
    }
}

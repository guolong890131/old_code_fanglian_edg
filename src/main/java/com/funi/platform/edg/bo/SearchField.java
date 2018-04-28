package com.funi.platform.edg.bo;/**
 * Created by as on 2016/12/12 0012.
 */

import java.util.List;

/**
 * @Package: [com.funi.platform.edg.bo]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/12/12 0012 11:02]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2016/12/12 0012 11:02，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */
public class SearchField {
    private String timeType;//时间类型

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    private String regList;//区域
    private String ftradetype;//出让方式
    private String searchType;//查询类型
    private String searchValue;//查询值
    private String startFtr;//出让时间 起
    private String endFtr;//出让时间 止


    public String getRegList() {
        return regList;
    }

    public void setRegList(String regList) {
        this.regList = regList;
    }

    public String getFtradetype() {
        return ftradetype;
    }

    public void setFtradetype(String ftradetype) {
        this.ftradetype = ftradetype;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getStartFtr() {
        return startFtr;
    }

    public void setStartFtr(String startFtr) {
        this.startFtr = startFtr;
    }

    public String getEndFtr() {
        return endFtr;
    }

    public void setEndFtr(String endFtr) {
        this.endFtr = endFtr;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    //========================================================分页===========================================
    private Integer pageSize=10;// 如果每页长度设置为-1，那么就不分页
    private Integer currentPage=1;
    private Integer start;
    private Integer end;

    //算出每页第一条是总数据中的第几条数据
    public Integer getStart(){
        return (currentPage-1)*pageSize;
    }
    //算出每页最后一条是总数据中的第几条数据
    public Integer getEnd() {
        return currentPage*pageSize;
    }

    public Integer getPageSize() {
        return pageSize;
    }
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    public Integer getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    // EasyUI分页组件兼容
    public void setPage(Integer page) {
        this.currentPage = page;
    }
    public void setRows(Integer rows) {
        this.pageSize = rows;
    }


    @Override
    public String toString() {
        return "SearchField{" +
                "regList=" + regList +
                ", ftradetype='" + ftradetype + '\'' +
                ", searchType='" + searchType + '\'' +
                ", searchValue='" + searchValue + '\'' +
                ", startFtr='" + startFtr + '\'' +
                ", endFtr='" + endFtr + '\'' +
                ", pageSize=" + pageSize +
                ", currentPage=" + currentPage +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}

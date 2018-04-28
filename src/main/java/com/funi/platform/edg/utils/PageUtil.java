package com.funi.platform.edg.utils;

/**
 * @author jin.ran@funi365.com
 * @version V1.0
 * @Title: funi-platform-edg
 * @Package com.funi.platform.edg.utils
 * @date 2016/12/13 0013 19:35
 * @Description: ${todo}(用一句话描述该文件做什么)
 */
public class PageUtil{

    private int page = 1;

    private int rows = 20;

    private int start =0;

    private int end = 0;

    public PageUtil() {
        Integer start = (this.getPage()-1)*this.getRows();
        Integer end = (this.getPage())*this.getRows();
        this.setStart(start);
        this.setEnd(end);
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        Integer start = (this.getPage()-1)*rows+1;
        Integer end = (this.getPage())*rows;
        this.setStart(start);
        this.setEnd(end);
        this.rows = rows;
    }

}

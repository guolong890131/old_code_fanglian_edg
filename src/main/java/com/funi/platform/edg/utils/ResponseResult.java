package com.funi.platform.edg.utils;

import java.util.List;

/**
 * @author jin.ran@funi365.com
 * @version V1.0
 * @Title: funi-platform-edg
 * @Package com.funi.platform.edg.utils
 * @date 2016/12/13 0013 19:57
 * @Description: ${todo}(用一句话描述该文件做什么)
 */
public class ResponseResult {

    private Integer total;

    private List rows;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}

package com.funi.platform.edg.query;

/**
 * @Package: [com.funi.platform.edg.query]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/3/21 0021 15:46]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2016/3/21 0021 15:46，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录，增加修改记录)]
 * @Version: [v2.0]
 */
public class TimeQuery {
    private String page_code;//  '001代表市场概况，002待办存量统计';

    public TimeQuery() {
    }

    public TimeQuery(String page_code) {
        this.page_code = page_code;
    }

    public String getPage_code() {
        return page_code;
    }

    public void setPage_code(String page_code) {
        this.page_code = page_code;
    }
}

package com.funi.platform.edg.bo;

import java.util.List;

/**
 * @author jin.ran@funi365.com
 * @version V1.0
 * @Title: funi-platform-edg
 * @Package com.funi.platform.edg.bo
 * @date 2016/12/14 0014 19:36
 * @Description: ${todo}(用一句话描述该文件做什么)
 */
public class BuildVo extends Build {



    private List uuidList;

    private Integer ifcurrent;

    public Integer getIfcurrent() {
        return ifcurrent;
    }

    public void setIfcurrent(Integer ifcurrent) {
        this.ifcurrent = ifcurrent;
    }

    public List getUuidList() {
        return uuidList;
    }

    public void setUuidList(List uuidList) {
        this.uuidList = uuidList;
    }




}

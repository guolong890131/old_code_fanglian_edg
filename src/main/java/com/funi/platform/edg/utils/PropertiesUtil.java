package com.funi.platform.edg.utils;/**
 * Created by as on 2017/2/27 0027.
 */

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * @Package: [com.funi.platform.edg.utils]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2017/2/27 0027 15:18]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2017/2/27 0027 15:18，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */
public class PropertiesUtil extends ReloadableResourceBundleMessageSource {

    //resources.properties中的内容
    public String getResourcesMsg(String key){
        return getProperties("resources").getProperty(key);
    }

    //resources.properties中的内容
    public String getPropertiesMsg(String name,String key){
        return getProperties(name).getProperty(key);
    }
}

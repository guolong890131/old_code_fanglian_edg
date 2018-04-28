package com.funi.platform.edg.annotation;/**
 * Created by as on 2016/12/26 0026.
 */

import java.lang.annotation.*;

/**
 * @Package: [com.funi.platform.edg.annotation]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/12/26 0026 11:08]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2016/12/26 0026 11:08，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperLog {

    /** 要执行的操作模块比如：国土 **/
    public String operationType() default "";

    /** 要执行的具体操作比如：添加用户 **/
    public String operationName() default "";
}

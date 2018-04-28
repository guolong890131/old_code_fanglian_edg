package com.funi.platform.edg.utils;/**
 * Created by as on 2017/2/27 0027.
 */

import com.funi.framework.data.migrate.ImportException;
import com.funi.framework.data.migrate.Importer;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;

import java.io.InputStream;
import java.util.List;

/**
 * @Package: [com.funi.platform.edg.utils]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2017/2/27 0027 14:34]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2017/2/27 0027 14:34，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */
public class MyExcelImporter<E> implements Importer<List<E>> {

    private ImportParams _params = new ImportParams();

    /**
     * 添加构造方法 ，重新配置默认参数
     */
    public MyExcelImporter() {
        //重新配置默认值
        this._params.setKeyIndex(null);
    }


    private Class cls;


    /**
     * 设置主键列索引值(默认为0)
     *
     * @param keyIndex 列索引值
     * @return 当前导入对象
     */
    public MyExcelImporter<E> setKeyIndex(Integer keyIndex) {
        this._params.setKeyIndex(keyIndex);
        return this;
    }

    /**
     * 设置行对象类名称
     *
     * @param cls 类定义
     * @return 当前对象
     */
    public MyExcelImporter<E> setItemClass(Class<E> cls) {
        this.cls = cls;
        return this;
    }

    /**
     * 设置标题行,默认无标题
     *
     * @param titleRows 表头行下标 ,计数方式与Excel 左侧序号保持一致
     * @return 当前对象
     */
    public MyExcelImporter<E> setTitleRows(int titleRows) {
        _params.setTitleRows(titleRows);
        return this;
    }

    /**
     * 设置表头行,默认第一行为表头行
     * 用于查找表头行各列与对象属性关系
     *
     * @param headRows 表头行下标 ,计数方式与Excel 左侧序号保持一致
     * @return 当前对象
     */
    public MyExcelImporter<E> setHeadRows(int headRows) {
        _params.setHeadRows(headRows);
        return this;
    }

    /**
     * 设置数据读取开始数据行，比如setStartRows(2),则意味着数据从第二行开始导入
     *
     * @param startRows 行下标,计数方式与Excel 左侧序号保持一致
     * @return 当前对象
     */
    public MyExcelImporter<E> setStartRows(int startRows) {
        _params.setStartRows(startRows);
        return this;
    }

    @SuppressWarnings("unchecked")
    public List<E> execute(InputStream inputStream) {
        if (cls == null) {
            throw new IllegalArgumentException("Import target class not found , you may set this value through setItemClass");
        }
        if (_params.getHeadRows() <= 0) {
            throw new IllegalArgumentException("head row number should start from 1");
        }
        if (_params.getHeadRows() > _params.getStartRows()) {
            throw new IllegalArgumentException("start row number should greater than head row number");
        }
        try {
            return ExcelImportUtil.importExcel(inputStream, cls, _params);
        } catch (Exception e) {
            throw new ImportException(e.getMessage(), e);
        }
    }
}

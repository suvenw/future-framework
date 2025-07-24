package com.suven.framework.util.excel;

import java.util.ArrayList;
import java.util.List;

import com.suven.framework.http.api.IBaseExcelData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;

/**
 * 模板的读取类，读取excel的数据 封装到list列表中， 通过dao对象 调用业务操作方法
 *
 * @author dongxie
 */
public class ExcelListener extends AnalysisEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelListener.class);
    /**
     * 每隔50条存储数据库，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 50;//还需迁移到 常量配置表 不能写死在类里
    List<Object> list = new ArrayList<Object>();

    /**
     * 基础dao。
     */
    private IBaseExcelData baseDAO;

//
//    private ExcelListener() {
//        // 这里是demo，所以随便new一个。实际使用如果到了spring,请使用下面的有参构造函数
//        //baseDAO = new ExcelBaseDAO();
//    }


    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param baseDao
     */
    public static ExcelListener build(IBaseExcelData baseDao) {
        ExcelListener listener =  new ExcelListener();
        listener.baseDAO = baseDao;
        return listener;
    }


    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data
     *            one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(Object data, AnalysisContext context) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        list.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            this.saveData();
            // 存储完成清理 list
           list.clear();
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        LOGGER.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", list.size());
        baseDAO.saveData(list);
        LOGGER.info("存储数据库成功！");
    }
}
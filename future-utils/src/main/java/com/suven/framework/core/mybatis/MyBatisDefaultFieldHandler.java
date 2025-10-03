package com.suven.framework.core.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 通用参数填充实现类
 *
 * 如果没有显式的对通用参数进行赋值，这里会对通用参数进行填充、赋值
 *
 * @author hexiaowu
 */
public class MyBatisDefaultFieldHandler implements MetaObjectHandler {



    private static final String CREATE_TIME = "createDate";
    private static final String UPDATE_TIME = "updateDate";
    /**
     * 插入时的填充策略
     * @param metaObject 代理对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        // 新增时，同时设置创建时间和更新时间
        LocalDateTime now = LocalDateTime.now();
        if (metaObject.hasSetter(CREATE_TIME)) {
            this.setFieldValByName(CREATE_TIME, now, metaObject);
        }  if (metaObject.hasSetter(UPDATE_TIME)) {
            this.setFieldValByName(UPDATE_TIME, now, metaObject);
        }
    }

    public Long getLoginUserId(){
        return 0L;
    }

    public Long getModifierUserName(){
        return 0L;
    }

    public List<String> modifierFieldValByTime(){
        return Arrays.asList("updateDate");
    }

    public List<String> modifierFieldValByName(){
        return Arrays.asList("updater");
    }
    public Object getFieldValueByNameMap(String fieldName){
        return "updater";
    }
    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新时间为空，则以当前时间为更新时间
        if (metaObject.hasSetter(UPDATE_TIME)) {
            this.setFieldValByName(UPDATE_TIME, LocalDateTime.now(), metaObject);
        }
    }


}

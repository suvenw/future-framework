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

    @Override
    public void insertFill(MetaObject metaObject) {
//        if (Objects.nonNull(metaObject) && metaObject.getOriginalObject() instanceof BaseDO) {
//            BaseDO baseDO = (BaseDO) metaObject.getOriginalObject();
//
//            LocalDateTime current = LocalDateTime.now();
//            // 创建时间为空，则以当前时间为插入时间
//            if (Objects.isNull(baseDO.getCreateTime())) {
//                baseDO.setCreateTime(current);
//            }
//            // 更新时间为空，则以当前时间为更新时间
//            if (Objects.isNull(baseDO.getUpdateTime())) {
//                baseDO.setUpdateTime(current);
//            }
//
//            Long userId = WebFrameworkUtils.getLoginUserId();
//            // 当前登录用户不为空，创建人为空，则当前登录用户为创建人
//            if (Objects.nonNull(userId) && Objects.isNull(baseDO.getCreator())) {
//                baseDO.setCreator(userId.toString());
//            }
//            // 当前登录用户不为空，更新人为空，则当前登录用户为更新人
//            if (Objects.nonNull(userId) && Objects.isNull(baseDO.getUpdater())) {
//                baseDO.setUpdater(userId.toString());
//            }
//        }
    }

    public Long getLoginUserId(){
        return 0L;
    }

    public Long getModifierUserName(){
        return 0L;
    }

    public List<String> modifierFieldValByTime(){
        return Arrays.asList("updateTime");
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
        modifierFieldValByTime().forEach(time->{
            if (time.equals("updateTime") || time.equals("updateDate")){
                Object modifyTime = getFieldValByName(time, metaObject);
                if (Objects.isNull(modifyTime)) {
                    setFieldValByName(time, LocalDateTime.now(), metaObject);
                }
            }
        });
        modifierFieldValByName().forEach(name->{
            Object fieldValByName = getFieldValueByNameMap(name);
            if (Objects.isNull(fieldValByName)) {
                setFieldValByName(name, fieldValByName, metaObject);
            }
    });
    }
}

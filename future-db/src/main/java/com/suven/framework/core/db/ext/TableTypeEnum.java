package com.suven.framework.core.db.ext;

import java.util.HashMap;
import java.util.Map;

public enum  TableTypeEnum{
        TB_ID(1),
        TB_HASH(2),//2^n进行分表
        TB_TIME(3);

        private int id;

        TableTypeEnum(int id) {
            this.id = id;
        }
        private static Map<Integer, TableTypeEnum> typeMap = new HashMap<>();
        static {
            for(TableTypeEnum type : values()) {
                typeMap.put(type.id, type);
            }
        }

        /**
         * 验证是否包括这类型的属性类型
         * @param type
         * @return
         */
        public static TableTypeEnum get(int type){
            return typeMap.get(type);
        }

    }
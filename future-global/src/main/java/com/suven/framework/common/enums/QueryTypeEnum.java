package com.suven.framework.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * "1" =
 * "2" !=
 * "3" &gt;
 * "4" &gt;=
 * "5" &lt;
 * "6" &lt;=
 * "7" like
 * "8" lift like
 * "9" right like
 *
 * @author by jueyue on 18-8-7.
 */
public enum QueryTypeEnum {

    EMPTY(0, "","","为空"),
    EQ(1, "=?","eq","等于"),
    NOT_EQ(2, "<>?","notIn","不等于"),
    GT(3, ">?","gt","大于"),
    GT_EQ(4, ">=?","ge","大于等于"),
    LT(5, "<?","lt","小于"),
    LT_EQ(6, "<=?","le","小于等于"),
    LIKE(7, " like concat('%',concat(?,'%')) ","like","like模糊查询"),
    LEFT_LIKE(8, " like concat('%', ?)","likeLeft","左模糊查询"),
    RIGHT_LIKE(9, " like concat(?, '%')","likeRight","右模糊查询"),
;

    private Integer code;
    private String jdbcKey;
    private String mybatisKey;
    private String desc;

    QueryTypeEnum(Integer code, String jdbcKey,String mybatisKey, String desc) {
        this.code = code;
        this.jdbcKey = jdbcKey;
        this.mybatisKey = mybatisKey;
        this.desc=desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getJdbcKey() {
        return jdbcKey;
    }

    public String getMybatisKey() {
        return mybatisKey;
    }

    public String getDesc() {
        return desc;
    }

    private static Map<String, QueryTypeEnum> enumMap = new HashMap<>();
    static {
        for(QueryTypeEnum enums : values()) {
            enumMap.put(enums.desc, enums);
        }

    }


    public static int getId(String des){
        QueryTypeEnum enums =  enumMap.get(des);
        if(null == enums){
            return 0;
        }return enums.code;
    }

    public static QueryTypeEnum getByCode(int code){
        if(0==code) {
            return null;
        }
        for(QueryTypeEnum val :values()){
            if (val.getCode().intValue()==code){
                return val;
            }
        }
        return  null;
    }

}

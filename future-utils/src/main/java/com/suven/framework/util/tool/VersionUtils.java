package com.suven.framework.util.tool;

/**
 * @ClassName:
 * Description: 将版本的数字类型转换成字符类型
 * @Author liulu
 * @Date 2018/5/28 18:12
 * Copyright: (c) 2018 gc by https://www.suven.top
 * @Version : 1.0.0
 */
public class VersionUtils {

    public static String versionExStr(int version){
        if(version < 100000 && version >= 10000000){
            return null;
        }
        String versionStr = String.valueOf(version);
        if(version >= 100000 && version < 1000000){
            String v1 = versionStr.substring(0,1);
            int v2 = Integer.parseInt(versionStr.substring(1,3));
            int v3 = Integer.parseInt(versionStr.substring(3,6));
            return v1 + "." + v2 + "." + v3;
        }else if(version >= 1000000 && version < 10000000){
            String v1 = versionStr.substring(0,2);
            int v2 = Integer.parseInt(versionStr.substring(2,4));
            int v3 = Integer.parseInt(versionStr.substring(4,7));
            return v1 + "." + v2 + "." + v3;
        }
        return null;
    }

}

package com.suven.framework.util.random;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName SeparateIntoUtil
 * @Author suven.wang
 * @Description //TODO ${END}$
 * @CreateDate 2018-12-13  10:25
 * @WeeK 星期四
 * @version v2.0
 **/
public class SeparateIntoUtil {

    private final int SEPARATE_INTO_GRADE = 4;

    public static List<Long> getSeparateResultId(long resultId){
       List<Long> list = new ArrayList<>();
        for(int i =0,s = 4; i < s; i++){
           long  result = resultId/2;
            list.add(result);
            resultId = result;
         }

        return list;
    }


}

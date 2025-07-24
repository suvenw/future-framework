package com.suven.framework.util.validate;


import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * 正则表达式
 *
 * ^\\d+$　　//非负整数（正整数 + 0）
 * ^[0-9]*[1-9][0-9]*$　　//正整数
 * ^((-\\d+)|(0+))$　　//非正整数（负整数 + 0）
 * ^-[0-9]*[1-9][0-9]*$　　//负整数
 * ^-?\\d+$　　　　//整数
 * ^\\d+(　　//非负浮点数（正浮点数 + 0）
 * ^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$　
 * //正浮点数
 * ^((-\\d+(　　//非正浮点数（负浮点数 + 0）
 * ^(-(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*)))$
 * //负浮点数
 * ^(-?\\d+)(　　//浮点数
 * ^[A-Za-z]+$　　//由26个英文字母组成的字符串
 * ^[A-Z]+$　　//由26个英文字母的大写组成的字符串
 * ^[a-z]+$　　//由26个英文字母的小写组成的字符串
 * ^[A-Za-z0-9]+$　　//由数字和26个英文字母组成的字符串
 * ^\\w+$　　//由数字、26个英文字母或者下划线组成的字符串
 * ^[\\w-]+(　　　　//email地址
 * ^[a-zA-z]+://(　　//url
 * ^[A-Za-z0-9_]*$
 * 匹配完整域名的正则表达式：
 * [a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\.
 */
public class ValidateRegex {

    /**
     * 1，不能全部是数字
     * 2，不能全部是字母
     * 3，必须是数字或字母
     * 只要能同时满足上面3个要求就可以了，写出来如下：
     * ^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$
     *
     * 正则表达式校验密码
     * 1、密码必须由数字、字符、特殊字符三种中的两种组成；
     * 2、密码长度不能少于8个字符；
     * 满足以上两点，应该怎么实现？
     * (?!^\\d+$)不能全是数字
     * (?!^[a-zA-Z]+$)不能全是字母
     * (?!^[_#@]+$)不能全是符号（这里只列出了部分符号，可自己增加，有的符号可能需要转义）
     * .{8,}长度不能少于8位
     * 合起来就是
     * (?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_#@]+$).{8,}
     * 完整的js正则表达式：
     *
     * //强：字母+数字+特殊字符
     *  ^(?![a-zA-z]+$)(?!\d+$)(?![!@#$%^&*]+$)(?![a-zA-z\d]+$)(?![a-zA-z!@#$%^&*]+$)(?![\d!@#$%^&*]+$)[a-zA-Z\d!@#$%^&*]+$
     *
     *
     * //中：字母+数字，字母+特殊字符，数字+特殊字符
     *      ^(?![a-zA-z]+$)(?!\d+$)(?![!@#$%^&*]+$)[a-zA-Z\d!@#$%^&*]+$
     * //弱：纯数字，纯字母，纯特殊字符
     * ^(?:\d+|[a-zA-Z]+|[!@#$%^&*]+)$
     * @return
     */
    public static boolean checkPassword(String password){
        String regex ="(?!^\\\\d+$)(?!^[a-zA-Z]+$)(?!^[_#@]+$).{6,20}";// "/^[a-zA-Z0-9]{6,20}$/";
        return  password.matches(regex);
    }
    /** 校验密码：只能输入6-20个字母、数字、下划线 **/
    public static boolean isPassword(String password){
        String regex = "/^(\\w){6,20}$/";
        return  password.matches(regex);
    }

    /** 校验是否全由数字组成 **/
  public static boolean isNumber(String number){
      String regex = "/^[0-9]{1,20}$/";
      return  number.matches(regex);
    }

    /** 校验登录名：只能输入5-20个以字母开头、可带数字、“_”、“.”的字串 **/
    public static boolean isRegisterUserName(String number){
        String regex = "/^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){4,19}$/";
        return  number.matches(regex);
    }

    /** 校验用户姓名：只能输入1-30个以字母开头的字串 **/
    public static boolean isTrueName(String number){
        String regex = "/^[a-zA-Z]{1,30}$/";
        return  number.matches(regex);
    }

    /** 校验普通电话、传真号码：可以“+”开头，除数字外，可含有“-” **/
    public static boolean isTelPhone(String number){
        String regex = "/^[+]{0,1}(\\d){1,3}[ ]?([-]?((\\d)|[ ]){1,12})+$/";
        return  number.matches(regex);
    }

    /** 校验手机号码：必须以数字开头，除数字外，可含有“-” **/
    public static boolean isPhone(String number){
        //regex = "/^1\d{10}$/";
        String regex = "/^[+]{0,1}(\\d){1,9}[ ]?([-]?((\\d)|[ ]){1,12})+$/";
        return  number.matches(regex);
    }

    /** 校验邮政编码 **/
    public static boolean isPostalCode(String number){
        String regex = "/^[a-zA-Z0-9 ]{3,12}$/";
        return  number.matches(regex);
    }

    /** 校验IP **/
    public static boolean isIP(String number){
        String regex = "/^[0-9.]{1,20}$/";
        return  number.matches(regex);
    }

    /** 验证邮箱 **/
    public static boolean isEmail(String number){
        // "regex = /^\w+@\w+(\.[a-zA-Z]{2,3}){1,2}$/";
        String regex = "/^\\w+@\\w+(\\.[a-zA-Z]{2,3}){1,2}$/";
        return  number.matches(regex);
    }

    //------------------常量定义
    /**
     * Email正则表达式="^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
     */
    //public static final String EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";;
    public static final String EMAIL = "\\w+(\\.\\w+)*@\\w+(\\.\\w+)+";
    /**
     * 电话号码正则表达式= (^(\d{2,4}[-_－—]?)?\d{3,8}([-_－—]?\d{3,8})?([-_－—]?\d{1,7})?$)|(^0?1[35]\d{9}$)
     */
    public static final String PHONE = "(^(\\d{2,4}[-_－—]?)?\\d{3,8}([-_－—]?\\d{3,8})?([-_－—]?\\d{1,7})?$)|(^0?1[35]\\d{9}$)" ;
    /**
     * 国内三家运营商的号段分别如下：
     * 联通号段:130/131/132/155/156/185/186/145/176；
     * 电信号段:133/153/180/181/189/177；
     * 移动号段：134/135/136/137/138/139/150/151/152/157/158/159/182/183/184/187/188/147/178。
     */
    public static final String MOBILE ="^(13[0-9]|14[0-9]|15[0-9]|16[0-9]|17[0-9]|18[0-9]|19[0-9])[\\d]{8}$";

    /**Regular
     * Integer正则表达式 ^-?(([1-9]\d*$)|0)
     */
    public static final String  INTEGER = "^-?(([1-9]\\d*$)|0)";
    /**
     * 正整数正则表达式 >=0 ^[1-9]\d*|0$
     */
    public static final String  INTEGER_NEGATIVE = "^[1-9]\\d*|0$";
    /**
     * 负整数正则表达式 <=0 ^-[1-9]\d*|0$
     */
    public static final String  INTEGER_POSITIVE = "^-[1-9]\\d*|0$";
    /**
     * Double正则表达式 ^-?([1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0)$
     */
    public static final String  DOUBLE ="^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$";
    /**
     * 正Double正则表达式 >=0  ^[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0$　
     */
    public static final String  DOUBLE_NEGATIVE ="^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0$";
    /**
     * 负Double正则表达式 <= 0  ^(-([1-9]\d*\.\d*|0\.\d*[1-9]\d*))|0?\.0+|0$
     */
    public static final String  DOUBLE_POSITIVE ="^(-([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*))|0?\\.0+|0$";
    /**
     * 年龄正则表达式 ^(?:[1-9][0-9]?|1[01][0-9]|120)$ 匹配0-120岁
     */
    public static final String  AGE="^(?:[1-9][0-9]?|1[01][0-9]|120)$";
    /**
     * 邮编正则表达式  [0-9]\d{5}(?!\d) 国内6位邮编
     */
    public static final String  CODE="[0-9]\\d{5}(?!\\d)";
    /**
     * 匹配由数字、26个英文字母或者下划线组成的字符串 ^\w+$
     */
    public static final String STR_ENG_NUM_="^\\w+$";
    /**
     * 匹配由数字和26个英文字母组成的字符串 ^[A-Za-z0-9]+$
     */
    public static final String STR_ENG_NUM="^[A-Za-z0-9]+";
    /**
     * 匹配由26个英文字母组成的字符串  ^[A-Za-z]+$
     */
    public static final String STR_ENG="^[A-Za-z]+$";
    /**
     * 过滤特殊字符串正则
     * regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
     */
    public static final String STR_SPECIAL="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
    /***
     * 日期正则 支持：
     *  YYYY-MM-DD
     *  YYYY/MM/DD
     *  YYYY_MM_DD
     *  YYYYMMDD
     *  YYYY.MM.DD的形式
     */
    public static final String DATE_ALL="((^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._]?)(10|12|0?[13578])([-\\/\\._]?)(3[01]|[12][0-9]|0?[1-9])$)" +
            "|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._]?)(11|0?[469])([-\\/\\._]?)(30|[12][0-9]|0?[1-9])$)" +
            "|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._]?)(0?2)([-\\/\\._]?)(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|(^([3579][26]00)" +
            "([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)" +
            "|(^([1][89][0][48])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|(^([2-9][0-9][0][48])([-\\/\\._]?)" +
            "(0?2)([-\\/\\._]?)(29)$)" +
            "|(^([1][89][2468][048])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|(^([2-9][0-9][2468][048])([-\\/\\._]?)(0?2)" +
            "([-\\/\\._]?)(29)$)|(^([1][89][13579][26])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|" +
            "(^([2-9][0-9][13579][26])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$))";
    /***
     * 日期正则 支持：
     *  YYYY-MM-DD
     */
    public static final String DATE_FORMAT1="(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";

    /**
     * URL正则表达式
     * 匹配 http www ftp
     */
    public static final String URL = "^(http|www|ftp|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?" +
            "(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*" +
            "(\\w*:)*(\\w*\\+)*(\\w*\\.)*" +
            "(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$";

    /**
     * 身份证正则表达式
     */
    public static final String IDCARD="((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65)[0-9]{4})" +
            "(([1|2][0-9]{3}[0|1][0-9][0-3][0-9][0-9]{3}" +
            "[Xx0-9])|([0-9]{2}[0|1][0-9][0-3][0-9][0-9]{3}))";

    /**
     * 机构代码
     */
    public static final String JIGOU_CODE = "^[A-Z0-9]{8}-[A-Z0-9]$";

    /**
     * 匹配数字组成的字符串  ^[0-9]+$
     */
    public static final String STR_NUM = "^[0-9]+$";

    /**
     * 匹配2~10位的中文字符串  ([\u4e00-\u9fa5]){2,10}
     */
    public static final String IS_CHINESE = "([\u4e00-\u9fa5]){2,10}";

    /**
     * 包含字母
     */
    public static final String IS_CHARACTER=".*[a-zA-Z]+.*";

    /**
     * 包含数字
     */
    public static final String IS_NUMBER=".*\\d+.*";

    /**
     * 判断字段是否为空 符合返回ture
     * @param str
     * @return boolean
     */
    public static synchronized boolean StrisNull(String str) {
        return null == str || str.trim().length() <= 0 ? true : false ;
    }
    /**
     * 判断字段是非空 符合返回ture
     * @param str
     * @return boolean
     */
    public static  boolean StrNotNull(String str) {
        return !StrisNull(str) ;
    }
    /**
     * 字符串null转空
     * @param str
     * @return boolean
     */
    public static  String nulltoStr(String str) {
        return StrisNull(str)?"":str;
    }
    /**
     * 字符串null赋值默认值
     * @param str    目标字符串
     * @param defaut 默认值
     * @return String
     */
    public static  String nulltoStr(String str,String defaut) {
        return StrisNull(str)?defaut:str;
    }

    /**
     * 判断是否为手机号码 符合返回ture
     * @param str
     * @return boolean
     */
    public static  boolean isMobile(String str) {
        return Regular(str,MOBILE);
    }
    /**
     * 判断是否为Url 符合返回ture
     * @param str
     * @return boolean
     */
    public static  boolean isUrl(String str) {
        return Regular(str,URL);
    }
    /**
     * 判断字段是否为数字 正负整数 正负浮点数 符合返回ture
     * @param str
     * @return boolean
     */
    public static  boolean isNumbers(String str) {
        return Regular(str,DOUBLE);
    }
    /**
     * 判断字段是否为INTEGER  符合返回ture
     * @param str
     * @return boolean
     */
    public static  boolean isInteger(String str) {
        return Regular(str,INTEGER);
    }
    /**
     * 判断字段是否为正整数正则表达式 >=0 符合返回ture
     * @param str
     * @return boolean
     */
    public static  boolean isINTEGER_NEGATIVE(String str) {
        return Regular(str,INTEGER_NEGATIVE);
    }
    /**
     * 判断字段是否为负整数正则表达式 <=0 符合返回ture
     * @param str
     * @return boolean
     */
    public static  boolean isINTEGER_POSITIVE(String str) {
        return Regular(str,INTEGER_POSITIVE);
    }
    /**
     * 判断字段是否为DOUBLE 符合返回ture
     * @param str
     * @return boolean
     */
    public static  boolean isDouble(String str) {
        return Regular(str,DOUBLE);
    }
    /**
     * 判断字段是否为正浮点数正则表达式 >=0 符合返回ture
     * @param str
     * @return boolean
     */
    public static  boolean isDOUBLE_NEGATIVE(String str) {
        return Regular(str,DOUBLE_NEGATIVE);
    }
    /**
     * 判断字段是否为负浮点数正则表达式 <=0 符合返回ture
     * @param str
     * @return boolean
     */
    public static  boolean isDOUBLE_POSITIVE(String str) {
        return Regular(str,DOUBLE_POSITIVE);
    }
    /**
     * 判断字段是否为日期 符合返回ture
     * @param str
     * @return boolean
     */
    public static  boolean isDate(String str) {
        return Regular(str,DATE_ALL);
    }
    /**
     * 验证2010-12-10
     * @param str
     * @return
     */
    public static  boolean isDate1(String str) {
        return Regular(str,DATE_FORMAT1);
    }
    /**
     * 判断字段是否为年龄 符合返回ture
     * @param str
     * @return boolean
     */
    public static  boolean isAge(String str) {
        return Regular(str,AGE) ;
    }
    /**
     * 判断字段是否超长
     * 字串为空返回fasle, 超过长度{leng}返回ture 反之返回false
     * @param str
     * @param leng
     * @return boolean
     */
    public static  boolean isLengOut(String str,int leng) {
        return StrisNull(str)?false:str.trim().length() > leng ;
    }
    /**
     * 判断字段是否为身份证 符合返回ture
     * @param str
     * @return boolean
     */
    public static  boolean isIdCard(String str) {
        if(StrisNull(str)) return false ;
        if(str.trim().length() == 15 || str.trim().length() == 18) {
            return Regular(str,IDCARD);
        }else {
            return false ;
        }

    }
    /**
     * 判断字段是否为邮编 符合返回ture
     * @param str
     * @return boolean
     */
    public static  boolean isCode(String str) {
        return Regular(str,CODE) ;
    }
    /**
     * 判断字符串是不是全部是英文字母
     * @param str
     * @return boolean
     */
    public static boolean isEnglish(String str) {
        return Regular(str,STR_ENG) ;
    }
    /**
     * 判断字符串是不是全部是英文字母+数字
     * @param str
     * @return boolean
     */
    public static boolean isENG_NUM(String str) {
        return Regular(str,STR_ENG_NUM) ;
    }
    /**
     * 判断字符串是不是全部是英文字母+数字+下划线
     * @param str
     * @return boolean
     */
    public static boolean isENG_NUM_(String str) {
        return Regular(str,STR_ENG_NUM_) ;
    }
    /**
     * 过滤特殊字符串 返回过滤后的字符串
     * @param str
     * @return boolean
     */
    public static  String filterStr(String str) {
        Pattern p = Pattern.compile(STR_SPECIAL);
        Matcher m = p.matcher(str);
        return   m.replaceAll("").trim();
    }

    /**
     * 校验机构代码格式
     * @return
     */
    public static boolean isJigouCode(String str){
        return Regular(str,JIGOU_CODE) ;
    }

    /**
     * 判断字符串是不是数字组成
     * @param str
     * @return boolean
     */
    public static boolean isSTR_NUM(String str) {
        return Regular(str,STR_NUM) ;
    }

    /**
     * 匹配是否符合正则表达式pattern 匹配返回true
     * @param str 匹配的字符串
     * @param pattern 匹配模式
     * @return boolean
     */
    private static  boolean Regular(String str,String pattern){
        if(null == str || str.trim().length()<=0)
            return false;
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 判断字符串是不是2~10位的中文组成的
     * @param str
     * @return boolean
     */
    public static boolean isChinese(String str) {
        return Regular(str,IS_CHINESE) ;
    }

    /**
     * 判断字符串是否包含字母
     * @param str
     * @return boolean
     */
    public static boolean isContainCharacter(String str) {
        return Regular(str,IS_CHARACTER);
    }
    /**
     * 判断字符串是否包含数字
     * @param str
     * @return boolean
     */
    public static boolean isContainNumber(String str) {
        return Regular(str,IS_NUMBER) ;
    }

    /**
     * 校验mac地址是否符合规范
     * @param mac
     * @return boolean
     */
    public static boolean checkMac(String mac){
        if(StringUtils.isBlank(mac)) return false;
        String patternMac="^[a-fA-F0-9]{2}+:[a-fA-F0-9]{2}+:[a-fA-F0-9]{2}+:[a-fA-F0-9]{2}+:[a-fA-F0-9]{2}+:[a-fA-F0-9]{2}$";
        Pattern pa= Pattern.compile(patternMac);
        return pa.matcher(mac).find();
    }

    /**
     * 判断字符是否是中文
     *
     * @param c
     *            字符
     * @return 是否是中文
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS

                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT
                || ub == Character.UnicodeBlock.VERTICAL_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否是乱码
     *
     * @param strName
     *            字符串
     * @return 是否是乱码
     */
    public static boolean isMessyCode(String strName) {
        Pattern p = Pattern.compile("\\s*|t*|r*|n*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = ch.length;
        float count = 0;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!Character.isLetterOrDigit(c)) {
                if (!isChinese(c)) {
                    count = count + 1;
                }
            }
        }
        float result = count / chLength;
        if (result > 0.4) {
            return true;
        } else {
            return false;
        }
    }









//    public static void main(String[] args) throws Exception {
//        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
//
//        String value = "aaa";  // 长度不够
//        System.out.println(value.matches(regex));
//
//        value = "1111aaaa1111aaaaa";  // 太长
//        System.out.println(value.matches(regex));
//
//        value = "111111111"; // 纯数字
//        System.out.println(value.matches(regex));
//
//        value = "aaaaaaaaa"; // 纯字母
//        System.out.println(value.matches(regex));
//
//        value = "####@@@@#"; // 特殊字符
//        System.out.println(value.matches(regex));
//
//        value = "1111aaaa";  // 数字字母组合
//        System.out.println(value.matches(regex));
//
//        value = "aaaa1111"; // 数字字母组合
//        System.out.println(value.matches(regex));
//
//        value = "aa1111aa";	// 数字字母组合
//        System.out.println(value.matches(regex));
//
//        value = "11aaaa11";	// 数字字母组合
//        System.out.println(value.matches(regex));
//
//        value = "aa11aa11"; // 数字字母组合
//        System.out.println(value.matches(regex));
//    }
}

package com.suven.framework.test.rule.util;

import com.alibaba.fastjson2.JSON;
import com.suven.framework.common.constants.GlobalConfigConstants;
import com.suven.framework.util.crypt.CryptUtil;
import com.suven.framework.util.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 签名参数测试工具类
 * <p>
 * 提供客户端和服务端签名生成功能，支持 MD5 加密
 * </p>
 *
 * @author suven
 */
@SuppressWarnings({"rawtypes", "unchecked", "unused", "StringBufferReplaceableByString"})
public class SignParamTest {

    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(SignParamTest.class);

    /**
     * 获取签名（客户端）
     * <p>
     * 将 head 和 body 对象转换为参数并生成签名
     * </p>
     *
     * @param head 头部对象，可为 null
     * @param body 主体对象，可为 null
     * @return 签名字符串（MD5 加密后取 8-24 位）
     */
    public static String getClientSign(@Nullable Object head, @Nullable Object body) {
        String signParam = getClientSignParam(head, body);
        String pass = paramMd5(signParam);
        return pass.substring(8, 24);
    }

    /**
     * 获取签名（客户端，简化版）
     * <p>
     * 直接从 Map 对象生成签名
     * </p>
     *
     * @param map 参数 Map
     * @return 签名字符串（MD5 加密后取 8-24 位）
     */
    public static String getClientSing(Map map) {
        String signParam = getSignParam(map);
        String pass = paramMd5(signParam);
        return pass.substring(8, 24);
    }

    /**
     * 获取签名（服务端）
     * <p>
     * 从参数中移除 sign 字段后生成签名用于校验
     * </p>
     *
     * @param param 参数 Map
     * @return 签名字符串（MD5 加密后取 8-24 位）
     */
    public static String getServerSign(Map<String, Object> param) {
        param = getParameterMap(param);
        if (param.containsKey("sign")) {
            param.remove("sign");  // 不参与服务端校验
        }
        String signParam = getServerSignParam(param);
        String pass = paramMd5(signParam);
        return pass.substring(8, 24);
    }

    /**
     * 获取签名的参数（服务端）
     * <p>
     * 将 Map 参数转换为 key=value& 格式
     * </p>
     *
     * @param param 参数 Map
     * @return 加密字符串（格式：a=1&b=1）
     */
    public static String getServerSignParam(Map<String, Object> param) {
        return getSignParam(param);
    }

    /**
     * 获取签名的参数（客户端）
     * <p>
     * 将 head 和 body 对象合并后转换为参数字符串
     * </p>
     *
     * @param head 头部对象，可为 null
     * @param body 主体对象，可为 null
     * @return 加密字符串（格式：a=1&b=1）
     */
    public static String getClientSignParam(@Nullable Object head, @Nullable Object body) {
        return getSignParam(object2MapParam(head, body));
    }

    /**
     * 获取签名的参数
     * <p>
     * 将 Map 参数转换为 key=value& 格式
     * </p>
     *
     * @param param 参数 Map
     * @return 加密字符串（格式：a=1&b=1），如果参数为空则返回空字符串
     */
    public static String getSignParam(Map param) {
        StringBuffer sb = new StringBuffer();
        String strBody = "";
        if (null != param && param.size() > 0) {
            for (Iterator<Map.Entry<String, Object>> iterator = param.entrySet().iterator(); iterator.hasNext(); ) {
                Map.Entry<String, Object> obj = iterator.next();
                sb.append(obj.getKey()).append("=").append(String.valueOf(obj.getValue())).append("&");
            }
            if (sb.length() > 1) {
                strBody = sb.substring(0, sb.length() - 1);
            }
        }
        return strBody;
    }

    /**
     * 将对象转换为 Map 参数
     * <p>
     * 将 head 和 body 对象序列化为 JSON 后转换为 TreeMap
     * </p>
     *
     * @param head 头部对象，可为 null
     * @param body 主体对象，可为 null
     * @return 合并后的 Map，如果两个参数都为 null 则返回 null
     */
    @Nullable
    public static Map<String, String> object2MapParam(@Nullable Object head, @Nullable Object body) {
        Map bodyMap = null;
        Map headMap = null;
        try {
            if (null != body) {
                bodyMap = JsonUtils.toTreeMap(JSON.toJSONString(body));
            }
            if (null != head) {
                headMap = JsonUtils.toTreeMap(JSON.toJSONString(head));
            }
        } catch (Exception e) {
            logger.error("body or head content should be key-value");
        }
        if (headMap != null && bodyMap != null) {
            headMap.putAll(bodyMap);
        }
        return headMap;
    }

    /**
     * MD5 加密
     * <p>
     * 对参数字符串追加应用密钥后进行 MD5 加密
     * </p>
     *
     * @param param 待加密的字符串
     * @return 小写的 MD5 加密字符串，加密失败则返回空字符串
     */
    private static String paramMd5(String param) {
        String pass = "";
        try {
            pass = CryptUtil.md5(param + GlobalConfigConstants.TOP_SERVER_APPKEY).toLowerCase();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return pass;
    }

    /**
     * 将 getParameterMap 转成可读的 Map
     * <p>
     * 使用 TreeMap 对参数进行排序，处理数组参数（取最后一个值）
     * </p>
     *
     * @param properties 原始参数 Map
     * @return 排序后的参数 Map
     */
    private static Map<String, Object> getParameterMap(Map<String, Object> properties) {
        Map<String, Object> returnMap = new TreeMap<>();
        Iterator<?> entries = properties.entrySet().iterator();
        Map.Entry<String, Object> entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                value = values[values.length - 1];
            } else {
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }
}

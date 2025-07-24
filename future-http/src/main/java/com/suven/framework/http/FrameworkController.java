package com.suven.framework.http;

import com.suven.framework.common.constants.GlobalConfigConstants;
import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.http.data.vo.RequestParserVo;
import com.suven.framework.http.handler.OutputAesResponse;
import com.suven.framework.http.handler.OutputAllResponse;
import com.suven.framework.http.handler.OutputCacheResponse;
import com.suven.framework.http.handler.OutputResponse;
import com.suven.framework.http.inters.IResultCodeEnum;
import com.suven.framework.http.message.HttpMsgEnumError;
import com.suven.framework.http.message.HttpRequestGetMessage;
import com.suven.framework.http.message.HttpRequestPostMessage;
import com.suven.framework.http.processor.url.SysURLCommand;
import com.suven.framework.util.crypt.CryptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;


@ApiDoc(
        group = DocumentConst.Global.API_DOC_BASE_GROUP,
        groupDesc= DocumentConst.Global.API_DOC_BASE_DES,
        module = "API 接口公共文档", isApp = true
)
@Controller
public class FrameworkController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @ApiDoc(
            value = "错误信息查询",
            request = RequestParserVo.class,
            response = HttpMsgEnumError.class
    )
    @RequestMapping(value = SysURLCommand.sys_get_error_no, method = RequestMethod.GET)
    public void getSysMsgEnumError(OutputResponse out, RequestParserVo jsonParse)  {
        Map<Integer, IResultCodeEnum> error =  IResultCodeEnum.MsgEnumType.getMsgTypeMap();
        Collection<IResultCodeEnum> list =   error.values();
        List<HttpMsgEnumError>  voList = new ArrayList<>();
        list.forEach(code ->{
            voList.add(HttpMsgEnumError.build().init(code.getCode(),code.getMsg()));
        });
        Collections.sort(voList);
        out.write(voList);
    }

//    @ApiDoc(
//            value = "文档数据搜索数据",
//            request = ApiDocJsonParse.class,
//            response = String.class
//    )
//    @RequestMapping(value = SysURLCommand.sys_get_service_api_doc, method = RequestMethod.GET)
//    public void getServiceApiDoc(OutputResponse out, ApiDocJsonParse jsonParse)  {
//        SwaggerResultBean api =  SwaggerReflectionsDoc.getApiDoc(jsonParse.getSearch());
//        if(null != api){
//            out.writeResult(api);
//            return;
//        }
//        StringBuilder sb = new StringBuilder();
//        sb.append("please go to  application.properties file  system config info : ");
//        sb.append(GlobalConfigConstants.TOP_SERVER_API_ENABLED);
//        sb.append(" = true");
//        out.writeResult(sb.toString());
//
//    }

    @ApiDoc(
            value = "接口服务基本响应",
            request = RequestParserVo.class,
            response = long.class
    )
    @RequestMapping(value = SysURLCommand.sys_get_framework, method = RequestMethod.GET)
    public void getFrameworkApi(OutputResponse out, RequestParserVo jsonParse)  {
        out.writeSuccess();
    }

//    @ApiDoc(
//            value = "接口服务基本响应",
//            request = HttpRequestGetMessage.class,
//            response = long.class
//    )
//
//    @RequestMapping(value = SysURLCommand.sys_get_framework_http, method = RequestMethod.GET)
//    public void getFrameworkApiHttp(OutputResponse out, HttpRequestGetMessage jsonParse)  {
//        String json = JsonUtils.toJson(jsonParse);
//        Map map  = JsonUtils.toMap(json);
//        map.put("sysVersion", "android");
//        map.put("version", "1001001");
//        map.put("appId", "1000");
//        map.put("userId", 123456);
//        map.put("accessToken", "123456");
//        map.put("device", "3f25d333755240e");
//        map.put("times", System.currentTimeMillis());
////        map.clear();
//        final String md5Key = "H@s0zSix!fiNger8";
//        HttpRequestParams params = HttpRequestParseApi.getHttpRequestParams(map,md5Key);
//
//        HttpClientUtil.getAsync("http://127.0.0.1:8080/api/sys/postParam",params.getSignToBodyMap(),true);
//
//        out.writeSuccess();
//    }


    @ApiDoc(
            value = "POST请求的公共参数",
            request = HttpRequestPostMessage.class,
            response = long.class
    )
    @RequestMapping(value = SysURLCommand.sys_get_post_param, method = RequestMethod.GET)
    public void getSystemPostParam(OutputResponse out, ApiDocJsonParse jsonParse)  {
        out.writeSuccess();
    }

    @ApiDoc(
            value = "Get请求的公共参数",
            request = HttpRequestGetMessage.class,
            response = long.class
    )
    @RequestMapping(value = SysURLCommand.sys_get_get_param, method = RequestMethod.GET)
    public void getSystemGetParam(OutputResponse out, ApiDocJsonParse jsonParse)  {
        out.writeSuccess();
    }


    @ApiDoc(
            value = "请求参数加密例子",
            request = SystemParamSignParse.class,
            response = long.class
    )
    @RequestMapping(value = SysURLCommand.sys_get_sign_param, method = RequestMethod.GET)
    public void getSystemParamSign(OutputResponse out, SystemParamSignParse signParse)  {
        String param = "";
        if(signParse.getSalt() == 1){
            param = CryptUtil.md5(param + GlobalConfigConstants.TOP_SERVER_APPKEY).toLowerCase();
        }else{
            param  = CryptUtil.md5(signParse.getCliSign()).toLowerCase();
        }

        String result =  "原始参数:["+signParse.getCliSign()+"]\n  md5 加密后的结果:[" + param +"]";
        out.write(result);
    }

    public static class ApiDocJsonParse extends RequestParserVo {
        @ApiDesc(value= "模糊搜索中文描述或url " )
        private String search;

        public String getSearch() {
            return search;
        }

        public void setSearch(String search) {
            this.search = search;
        }
    }

    public static class SystemParamSignParse extends RequestParserVo {
        @ApiDesc(value= "参与加密的字符串:eg: appId=10000&accessToken=abcedeeee&device=abcdeee123 " )
        private String cliSign;
        @ApiDesc(value= "是否拼接盐,1.后台拼接,0.直接md5,不拼接盐信息" )
        private int salt;

        public String getCliSign() {
            return cliSign;
        }

        public void setCliSign(String cliSign) {
            this.cliSign = cliSign;
        }

        public int getSalt() {
            return salt;
        }

        public void setSalt(int salt) {
            this.salt = salt;
        }
    }

    @ApiDoc(
            value = "接口服务基本例子-base test,返回success",
            request = RequestParserVo.class,
            response = long.class
    )
    @RequestMapping(value = SysURLCommand.sys_get_base_test, method = RequestMethod.GET)
    public void getFrameworkSuccessTest(OutputResponse out, RequestParserVo jsonParse)  {
        out.write("success");
    }

    @ApiDoc(
            value = "接口服务基本例子-redis cache test",
            request = RequestParserVo.class,
            response = long.class
    )
    @RequestMapping(value = SysURLCommand.sys_get_cache_test, method = RequestMethod.GET)
    public void getFrameworkCacheTest(OutputCacheResponse out, RequestParserVo jsonParse)  {
        logger.info("========== FrameworkController getFrameworkCacheTest ==========" );
        out.write("success");
    }

    @ApiDoc(
            value = "接口服务基本例子-aes_test",
            request = RequestParserVo.class,
            response = long.class
    )
    @RequestMapping(value = SysURLCommand.sys_get_aes_test, method = RequestMethod.GET)
    public void getFrameworkAesTest(OutputAesResponse out, RequestParserVo jsonParse)  {
        out.write("success");
    }

    @ApiDoc(
            value = "接口服务基本例子-aes_test",
            request = HttpRequestGetMessage.class,
            response = long.class
    )
    @RequestMapping(value = SysURLCommand.sys_get_aes_cache_test, method = RequestMethod.GET)
    public void getFrameworkAesCacheTest(OutputAllResponse out, RequestParserVo jsonParse)  {
        out.write("success");
    }



}
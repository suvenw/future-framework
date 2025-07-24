package com.suven.framework.core.aliyun.sms;

import com.aliyun.oss.ClientException;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.suven.framework.util.json.JsonUtils;
import com.suven.framework.util.json.StringFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
@ConditionalOnClass(AliyunSmsConfigSetting.class)
public class AliyunSmsApi {




    @Autowired(required = false)
   private AliyunSmsConfigSetting aliyunSmsConfigSetting;

    private IAcsClient client;

//    private String singName = ""; //"云通信" 必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
//    private String templateCode = "";//"SMS_1000000" 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
//




    @Bean("smsClient")
    @ConditionalOnBean({AliyunSmsConfigSetting.class})
    public IAcsClient buildClient() throws Exception{
        if(null == aliyunSmsConfigSetting){
            return null;
        }
        if(null == client){
            int cTimeout =   aliyunSmsConfigSetting.getConnectTimeout()  > 0 ? aliyunSmsConfigSetting.getConnectTimeout()  : 3000;
            int rTimeout =   aliyunSmsConfigSetting.getConnectTimeout()  > 0 ? aliyunSmsConfigSetting.getConnectTimeout()  : 3000;
            System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(cTimeout));
            System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(rTimeout));
            //初始化ascClient,暂时不支持多region（请勿修改）
            DefaultProfile profile = DefaultProfile.getProfile(aliyunSmsConfigSetting.getRegionId(), aliyunSmsConfigSetting.getAccessKeyId(),aliyunSmsConfigSetting.getAccessSecret());
            DefaultProfile.addEndpoint("cn-hangzhou","cn-hangzhou", aliyunSmsConfigSetting.getProduct(), aliyunSmsConfigSetting.getDomain());

            client = new DefaultAcsClient(profile);
            return client;
        }return this.client;

    }




    public AliyunSmsResult send(AliyunSmsRequestDto aliyunSmsRequestDto){

        try {

            CommonRequest request = new CommonRequest();
            request.setMethod(MethodType.POST);
            request.setDomain( aliyunSmsConfigSetting.getDomain());
            request.setVersion("2017-05-25");
            request.setAction("SendSms");
            Map<String,Object> map =  JsonUtils.objectToMap(aliyunSmsRequestDto);

            if(null == map || map.isEmpty()){
                return AliyunSmsResult.build();
             }
            map.forEach((k,v)->{

                request.putQueryParameter( StringFormat.toUpperCaseFirstOne(k), String.valueOf(v));
            });
            CommonResponse response = client.getCommonResponse(request);
            if(response == null){
               return AliyunSmsResult.build();
            }
            AliyunSmsResult aliyunSmsResult =  JsonUtils.parseObject(response.getData(), AliyunSmsResult.class);
            return  aliyunSmsResult;

        } catch (ClientException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AliyunSmsResult.build();
    }



    public  static void main(String[] args) throws Exception
    {

        String url = "http://gw.api.taobao.com/router/rest";
        String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
        String accessKeyId = "LTAIZc2IX6pLi7b3";
        String accessSecret = "T9wsDLO5Iz543AZZOvT7x9RKglYhWV";
         String regionId = "cn-hangzhou"; //地域名；


        // 构建一个客户端实例，用于发起请求
        IClientProfile profile = DefaultProfile.getProfile(
                regionId, accessKeyId,accessSecret);
//        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        DefaultProfile.addEndpoint( "cn-hangzhou", product, domain);
        DefaultAcsClient client = new DefaultAcsClient(profile);

        try
        {
            String templateParam = "{ \"code\":\""+123456+"\"}";

            CommonRequest request = new CommonRequest();
//            request.setSysProtocol(ProtocolType.HTTP);
            request.setSysMethod(MethodType.POST);
            // 构造请求
            request.setSysDomain( domain);
            request.setSysMethod(MethodType.POST);
            request.setSysVersion("2017-05-25");
            request.setSysAction("SendSms");



            AliyunSmsRequestDto aliyunSmsRequestDto = AliyunSmsRequestDto.buildRegister("15819123355","1234");
//
            Map<String,Object> map =  JsonUtils.objectToMap(aliyunSmsRequestDto);


            map.forEach((k,v)->{

                request.putQueryParameter( StringFormat.toUpperCaseFirstOne(k), String.valueOf(v));
            });
//
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }



}

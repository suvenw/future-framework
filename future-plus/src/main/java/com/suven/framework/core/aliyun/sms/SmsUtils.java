//package top.suven.future.core.aliyun.sms;
//
//public class SmsUtils {
//    // 产品名称:云通信短信API产品,开发者无需替换
//    static final String product = "Dysmsapi";
//    // 产品域名,开发者无需替换
//    static final String domain = "dysmsapi.aliyuncs.com";
//
//    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
//    static final String accessKeyId = "LTAIH5NIE4eD8EQ4";
//    static final String accessKeySecret = "ASYTA7loViLL4ATorkcnlBxJMYwW2N";
//
////    public static SendSmsResponse sendSms(String telephone, String checkCode) {
////
////        // 可自助调整超时时间
////        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
////        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
////
////        // 初始化acsClient,暂不支持region化
////        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
////        try {
////            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
////        } catch (ClientException e1) {
////
////            e1.printStackTrace();
////        }
////        IAcsClient acsClient = new DefaultAcsClient(profile);
////
////        // 组装请求对象-具体描述见控制台-文档部分内容
////        CommonRequest request = new CommonRequest();
////        // 必填:待发送手机号
////        request.setPhoneNumbers(telephone);
////        // 必填:短信签名-可在短信控制台中找到
////        request.setSignName("速运快递");
////        // 必填:短信模板-可在短信控制台中找到
////        request.setTemplateCode("SMS_76455085");
////        // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
////        request.setTemplateParam("{\"name\":" + telephone + ", \"code\":" + checkCode + "}");
////
////        // hint 此处可能会抛出异常，注意catch
////        SendSmsResponse sendSmsResponse = null;
////
////        try {
////            sendSmsResponse = acsClient.getAcsResponse(request);
////        } catch (Exception e) {
////
////            e.printStackTrace();
////        }
////
////        return sendSmsResponse;
////
////    }
////
////    public static void main(String[] args) {
////        System.out.println(SmsUtils.sendSms("13429891756", "123456"));
////
////    }
//
//}
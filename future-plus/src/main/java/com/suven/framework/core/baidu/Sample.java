package com.suven.framework.core.baidu;

import com.alibaba.fastjson.JSON;
import com.baidu.aip.util.Util;
import okhttp3.*;
import org.json.JSONObject;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;

public class Sample {
    public static final String API_KEY = "nDuA7GxRW3cFc0bU1j6yEZKF";
    public static final String SECRET_KEY = "9y8cKbk5jhMueYZdxutM9MX4I984LrDw";
    public static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    public static String content = "当提到太宗皇帝，我们可以联想到以下特点：\n" +
            "1.开创盛世：太宗是唐朝的开国皇帝，他在位期间实行了一系列改革措施，开创了唐朝的盛世，被誉为“贞观之治”。\n" +
            "2.尊儒重文：太宗十分注重文化教育，推崇儒学，提拔和重用许多文学、历史学家和哲学家，他本人也亲自撰写了许多文学和历史著作。\n" +
            "3.爱民惜民：太宗非常关心人民的生活，采取了一系列措施改善民生，如减轻赋税、开放州县市场、加强农业生产等。\n" +
            "4.崇尚武功：太宗也非常崇尚武功，他自己也是一名出色的将领，多次亲征战场，扩大了唐朝的疆域。\n" +
            "5.重视外交：太宗重视外交，通过外交手段巩固了唐朝的国际地位，与邻国建立了友好关系，达成了一系列重要条约。\n" +
            "通过以上多维度分析和联想，我们可以快速地记住太宗皇帝的特点，他是一个开创盛世、尊儒重文、爱民惜民、崇尚武功和重视外交的伟大皇帝，对中国历史和文化产生了深远影响。";

    public static void main2(String []args) throws IOException{
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");



        String contents = URLEncoder.encode(content,"UTF-8");
//        RequestBody body = RequestBody.create(mediaType,"tex="+ contents);
//                RequestBody body = RequestBody.create(mediaType, "tex=%E5%85%B3%E4%BA%8E%E5%94%90%E4%BB%A3%E8%91%97%E5%90%8D%E5%A4%AA%E5%AE%97%E7%9A%87%E5%B8%9D%E7%9A%84%E7%AE%80%E4%BB%8B%20%E5%A4%AA%E5%AE%97%EF%BC%88599%E5%B9%B4%EF%BC%8D649%E5%B9%B4%EF%BC%89%EF%BC%8C%E5%A7%93%E6%9D%8E%EF%BC%8C%E5%90%8D%E4%B8%96%E6%B0%91%EF%BC%8C%E5%94%90%E6%9C%9D%E7%AC%AC%E4%BA%8C%E4%BD%8D%E7%9A%87%E5%B8%9D%EF%BC%8C%E4%BB%96%E5%9C%A8%E4%BD%8D%E6%9C%9F%E9%97%B4%E5%AE%9E%E8%A1%8C%E4%BA%86%E4%B8%80%E7%B3%BB%E5%88%97%E6%94%B9%E9%9D%A9%E5%92%8C%E5%BC%80%E6%8B%93%E6%89%A9%E5%BC%A0%E7%9A%84%E6%94%BF%E7%AD%96%EF%BC%8C%E5%BC%80%E5%88%9B%E4%BA%86%E5%94%90%E6%9C%9D%E7%9A%84%E7%9B%9B%E4%B8%96%E3%80%82%20%E5%A4%AA%E5%AE%97%E6%98%AF%E6%9D%8E%E6%B8%8A%E7%9A%84%E7%AC%AC%E4%B8%89%E4%B8%AA%E5%84%BF%E5%AD%90%EF%BC%8C%E5%B9%B4%E5%B0%91%E6%97%B6%E5%8D%B3%E6%98%BE%E5%BE%97%E8%81%AA%E6%98%8E%E8%BF%87%E4%BA%BA%EF%BC%8C%E4%BB%8E%E5%B0%8F%E5%8F%97%E5%88%B0%E5%BE%88%E5%A5%BD%E7%9A%84%E6%95%99%E8%82%B2%E3%80%82%E4%BB%96%E6%B7%B1%E8%B0%99%E5%84%92%E5%AE%B6%E7%BB%8F%E5%85%B8%EF%BC%8C%E7%86%9F%E7%BB%83%E6%8E%8C%E6%8F%A1%E6%96%87%E5%AD%A6%E3%80%81%E5%8E%86%E5%8F%B2%E5%92%8C%E5%93%B2%E5%AD%A6%E7%AD%89%E5%AD%A6%E7%A7%91%EF%BC%8C%E5%90%8C%E6%97%B6%E4%B9%9F%E5%96%84%E4%BA%8E%E6%AD%A6%E8%89%BA%E3%80%82%20%E8%B4%9E%E8%A7%82%E5%B9%B4%E9%97%B4%EF%BC%8C%E5%A4%AA%E5%AE%97%E5%AE%9E%E8%A1%8C%E4%BA%86%E4%B8%80%E7%B3%BB%E5%88%97%E6%94%B9%E9%9D%A9%EF%BC%8C%E5%8C%85%E6%8B%AC%E6%94%B9%E9%9D%A9%E6%94%BF%E6%B2%BB%E5%88%B6%E5%BA%A6%E3%80%81%E5%8A%A0%E5%BC%BA%E4%B8%AD%E5%A4%AE%E9%9B%86%E6%9D%83%E3%80%81%E5%87%8F%E8%BD%BB%E8%B5%8B%E7%A8%8E%E3%80%81%E5%BC%80%E6%94%BE%E5%B7%9E%E5%8E%BF%E5%B8%82%E5%9C%BA%E3%80%81%E5%8A%A0%E5%BC%BA%E5%86%9C%E4%B8%9A%E7%94%9F%E4%BA%A7%E7%AD%89%E3%80%82%E4%BB%96%E8%BF%98%E6%8F%90%E5%80%A1%E7%A7%91%E4%B8%BE%E5%88%B6%E5%BA%A6%EF%BC%8C%E9%80%89%E6%8B%94%E4%BA%BA%E6%89%8D%EF%BC%8C%E6%8E%A8%E5%B4%87%E5%84%92%E5%AD%A6%EF%BC%8C%E6%8F%90%E6%8B%94%E5%92%8C%E9%87%8D%E7%94%A8%E8%AE%B8%E5%A4%9A%E6%96%87%E5%AD%A6%E3%80%81%E5%8E%86%E5%8F%B2%E5%AD%A6%E5%AE%B6%E5%92%8C%E5%93%B2%E5%AD%A6%E5%AE%B6%EF%BC%8C%E4%BB%96%E6%9C%AC%E4%BA%BA%E4%B9%9F%E4%BA%B2%E8%87%AA%E6%92%B0%E5%86%99%E4%BA%86%E8%AE%B8%E5%A4%9A%E6%96%87%E5%AD%A6%E5%92%8C%E5%8E%86%E5%8F%B2%E8%91%97%E4%BD%9C%E3%80%82%20%E5%A4%AA%E5%AE%97%E9%87%8D%E8%A7%86%E6%AD%A6%E5%8A%9F%EF%BC%8C%E8%87%AA%E5%B7%B1%E4%B9%9F%E6%98%AF%E4%B8%80%E5%90%8D%E5%87%BA%E8%89%B2%E7%9A%84%E5%B0%86%E9%A2%86%EF%BC%8C%E5%A4%9A%E6%AC%A1%E4%BA%B2%E5%BE%81%E6%88%98%E5%9C%BA%EF%BC%8C%E6%89%A9%E5%A4%A7%E4%BA%86%E5%94%90%E6%9C%9D%E7%9A%84%E7%96%86%E5%9F%9F%E3%80%82%E4%BB%96%E8%BF%98%E9%87%8D%E8%A7%86%E5%A4%96%E4%BA%A4%EF%BC%8C%E9%80%9A%E8%BF%87%E5%A4%96%E4%BA%A4%E6%89%8B%E6%AE%B5%E5%B7%A9%E5%9B%BA%E4%BA%86%E5%94%90%E6%9C%9D%E7%9A%84%E5%9B%BD%E9%99%85%E5%9C%B0%E4%BD%8D%EF%BC%8C%E4%B8%8E%E9%82%BB%E5%9B%BD%E5%BB%BA%E7%AB%8B%E4%BA%86%E5%8F%8B%E5%A5%BD%E5%85%B3%E7%B3%BB%EF%BC%8C%E8%BE%BE%E6%88%90%E4%BA%86%E4%B8%80%E7%B3%BB%E5%88%97%E9%87%8D%E8%A6%81%E6%9D%A1%E7%BA%A6%E3%80%82%20%E5%A4%AA%E5%AE%97%E5%9C%A8%E4%BD%8D%E6%9C%9F%E9%97%B4%EF%BC%8C%E5%94%90%E6%9C%9D%E7%BB%8F%E6%B5%8E%E3%80%81%E6%96%87%E5%8C%96%E3%80%81%E8%89%BA%E6%9C%AF%E3%80%81%E7%A7%91%E6%8A%80%E7%AD%89%E6%96%B9%E9%9D%A2%E9%83%BD%E5%8F%96%E5%BE%97%E4%BA%86%E9%95%BF%E8%B6%B3%E7%9A%84%E5%8F%91%E5%B1%95%EF%BC%8C%E8%A2%AB%E5%90%8E%E4%BA%BA%E7%A7%B0%E4%B8%BA%E2%80%9C%E8%B4%9E%E8%A7%82%E4%B9%8B%E6%B2%BB%E2%80%9D%E3%80%82%E4%BB%96%E6%98%AF%E4%B8%AD%E5%9B%BD%E5%8E%86%E5%8F%B2%E4%B8%8A%E6%9D%B0%E5%87%BA%E7%9A%84%E6%94%BF%E6%B2%BB%E5%AE%B6%E3%80%81%E5%86%9B%E4%BA%8B%E5%AE%B6%E3%80%81%E6%96%87%E5%8C%96%E5%AE%B6%EF%BC%8C%E5%AF%B9%E4%B8%AD%E5%9B%BD%E5%8E%86%E5%8F%B2%E5%92%8C%E6%96%87%E5%8C%96%E4%BA%A7%E7%94%9F%E4%BA%86%E6%B7%B1%E8%BF%9C%E5%BD%B1%E5%93%8D%E3%80%82&tok=" + getAccessToken() + "&cuid=C02XHQSCJG5J&ctp=1&lan=zh&spd=3&pit=5&vol=5&per=103&aue=3");

        HashMap<String, Object> options = new HashMap<String, Object>();
        options.put("spd", "3");
        options.put("pit", "5");
        options.put("vol", "8");
        options.put("per", "0");
        options.put("tex", contents);
        String ext = "&tok=" + getAccessToken() + "&cuid=C02XHQSCJG5J&ctp=1&lan=zh&spd=3&pit=5&vol=5&per=103&aue=3";

        RequestBody body = RequestBody.create(mediaType,"tex="+ contents+ext);
//        RequestBody body = RequestBody.create(mediaType, "tex=%E5%BD%93%E6%8F%90%E5%88%B0%E5%A4%AA%E5%AE%97%E7%9A%87%E5%B8%9D%EF%BC%8C%E6%88%91%E4%BB%AC%E5%8F%AF%E4%BB%A5%E8%81%94%E6%83%B3%E5%88%B0%E4%BB%A5%E4%B8%8B%E7%89%B9%E7%82%B9%EF%BC%9A%201.%E5%BC%80%E5%88%9B%E7%9B%9B%E4%B8%96%EF%BC%9A%E5%A4%AA%E5%AE%97%E6%98%AF%E5%94%90%E6%9C%9D%E7%9A%84%E5%BC%80%E5%9B%BD%E7%9A%87%E5%B8%9D%EF%BC%8C%E4%BB%96%E5%9C%A8%E4%BD%8D%E6%9C%9F%E9%97%B4%E5%AE%9E%E8%A1%8C%E4%BA%86%E4%B8%80%E7%B3%BB%E5%88%97%E6%94%B9%E9%9D%A9%E6%8E%AA%E6%96%BD%EF%BC%8C%E5%BC%80%E5%88%9B%E4%BA%86%E5%94%90%E6%9C%9D%E7%9A%84%E7%9B%9B%E4%B8%96%EF%BC%8C%E8%A2%AB%E8%AA%89%E4%B8%BA%E2%80%9C%E8%B4%9E%E8%A7%82%E4%B9%8B%E6%B2%BB%E2%80%9D%E3%80%82%202.%E5%B0%8A%E5%84%92%E9%87%8D%E6%96%87%EF%BC%9A%E5%A4%AA%E5%AE%97%E5%8D%81%E5%88%86%E6%B3%A8%E9%87%8D%E6%96%87%E5%8C%96%E6%95%99%E8%82%B2%EF%BC%8C%E6%8E%A8%E5%B4%87%E5%84%92%E5%AD%A6%EF%BC%8C%E6%8F%90%E6%8B%94%E5%92%8C%E9%87%8D%E7%94%A8%E8%AE%B8%E5%A4%9A%E6%96%87%E5%AD%A6%E3%80%81%E5%8E%86%E5%8F%B2%E5%AD%A6%E5%AE%B6%E5%92%8C%E5%93%B2%E5%AD%A6%E5%AE%B6%EF%BC%8C%E4%BB%96%E6%9C%AC%E4%BA%BA%E4%B9%9F%E4%BA%B2%E8%87%AA%E6%92%B0%E5%86%99%E4%BA%86%E8%AE%B8%E5%A4%9A%E6%96%87%E5%AD%A6%E5%92%8C%E5%8E%86%E5%8F%B2%E8%91%97%E4%BD%9C%E3%80%82%203.%E7%88%B1%E6%B0%91%E6%83%9C%E6%B0%91%EF%BC%9A%E5%A4%AA%E5%AE%97%E9%9D%9E%E5%B8%B8%E5%85%B3%E5%BF%83%E4%BA%BA%E6%B0%91%E7%9A%84%E7%94%9F%E6%B4%BB%EF%BC%8C%E9%87%87%E5%8F%96%E4%BA%86%E4%B8%80%E7%B3%BB%E5%88%97%E6%8E%AA%E6%96%BD%E6%94%B9%E5%96%84%E6%B0%91%E7%94%9F%EF%BC%8C%E5%A6%82%E5%87%8F%E8%BD%BB%E8%B5%8B%E7%A8%8E%E3%80%81%E5%BC%80%E6%94%BE%E5%B7%9E%E5%8E%BF%E5%B8%82%E5%9C%BA%E3%80%81%E5%8A%A0%E5%BC%BA%E5%86%9C%E4%B8%9A%E7%94%9F%E4%BA%A7%E7%AD%89%E3%80%82%204.%E5%B4%87%E5%B0%9A%E6%AD%A6%E5%8A%9F%EF%BC%9A%E5%A4%AA%E5%AE%97%E4%B9%9F%E9%9D%9E%E5%B8%B8%E5%B4%87%E5%B0%9A%E6%AD%A6%E5%8A%9F%EF%BC%8C%E4%BB%96%E8%87%AA%E5%B7%B1%E4%B9%9F%E6%98%AF%E4%B8%80%E5%90%8D%E5%87%BA%E8%89%B2%E7%9A%84%E5%B0%86%E9%A2%86%EF%BC%8C%E5%A4%9A%E6%AC%A1%E4%BA%B2%E5%BE%81%E6%88%98%E5%9C%BA%EF%BC%8C%E6%89%A9%E5%A4%A7%E4%BA%86%E5%94%90%E6%9C%9D%E7%9A%84%E7%96%86%E5%9F%9F%E3%80%82%205.%E9%87%8D%E8%A7%86%E5%A4%96%E4%BA%A4%EF%BC%9A%E5%A4%AA%E5%AE%97%E9%87%8D%E8%A7%86%E5%A4%96%E4%BA%A4%EF%BC%8C%E9%80%9A%E8%BF%87%E5%A4%96%E4%BA%A4%E6%89%8B%E6%AE%B5%E5%B7%A9%E5%9B%BA%E4%BA%86%E5%94%90%E6%9C%9D%E7%9A%84%E5%9B%BD%E9%99%85%E5%9C%B0%E4%BD%8D%EF%BC%8C%E4%B8%8E%E9%82%BB%E5%9B%BD%E5%BB%BA%E7%AB%8B%E4%BA%86%E5%8F%8B%E5%A5%BD%E5%85%B3%E7%B3%BB%EF%BC%8C%E8%BE%BE%E6%88%90%E4%BA%86%E4%B8%80%E7%B3%BB%E5%88%97%E9%87%8D%E8%A6%81%E6%9D%A1%E7%BA%A6%E3%80%82%20%E9%80%9A%E8%BF%87%E4%BB%A5%E4%B8%8A%E5%A4%9A%E7%BB%B4%E5%BA%A6%E5%88%86%E6%9E%90%E5%92%8C%E8%81%94%E6%83%B3%EF%BC%8C%E6%88%91%E4%BB%AC%E5%8F%AF%E4%BB%A5%E5%BF%AB%E9%80%9F%E5%9C%B0%E8%AE%B0%E4%BD%8F%E5%A4%AA%E5%AE%97%E7%9A%87%E5%B8%9D%E7%9A%84%E7%89%B9%E7%82%B9%EF%BC%8C%E4%BB%96%E6%98%AF%E4%B8%80%E4%B8%AA%E5%BC%80%E5%88%9B%E7%9B%9B%E4%B8%96%E3%80%81%E5%B0%8A%E5%84%92%E9%87%8D%E6%96%87%E3%80%81%E7%88%B1%E6%B0%91%E6%83%9C%E6%B0%91%E3%80%81%E5%B4%87%E5%B0%9A%E6%AD%A6%E5%8A%9F%E5%92%8C%E9%87%8D%E8%A7%86%E5%A4%96%E4%BA%A4%E7%9A%84%E4%BC%9F%E5%A4%A7%E7%9A%87%E5%B8%9D%EF%BC%8C%E5%AF%B9%E4%B8%AD%E5%9B%BD%E5%8E%86%E5%8F%B2%E5%92%8C%E6%96%87%E5%8C%96%E4%BA%A7%E7%94%9F%E4%BA%86%E6%B7%B1%E8%BF%9C%E5%BD%B1%E5%93%8D%E3%80%82&tok=" + getAccessToken() + "&cuid=C02XHQSCJG5J&ctp=1&lan=zh&spd=3&pit=5&vol=5&per=103&aue=3");

        Request request = new Request.Builder()
            .url("https://tsn.baidu.com/text2audio")
            .method("POST", body)
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("Accept", "*/*")
            .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
//        ResponseBody body1 = response.body();
       String data = response.body().toString();
        System.out.println("------------------->");
        System.out.println(data);
//        byte[] data = response.body().bytes();
            try {
//                Util.writeBytesToFileSystem(response.body().string().getBytes(StandardCharsets.UTF_8), "abc.mp3");
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    public static void main(String []args) throws IOException{
        Sample sample = new Sample();
        sample.createMp3(args);
        sample.queryMp3(args);
    }

        public void createMp3(String []args) throws IOException{
            //        参数	类型	描述	是否必须
//            Body中按JSON格式放置请求参数，参数如下：
//            参数名	类型	是否必需	描述	取值范围
//            text	list	是	待合成的文本，需要为UTF-8编码；输入多段文本时，文本间会插入1s长度的空白间隔	总字数不超过10万个字符，1个中文字、英文字母、数字或符号均算作1个字符
//            format	string	否	音频格式	"mp3-16k"，"mp3-48k"，"wav"，"pcm-8k"，"pcm-16k"，默认为mp3-16k
//            voice	int	否 度小萌=111 	音库	基础音库：度小宇=1，度小美=0，度逍遥（基础）=3，度丫丫=4；
//            精品音库：度逍遥（精品）=5003，度小鹿=5118，度博文=106，度小童=110，度小萌=111，度米朵=103，度小娇=5。默认为度小美
            //女声:度小萌=111(小) ,度小美=0(大)
            //男声: 度小宇=1(小),度逍遥=3(大)

//            lang	string	是	语言	固定值zh。语言选择,目前只有中英文混合模式，填写固定值zh
//            speed	int	否	语速	取值0-15，默认为5中语速
//            pitch	int	否	音调	取值0-15，默认为5中语调
//            volume	int	否	音量	取值0-15，默认为5中音量（取值为0时为音量最小值，并非为无声）
//            enable_subtitle	string/int	否	是否开启字幕	取值范围0, 1, 2，默认为0。0表示不开启字幕，1表示开启句级别字幕，2表示开启词级别字幕
// 设置可选参数
            HashMap<String, Object> options = new HashMap<String, Object>();

            String[] contents = content.split("，｜。");

            options.put("format", "mp3-48k");
            options.put("voice", 5003);
            options.put("lang", "zh");
            options.put("speed", 3);
            options.put("pitch", 5);
            options.put("volume", 5);
            options.put("enable_subtitle", 0);
            options.put("text", Arrays.asList(contents));
            String requestContent = JSON.toJSONString(options);
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType,requestContent );
                Request request = new Request.Builder()
                        .url("https://aip.baidubce.com/rpc/2.0/tts/v1/create?access_token=" + getAccessToken())
                        .method("POST", body)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Accept", "application/json")
                        .build();
                Response response = HTTP_CLIENT.newCall(request).execute();
                System.out.println(response.body().string());


        }




    public  void queryMp3(String []args) throws IOException{
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"task_ids\":[\"6448d183d4a5ed0001cec1c0\"]}");
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rpc/2.0/tts/v1/query?access_token=" + getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        System.out.println(response.body().string());

    }

    /**
     * 从用户的AK，SK生成鉴权签名（Access Token）
     *
     * @return 鉴权签名（Access Token）
     * @throws IOException IO异常
     */
    static String getAccessToken() throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=" + API_KEY
                + "&client_secret=" + SECRET_KEY);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        return new JSONObject(response.body().string()).getString("access_token");
    }
    
}
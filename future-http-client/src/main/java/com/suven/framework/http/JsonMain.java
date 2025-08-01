package com.suven.framework.http;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonMain {

    public static void main3(String[] args) throws Exception {
        String url = "http://xjh.haitou.cc/sx";
        Document document = Jsoup.connect(url).get();//连接发送get请求，并获取document对象
        Element tboady = document.select("tbody").first();//获得body标签
        Elements trs = tboady.select("tr");
        for(Element tr : trs){//解析各种标签
            Element title = tr.select("td[class$=cxxt-title]").first();
            Element companyName = title.select("div").first();
            String companyText = companyName.text();
            Element schoolName = title.select("span").first();
            String schoolText = schoolName.text();
            Element timeAll = tr.select("td[class$=text-left cxxt-holdtime]").first();
            Element dateTime = timeAll.select("span[class$=hold-ymd]").first();
            String dateText = dateTime.text();
            Element where = tr.select("td[class$=text-ellipsis]").first();
            Element whereName = where.select("span").first();
            String whereText = whereName.text();
            System.out.println("公司名称:"+companyText+" 地点:"+schoolText+" "+whereText+" 时间:"+dateText);
        }

    }
        public static void main(String[] args) {
            String url = "https://www.qcc.com/api/search/searchMulti";

            String jsonData = "{\"searchKey\":\"阳江\",\"pageIndex\":1,\"pageSize\":20,\"filter\":\"{\\\"d\\\":[{\\\"start\\\":\\\"20250427\\\",\\\"end\\\":\\\"\\\",\\\"value\\\":\\\"0-0.25\\\"},{\\\"start\\\":\\\"20250127\\\",\\\"end\\\":\\\"\\\",\\\"value\\\":\\\"0-0.5\\\"},{\\\"start\\\":\\\"20240727\\\",\\\"end\\\":\\\"\\\",\\\"value\\\":\\\"0-1\\\"},{\\\"start\\\":\\\"20220727\\\",\\\"end\\\":\\\"20240727\\\",\\\"value\\\":\\\"1-3\\\"}],\\\"ot\\\":[\\\"001001001\\\",\\\"001001002\\\"]}\"}";
            Map<String, String> cookies = new HashMap<>();
            //  -b 'qcc_did=303d01a5-7393-489f-9114-666383b83cf5; i18next=zh-CN; UM_distinctid=1983ab98f7c1ca-0375d39225e7598-17525636-1fa400-1983ab98f7d835; _c_WBKFRo=TsPY39HLmahZ5l85ferqWy67IDX3ZjQJgvZax5Qr; _nb_ioWEgULi=; QCCSESSID=dae02f4ef542d49be801025556; acw_tc=1a0c599817535409947384928e00738af541c92997739afaac78088958a706; CNZZDATA1254842228=651839021-1753331896-https%253A%252F%252Fwww.baidu.com%252F%7C1753542653' \


            cookies.put("qcc_did", "303d01a5-7393-489f-9114-666383b83cf5");
            cookies.put("i18next", "zh-CN");
            cookies.put("UM_distinctid", "1983ab98f7c1ca-0375d39225e7598-17525636-1fa400-1983ab98f7d835");
            cookies.put("_c_WBKFRo", "TsPY39HLmahZ5l85ferqWy67IDX3ZjQJgvZax5Qr");
            cookies.put("_nb_ioWEgULi", "");
            cookies.put("QCCSESSID", "dae02f4ef542d49be801025556");
            cookies.put("acw_tc", "1a0c599817535409947384928e00738af541c92997739afaac78088958a706");
            cookies.put("CNZZDATA1254842228", "651839021-1753331896-https%253A%252F%252Fwww.baidu.com%252F%7C1753541144");
            cookies.put("de49c69699b931c5b9b0", "be334fbfb63ae9404ded9574e36278a0b8e6e3ed72d2b4b3b04c7319a4fceb36b7ee638179399d551ad8f5ac7c04f9a22dc4beffcbd93c5171fec55947583dc5");

            String cookie = "qcc_did=303d01a5-7393-489f-9114-666383b83cf5; i18next=zh-CN; UM_distinctid=1983ab98f7c1ca-0375d39225e7598-17525636-1fa400-1983ab98f7d835; _c_WBKFRo=TsPY39HLmahZ5l85ferqWy67IDX3ZjQJgvZax5Qr; _nb_ioWEgULi=; QCCSESSID=dae02f4ef542d49be801025556; acw_tc=1a0c599817535409947384928e00738af541c92997739afaac78088958a706; CNZZDATA1254842228=651839021-1753331896-https%253A%252F%252Fwww.baidu.com%252F%7C1753541144";
            Map<String, String> headers = new HashMap<>();
            headers.put("accept", "application/json, text/plain, */*");
            headers.put("content-type", "application/json");
            headers.put("accept-encoding", "gzip, deflate, br, zstd");
            headers.put("de49c69699b931c5b9b0", "be334fbfb63ae9404ded9574e36278a0b8e6e3ed72d2b4b3b04c7319a4fceb36b7ee638179399d551ad8f5ac7c04f9a22dc4beffcbd93c5171fec55947583dc5");
            headers.put("accept-language", "zh-CN,zh;q=0.9");
            headers.put("origin", "https://www.qcc.com");
            headers.put("priority", "u=1, i");
            headers.put("referer", "https://www.qcc.com/web/search?key=%E9%98%B3%E6%B1%9F");
            headers.put("sec-ch-ua", "Not)A;Brand\";v=\"8\", \"Chromium\";v=\"138\", \"Google Chrome\";v=\"138\"");
            headers.put("ec-ch-ua-platform", "macOS");
            headers.put("sec-fetch-dest", "empty");
            headers.put("sec-fetch-mode", "cors");
            headers.put("sec-fetch-site", "same-origin");
            headers.put("ser-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36");
            headers.put("x-pid", "721c20bacc7dba004f28a52c9db943b0");
            headers.put("x-requested-with", "XMLHttpRequest");
            try {
                Connection.Response response = Jsoup.connect(url)
                        .ignoreContentType(true) // 忽略内容类型，因为通常Jsoup期望HTML，但这里我们接收JSON
                        .headers(headers) // 设置请求头为JSON
                        .cookies(cookies)
                        .method(Connection.Method.POST) // 设置请求方法为POST
                        .requestBody(jsonData) // 设置请求体为JSON字符串
                        .execute();
                String jsonResponse = response.body();
                System.out.println(jsonResponse);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

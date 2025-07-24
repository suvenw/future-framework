//package top.suven.base.test.rule.util;
//
//import JsonParse;
//import HttpClientTest;
//import Constants;
//import top.suven.base.util.utilize.JsonUtils;
//import top.suven.base.util.utilize.StringUtil;
//import top.suven.base.util.utilize.TestUtils;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import org.apache.commons.io.IOUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.ByteArrayEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import javax.annotation.PostConstruct;
//import javax.servlet.Filter;
//import java.io.IOException;
//import java.io.InputStream;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.Comparator;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.TreeMap;
//import java.util.concurrent.ConcurrentHashMap;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
///**
// * Created by Alex on 2014/5/20
// */
////@Component
//public class MvcTestUtil {
//	private final static Comparator<String> comparator = new Comparator<String>() {
//		public int compare(String s1, String s2) {
//			return s1.compareTo(s2);
//		}
//	};
//    @Autowired
//    private WebApplicationContext wac;
//
//    private MockMvc mockMvc;
//
//    private static Map<String, Boolean> needProEnvTestUrl = new ConcurrentHashMap<>();
//
//    private static String proUrl;
//
//    private static boolean needProTest;
//
//    private static final  String  UTF_8 = "UTF-8";
//
//
//    @PostConstruct
//    public void init() {
//    	DefaultMockMvcBuilder webApp= MockMvcBuilders.webAppContextSetup(wac);
//    	//自动添加filter到测试spring容器
//    	Map<String, Filter> beansOfType = wac.getBeansOfType(Filter.class);
//    	for (Entry<String, Filter> entry :beansOfType.entrySet()){
//    		webApp.addFilter(entry.getValue());
//		}
//        mockMvc = webApp.build();
//
//    }
//
//    public void initUrlRemote() {
////        String remote = System.getenv("REMOTE");
////        System.out.println("Prod env test is "+(remote!=null));
////        needProTest = StringUtils.equalsIgnoreCase(remote, "true");
////        for(Field f : URLCommSand.class.getDeclaredFields()){
////
////        	if(f.isAnnotationPresent(UrlRemote.class)){
////        		try {
////					needProEnvTestUrl.put(f.get(URLCommand.class).toString(), f.getAnnotation(UrlRemote.class).isCdn());
////				} catch (IllegalArgumentException | IllegalAccessException e) {
////					System.out.println("UrlRemote initial error.");
////				}
////        		proUrl = f.getAnnotation(UrlRemote.class).value();
////        	}
////        }
//    }
//
//
//
//
//
//	private String get2Mock(String url, Map param)  {
//		String sMessage = "";
//		try {
//			MockHttpServletRequestBuilder paramBuilder = MockMvcRequestBuilders.get(url);
//			for (Iterator<Entry<String, Object>> iterator = param.entrySet().iterator(); iterator.hasNext();) {
//				Entry<String, Object> obj = iterator.next();
//				paramBuilder.param(obj.getKey(), String.valueOf(obj.getValue()));
//			}
//			MockHttpServletResponse response = null;
//			response = mockMvc.perform(paramBuilder).andExpect(status().isOk()).andReturn().getResponse();
//			sMessage = response.getContentAsString();
//			return sMessage;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//    private String post2Remote(String url, byte[] httpBody)  {
//        try {
//            CloseableHttpClient httpclient = HttpClients.createDefault();
//            HttpPost httpPost = new HttpPost(url);
//            httpPost.setEntity(new ByteArrayEntity(httpBody));
//            CloseableHttpResponse response = httpclient.execute(httpPost);
//            if (response.getStatusLine().getStatusCode() != 200) {
//                throw new RuntimeException("请求" + url + " 失败: reason:" + response.getStatusLine().getStatusCode());
//            }
//            InputStream in = response.getEntity().getContent();
//            String responseMess= IOUtils.toString(in, UTF_8);
//            return responseMess;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//
//    private byte[] post2Mock(String url, byte[] httpBody) {
//        byte[] result = null;
//        try {
//            MockHttpServletResponse response = null;
//
//            response = mockMvc.perform(MockMvcRequestBuilders.post(url).content(httpBody))
//                    .andExpect(status().isOk())
//                    .andReturn().getResponse();
//            result = response.getContentAsByteArray();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    private byte[] get2Mock(String url, String param) {
//    	 byte[] result = null;
//        try {
//            MockHttpServletResponse response = null;
//            System.out.println(url + "?data=" + param);
//            response = mockMvc.perform(MockMvcRequestBuilders.get(url + "?data=" + param))
//                    .andExpect(status().isOk())
//                    .andReturn().getResponse();
//			response.getContentAsByteArray();
//			result = response.getContentAsByteArray();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    public static String sendGet(String url, String param) {
//
//        try {
//            String urlNameString = url + "?data=" + param;
//            System.out.println(urlNameString);
//            URL realUrl = new URL(urlNameString);
//            // 打开和URL之间的连接
//            URLConnection connection = realUrl.openConnection();
//            // 设置通用的请求属性
//            connection.setRequestProperty("accept", "*/*");
//            connection.setRequestProperty("connection", "Keep-Alive");
//            connection.setRequestProperty("user-agent",
//                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//            // 建立实际的连接
//            connection.connect();
//            // 获取所有响应头字段
//            //Map<String, List<String>> map = connection.getHeaderFields();
//            // 遍历所有的响应头字段
//            /*for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }*/
//            String responseMess= IOUtils.toString(connection.getInputStream(),UTF_8);
//            return responseMess;
//        } catch (Exception e) {
//            System.out.println("发送GET请求出现异常！" + e);
//            e.printStackTrace();
//        }
//		return null;
//    }
//
//	public static String sendGet(String url, Map param) {
//		try {
//			String urlNameString = url +(url.contains("?")?(url.endsWith("&")?"":"&"):"?")+ SignParamTest.getSignParam(param);
//			URL realUrl = new URL(urlNameString);
//			// 打开和URL之间的连接
//			URLConnection connection = realUrl.openConnection();
//			// 设置通用的请求属性
//			connection.setRequestProperty("accept", "*/*");
//			connection.setRequestProperty("connection", "Keep-Alive");
//			connection.setRequestProperty("user-agent",
//					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//			// 建立实际的连接
//			connection.connect();
//			String responseMess= IOUtils.toString(connection.getInputStream(),UTF_8);
//			return responseMess;
////			return JSON.parseObject(responseMess,SMessage.class);
//		} catch (Exception e) {
//			System.out.println("发送GET请求出现异常！" + e);
//			e.printStackTrace();
//		}
//		return null;
//	}
//
////    private TokenData getTokenData() {
////		//playerid= 484165171
////		String userName = "kqone444";
////		String pwd = "112233";
////		TestContext.UserVO userAnno = TestContext.getUserAnno();
////		if (userAnno != null && userAnno.hasUser) {
////			userName = userAnno.userAnno.username();
////			pwd = userAnno.userAnno.pwd();
////		}
////		LoginToken loginToken = new LoginToken();
////		TokenData tokenData = loginToken.getToken(userName, pwd);
////		//TokenData tokenData = new TokenData();
////		//tokenData.setUserid(484165171);
////		//tokenData.setToken("29b81056e47568116c54752c4f919befa55003766e153a2102ea3041a550bf8f");
////		return tokenData;
////	}
//
//    private String convertString(String str) {
//		StringBuilder sb = new StringBuilder();
//		String[] arr = str.split("_");
//		for (int i = 0; i < arr.length; i++) {
//			String tmp = arr[i];
//			if (i != 0) {
//				tmp = tmp.substring(0, 1).toUpperCase() + tmp.substring(1);
//			}
//			sb.append(tmp);
//		}
//		return sb.toString();
//	}
//
//
//
//
//
//	 private String post2Mock(String url, String json) {
//	        String sMessage = null;
//	        try {
//	            MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post(url+"?" +json))
//	                    .andExpect(status().isOk())
//	                    .andReturn().getResponse();
//	            sMessage = response.getContentAsString();
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }
//	        return sMessage;
//	    }
//
//	/**
//	 * 将PB对象转换成MAP实现参数
//	 * @param body
//	 * @param parametersMap
//	 */
//	private void parsePOJO(Object body,TreeMap<String, Object> parametersMap) {
//		if (body == null) {
//			return;
//		}
//
//		String json = JsonUtils.toJson(body);
//		TreeMap map = JSON.parseObject(json, TreeMap.class);
//		parametersMap.putAll(map);
//	}
//
//	public String get(String url, Object body) {
//		int platForm = 1;
//		String sMessage;
//		initUrlRemote();
//
//        JSONObject gMessage = new JSONObject();
//        gMessage.put("version",9000);
//        gMessage.put("platform",platForm);
//		gMessage.put("sign",SignParamTest.getClientSign(gMessage,body));
//		TestContext.RemoteVO remoteVO = TestContext.getRemoteVO();
//		Map param = SignParamTest.object2MapParam(gMessage,body);
//		// get请求
//		if (needProTest) {
//			System.out.println("Get REMOTE:" + proUrl + url);
//			sMessage = sendGet(proUrl + url, param);
//		} else if (!needProTest && (TestUtils.isJenkinsJunit() || remoteVO.isRemote)) {
//			sMessage = sendGet(TestUtils.getRmtJenkins(remoteVO.remote==null?"":remoteVO.remote.value()) + url, param);
//		} else {
//			sMessage = get2Mock(url, param);
//		}
//		return sMessage;
//	}
//
//
//	/**
//	 * 通过get 发送POJO 对象转换成URL+参数方式实现;
//	 * @param url
//	 * @param body
//	 * @return
//	 * @throws Exception
//	 */
//	public String getPOJO(String url, JsonParse body) {
//		 int platForm =1;
//	        initUrlRemote();
//	        try {
//	            JSONObject gMessage = new JSONObject();
////	            RequestBaseMessage gMessage = new RequestBaseMessage();
////	            gMessage.setVersion(9000);
////	            gMessage.setPlatform(platForm);
//                gMessage.put("version",9000);
//                gMessage.put("platform",platForm);
//	            String jsonHead = JsonUtils.toJson(gMessage);
//	            Map map = JsonUtils.stringToTreeMap(jsonHead);
//	            if (null!=body) {
//		            Class<?> cla = body.getClass();
//					for (Field field : cla.getDeclaredFields()) {
//						String fieldName = field.getName();
//						String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
//						Method method = cla.getMethod(methodName);
//						String value;
//						Class<?>[] interfaces = field.getType().getInterfaces();
//						if (field.getType().isArray() || (interfaces.length>0 && interfaces[0].getName()=="java.util.Collection")) {
//							value = JSON.toJSONString(method.invoke(body)).replace("[", "").replace("]", "").replace(',', Constants.CHARKEY);
//						} else {
//							value = StringUtil.getString(method.invoke(body));
//						}
//						map.put(fieldName, value);
//					}
//	            }
//	            SignParamTest signParm = new SignParamTest();
//	            String json = SignParamTest.getSignParam(map);
////					json = URLEncoder.encode(json, "UTF-8");
//	            String data =  HttpClientTest.get(url, json);
//	            return data;
//		            //get请求
//	        } catch (Exception e) {
//				e.printStackTrace();
//			}
//	        return null;
//	}
//
//
//
//
//	/**
//	 * 通过get方面发URL+参加的请求发送实现;
//	 * @param url
//	 * @param json
//	 * @return
//	 */
//	public String getHttpJson(String url,String json){
//		TestContext.RemoteVO remoteVO = TestContext.getRemoteVO();
//		String responseData = "";
//		if (remoteVO != null && (TestUtils.isJenkinsJunit() || remoteVO.isRemote)) {
//			url = TestUtils.getRmtJenkins(remoteVO.remote==null?"":remoteVO.remote.value()) + url + "?" + json;
//			try {
//				CloseableHttpClient httpclient = HttpClients.createDefault();
//				HttpGet httpGet = new HttpGet(url);
//				CloseableHttpResponse response = httpclient.execute(httpGet);
//				if (response.getStatusLine().getStatusCode() != 200) {
//					throw new RuntimeException("请求" + url + " 失败:" + response.getStatusLine().getStatusCode());
//				}
//				responseData = EntityUtils.toString(response.getEntity(), "utf-8").trim();
//			} catch (IOException e) {
//				e.printStackTrace();
//				System.exit(0);
//			}
//		} else {
//			try {
//				url = url + "?" + json;
//				MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get(url)).andExpect(status().isOk()).andReturn().getResponse();
//				responseData = response.getContentAsString();
//				System.out.println("responseData = ==================" + responseData);
//			} catch (Exception e) {
//				e.printStackTrace();
//				System.exit(0);
//			}
//		}
//		if (responseData.isEmpty()) {
//			System.err.println("未能获取数据");
//			return null;
//		}
//		return responseData;
//	}
//
//	public <T> T getHttpJson(String url,String json, Class<T> clazz){
//		TestContext.RemoteVO remoteVO = TestContext.getRemoteVO();
//		String responseData = "";
//		if (remoteVO != null && (TestUtils.isJenkinsJunit() || remoteVO.isRemote)) {
//			url = TestUtils.getRmtJenkins(remoteVO.remote==null?"":remoteVO.remote.value()) + url + "?" + json;
//			try {
//				CloseableHttpClient httpclient = HttpClients.createDefault();
//				HttpGet httpGet = new HttpGet(url);
//				CloseableHttpResponse response = httpclient.execute(httpGet);
//				if (response.getStatusLine().getStatusCode() != 200) {
//					throw new RuntimeException("请求" + url + " 失败:" + response.getStatusLine().getStatusCode());
//				}
//				responseData = EntityUtils.toString(response.getEntity(), "utf-8").trim();
//			} catch (IOException e) {
//				e.printStackTrace();
//				System.exit(0);
//			}
//		} else {
//			try {
//				url = url + "?" + json;
//				MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get(url)).andExpect(status().isOk()).andReturn().getResponse();
//				responseData = response.getContentAsString();
//				System.out.println("responseData = ==================" + responseData);
//			} catch (Exception e) {
//				e.printStackTrace();
//				System.exit(0);
//			}
//		}
//		if (responseData.isEmpty()) {
//			System.err.println("未能获取数据");
//			return null;
//		}
//
//		String data = "";
//		try {
//			JSONObject myJsonObject =  JSONObject.parseObject(responseData);// new JSONObject(responseData);
//			data = myJsonObject.get("data").toString();
//
//			if (StringUtils.isBlank(data)) {
//				return clazz.newInstance();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return JsonUtils.parseObject(data, clazz);
//	}
//}

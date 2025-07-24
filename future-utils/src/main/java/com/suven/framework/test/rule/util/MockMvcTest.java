//package top.suven.base.test.rule.util;
//
//import BaseConfigTest;
//import HttpClientTest;
//import Remote;
//import Constants;
//import top.suven.base.util.utilize.JsonUtils;
//import top.suven.base.util.utilize.StringUtil;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.annotation.AnnotationUtils;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.TreeMap;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
///**
// * Created with IntelliJ IDEA. User: 焦一平 Date: 14-9-25 Time: 下午6:45 To change
// * this template use File | Settings | File Templates.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//// @ContextConfiguration(classes = {WebMvcConfig.class, MockDataConfig.class})
////@ContextConfiguration(locations = { "classpath:/application.xml" })
//@Remote
//public class MockMvcTest {
//	@Autowired
//	private org.springframework.web.context.WebApplicationContext context;
//	public static MockMvc mockMvc;
//	public static HttpClientTest httpClientTest;
//	public Map map;
//	public boolean isPost;
//	public static String userToken = "597BD2FAFD7B7A431E59F892123B30EF";
//
//	@Before
//	public void before() {
//		// 可以对所有的controller来进行测试
//		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
//		// 仅仅对单个Controller来进行测试
//		// mockMvc = MockMvcBuilders.standaloneSetup(new
//		// MeunController()).build();
//	}
//
//	private void initParam() {
//		try {
//            String jsonHead = null;
//            JSONObject message = new JSONObject();
//            message.put("version",9000);
//            message.put("platform",1);
//            jsonHead = JsonUtils.toJson(message);
//			if (isPost) {
////				IUserToken tokenData = getTokenData();
//                message.put("sysVersion","android 5.1.x");
////                message.put("userId",tokenData.getUserId());
////                message.put("token",tokenData.getToken());
//                message.put("passportId",27);
//                message.put("accessToken",userToken);
//                message.put("times",System.currentTimeMillis());
//                message.put("appId",1005);
//                message.put("device","123456abcdefg");
//				jsonHead = JsonUtils.toJson(message);
//			}
//			map = JsonUtils.stringToTreeMap(jsonHead);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
////
////	private static IUserToken getTokenData() {
////		String userName = "kqone444";
////		String pwd = "112233";
////		TestContext.UserVO userAnno = TestContext.getUserAnno();
////		if (userAnno != null && userAnno.hasUser) {
////			userName = userAnno.userAnno.username();
////			pwd = userAnno.userAnno.pwd();
////		}
////		IUserToken userToken = null;
////		Map map = new HashMap();
////		map.put("userType", 1);
////		map.put("userCode", userName);
////		map.put("passWord", pwd);
////
////		return userToken;
////	}
//
//	private static String get2MockMvc(String url, String param) {
//		String result = null;
//		try {
//			MockHttpServletResponse response = null;
//			String requestUrl = url + "?" + param;
//			System.out.println("requestUrl ==>: " + requestUrl);
//			response = mockMvc.perform(MockMvcRequestBuilders.get(requestUrl))
//					.andExpect(status().isOk()).andReturn().getResponse();
//			response.getContentAsByteArray();
//			// result = response.getContentAsByteArray();
//			result = response.getContentAsString();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
//	}
//
//	private static String post2MockMvc(String url, Map param) {
//		String sMessage = "";
//		try {
//			MockHttpServletRequestBuilder paramBuilder = MockMvcRequestBuilders
//					.post(url);
//			for (Iterator<Entry<String, Object>> iterator = param.entrySet()
//					.iterator(); iterator.hasNext();) {
//				Entry<String, Object> obj = iterator.next();
//				paramBuilder
//						.param(obj.getKey(), String.valueOf(obj.getValue()));
//			}
//			MockHttpServletResponse response = null;
//			response = mockMvc.perform(paramBuilder).andExpect(status().isOk())
//					.andReturn().getResponse();
//			sMessage = response.getContentAsString();
//			return sMessage;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	private static String post2MockMvc(String url, byte[] httpBody) {
//		String result = null;
//		try {
//			MockHttpServletResponse response = null;
//			response = mockMvc
//					.perform(MockMvcRequestBuilders.post(url).content(httpBody))
//					.andExpect(status().isOk()).andReturn().getResponse();
//			result = response.getContentAsString();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
//	}
//
//	/**
//	 * 通过post 发送POJO 对象转换成URL+参数方式实现;
//	 *
//	 * @param url
//	 * @param body
//	 * @return
//	 * @throws Exception
//	 */
//	public String post(String url, Object body) {
//		try {
//			isPost = true;
//			initParam();
//			Map bodyMap = this.obj2map(body);
//			map.putAll(bodyMap);
//			String sign = SignParamTest.getClientSing(map);
//			map.put("sign", sign);
//			String data = null;
//			TestContext.RemoteVO remoteVO = TestContext.getRemoteVO();
//			// post请求
//			remoteVO.isRemote = true;
//			if ( remoteVO.isRemote) {
//				if(remoteVO.remote == null){
//					Remote head = AnnotationUtils.findAnnotation(this.getClass(),Remote.class);
//					remoteVO.remote = head;
//				}
//				BaseConfigTest.requestURLBase = remoteVO.remote.value();
//				data =  HttpClientTest.post(url, map);
//			} else {
//				data = post2MockMvc(url, map);
//			}
//			return data;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//
//	/**
//	 * 通过get 发送POJO 对象转换成URL+参数方式实现;
//	 *
//	 * @param url
//	 * @param body
//	 * @return
//	 * @throws Exception
//	 */
//	public String get(String url, Object body) {
//		try {
//			initParam();
//			Map bodyMap = this.obj2map(body);
//			map.putAll(bodyMap);
//			String sign = SignParamTest.getClientSing(map);
//			map.put("sign", sign);
//			String json = SignParamTest.getSignParam(map);
//			// json = URLEncoder.encode(json, "UTF-8");
//			String data = null;
//			TestContext.RemoteVO remoteVO = TestContext.getRemoteVO();
//			// post请求
//			remoteVO.isRemote = true;
//			if ( remoteVO.isRemote) {
//				if(remoteVO.remote == null){
//					Remote head = AnnotationUtils.findAnnotation(this.getClass(),Remote.class);
//					remoteVO.remote = head;
//				}
//				BaseConfigTest.requestURLBase = remoteVO.remote.value();
//				data =  HttpClientTest.get(url, json);
//			} else {
//				data = get2MockMvc(url, json);
//			}
//			return data;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//
//
//	private Map obj2map(Object body){
//		Map map = new TreeMap();
//		if (null != body) {
//			try {
//				Class<?> cla = body.getClass();
//				for (Field field : cla.getDeclaredFields()) {
//					String fieldName = field.getName();
//					String methodName = "get"+ fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
//					Method method = cla.getMethod(methodName);
//					String value;
//					Class<?>[] interfaces = field.getType().getInterfaces();
//					if (field.getType().isArray()
//							|| (interfaces.length > 0 && interfaces[0].getName() == "java.util.Collection")) {
//						value = JSON.toJSONString(method.invoke(body)).replace("[", "").replace("]", "").replace(',', Constants.CHARKEY);
//					} else {
//						value = StringUtil.getString(method.invoke(body));
//					}
//					if(null == value || "" .equals(value)){
//						continue;
//					}
//					map.put(fieldName, value);
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return map;
//	}
//
//	@Test
//	public void testGetMenu() {
//		try {
//			System.out.println("----------------------------");
//			ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
//					.get("/json/cdn/user/user_login_get"));
//			System.out.println(status());//
//			System.out.println(content().toString());
//			actions.andExpect(status().isOk());
//			// actions.andExpect(content().contentType("text/html"));
//			System.out.println("----------------------------");
//		} catch (Exception e) {
//			e.printStackTrace(); // To change body of catch statement use File |
//									// Settings | File Templates.
//		}
//	}
//}

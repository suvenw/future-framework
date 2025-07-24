package com.suven.framework.test.junit;

import com.suven.framework.test.rule.util.TestContext;
import org.junit.runners.model.InitializationError;

/**
 * Created by joven on 16/9/30.
 */

public class BaseConfigTest  {

	/**
	 * @param klass
	 * @throws InitializationError
	 */
//	public BaseConfigTest(Class<?> klass) throws InitializationError {
////		super(klass);
//		ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
//		// TODO Auto-generated constructor stub
//	}

	static String sshKeystorePass;
	static  String sshKeyStorePath;
	public static String requestURLBase = "http://106.14.83.31:9020/";// "http://127.0.0.1:9010";//"http://106.14.83.31:9010/";
//	public static String requestURLBase = "http://127.0.0.1:9010";//"http://106.14.83.31:9010/";
	static  String requestURL =  "https://localhost:8443/myDemo/Ajax/serivceJ.action";
	static  String requestURLParam = "";
	static String token ="";
	static long  userId = 0;
	static {
		userId = 100003;
		userId = 1925328;
		
		token ="5AA46CDA88F6FAB390E7E8258C273CF5";
//		userId = 32;
//		token ="A5458572CD81D4DEBD60C72E2042951A";
//		userId = 33;
//		token ="C90A38C380D48BBD3EEAEDD5D9278C10";
		
		setServerUrl(null);
		
		
//		requestURLBase = "http://127.0.0.1:9010";
	}
	
	public static void setUser(int userInfoId,String userToken){
		TestContext.RemoteVO remoteVo = TestContext.getRemoteVO();
		userId = userInfoId;
		token = userToken;
	}
	public static void setUrl(){
		requestURLBase = "http://127.0.0.1:9010";
	}
//	@Rule
	public static void setServerUrl(String url){
//		Remote head = AnnotationUtils.findAnnotation(BaseConfigTest.class,Remote.class);
//		System.out.println(head);
		TestContext.RemoteVO remoteVo = TestContext.getRemoteVO();
//		requestURLBase = remoteVo.remote.value();
		if(null == url){
			return ;
		}
		if(url.indexOf("http") < 0){
			requestURLBase = "http://" +url;
		}else{
			requestURLBase = url;
		}
		
		
	}
	
//	public List<MethodRule> rules(Object o) {
//        List<MethodRule> rules = super.rules(o);
//        try {
//            InputStream in = new ClassPathResource("rules.yml").getInputStream();
//            List<String> ruleList = (List<String>) new Yaml().load(in);
//            Collections.reverse(ruleList);
//            for (String rule : ruleList) {
//                rules.add(0, (MethodRule) Class.forName(rule).newInstance());
//            }
//        } catch (Exception ignored) { }
//        rules.add(0, new MockitoInit());
//        return rules;
//    }
	
}

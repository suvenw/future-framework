//package com.suven.framework.http.shiro;
//
///**
// * Description: shiro配置
// * @author: xuegao
// **/
//
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//import javax.servlet.Filter;
//
//import com.suven.framework.common.constants.GlobalConfigConstants;
//import com.suven.framework.http.processor.url.UrlExplain;
//import org.apache.shiro.mgt.SecurityManager;
//import org.apache.shiro.session.mgt.SessionManager;
//import org.apache.shiro.spring.LifecycleBeanPostProcessor;
//import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
//import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//@Configuration
//@ConditionalOnProperty(name = GlobalConfigConstants.TOP_SHIRO_OAUTH_CONFIG_ENABLED,  matchIfMissing = true)
////@ConfigurationProperties(value = GlobalConfigConstants.TOP_SHIRO_OAUTH_CONFIG)
//public class ShiroConfiguration {
//	@Bean("sessionManager")
//	public SessionManager sessionManager() {
//		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//		sessionManager.setSessionValidationSchedulerEnabled(true);
//		sessionManager.setSessionIdCookieEnabled(true);
//		return sessionManager;
//	}
//
//	@Bean("securityManager")
//	public SecurityManager securityManager(OAuth2Realm auth2Realm, SessionManager sessionManager) {
//		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//		securityManager.setRealm(auth2Realm);
//		securityManager.setSessionManager(sessionManager);
//
//		return securityManager;
//	}
//
//	@Bean("shiroFilter")
//	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
//		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
//		shiroFilter.setSecurityManager(securityManager);
//
//		//oauth过滤
//		Map<String, Filter> filters = new HashMap<>();
//		filters.put("oauth2", new OAuth2Filter());
//		shiroFilter.setFilters(filters);
//
//		Map<String, String> filterMap = new LinkedHashMap<>();
//		filterMap.put("/sys/user/login", "anon");
//		filterMap.put("/sys/randomImage", "anon");
//		filterMap.put("/sys/user/logout", "anon");
//		filterMap.put("/jeecg-boot/sys/getCheckCode", "anon");
//		filterMap.put("/sys/getCheckCode", "anon");
//		filterMap.put("/docs.html", "anon");
//		filterMap.put("/v2/api-docs", "anon");
//		filterMap.put("/webjars/**", "anon");
//		filterMap.put("/**", "oauth2");
//
//		shiroFilter.setFilterChainDefinitionMap(filterMap);
//
//		return shiroFilter;
//	}
//
//	@Bean("lifecycleBeanPostProcessor")
//	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
//		return new LifecycleBeanPostProcessor();
//	}
//
//	@Bean
//	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
//		DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
//		proxyCreator.setProxyTargetClass(true);
//		return proxyCreator;
//	}
//
//	@Bean
//	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
//		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
//		advisor.setSecurityManager(securityManager);
//		return advisor;
//	}
//}

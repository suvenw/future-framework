package com.suven.framework.http.shiro;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.shiro.authc.AuthenticationToken;
/**
 * @program: st-software-service
 * Description: token
 * @author: xuegao
 * @create: 2019-08-10 15:11
 **/
public class OAuth2Token implements AuthenticationToken {


	private String token;

	public OAuth2Token(String token) {
		this.token = token;
	}

	@Override
	public String getPrincipal() {
		return token;
	}

	@Override
	public Object getCredentials() {
		return token;
	}


	/**
	 * 收集用户的原来token,并校验通过后,返回更新后的token,否则返回空;
	 * @param
	 * @return
	 */
	public static String refreshToken() throws OAuthSystemException {
		OAuthIssuer oauthIssuer = new OAuthIssuerImpl(new MD5Generator());
		final String accessToken = oauthIssuer.accessToken();

		return accessToken;

	}


}

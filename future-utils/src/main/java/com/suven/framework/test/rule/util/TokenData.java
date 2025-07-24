package com.suven.framework.test.rule.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true) 
public class TokenData {
	private long userid;
	private String username;
	private String nickname;
	private int sex;
	private String pic;
	private int score;
	private int vip_type;
	private String vip_begin_time;
	private String vip_end_time;
	private String reg_tme;
	private String token;
	private String servertime;

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getVip_type() {
		return vip_type;
	}

	public void setVip_type(int vip_type) {
		this.vip_type = vip_type;
	}

	public String getVip_begin_time() {
		return vip_begin_time;
	}

	public void setVip_begin_time(String vip_begin_time) {
		this.vip_begin_time = vip_begin_time;
	}

	public String getVip_end_time() {
		return vip_end_time;
	}

	public void setVip_end_time(String vip_end_time) {
		this.vip_end_time = vip_end_time;
	}

	public String getReg_tme() {
		return reg_tme;
	}

	public void setReg_tme(String reg_tme) {
		this.reg_tme = reg_tme;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getServertime() {
		return servertime;
	}

	public void setServertime(String servertime) {
		this.servertime = servertime;
	}
}

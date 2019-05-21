package com.example.tvsapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/** A model Class to send login id and password inside the API request*/
public class LoginObject {

	@SerializedName("username")
	@Expose
	String loginId;

	@SerializedName("password")
	@Expose
	String password;

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

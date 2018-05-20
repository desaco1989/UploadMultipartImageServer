package com.zs.action.user.model;

import java.io.Serializable;

public class RequestInfo1 implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String email;
	
	private String password;
	
	private String username;

	
	
	public RequestInfo1() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "RequestInfo1 [email=" + email + ", password=" + password
				+ ", username=" + username + "]";
	}
	
	
}

package com.zs.action.frend.model;

import java.io.Serializable;

public class RequestFriend1 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
    private String friendEmailOrUserName;//好友的邮箱或者好友的用户ID
    
    private String userID;

    private String pushContent;
    
    
	public String getPushContent() {
		return pushContent;
	}

	public void setPushContent(String pushContent) {
		this.pushContent = pushContent;
	}

	public String getFriendEmailOrUserName() {
		return friendEmailOrUserName;
	}

	public void setFriendEmailOrUserName(String friendEmailOrUserName) {
		this.friendEmailOrUserName = friendEmailOrUserName;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	@Override
	public String toString() {
		return "RequestInfo1 [friendEmailOrUserName=" + friendEmailOrUserName
				+ ", userID=" + userID + "]";
	}
    
    
}

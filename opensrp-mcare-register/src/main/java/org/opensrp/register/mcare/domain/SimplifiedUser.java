package org.opensrp.register.mcare.domain;

public class SimplifiedUser {	
	
	private String user_name;
	private String id;

	public SimplifiedUser(String name, String id)
	{
		this.user_name = name;
		this.id = id;
	}
	public SimplifiedUser(){
		
	}
	public SimplifiedUser withUserName(String userName) {
		this.user_name = userName;
		return this;
	}
	
	public SimplifiedUser withId(String id){
		this.id = id;
		return this;
	}
	public String getUserName(){
		return this.user_name;
	}
	public String getId(){
		return this.id;
	}
}

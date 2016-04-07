package org.opensrp.register.mcare.domain;

public class SimplifiedRole {	
	
	private String name;
	private String id;

	public SimplifiedRole(String name, String id)
	{
		this.name = name;
		this.id = id;
	}
	public SimplifiedRole(){
		
	}
	public SimplifiedRole withName(String name) {
		this.name = name;
		return this;
	}
	
	public SimplifiedRole withId(String id){
		this.id = id;
		return this;
	}
	public String getName(){
		return this.name;
	}
	public String getId(){
		return this.id;
	}
}

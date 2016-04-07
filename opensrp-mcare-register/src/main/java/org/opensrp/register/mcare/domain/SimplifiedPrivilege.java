package org.opensrp.register.mcare.domain;

public class SimplifiedPrivilege {	
	
	private String name;
	private String id;

	public SimplifiedPrivilege(String name, String id)
	{
		this.name = name;
		this.id = id;
	}
	public SimplifiedPrivilege(){
		
	}
	public SimplifiedPrivilege withName(String name) {
		this.name = name;
		return this;
	}
	
	public SimplifiedPrivilege withId(String id){
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

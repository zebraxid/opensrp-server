package org.opensrp.register.mcare.domain;

public class SimplifiedLocation {	
	
	private String name;
	private String id;

	public SimplifiedLocation(String name, String id)
	{
		this.name = name;
		this.id = id;
	}
	public SimplifiedLocation(){
		
	}
	public SimplifiedLocation withName(String name) {
		this.name = name;
		return this;
	}
	
	public SimplifiedLocation withId(String id){
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

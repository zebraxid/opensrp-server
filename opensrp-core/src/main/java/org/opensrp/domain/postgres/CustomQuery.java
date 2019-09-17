package org.opensrp.domain.postgres;

public class CustomQuery {
	
	private int id;
	
	private String uuid;
	
	private String name;
	
	private String password;
	
	private String personUUID;
	
	private String userUUID;
	
	private String fullName;
	
	public String getPersonUUID() {
		return personUUID;
	}
	
	public void setPersonUUID(String personUUID) {
		this.personUUID = personUUID;
	}
	
	public String getUserUUID() {
		return userUUID;
	}
	
	public void setUserUUID(String userUUID) {
		this.userUUID = userUUID;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "CustomQuery [id=" + id + ", uuid=" + uuid + ", name=" + name + ", password=" + password + "]";
	}
	
}

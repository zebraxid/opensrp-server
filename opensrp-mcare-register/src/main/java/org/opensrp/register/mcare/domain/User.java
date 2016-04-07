package org.opensrp.register.mcare.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.model.MotechBaseDataObject;
import org.opensrp.dto.PrivilegeDTO;

@TypeDiscriminator("doc.type === 'User'")
public class User extends MotechBaseDataObject {
	
	@JsonProperty
	private String name;
	@JsonProperty
	private String status;
	@JsonProperty
	private String given_name;
	@JsonProperty
	private String middle_name;
	@JsonProperty
	private String family_name;
	@JsonProperty
	private String contact_number;
	@JsonProperty
	private String personal_address;
	@JsonProperty
	private String email;
	@JsonProperty
	private String password;
	@JsonProperty
	private String user_name;
	@JsonProperty
	private String gender;
	@JsonProperty
	private SimplifiedUser parent;
	@JsonProperty
	private List<SimplifiedUser> children;	
	@JsonProperty
	private List<SimplifiedRole> roles;
	@JsonProperty
	private List<SimplifiedLocation> locations;	
	@JsonProperty
	private Map<String, String> meta;
	@JsonProperty
	private String created_at;
	@JsonProperty
	private String updated_at;
	@JsonProperty
	private String created_by;
	@JsonProperty
	private String updated_by;
	
	public User(){
		this.created_at = new Date().toString();
		this.updated_at = new Date().toString();
		this.created_by = "Admin";
		this.updated_by = null;
	}
	public User withName(String roleName) {
		this.name = roleName;
		return this;
	}
	public User withStatus(String status) {
		this.status = status;
		return this;
	}
	public User withGivenName(String givenName) {
		this.given_name = givenName;
		return this;
	}
	public User withMiddleName(String middleName) {
		this.middle_name = middleName;
		return this;
	}
	public User withFamilyName(String familyName) {
		this.family_name = familyName;
		return this;
	}
	public User withContactNumber(String contactNumber) {
		this.contact_number = contactNumber;
		return this;
	}
	public User withPersonalAddress(String personalAddress) {
		this.personal_address = personalAddress;
		return this;
	}
	public User withPassword(String password) {
		this.password = password;
		return this;
	}
	public User withEmail(String email) {
		this.email = email;
		return this;
	}
	public User withGender(String gender) {
		this.gender = gender;
		return this;
	}
	public User withParent(SimplifiedUser parent) {
		this.parent = parent;
		return this;
	}
	public User withChildren(List<SimplifiedUser> children) {
		this.children = children;
		return this;
	}
	public User withRoles(List<SimplifiedRole> roles) {
		this.roles = roles;
		return this;
	}
	public User withUserName(String userName) {
		this.user_name = userName;
		return this;
	}
	public User withLocation(List<SimplifiedLocation> locaitons) {
		this.locations = locations;
		return this;
	}
	public String getName() {
		return name;
	}
	public String getStatus() {
		return status;
	}
	public String getGivenName() {
		return given_name;
	}
	public String getMiddleName() {
		return middle_name;
	}
	public String getFamilyName() {
		return family_name;
	}
	public String getContactNumber() {
		return contact_number;
	}
	public String getPersonalAddress() {
		return personal_address;
	}
	public String getPassword() {
		return password;
	}
	public String getEmail() {
		return email;
	}
	public String getGender() {
		return gender;
	}
	public SimplifiedUser getParent() {
		return parent;
	}
	public List<SimplifiedUser> getChildren() {
		return children;
	}
	public List<SimplifiedRole> getRoles() {
		return roles;
	}
	public String withUserName() {
		return user_name;
	}
	public List<SimplifiedLocation> getLocation(){
		return locations;
	}

	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o, "id", "revision");
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, "id", "revision");
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}

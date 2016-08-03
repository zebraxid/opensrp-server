package org.opensrp.register.mcare.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.model.MotechBaseDataObject;

@JsonIgnoreProperties(ignoreUnknown = true)
@TypeDiscriminator("doc.type === 'Child'")
public class Child extends MotechBaseDataObject {
	
	@JsonProperty
	private String caseId;
	
	@JsonProperty
	private String INSTANCEID;
	
	@JsonProperty
	private String PROVIDERID;
	
	@JsonProperty
	private String LOCATIONID;
	
	@JsonProperty
	private String TODAY;
	
	@JsonProperty
	private String START;
	
	@JsonProperty
	private String END;
	
	@JsonProperty
	private String child_nid;
	
	@JsonProperty
	private String name_english;
	
	@JsonProperty
	private String name_bengali;
	
	@JsonProperty
	private String father_nid;
	
	@JsonProperty
	private String father_name_english;
	
	@JsonProperty
	private String father_name_bengali;
	
	@JsonProperty
	private String father_dob;
	
	@JsonProperty
	private String mother_nid;
	
	@JsonProperty
	private String mother_name_english;
	
	@JsonProperty
	private String mother_name_bengali;
	
	@JsonProperty
	private String mother_dob;
	
	@JsonProperty
	private String child_dob;
	
	@JsonProperty
	private String place_of_birth;
	
	@JsonProperty
	private String nationality;
	
	@JsonProperty
	private String gender;
	
	@JsonProperty
	private String present_address;
	
	@JsonProperty
	private String permanent_address;
	
	@JsonProperty
	private List<Map<String, String>> multimediaAttachments;
	
	@JsonProperty
	private Map<String, String> details;
	
	@JsonProperty
	private String BAHMNI_ID;
	
	public Child() {
		this.multimediaAttachments = new ArrayList<>();
	}
	
	public Child withCASEID(String caseId) {
		this.caseId = caseId;
		return this;
	}
	
	public Child withBAHMNIID(String BAHMNI_ID) {
		this.BAHMNI_ID = BAHMNI_ID;
		return this;
	}
	
	public Child withINSTANCEID(String INSTANCEID) {
		this.INSTANCEID = INSTANCEID;
		return this;
	}
	
	public Child withPROVIDERID(String PROVIDERID) {
		this.PROVIDERID = PROVIDERID;
		return this;
	}
	
	public Child withLOCATIONID(String LOCATIONID) {
		this.LOCATIONID = LOCATIONID;
		return this;
	}
	
	public Child withTODAY(String TODAY) {
		this.TODAY = TODAY;
		return this;
	}
	
	public Child withSTART(String START) {
		this.START = START;
		return this;
	}
	
	public Child withEND(String END) {
		this.END = END;
		return this;
	}
	
	public Child withchild_nid(String child_nid) {
		this.child_nid = child_nid;
		return this;
	}
	
	public Child withname_english(String name_english) {
		this.name_english = name_english;
		return this;
	}
	
	public Child withname_bengali(String name_bengali) {
		this.name_bengali = name_bengali;
		return this;
	}
	
	public Child withchild_dob(String child_dob) {
		this.child_dob = child_dob;
		return this;
	}
	
	public Child withplace_of_birth(String place_of_birth) {
		this.place_of_birth = place_of_birth;
		return this;
	}
	
	public Child withfather_nid(String father_nid) {
		this.father_nid = father_nid;
		return this;
	}
	
	public Child withfather_name_english(String father_name_english) {
		this.father_name_english = father_name_english;
		return this;
	}
	
	public Child withfather_name_bengali(String father_name_bengali) {
		this.father_name_bengali = father_name_bengali;
		return this;
	}
	
	public Child withfather_dob(String father_dob) {
		this.father_dob = father_dob;
		return this;
	}
	
	public Child withmother_nid(String mother_nid) {
		this.mother_nid = mother_nid;
		return this;
	}
	
	public Child withmother_name_english(String mother_name_english) {
		this.mother_name_english = mother_name_english;
		return this;
	}
	
	public Child withmother_name_bengali(String mother_name_bengali) {
		this.mother_name_bengali = mother_name_bengali;
		return this;
	}
	
	public Child withmother_dob(String mother_dob) {
		this.mother_dob = mother_dob;
		return this;
	}
	
	public Child withnationality(String nationality) {
		this.nationality = nationality;
		return this;
	}
	
	public Child withgender(String gender) {
		this.gender = gender;
		return this;
	}
	
	public Child withpresent_address(String present_address) {
		this.present_address = present_address;
		return this;
	}
	
	public Child withmultimediaAttachments(List<Map<String, String>> multimediaAttachments) {
		this.multimediaAttachments = multimediaAttachments;
		return this;
	}
	
	public Child withDetails(Map<String, String> details) {
		this.details = new HashMap<>(details);
		return this;
	}
	
	public String caseId() {
		return caseId;
	}
	
	public String BAHMNIID() {
		return BAHMNI_ID;
	}
	
	public String INSTANCEID() {
		return INSTANCEID;
	}
	
	public String PROVIDERID() {
		return PROVIDERID;
	}
	
	public String LOCATIONID() {
		return LOCATIONID;
	}
	
	public String TODAY() {
		return TODAY;
	}
	
	public String START() {
		return START;
		
	}
	
	public String END() {
		return END;
	}
	
	public String child_nid() {
		return child_nid;
	}
	
	public String name_english() {
		return name_english;
	}
	
	public String name_bengali() {
		return name_bengali;
	}
	
	public String child_dob() {
		return child_dob;
	}
	
	public String place_of_birth() {
		return place_of_birth;
	}
	
	public String father_nid() {
		return father_nid;
	}
	
	public String father_name_english() {
		return father_name_english;
	}
	
	public String father_name_bengali() {
		return father_name_bengali;
	}
	
	public String father_dob() {
		return father_dob;
	}
	
	public String mother_nid() {
		return mother_nid;
	}
	
	public String mother_name_english() {
		return mother_name_english;
	}
	
	public String mother_name_bengali() {
		return mother_name_bengali;
	}
	
	public String mother_dob() {
		return mother_dob;
	}
	
	public String nationality() {
		return nationality;
	}
	
	public String gender() {
		return gender;
	}
	
	public String present_address() {
		return present_address;
	}
	
	public String permanent_address() {
		return permanent_address;
	}
	
	public List<Map<String, String>> multimediaAttachments() {
		if (multimediaAttachments == null) {
			multimediaAttachments = new ArrayList<>();
		}
		return multimediaAttachments;
	}
	
	private String getCaseId() {
		return caseId;
	}
	
	public Map<String, String> details() {
		if (details == null)
			this.details = new HashMap<>();
		return details;
	}
	
	public String getDetail(String name) {
		return details.get(name);
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

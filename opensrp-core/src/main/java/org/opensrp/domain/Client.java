package org.opensrp.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.joda.time.DateTime;
import org.opensrp.common.Gender;

@TypeDiscriminator("doc.type == 'Client'")
public class Client extends BaseEntity {
	@JsonProperty
	private String firstName;
	@JsonProperty
	private String middleName;
	@JsonProperty
	private String lastName;
	@JsonProperty
	private DateTime birthdate;
	@JsonProperty
	private DateTime deathdate;
	@JsonProperty
	private Boolean birthdateApprox;
	@JsonProperty
	private Boolean deathdateApprox;
	@JsonProperty
	private String gender;
	@JsonProperty
	private List<RelationShip> relationships;

	protected Client() {
		
	}

	public Client(String baseEntityId) {
		super(baseEntityId);
	}
	
	public Client(String baseEntityId, String firstName, String middleName, String lastName, DateTime birthdate, 
			DateTime deathdate, Boolean birthdateApprox, Boolean deathdateApprox, String gender) {
		super(baseEntityId);
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.deathdate = deathdate;
		this.birthdateApprox = birthdateApprox;
		this.deathdateApprox = deathdateApprox;
		this.gender = gender;
	}
	
	public Client(String baseEntityId, String firstName, String middleName, String lastName, DateTime birthdate, 
			DateTime deathdate, Boolean birthdateApprox, Boolean deathdateApprox, String gender, 
			String identifierType, String identifier) {
		super(baseEntityId);
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.deathdate = deathdate;
		this.birthdateApprox = birthdateApprox;
		this.deathdateApprox = deathdateApprox;
		this.gender = gender;
		addIdentifier(identifierType, identifier);
	}
	
	public Client(String baseEntityId, String firstName, String middleName, String lastName, DateTime birthdate, DateTime deathdate, 
			Boolean birthdateApprox, Boolean deathdateApprox, String gender, List<Address> addresses,
			Map<String, String> identifiers, Map<String, Object> attributes) {
		super(baseEntityId);
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.deathdate = deathdate;
		this.birthdateApprox = birthdateApprox;
		this.deathdateApprox = deathdateApprox;
		this.gender = gender;
		setIdentifiers(identifiers);
		setAddresses(addresses);
		setAttributes(attributes);
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String fullName() {
		String n = "";
		if(StringUtils.isNotBlank(firstName)){
			n += firstName;
		}
		if(StringUtils.isNotBlank(middleName)){
			n += " "+ middleName;
		}
		if(StringUtils.isNotBlank(lastName)){
			n += " " + lastName;
		}
		return n.trim();
	}
	
	public DateTime getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(DateTime birthdate) {
		this.birthdate = birthdate;
	}

	public DateTime getDeathdate() {
		return deathdate;
	}

	public void setDeathdate(DateTime deathdate) {
		this.deathdate = deathdate;
	}

	public Boolean getBirthdateApprox() {
		return birthdateApprox;
	}

	public void setBirthdateApprox(Boolean birthdateApprox) {
		this.birthdateApprox = birthdateApprox;
	}

	public Boolean getDeathdateApprox() {
		return deathdateApprox;
	}

	public void setDeathdateApprox(Boolean deathdateApprox) {
		this.deathdateApprox = deathdateApprox;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public  List<RelationShip> getRelationships() {
		if(relationships == null){
			relationships = new ArrayList<>();
		}
		return relationships;
	}

	public void setRelationShip(List<RelationShip> relationship) {
		this.relationships = relationship;
	}

	public void addRelationship(RelationShip relationship) {
		addRelationship(relationship.getRelationship(), relationship.getPerson_b());
	}

	public Client withFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public Client withMiddleName(String middleName) {
		this.middleName = middleName;
		return this;
	}

	public Client withLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public Client withName(String firstName, String middleName,
			String lastName) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		return this;
	}

	public Client withBirthdate(DateTime birthdate, Boolean isApproximate) {
		this.birthdate = birthdate;
		this.birthdateApprox = isApproximate;
		return this;
	}

	public Client withDeathdate(DateTime deathdate, Boolean isApproximate) {
		this.deathdate = deathdate;
		this.deathdateApprox = isApproximate;
		return this;
	}

	public Client withGender(String gender) {
		this.gender = gender;
		return this;
	}

	public Client withGender(Gender gender) {
		this.gender = gender.name();
		return this;
	}

	/**
	 * Overrides the existing data
	 */
	public Client withRelation(List<RelationShip> relationShips) {
		this.relationships = relationShips;
		return this;
	}
	public Client withRelation(RelationShip relationship) {
		if(relationships == null){
			relationships  = new ArrayList<>();
		}
		relationships.add(relationship);
		return this;
	}
		
	public RelationShip findRelative(String relationshipType, String relativeId) {
		if(relationships == null){
			relationships = new ArrayList<>();
		}

		for (RelationShip relationShip : relationships) {
			if(relationShip.getRelationship().equalsIgnoreCase(relationshipType)
					&& relationShip.getPerson_b().equalsIgnoreCase(relativeId)){
				return relationShip;
			}
		}
		return null;
	}
	
	public List<RelationShip> findRelations(String relativeId) {
		if(relationships == null){
			relationships = new ArrayList<>();
		}

		List<RelationShip> res = new ArrayList<>();
		for (RelationShip relationShip : relationships) {
			if(relationShip.getPerson_b().equalsIgnoreCase(relativeId)){
				res.add(relationShip);
			}
		}
		return res;
	}
	
	public List<RelationShip> findRelatives(String relationShip) {
		if(relationships == null){
			relationships = new ArrayList<>();
		}

		List<RelationShip> res = new ArrayList<>();
		for (RelationShip rel : relationships) {
			if(rel.getRelationship().equalsIgnoreCase(relationShip)){
				res.add(rel);
			}
		}
		return res;
	}
	
	public void addRelationship(String relationType, String relativeEntityId) {
		if(relationships == null){
			relationships = new ArrayList<>();
		}
		
		RelationShip relative = findRelative(relationType, relativeEntityId);
		if(relative == null){
			relationships.add(new RelationShip(relationType, relativeEntityId, null, null));
		}
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

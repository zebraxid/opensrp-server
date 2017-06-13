package org.opensrp.register.encounter.sync;

import java.lang.reflect.Field;
import java.util.Map;

import org.json.JSONObject;
import org.opensrp.form.domain.FormField;
import org.opensrp.form.repository.AllFormSubmissions;
import org.opensrp.register.encounter.sync.mapping.domain.EncounterSyncMapping;
import org.opensrp.register.mcare.domain.Members;
import org.opensrp.register.mcare.repository.AllMembers;

public class VaccineParamsBuilder {
	private static VaccineParamsBuilder paramsBuilder = new VaccineParamsBuilder();
	private String formDir;
	private String vaccineDate;
	private int vaccineDose;
	private Members member;
	private String vaccineName;
	private EncounterSyncMapping encounterSyncMapping;
	private AllFormSubmissions formSubmissions;
	private AllMembers allMembers;
	
	
	private FormField formField;
	private String fieldName;
	private String finalFieldName;
	private String retroFieldName;
	private String doseFieldName;	
	private Map<String, String> childVaccineFollowup;
	private boolean type;
	private Field field;
	private JSONObject convertMemberToJsonObject;
	
	private VaccineParamsBuilder(){
		
	}

	
	public String getFormDir() {
		return formDir;
	}

	public VaccineParamsBuilder setFormDir(String formDir) {
		this.formDir = formDir;
		return this;
	}

	public String getVaccineDate() {
		return vaccineDate;
	}

	public VaccineParamsBuilder setVaccineDate(String vaccineDate) {
		this.vaccineDate = vaccineDate;
		return this;
	}

	public int getVaccineDose() {
		return vaccineDose;
	}

	public VaccineParamsBuilder setVaccineDose(int vaccineDose) {
		this.vaccineDose = vaccineDose;
		return this;
	}

	public Members getMember() {
		return member;
	}

	public VaccineParamsBuilder setMember(Members member) {
		this.member = member;
		return this;
	}

	public String getVaccineName() {
		return vaccineName;
	}

	public VaccineParamsBuilder setVaccineName(String vaccineName) {
		this.vaccineName = vaccineName;
		return this;
	}

	public EncounterSyncMapping getEncounterSyncMapping() {
		return encounterSyncMapping;
	}

	public VaccineParamsBuilder setEncounterSyncMapping(EncounterSyncMapping encounterSyncMapping) {
		this.encounterSyncMapping = encounterSyncMapping;
		return this;
	}

	public AllFormSubmissions getFormSubmissions() {
		return formSubmissions;
	}

	public VaccineParamsBuilder setFormSubmissions(AllFormSubmissions formSubmissions) {
		this.formSubmissions = formSubmissions;
		return this;
	}

	public AllMembers getAllMembers() {
		return allMembers;
	}

	public VaccineParamsBuilder setAllMembers(AllMembers allMembers) {
		this.allMembers = allMembers;
		return this;
	}

	public static VaccineParamsBuilder getParamsBuilderInstance(){
		return paramsBuilder;
		
	}


	public FormField getFormField() {
		return formField;
	}


	public VaccineParamsBuilder setFormField(FormField formField) {
		this.formField = formField;
		return this;
	}


	public String getFieldName() {
		return fieldName;
	}


	public VaccineParamsBuilder setFieldName(String fieldName) {
		this.fieldName = fieldName;
		return this;
	}


	public String getFinalFieldName() {
		return finalFieldName;
	}


	public VaccineParamsBuilder setFinalFieldName(String finalFieldName) {
		this.finalFieldName = finalFieldName;
		return this;
	}


	public String getRetroFieldName() {
		return retroFieldName;
	}


	public VaccineParamsBuilder setRetroFieldName(String retroFieldName) {
		this.retroFieldName = retroFieldName;
		return this;
	}


	public String getDoseFieldName() {
		return doseFieldName;
	}


	public VaccineParamsBuilder setDoseFieldName(String doseFieldName) {
		this.doseFieldName = doseFieldName;
		return this;
	}


	public Map<String, String> getChildVaccineFollowup() {
		return childVaccineFollowup;
	}


	public VaccineParamsBuilder setChildVaccineFollowup(Map<String, String> childVaccineFollowup) {
		this.childVaccineFollowup = childVaccineFollowup;
		return this;
	}


	public boolean isType() {
		return type;
	}


	public VaccineParamsBuilder setType(boolean type) {
		this.type = type;
		return this;
	}


	public Field getField() {
		return field;
	}


	public VaccineParamsBuilder setField(Field field) {
		this.field = field;
		return this;
	}


	public JSONObject getConvertMemberToJsonObject() {
		return convertMemberToJsonObject;
	}


	public VaccineParamsBuilder setConvertMemberToJsonObject(JSONObject convertMemberToJsonObject) {
		this.convertMemberToJsonObject = convertMemberToJsonObject;
		return this;
	}
	
	
	
	
}


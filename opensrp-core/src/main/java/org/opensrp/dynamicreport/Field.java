package org.opensrp.dynamicreport;

import org.json.JSONException;
import org.json.JSONObject;

public class Field {
	private String varName;
	private String entityForm ;
	private String field;
	private String description;
	
	public Field(String json) throws JSONException  {
		
	this(new JSONObject(json));
	}
	
	public Field(JSONObject json)throws JSONException {
		
		this(json.getString("var_name"),json.getString("entity_form"),json.getString("field") ,json.getString("description"));
	}
	
	
	Field(String varName , String entitString, String field, String description )
	{
		this.varName=varName;
		this.entityForm=entitString;
		this.field=field;
		this.description=description;
				
	}

	public String getVarName() {
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

	public String getEntityForm() {
		return entityForm;
	}

	public void setEntityForm(String entityForm) {
		this.entityForm = entityForm;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	

}

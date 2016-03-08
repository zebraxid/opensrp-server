package org.opensrp.scheduler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;


public class Rule {
	@JsonProperty
	private String startFormName;
	@JsonProperty
	private String endFormName; 
	@JsonProperty
	private List<Defination> defination;
	private Map<String, String> mapOfFieldsByName;
	public Rule(String startFormName, String endFormName, List<Defination> defination) {
		super();
		this.startFormName = startFormName;
		this.endFormName = endFormName;
		this.defination = defination;
	}
	public String getStartFormName() {
		return startFormName;
	}
	public void setStartFormName(String startFormName) {
		this.startFormName = startFormName;
	}
	public String getEndFormName() {
		return endFormName;
	}
	public void setEndFormName(String endFormName) {
		this.endFormName = endFormName;
	}
	public List<Defination> getDefination() {
		return defination;
	}
	public String setDefination(String defination) {
		 if (mapOfFieldsByName == null) {
			 createDefinationMapByName();
	        }
	        return mapOfFieldsByName.get(defination);
	}
	private void createDefinationMapByName() {
        mapOfFieldsByName = new HashMap<>();
        for (Defination field : defination) {
            mapOfFieldsByName.put(field.getName(), field.getValue());
        }
    }
	

}

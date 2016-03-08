package org.opensrp.scheduler;

import org.codehaus.jackson.annotate.JsonProperty;

public class Defination {
	 	@JsonProperty
	    private String name;
	    @JsonProperty
	    private String value;
		public Defination(String name, String value) {
			super();
			this.name = name;
			this.value = value;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	    
}

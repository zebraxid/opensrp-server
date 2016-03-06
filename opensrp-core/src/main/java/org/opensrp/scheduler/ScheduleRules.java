package org.opensrp.scheduler;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.joda.time.DateTime;
import org.motechproject.model.MotechBaseDataObject;

@TypeDiscriminator("doc.type === 'ScheduleRule'")
public class ScheduleRules extends MotechBaseDataObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty
    private String name;
	@JsonProperty
    private String createdBy;
	@JsonProperty
	private List<Map<String, String>> rule;
	@JsonProperty
    private DateTime createdDate;
	
	public ScheduleRules(String name, String createdBy,
			List<Map<String, String>> rule) {
		super();
		this.name = name;
		this.createdBy = createdBy;
		this.rule = rule;
		
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public List<Map<String, String>> getRule() {
		return rule;
	}
	public void setRule(List<Map<String, String>> rule) {
		this.rule = rule;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, "id");
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}

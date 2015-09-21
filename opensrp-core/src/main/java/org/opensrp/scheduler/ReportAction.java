package org.opensrp.scheduler;

import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.joda.time.DateTime;
import org.motechproject.model.MotechBaseDataObject;
import org.motechproject.util.DateUtil;
import org.opensrp.dto.ActionData;


@TypeDiscriminator("doc.type === 'ReportAction'")
public class ReportAction extends MotechBaseDataObject  {
	
	@JsonProperty
    private String anmIdentifier;
    @JsonProperty
    private String caseID;
    @JsonProperty
    private String instanceId;
    @JsonProperty
    private Map<String, String> data;
    @JsonProperty
    private String actionTarget;
    @JsonProperty
    private String actionType;
    @JsonProperty
    private Boolean isActionActive;
    @JsonProperty
    private DateTime scheduleGenerateDate;
    @JsonProperty
    private long timeStamp;
    @JsonProperty
    private Map<String, String> details;
    
    

    private ReportAction() {
    }

    public ReportAction(String caseId, String instanceId, String anmIdentifier, ActionData actionData) {
        this.anmIdentifier = anmIdentifier;
        this.caseID = caseId;
        this.instanceId = instanceId;
        this.data = actionData.data();
        this.actionTarget = actionData.target();
        this.actionType = actionData.type();
        this.timeStamp = DateUtil.now().getMillis();
        this.scheduleGenerateDate = DateUtil.now();
        this.details = actionData.details();
        this.isActionActive = true;
    }

    public String anmIdentifier() {
        return anmIdentifier;
    }

    public String caseId() {
        return caseID;
    }
    
    public String instanceId() {
        return instanceId;
    }

    
    public Map<String, String> data() {
        return data;
    }

    public String actionType() {
        return actionType;
    }

    public long timestamp() {
        return timeStamp;
    }
    public DateTime scheduleGenerateDate() {
        return scheduleGenerateDate;
    }
    public String target() {
        return actionTarget;
    }

    public ReportAction markAsInActive() {
        this.isActionActive = false;
        return this;
    }

    public Boolean getIsActionActive() {
        return isActionActive;
    }

    public Map<String, String> details() {
        return details;
    }

    private String getCaseID() {
        return caseID;
    }

    public String getActionTarget() {
        return actionTarget;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o, "timeStamp", "revision");
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, "timeStamp", "revision");
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}

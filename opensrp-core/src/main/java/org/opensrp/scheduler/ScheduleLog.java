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
import org.opensrp.dto.AlertStatus;


@TypeDiscriminator("doc.type === 'ScheduleLog'")
public class ScheduleLog extends MotechBaseDataObject  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty
    private String anmIdentifier;
    @JsonProperty
    private String caseID;
    @JsonProperty
    private String instanceId;
    @JsonProperty
    private String trackId;
    @JsonProperty
    private Map<String, String> data;
    @JsonProperty
    private String actionTarget;
    @JsonProperty
    private AlertStatus currentWindow;
    @JsonProperty
    private String scheduleName;
    @JsonProperty
    private DateTime scheduleCloseDate;
    @JsonProperty
    private DateTime currentWindowStartDate;
    @JsonProperty
    private DateTime currentWindowEndDate;
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
    
    

    private ScheduleLog() {
    }

    public ScheduleLog(String caseId, String instanceId, String anmIdentifier, ActionData actionData) {
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

    public ScheduleLog(String caseId, String instanceId, String anmIdentifier, ActionData actionData,String trackId,AlertStatus currentWindow,DateTime scheduleCloseDate,DateTime currentWindowStartDate,DateTime currentWindowEndDate,String scheduleName) {
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
        this.trackId = trackId;
        this.currentWindow = currentWindow;
        this.scheduleCloseDate = scheduleCloseDate;
        this.currentWindowStartDate = currentWindowStartDate;
        this.currentWindowEndDate = currentWindowEndDate;
        this.scheduleName = scheduleName;
        
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
    public String trackId() {
        return trackId;
    }
    public String scheduleName() {
        return scheduleName;
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
    public AlertStatus currentWindow() {
        return currentWindow;
    }
    public DateTime scheduleCloseDate() {
        return scheduleCloseDate;
    }
    public DateTime currentWindowStartDate() {
        return currentWindowStartDate;
    }
    public DateTime currentWindowEndDate() {
        return currentWindowEndDate;
    }
    public DateTime scheduleGenerateDate() {
        return scheduleGenerateDate;
    }
    public String target() {
        return actionTarget;
    }

    public ScheduleLog markAsInActive() {
        this.isActionActive = false;
        return this;
    }

    public Boolean getIsActionActive() {
        return isActionActive;
    }

    public Map<String, String> details() {
        return details;
    }

    public String getCaseID() {
        return caseID;
    }
    public String getTrackId() {
        return trackId;
    }
    public String getScheduleName() {
        return scheduleName;
    }

    public AlertStatus getCurrentWindow() {
        return currentWindow;
    }
    public DateTime getScheduleCloseDate() {
        return scheduleCloseDate;
    }
    public DateTime getCurrentWindowStartDate() {
        return currentWindowStartDate;
    }
    public DateTime getCurrentWindowEndDate() {
        return currentWindowEndDate;
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

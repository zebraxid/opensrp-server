package org.opensrp.dto.form;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class MultimediaDTO {

	@JsonProperty
	private String caseId;
	@JsonProperty
	private String providerId;
	@JsonProperty
	private String fileName;

	public MultimediaDTO(String caseId, String providerId, String fileName) {
		this.caseId = caseId;
		this.providerId = providerId;
		this.fileName = fileName;
	}

	public String caseId() {
		return this.caseId;
	}

	public String providerId() {
		return this.providerId;
	}

	public String fileName() {
		return this.fileName;
	}

	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}

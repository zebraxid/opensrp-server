package org.opensrp.register.mcare;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ChildRegistration {
	
	private final List<ChildRegistrationEntry> childRegistrationEntries;
	
	public ChildRegistration(List<ChildRegistrationEntry> childRegistrationEntries) {
		this.childRegistrationEntries = childRegistrationEntries;
	}
	
	public List<ChildRegistrationEntry> childRegistrationEntries() {
		return childRegistrationEntries;
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

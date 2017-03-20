package org.opensrp.register.mcare.bo;

public enum DgfpClientType {
	HOUSE_HOLD,
	MEMBERS;
	
	
	public static DgfpClientType getUserType(String type) {
		if(type.equalsIgnoreCase("members")) {
			return MEMBERS;
		}else if(type.equalsIgnoreCase("household")) {
			return HOUSE_HOLD;
		}else {
			throw new IllegalArgumentException("Illigal User type argument: " + type);
		}
	}
	
}

package org.opensrp.connector.rapidpro;

public abstract class MessageFactory {
	
	public abstract Message getMessageType(String type);
	
	public static MessageFactory getMessageFactory(String type) {
		MessageFactory messageFactory = null;
		if (type.equalsIgnoreCase("Ann")) {
			messageFactory = new AnnouncementMessage();
		} else if (type.equalsIgnoreCase("Remainder")) {
			messageFactory = new RemainderMessage();
		}
		return messageFactory;
		
	}
	
}

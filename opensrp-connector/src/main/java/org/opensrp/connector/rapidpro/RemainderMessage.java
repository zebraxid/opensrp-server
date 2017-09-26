package org.opensrp.connector.rapidpro;

public class RemainderMessage extends MessageFactory {
	
	@Override
	public Message getClientType(ClientType type) {
		// TODO Auto-generated method stub
		Message message = null;
		if (type == ClientType.mother) {
			message = new WomanRemainderMessage();
		} else if (type == ClientType.child) {
			message = new ChildRemainderMessage();
		} else {
			
		}
		return message;
		
	}
	
}

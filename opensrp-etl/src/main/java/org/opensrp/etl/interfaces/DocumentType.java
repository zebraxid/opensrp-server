package org.opensrp.etl.interfaces;

public interface DocumentType<T,X,Y> {
	
	public T getPreparedData(X x);
	public void sendPreparedData(X t,Y y);

}

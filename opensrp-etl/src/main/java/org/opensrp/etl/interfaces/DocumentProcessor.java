package org.opensrp.etl.interfaces;

public interface DocumentProcessor<T,X> {
	public T getPreparedData(X x);
	public void savePreparedData(X x);

}

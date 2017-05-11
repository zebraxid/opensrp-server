package org.opensrp.register.encounter.sync.interfaces;


public interface FormsType<T> {
	public void makeForm();
	public void submit();
	public void delete();
	public T get(String id);

}

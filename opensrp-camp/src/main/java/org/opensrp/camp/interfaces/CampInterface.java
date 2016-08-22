package org.opensrp.camp.interfaces;

import java.util.List;


public interface CampInterface<E> {
	public String add(E object);
	public String edit(E onject);
	public List<E> getAll();
	public String delete(String id);
	public E findById(String id);
	
}

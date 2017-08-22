package org.utils;

import org.motechproject.dao.MotechBaseRepository;

import java.util.List;

public final class CouchDbAccessUtils {

	private CouchDbAccessUtils() {

	}

	public static <T, R extends MotechBaseRepository> void addObjectToRepository(List<T> objectList, R repository) {
		for (T object : objectList) {
			repository.add(object);
		}
	}
}

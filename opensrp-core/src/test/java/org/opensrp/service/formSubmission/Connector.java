package org.opensrp.service.formSubmission;

import java.util.List;

import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.CouchDbRepositorySupport;
import org.opensrp.form.domain.FormSubmission;

public class Connector extends CouchDbRepositorySupport<FormSubmission> {

	protected Connector(Class type, CouchDbConnector db) {
		super(type, db);
		// TODO Auto-generated constructor stub
	}
@Override
protected ViewQuery createQuery(String viewName) {
	// TODO Auto-generated method stub
	return super.createQuery(viewName);
}
@Override
protected List<FormSubmission> queryView(String viewName) {
	// TODO Auto-generated method stub
	return super.queryView(viewName);
}@Override
protected List<FormSubmission> queryView(String viewName, int key) {
	// TODO Auto-generated method stub
	return super.queryView(viewName, key);
}
@Override
protected List<FormSubmission> queryView(String viewName, ComplexKey key) {
	// TODO Auto-generated method stub
	return super.queryView(viewName, key);
}
}

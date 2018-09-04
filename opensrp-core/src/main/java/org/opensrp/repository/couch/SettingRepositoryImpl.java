package org.opensrp.repository.couch;

import java.util.List;

import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;
import org.opensrp.common.AllConstants;
import org.opensrp.domain.setting.Setting;
import org.opensrp.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository("couchSettingRepository")
@Primary
public class SettingRepositoryImpl extends CouchDbRepositorySupport<Setting> implements SettingRepository {
	
	@Autowired
	protected SettingRepositoryImpl(@Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db) {
		super(Setting.class, db);
		initStandardDesignDocument();
	}
	
	@View(name = "all_settings", map = "function(doc) { if (doc.type==='Setting') { emit(doc.identifier); }}")
	public List<Setting> findAllSettings() {
		return db.queryView(createQuery("all_settings").includeDocs(true), Setting.class);
	}
	
	@View(name = "settings_by_version", map = "function(doc) { if (doc.type==='Setting') { emit([doc.serverVersion], null); }}")
	public List<Setting> findAllSettingsByVersion(Long lastSyncedServerVersion) {
		ComplexKey startKey = ComplexKey.of(lastSyncedServerVersion);
		ComplexKey endKey = ComplexKey.of(Long.MAX_VALUE);
		return db.queryView(
		    createQuery("settings_by_version").includeDocs(true).startKey(startKey).endKey(endKey), Setting.class);
	}
	
	/**
	 * Get all Settings without a server version
	 *
	 * @return settings
	 */
	@View(name = "settings_by_empty_server_version", map = "function(doc) { if ( doc.type == 'Setting' && !doc.serverVersion) { emit(doc._id, doc); } }")
	public List<Setting> findByEmptyServerVersion() {
		return db.queryView(createQuery("settings_by_empty_server_version").limit(200).includeDocs(true), Setting.class);
	}
	
	@Override
	public void safeRemove(Setting entity) {
		remove(entity);
	}
	
}

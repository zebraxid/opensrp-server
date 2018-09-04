package org.opensrp.repository;

import java.util.List;

import org.opensrp.domain.setting.Setting;

public interface SettingRepository extends BaseRepository<Setting> {
	
	List<Setting> findAllSettings();
	
	List<Setting> findAllSettingsByVersion(Long lastSyncedServerVersion);
	
	List<Setting> findByEmptyServerVersion();
}

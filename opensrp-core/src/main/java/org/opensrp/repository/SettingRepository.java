package org.opensrp.repository;

import java.util.List;

import org.opensrp.domain.setting.SettingConfiguration;

public interface SettingRepository extends BaseRepository<SettingConfiguration> {
	
	List<SettingConfiguration> findAllSettings();
	
	List<SettingConfiguration> findAllSettingsByVersion(Long lastSyncedServerVersion);
	
	List<SettingConfiguration> findByEmptyServerVersion();
}

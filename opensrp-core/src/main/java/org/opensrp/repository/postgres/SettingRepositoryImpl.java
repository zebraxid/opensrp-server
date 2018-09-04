package org.opensrp.repository.postgres;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.opensrp.domain.postgres.SettingsMetadata;
import org.opensrp.domain.postgres.SettingsMetadataExample;
import org.opensrp.domain.setting.Setting;
import org.opensrp.repository.SettingRepository;
import org.opensrp.repository.postgres.mapper.custom.CustomSettingMapper;
import org.opensrp.repository.postgres.mapper.custom.CustomSettingMetadataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("settingRepositoryPostgres")
public class SettingRepositoryImpl extends BaseRepositoryImpl<Setting> implements SettingRepository {
	
	@Autowired
	private CustomSettingMapper settingMapper;
	
	@Autowired
	private CustomSettingMetadataMapper settingMetadataMapper;
	
	@Override
	public Setting get(String id) {
		return convert(settingMetadataMapper.selectByDocumentId(id));
	}
	
	@Override
	public void add(Setting entity) {
		if (entity == null || entity.getKey() == null) {
			return;
		}
		
		if (retrievePrimaryKey(entity) != null) { //Setting already added
			return;
		}
		
		if (entity.getId() == null)
			entity.setId(UUID.randomUUID().toString());
		setRevision(entity);
		
		org.opensrp.domain.postgres.Settings pgSetting = convert(entity, null);
		if (pgSetting == null) {
			return;
		}
		
		int rowsAffected = settingMapper.insertSelectiveAndSetId(pgSetting);
		if (rowsAffected < 1 || pgSetting.getId() == null) {
			return;
		}
		
		SettingsMetadata metadata = createMetadata(entity, pgSetting.getId());
		if (metadata != null) {
			settingMetadataMapper.insertSelective(metadata);
		}
	}
	
	@Override
	public void update(Setting entity) {
		if (entity == null || entity.getId() == null || entity.getKey() == null) {
			return;
		}
		
		Long id = retrievePrimaryKey(entity);
		
		if (id == null) { //Setting not exists 
			return;
		}
		
		setRevision(entity);
		
		org.opensrp.domain.postgres.Settings pgSetting = convert(entity, id);
		
		if (pgSetting == null) {
			return;
		}
		
		SettingsMetadata metadata = createMetadata(entity, id);
		if (metadata == null) {
			return;
		}
		
		int rowsAffected = settingMapper.updateByPrimaryKey(pgSetting);
		if (rowsAffected < 1) {
			return;
		}
		
		SettingsMetadataExample metadataExample = new SettingsMetadataExample();
		metadataExample.createCriteria().andSettingsIdEqualTo(id);
		metadata.setId(settingMetadataMapper.selectByExample(metadataExample).get(0).getId());
		settingMetadataMapper.updateByPrimaryKey(metadata);
		
	}
	
	@Override
	public List<Setting> getAll() {
		return convert(settingMetadataMapper.selectMany(new SettingsMetadataExample(), 0, DEFAULT_FETCH_SIZE));
	}
	
	@Override
	public void safeRemove(Setting entity) {
		if (entity == null) {
			return;
		}
		
		Long id = retrievePrimaryKey(entity);
		if (id == null) {
			return;
		}
		
		SettingsMetadataExample metadataExample = new SettingsMetadataExample();
		metadataExample.createCriteria().andSettingsIdEqualTo(id);
		int rowsAffected = settingMetadataMapper.deleteByExample(metadataExample);
		if (rowsAffected < 1) {
			return;
		}
		
		settingMapper.deleteByPrimaryKey(id);
		
	}
	
	@Override
	public List<Setting> findAllSettings() {
		return getAll();
	}
	
	@Override
	public List<Setting> findAllSettingsByVersion(Long lastSyncedServerVersion) {
		SettingsMetadataExample metadataExample = new SettingsMetadataExample();
		metadataExample.createCriteria().andServerVersionGreaterThanOrEqualTo(lastSyncedServerVersion);
		return convert(settingMetadataMapper.selectMany(metadataExample, 0, DEFAULT_FETCH_SIZE));
	}
	
	@Override
	public List<Setting> findByEmptyServerVersion() {
		SettingsMetadataExample metadataExample = new SettingsMetadataExample();
		metadataExample.createCriteria().andServerVersionIsNull();
		metadataExample.or(metadataExample.createCriteria().andServerVersionEqualTo(0l));
		return convert(settingMetadataMapper.selectMany(metadataExample, 0, DEFAULT_FETCH_SIZE));
	}
	
	@Override
	protected Long retrievePrimaryKey(Setting setting) {
		if (getUniqueField(setting) == null) {
			return null;
		}
		String documentId = setting.getId();
		
		SettingsMetadataExample metadataExample = new SettingsMetadataExample();
		metadataExample.createCriteria().andDocumentIdEqualTo(documentId);
		
		org.opensrp.domain.postgres.Settings pgSetting = settingMetadataMapper.selectByDocumentId(documentId);
		if (pgSetting == null) {
			return null;
		}
		return pgSetting.getId();
	}
	
	@Override
	protected Object getUniqueField(Setting setting) {
		return setting == null ? null : setting.getId();
	}
	
	//private Methods
	private Setting convert(org.opensrp.domain.postgres.Settings setting) {
		if (setting == null || setting.getJson() == null || !(setting.getJson() instanceof Setting)) {
			return null;
		}
		return (Setting) setting.getJson();
	}
	
	private org.opensrp.domain.postgres.Settings convert(Setting entity, Long id) {
		if (entity == null) {
			return null;
		}
		
		org.opensrp.domain.postgres.Settings pgSetting = new org.opensrp.domain.postgres.Settings();
		pgSetting.setId(id);
		pgSetting.setJson(entity);
		
		return pgSetting;
	}
	
	private List<Setting> convert(List<org.opensrp.domain.postgres.Settings> settings) {
		if (settings == null || settings.isEmpty()) {
			return new ArrayList<>();
		}
		
		List<Setting> settingValues = new ArrayList<>();
		for (org.opensrp.domain.postgres.Settings setting : settings) {
			Setting convertedSetting = convert(setting);
			if (convertedSetting != null) {
				settingValues.add(convertedSetting);
			}
		}
		return settingValues;
	}
	
	private SettingsMetadata createMetadata(Setting entity, Long id) {
		SettingsMetadata metadata = new SettingsMetadata();
		metadata.setSettingsId(id);
		metadata.setDocumentId(entity.getId());
		metadata.setIdentifier(entity.getKey());
		metadata.setServerVersion(entity.getServerVersion());
		return metadata;
	}
	
}

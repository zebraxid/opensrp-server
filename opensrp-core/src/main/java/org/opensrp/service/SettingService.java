package org.opensrp.service;

import java.util.List;

import org.opensrp.domain.setting.SettingConfiguration;
import org.opensrp.repository.SettingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingService {
	
	private static Logger logger = LoggerFactory.getLogger(SettingService.class.toString());
	
	private SettingRepository settingRepository;
	
	@Autowired
	public void setSettingRepository(SettingRepository settingRepository) {
		this.settingRepository = settingRepository;
	}
	
	public List<SettingConfiguration> findSettingsByVersion(Long lastSyncedServerVersion) {
		return settingRepository.findAllSettingsByVersion(lastSyncedServerVersion);
	}
	
	public void addServerVersion() {
		try {
			List<SettingConfiguration> settingConfigurations = settingRepository.findByEmptyServerVersion();
			logger.info("RUNNING addServerVersion settings size: " + settingConfigurations.size());
			long currentTimeMillis = System.currentTimeMillis();
			for (SettingConfiguration settingConfiguration : settingConfigurations) {
				try {
					Thread.sleep(1);
					settingConfiguration.setServerVersion(currentTimeMillis);
					settingRepository.update(settingConfiguration);
					currentTimeMillis += 1;
				}
				catch (InterruptedException e) {
					logger.error(e.getMessage());
				}
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
}

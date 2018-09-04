package org.opensrp.service;

import java.util.List;

import org.opensrp.domain.setting.Setting;
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
	
	public List<Setting> findSettingsByVersion(Long lastSyncedServerVersion) {
		return settingRepository.findAllSettingsByVersion(lastSyncedServerVersion);
	}
	
	public void addServerVersion() {
		try {
			List<Setting> settings = settingRepository.findByEmptyServerVersion();
			logger.info("RUNNING addServerVersion settings size: " + settings.size());
			long currentTimeMillis = System.currentTimeMillis();
			for (Setting setting : settings) {
				try {
					Thread.sleep(1);
					setting.setServerVersion(currentTimeMillis);
					settingRepository.update(setting);
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

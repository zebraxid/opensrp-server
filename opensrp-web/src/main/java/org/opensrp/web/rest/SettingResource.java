package org.opensrp.web.rest;

import static org.opensrp.web.rest.RestUtils.getStringFilter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.opensrp.common.AllConstants.BaseEntity;
import org.opensrp.domain.setting.SettingConfiguration;
import org.opensrp.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/rest/settings")
public class SettingResource {
	
	private SettingService settingService;
	
	@Autowired
	public void setSettingService(SettingService settingService) {
		this.settingService = settingService;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/sync")
	@ResponseBody
	public List<SettingConfiguration> findSettingsByVersion(HttpServletRequest request) {
		String serverVersion = getStringFilter(BaseEntity.SERVER_VERSIOIN, request);
		Long lastSyncedServerVersion = null;
		if (serverVersion != null) {
			lastSyncedServerVersion = Long.valueOf(serverVersion) + 1;
		}
		return settingService.findSettingsByVersion(lastSyncedServerVersion);
	}
}

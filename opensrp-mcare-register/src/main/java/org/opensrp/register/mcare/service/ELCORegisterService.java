/**
 * @author Asifur
 */

package org.opensrp.register.mcare.service;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.opensrp.register.mcare.domain.Elco;
import org.opensrp.register.mcare.repository.AllElcos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@JsonIgnoreProperties(ignoreUnknown = true)
public class ELCORegisterService {
	
	private final AllElcos allElcos;
	
	@Autowired
	public ELCORegisterService(AllElcos allElcos) {
		this.allElcos = allElcos;
	}
	
	public Elco getElcoById(String id) {
		return allElcos.get(id);
	}
}

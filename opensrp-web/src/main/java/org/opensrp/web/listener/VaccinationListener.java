package org.opensrp.web.listener;

import java.util.Date;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class VaccinationListener {
	
	public void vaccinationListener() {		
		System.out.println(new Date() + " This runs in a fixed delay with a initial delay");
		
	}
	
}

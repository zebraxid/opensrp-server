package org.opensrp.scheduler.service;

import org.opensrp.domain.ScheduleRuleDTO;
import org.opensrp.scheduler.ScheduleRules;
import org.opensrp.scheduler.repository.ScheduleRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleRuleService {
	
	private ScheduleRuleRepository scheduleRuleRepository;
	
	@Autowired
	public ScheduleRuleService(ScheduleRuleRepository scheduleRuleRepository){
		this.scheduleRuleRepository = scheduleRuleRepository;
	}

	public String edit(ScheduleRuleDTO scheduleRulesDTO){
		
		
		ScheduleRules scheduleRule = scheduleRuleRepository.findByID(scheduleRulesDTO.getRuleID());
		System.out.println("IDsds:"+scheduleRule.toString());
		System.out.println("ID:"+scheduleRulesDTO.getRuleID());
		scheduleRule.setName(scheduleRulesDTO.getName());		
		scheduleRule.setRule(scheduleRulesDTO.getRule());
		System.out.println("IDss:"+scheduleRulesDTO);
		scheduleRule.setRevision(scheduleRule.getRevision());
		scheduleRuleRepository.update(scheduleRule);
		return "1";
		
		
	}
}

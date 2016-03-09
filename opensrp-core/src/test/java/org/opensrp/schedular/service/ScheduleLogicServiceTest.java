package org.opensrp.schedular.service;

import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.opensrp.scheduler.Defination;
import org.opensrp.scheduler.Rule;
import org.opensrp.scheduler.ScheduleRules;
import org.opensrp.scheduler.service.ScheduleRuleRepository;

import com.google.gson.Gson;

public class ScheduleLogicServiceTest {
	
	@Mock
	private ScheduleRuleRepository sr;

	@Before
    public void setUp() throws Exception {
        initMocks(this);
       
    }
	
	@Test
	public void saveData(){
		
		//System.out.println(Rule.createRule("sddd","fff"));
		/*List<Defination> definations1 = new ArrayList<Defination>();
		List<Defination> definations2 = new ArrayList<Defination>();
		List<Rule> rules = new ArrayList<Rule>();
		Defination definition1 = new Defination("Name1", "Value1");
		Defination definition2 = new Defination("Name2", "Value2");
		
		definations1.add(definition1);
		definations2.add(definition2);
		
		Rule rule1 = new Rule(definations1, "Household1", "Cencus1");
		
		
		Rule rule2 = new Rule(definations1, "Household2", "Cencus2");
		
		
		
		rules.add(rule1);
		rules.add(rule2);
		//System.out.println(rules.toString());
		ScheduleRules s = new ScheduleRules("kkk", "dff", rules);
		System.out.println(new Gson().toJson(s));
		sr.submit(new ScheduleRules("kkk", "dff", rules));*/
		
		List<Defination> definations1 = new ArrayList<Defination>();
		List<Defination> definations2 = new ArrayList<Defination>();
		List<Rule> rules = new ArrayList<Rule>();
		Defination definition2 = new Defination("elco", "FWWOMFNAME");
		Defination definition1 = new Defination("submission", "1");
		
		definations1.add(definition1);
		definations2.add(definition2);
		
		Rule rule1 = new Rule(definations1, "Household", "Cencus");
		
		
		Rule rule2 = new Rule(definations2, "Household", "psrf_form");
		
		
		
		rules.add(rule1);
		rules.add(rule2);
		//System.out.println(rules.toString());
		//ScheduleRules s = new ScheduleRules("kkk", "dff", rules);
		//System.out.println(new Gson().toJson(s));
		sr.submit(new ScheduleRules("HouseHold Form", "proshanto", rules));
		
		/*logger.info("SR:"+sr.allRule().toString());
		logger.info(sr.allRule().get(0).getRule().get(0).getDefination().get(0).getName());*/
		
		System.out.println("SR:"+sr.allRule().toString());
		
		
	}
}

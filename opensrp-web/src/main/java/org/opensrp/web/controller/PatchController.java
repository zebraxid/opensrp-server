package org.opensrp.web.controller;

import static org.opensrp.common.AllConstants.HHRegistrationFields.REFERENCE_DATE;

import java.util.List;

import org.opensrp.common.util.DateTimeUtil;
import org.opensrp.register.mcare.domain.Child;
import org.opensrp.register.mcare.domain.Mother;
import org.opensrp.register.mcare.repository.AllChilds;
import org.opensrp.register.mcare.repository.AllMothers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PatchController {
	private AllMothers allMothers;
	private AllChilds allChilds;
	
	@Autowired
	public PatchController(AllMothers allMothers,AllChilds allChilds){
		this.allMothers = allMothers;
		this.allChilds = allChilds;
	}
	@RequestMapping(headers = { "Accept=application/json" }, method = RequestMethod.GET, value = "/update-child")
	public void childUpdate(){
		List<Child> clilds = allChilds.getAll();
		int i =0;
		for (Child child : clilds) {
			try{
			Mother mother = allMothers.findByCaseId(child.details().get("relationalid"));
			if(child.TODAY()!=null){
			child.withClientVersion(DateTimeUtil.getTimestampOfADate(child.TODAY()));
			}else{
				child.withClientVersion(0);
			}
			try{
				child.withDistrict(mother.FWWOMDISTRICT());
			}catch(Exception e){
				child.withDistrict("");
			}
			try{
				child.withUpazilla(mother.FWWOMUPAZILLA());
			}catch(Exception e){
				child.withUpazilla("");
			}
			
			try{
				child.withUnion(mother.getFWWOMUNION());
			}catch(Exception e){
				child.withUnion("");
			}
			try{
				child.withUnit(mother.getFWWOMSUBUNIT());
			}catch(Exception e){
				child.withUnit("");
			}
			
			try{
				child.withMouzaPara(mother.getMother_mauza());
			}catch(Exception e){
				child.withMouzaPara("");
			}
			
			allChilds.update(child);
			System.err.println("I:"+i+1);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
	}

}

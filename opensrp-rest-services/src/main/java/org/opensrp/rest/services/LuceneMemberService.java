package org.opensrp.rest.services;

import static java.text.MessageFormat.format;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.opensrp.register.mcare.bo.DgfpClient;
import org.opensrp.rest.repository.LuceneHouseHoldRepository;
import org.opensrp.rest.repository.LuceneMemberRepository;
import org.opensrp.rest.util.ConvertDateStringToTimestampMills;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.github.ldriscoll.ektorplucene.LuceneResult;
import com.github.ldriscoll.ektorplucene.LuceneResult.Row;

@Service
public class LuceneMemberService {
	
	private static Logger logger = LoggerFactory.getLogger(LuceneMemberService.class);

	private LuceneMemberRepository luceneMemberRepositroy;
	
	@Autowired
	public LuceneMemberService(
			LuceneMemberRepository luceneMemberRepositroy) {
		this.luceneMemberRepositroy = luceneMemberRepositroy;
		
	}

	public List<DgfpClient> getAllHouseHoldClientBasedOn(String firstName, String nationalId, String birthId) {
		logger.info(format("Member search based on first name: {0}, National Id: {1}, Birth Id: {2}", firstName, nationalId, birthId));
		
		/*if(this.noParameterExist(firstName, nationalId, birthId)){
			return Collections.emptyList();
		}*/
		
		String queryWithFirstName = this.createQueryWith("First_Name", firstName);
		String queryWithNatioanlId = this.createQueryWith("NID", nationalId);
		String queryWithBirhtId = this.createQueryWith("BR_ID", birthId);
		String queryWithType = this.createQueryWith("type", "Members");
		String AND = " AND ";
		String finalQueryString = queryWithType + AND + queryWithFirstName + AND + queryWithFirstName +
				AND + queryWithNatioanlId + AND + queryWithBirhtId;
		System.out.println(finalQueryString);
		
		LuceneResult result = this.luceneMemberRepositroy.findDocsByName(finalQueryString);
		logger.info(format("Member found: {0}", result.getTotalRows()));
		
		return this.createUserListFrom(result.getRows());
	}
	
	private boolean noParameterExist(String firstName, String nationalId, String birthId){
		boolean firstNameDosentExit = firstName == null || firstName.isEmpty();
		boolean nationalIdDoesntExist = nationalId == null || nationalId.isEmpty();
		boolean birthIdDoesntExist = birthId == null || birthId.isEmpty();
		
		boolean noParameterExist = firstNameDosentExit && nationalIdDoesntExist && birthIdDoesntExist;
		
		return noParameterExist;
	}
	
	private String createQueryWith(String queryKey, String queryParam){
		if(queryParam == null || queryParam.isEmpty()){
			return "";
		}else {
			return queryKey+ ":" + queryParam;
		}
	}
	
	private List<DgfpClient> createUserListFrom(List<Row> resultRows) {
		List<DgfpClient> dgfpClients = new ArrayList<DgfpClient>();
		for (Row row : resultRows) {
			String caseId = this.getValue(row, "Case_Id");
			String firstName = this.getValue(row, "First_Name");
			String nationalId = this.getValue(row, "NID");
			String birthId = this.getValue(row, "BR_ID");
			String type = this.getValue(row, "type");
			dgfpClients.add(new DgfpClient(caseId, firstName, nationalId, birthId, type));
		}
		return dgfpClients;
	}

	private String getValue(Row row, String key) {
		return row.getFields().containsKey(key) ? (String) row
				.getFields().get(key) : "";
	}
	
	

}

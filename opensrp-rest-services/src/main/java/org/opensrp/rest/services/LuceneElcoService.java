package org.opensrp.rest.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.opensrp.rest.repository.LuceneElcoRepository;
import org.opensrp.rest.util.ConvertDateStringToTimestampMills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ldriscoll.ektorplucene.LuceneResult;

@Service
public class LuceneElcoService {
	
	private LuceneElcoRepository luceneElcoRepository;
	private ConvertDateStringToTimestampMills convertDateStringToTimestampMills;
	@Autowired
	public LuceneElcoService(LuceneElcoRepository luceneElcoRepository,ConvertDateStringToTimestampMills convertDateStringToTimestampMills){
		this.luceneElcoRepository = luceneElcoRepository;
		this.convertDateStringToTimestampMills = convertDateStringToTimestampMills;
	}
	
	/**
	 * This method return Elco count as today, this month or this week
	 * @param start this may be start date of a month or week
	 * @param end   this may be end date of a month or week
	 * */
	public int getElcoCount(String start,String end){
		if(start.equalsIgnoreCase("")){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	Date today = Calendar.getInstance().getTime();    	
		String makeQueryString ="type:Elco" + " AND " + "SUBMISSIONDATE:["+convertDateStringToTimestampMills.convertDateToTimestampMills(dateFormat.format(today))+" TO "+convertDateStringToTimestampMills.convertDateToTimestampMills(dateFormat.format(today))+"]" ;
    	LuceneResult result = luceneElcoRepository.findDocsByProvider(makeQueryString);
		return result.getRows().size();
		}else{
			String makeQueryString ="type:Elco" + " AND " + "SUBMISSIONDATE:["+convertDateStringToTimestampMills.convertDateToTimestampMills(start)+" TO "+convertDateStringToTimestampMills.convertDateToTimestampMills(end)+"]" ;
	    	LuceneResult result = luceneElcoRepository.findDocsByProvider(makeQueryString);
			return result.getRows().size();
		}
		
		
	}
}

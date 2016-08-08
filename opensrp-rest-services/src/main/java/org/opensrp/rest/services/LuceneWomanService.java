/**
 * @author proshanto
 * */
package org.opensrp.rest.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.opensrp.rest.repository.LuceneWomanRepository;
import org.opensrp.rest.util.ConvertDateStringToTimestampMills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ldriscoll.ektorplucene.LuceneResult;

@Service
public class LuceneWomanService {
	private LuceneWomanRepository luceneWomanRepository;
	private ConvertDateStringToTimestampMills convertDateStringToTimestampMills;
	@Autowired
	public LuceneWomanService(LuceneWomanRepository luceneWomanRepository,ConvertDateStringToTimestampMills convertDateStringToTimestampMills){
		this.luceneWomanRepository = luceneWomanRepository;
		this.convertDateStringToTimestampMills = convertDateStringToTimestampMills;
	}
	/**
	 * This method return Woman count as today, this month or this week
	 * @param start this may be start date of a month or week
	 * @param end   this may be end date of a month or week
	 * */
	public int getWomanCount(String start,String end){
		if(start.equalsIgnoreCase("")){
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    	Date today = Calendar.getInstance().getTime();    	
			String makeQueryString ="isClosed:false" + " AND " + "type:Woman" + " AND " + "SUBMISSIONDATE:["+convertDateStringToTimestampMills.convertDateToTimestampMills(dateFormat.format(today))+" TO "+convertDateStringToTimestampMills.convertDateToTimestampMills(dateFormat.format(today))+"]" ;
	    	LuceneResult result = luceneWomanRepository.findDocsByProvider(makeQueryString);
			return result.getRows().size();
		}else{
			String makeQueryString ="isClosed:false" + " AND " + "type:Woman" + " AND " + "SUBMISSIONDATE:["+convertDateStringToTimestampMills.convertDateToTimestampMills(start)+" TO "+convertDateStringToTimestampMills.convertDateToTimestampMills(end)+"]" ;
	    	LuceneResult result = luceneWomanRepository.findDocsByProvider(makeQueryString);
			return result.getRows().size();
		}
		
		
	}

}

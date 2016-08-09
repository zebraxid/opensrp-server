/**
 * @author proshanto
 * */
package org.opensrp.rest.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.opensrp.rest.repository.LuceneMembersRepository;
import org.opensrp.rest.util.ConvertDateStringToTimestampMills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ldriscoll.ektorplucene.LuceneResult;

@Service
public class LuceneMembersService {
	private LuceneMembersRepository luceneMembersRepository;
	private ConvertDateStringToTimestampMills convertDateStringToTimestampMills;
	@Autowired
	public LuceneMembersService(LuceneMembersRepository luceneMembersRepository,ConvertDateStringToTimestampMills convertDateStringToTimestampMills){
		this.luceneMembersRepository = luceneMembersRepository;
		this.convertDateStringToTimestampMills = convertDateStringToTimestampMills;
	}
	/**
	 * This method return Members count as today, this month or this week
	 * @param start this may be start date of a month or week
	 * @param end   this may be end date of a month or week
	 * */
	public int getMembersCount(String start,String end){
		if(start.equalsIgnoreCase("")){
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    	Date today = Calendar.getInstance().getTime();    	
			String makeQueryString ="isClosed:false" + " AND " + "type:Members" + " AND " + "SUBMISSIONDATE:["+convertDateStringToTimestampMills.convertDateToTimestampMills(dateFormat.format(today))+" TO "+convertDateStringToTimestampMills.convertDateToTimestampMills(dateFormat.format(today))+"]" ;
	    	LuceneResult result = luceneMembersRepository.findDocsByProvider(makeQueryString);
			return result.getRows().size();
		}else{
			String makeQueryString ="isClosed:false" + " AND " + "type:Members" + " AND " + "SUBMISSIONDATE:["+convertDateStringToTimestampMills.convertDateToTimestampMills(start)+" TO "+convertDateStringToTimestampMills.convertDateToTimestampMills(end)+"]" ;
	    	LuceneResult result = luceneMembersRepository.findDocsByProvider(makeQueryString);
			return result.getRows().size();
		}
		
		
	}

}

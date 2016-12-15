/*package org.opensrp.rest.it;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.opensrp.common.util.DateUtil;
import org.opensrp.common.util.WeekBoundariesAndTimestamps;
import org.opensrp.rest.repository.LuceneElcoRepository;

import com.github.ldriscoll.ektorplucene.LuceneAwareCouchDbConnector;
import com.github.ldriscoll.ektorplucene.LuceneResult;

public class LuceneElcoRepositoryTest {

	private LuceneAwareCouchDbConnector connector;
	private LuceneElcoRepository luceneElcoRepository;

	@Before
	public void setUp() throws IOException {
		HttpClient httpClient = new StdHttpClient.Builder().host("localhost")
		// .host("192.168.19.55")
		// .host("192.168.23.223")
				.port(5984).socketTimeout(1000)
				// .username("testadmin").password("testpass")
				.build();
		CouchDbInstance instance = new StdCouchDbInstance(httpClient);

		connector = new LuceneAwareCouchDbConnector("opensrp", instance);
		connector.createDatabaseIfNotExists();
		luceneElcoRepository = new LuceneElcoRepository(2, connector);
		// createDocuments();
	}

	@After
	public void tearDown() {
		// deleteDocuments();
		connector = null;
	}

	public long convertDateToTimestampMills(String date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date day = null;
		try {
			day = dateFormat.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return day.getTime();
	}

	@Ignore
	@Test
	public void testInit() throws ParseException {
		// String makeQueryString ="PROVIDERID:proshanto" + " AND " +
		// "FWUPAZILLA:GAIBANDHA SADAR" + " AND " + "user_type:FWA"+
		// " AND SUBMISSIONDATE:[2014-02-01 TO 2017-03-30]" ;

		
		 * DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); Date
		 * today = Calendar.getInstance().getTime(); Date day= null; try { day =
		 * dateFormat.parse(dateFormat.format(today)); } catch (ParseException
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 * System.out.println("timestamp with previous way- " + day.getTime());
		 * System.out.println("timestamp with new way- " +
		 * DateUtil.getTimestampToday());
		 
		System.out.println("this.convertDateToTimestampMills(\"2016-06-08\") - " + this.convertDateToTimestampMills("2016-06-08"));
		System.out.println("this.convertDateToTimestampMills(\"2016-06-14\") - " + this.convertDateToTimestampMills("2016-06-14"));
		System.out.println("this.convertDateToTimestampMills(\"2016-06-15\") - " + this.convertDateToTimestampMills("2016-06-15"));
		System.out.println("this.convertDateToTimestampMills(\"2016-06-21\") - " + this.convertDateToTimestampMills("2016-06-21"));

		
		 * String makeQueryString ="type:Household" + " AND "+
		 * "SUBMISSIONDATE:["+this.convertDateToTimestampMills("2016-06-08")+
		 * " TO "+this.convertDateToTimestampMills("2016-06-14")+"]" ;
		 
		String makeQueryString = "type:Household" + " AND " + "SUBMISSIONDATE:[1465840800000 TO 1465840800000]";

		LuceneResult result = luceneElcoRepository.findDocsByProvider(makeQueryString);
		System.out.println(result.getRows().size() + " -count with getRows().size()");
		System.out.println(result.getTotalRows() + " -count with getTotalRows()");

		WeekBoundariesAndTimestamps boundaries = DateUtil.getWeekBoundariesForDashboard();
		for (int i = 0; i < boundaries.weekBoundariesAsTimeStamp.size(); i++) {
			System.out.println(boundaries.weekBoundariesAsTimeStamp.get(i) + " -this timestamp should refer to- " + boundaries.weekBoundariesAsString.get(i)
					+ " -and referring to- " + new Date(boundaries.weekBoundariesAsTimeStamp.get(i)).toString());
		}

		
		 * makeQueryString ="type:Household" + " AND "+
		 * "SUBMISSIONDATE:["+this.convertDateToTimestampMills("2016-06-15")+
		 * " TO "+this.convertDateToTimestampMills("2016-06-15")+"]" ;
		 * 
		 * result = luceneElcoRepository.findDocsByProvider(makeQueryString);
		 * System.out.println(result.getRows().size() +
		 * " -count with getRows().size() for today");
		 * System.out.println(result.getTotalRows() +
		 * " -count with getTotalRows() for today");
		 

		
		 * Date today = Calendar.getInstance().getTime(); Date dates =
		 * dateFormat.parse(dateFormat.format(today)); long end =
		 * dates.getTime(); //end has today's date
		 * 
		 * Date date = dateFormat.parse("2016-05-01"); long start =
		 * date.getTime();
		 * 
		 * System.out.println("today:- "+end);
		 * System.out.println("limit:- "+start);
		 
		
		 * String makeQueryString;// ="type:Elco" + " AND " +
		 * "SUBMISSIONDATE:["+start+" TO "+end+"]" ; makeQueryString
		 * ="type:Elco" + " AND " + "SUBMISSIONDATE:[" + end +" TO " + end +"]"
		 * ; LuceneResult result =
		 * luceneElcoRepository.findDocsByProvider(makeQueryString);
		 * System.out.println(result.getTotalRows() +
		 * " -today Count for may31");
		 * 
		 * makeQueryString = "type:Elco" + " AND " + "SUBMISSIONDATE:[" + start
		 * +" TO " + end +"]" ; result =
		 * luceneElcoRepository.findDocsByProvider(makeQueryString);
		 * System.out.println(result.getTotalRows() + " -month Count for may");
		 

		
		 * Date weekUpperDate = dateFormat.parse("2016-05-17"); long
		 * weekUpperDateTimestamp = weekUpperDate.getTime(); Date weekLowerDate
		 * = dateFormat.parse("2016-05-21"); long weekLowerDateTimestamp =
		 * weekLowerDate.getTime(); String makeQueryString = "type:Elco" +
		 * " AND " + "SUBMISSIONDATE:[" + weekUpperDateTimestamp +" TO " +
		 * weekLowerDateTimestamp +"]" ; LuceneResult result =
		 * luceneElcoRepository.findDocsByProvider(makeQueryString);
		 * System.out.println(result.getRows().size() + " -week Count for may");
		 * System.out.println(result.getTotalRows() + " -week Count for may");
		 

		// var x = new Date(); var y = new Date(x.getFullYear(), x.getMonth()-3,
		// 1,0,0,0); var time = y.getTime(); console.log(time); console.log(y);

		
		 * assertNotNull("Expecting a non null result", result);
		 * assertTrue("Should only have one result", result.getRows().size()
		 * >=0);
		 
	}
}*/
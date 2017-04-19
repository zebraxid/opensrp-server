/**
 * @author proshanto
 */

package org.opensrp.register.mcare.service;

import static java.text.MessageFormat.format;
import static org.opensrp.common.AllConstants.StockFields.date;

import java.util.List;

import org.json.JSONException;
import org.opensrp.api.domain.User;
import org.opensrp.common.util.DateTimeUtil;
import org.opensrp.common.util.DateUtil;
import org.opensrp.connector.openmrs.service.OpenmrsUserService;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.mcare.domain.Stock;
import org.opensrp.register.mcare.repository.AllStocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class StockService {

	private static Logger logger = LoggerFactory.getLogger(StockService.class
			.toString());
	private AllStocks allStocks;	
	
	@Autowired
	public StockService(AllStocks allStocks,OpenmrsUserService openmrsUserService) {
		this.allStocks = allStocks;	
		
	}	
	public void registerStock(FormSubmission submission)  {		
		Stock stock = allStocks.findByCaseId(submission.entityId());		
		if (allStocks == null) {
			logger.warn(format(
					"Failed to handle Stock form as there is no stock registered with ID: {0}",
					submission.entityId()));
			return;
		}
		long clientVersion = DateTimeUtil.getTimestampOfADate(submission.getField(date));
		stock.setProvider(submission.anmId())
		.setInstanceId(submission.instanceId())
		.setServerVersion(DateUtil.getTimestampToday())
		.setClientVersion(clientVersion)
		.setUpdateVersion(DateTimeUtil.getTimestampOfADate(submission.getField(date)))
		;
		List<Stock> stocks = allStocks.allStocksByProviderClientVersion(submission.anmId(), clientVersion);
		
		for (Stock st : stocks) {
			 if(!st.getId().equalsIgnoreCase(stock.getId())){
				try{
					allStocks.remove(st);
				}catch(Exception e){
					logger.info("Duplicate Stock Delete error Message: "+e.getMessage());
				}
				
			 }
		}
		allStocks.update(stock);
	}

	
	
}

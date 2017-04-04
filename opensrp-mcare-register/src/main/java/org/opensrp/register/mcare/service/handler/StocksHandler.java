/**
 * @author proshanto
 */

package org.opensrp.register.mcare.service.handler;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.mcare.service.StockService;
import org.opensrp.service.formSubmission.handler.FormSubmissionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StocksHandler implements FormSubmissionHandler {

	private StockService stockService;

	@Autowired
	public StocksHandler(StockService stockService) {
		this.stockService = stockService;
	}
	
	@Override
	public void handle(FormSubmission submission) {
		stockService.registerStock(submission)	;
	}

}

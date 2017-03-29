package org.opensrp.repository.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.ektorp.CouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.json.JSONObject;

import com.github.ldriscoll.ektorplucene.LuceneAwareCouchDbConnector;
import com.github.ldriscoll.ektorplucene.LuceneResult;
import com.github.ldriscoll.ektorplucene.LuceneResult.Row;

class LuceneDbConnector extends LuceneAwareCouchDbConnector{
	public LuceneDbConnector(CouchDbConnector db, StdCouchDbInstance dbinst) throws IOException {
		super(db.getDatabaseName(), dbinst);
	}
	
	public <T> List<T> asList(LuceneResult result, Class<T> type) throws JsonProcessingException, IOException {
		List<T> ol = new ArrayList<>();
		for (Row r : result.getRows()) {
			HashMap<String, Object> doc = r.getDoc();
			
			T ro = new ObjectMapper().readValue(new JSONObject(doc).toString(), type);
			ol.add(ro);
		}
		return ol;
	}
	

}

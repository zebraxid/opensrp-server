package org.opensrp.repository.lucene;

import com.github.ldriscoll.ektorplucene.CouchDbRepositorySupportWithLucene;
import com.github.ldriscoll.ektorplucene.LuceneQuery;
import com.github.ldriscoll.ektorplucene.LuceneResult;
import com.github.ldriscoll.ektorplucene.designdocument.annotation.FullText;
import com.github.ldriscoll.ektorplucene.designdocument.annotation.Index;
import com.mysql.jdbc.StringUtils;
import org.opensrp.common.AllConstants;
import org.opensrp.domain.Shipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

// To fix: later
@FullText({
        @Index(name = "by_all_criteria", analyzer = "perfield:{receivingFacility.code : \"keyword\"}", index = "function(doc) { if (doc.type !== 'Shipment') return null; var arr1 = ['orderedDate', 'orderCode', 'receivingFacility', 'supplyingFacility', 'lineItems', 'processingPeriod']; var ret = new Document(); ret.add(orderedDate, { 'field': 'orderedDate' }); for (var i in arr1) { ret.add(doc[arr1[i]], { 'field': arr1[i] }); } if (doc.orderCode) { var oc = doc.orderCode; ret.add(rf, { 'field': 'receivingFacility' }); } if (doc.lineItems) { var li = doc.lineItems; ret.add(pp, { 'field': 'processingPeriod' }); } return ret;}")
})
@Component
public class LuceneShipmentRepository extends CouchDbRepositorySupportWithLucene<Shipment> {

    private LuceneDbConnector luceneDbConnector;

    @Autowired
    protected LuceneShipmentRepository(LuceneDbConnector luceneDbConnector) {
        super(Shipment.class, luceneDbConnector);
        this.luceneDbConnector = luceneDbConnector;
        initStandardDesignDocument();
    }

    public List<Shipment> getShipmentsByLocationId(String locationId, long serverVersion, String orderBy, String sortOrder, int limit) {
        LuceneQuery luceneQuery = new LuceneQuery("Shipment", "by_all_criteria");

        Query query = new Query(FilterType.AND);

        query.eq(AllConstants.Shipment.LOCATION_ID, locationId);
        query.between(AllConstants.Shipment.SERVER_VERSION, serverVersion, Long.MAX_VALUE);

        luceneQuery.setQuery(query.query());
        luceneQuery.setStaleOk(false);
        luceneQuery.setLimit(limit);

        if (!StringUtils.isEmptyOrWhitespaceOnly(orderBy) && !StringUtils.isEmptyOrWhitespaceOnly(sortOrder)) {
            luceneQuery.setSort((sortOrder.contains("desc") ? "\\" : "/") + orderBy);
        }

        try {
            LuceneResult luceneResult = db.queryLucene(luceneQuery);
            return luceneDbConnector.asList(luceneResult, Shipment.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package org.opensrp.repository.lucene;

import com.github.ldriscoll.ektorplucene.CouchDbRepositorySupportWithLucene;
import com.github.ldriscoll.ektorplucene.LuceneQuery;
import com.github.ldriscoll.ektorplucene.LuceneResult;
import com.github.ldriscoll.ektorplucene.designdocument.annotation.FullText;
import com.github.ldriscoll.ektorplucene.designdocument.annotation.Index;
import com.mysql.jdbc.StringUtils;
import org.opensrp.common.AllConstants;
import org.opensrp.domain.Shipment;
import org.opensrp.repository.lucene.exception.LuceneShipmentRepositoryQueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@FullText({
        @Index(name = "by_all_criteria", analyzer = "perfield:{orderCode : \"keyword\", receivingFacility.code : \"keyword\"}", index = "function(doc) { if (doc.type !== 'Shipment') return null; var arr1 = ['orderedDate', 'orderCode', 'serverVersion']; var ret = new Document(); ret.add(doc.receivingFacility.code, {field: \"receivingFacility.code\"}); for (var i in arr1) { ret.add(doc[arr1[i]], { 'field': arr1[i] }); }  return ret;}")
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

    public List<Shipment> findShipmentsByReceivingFacility(String receivingFacilityCode, long serverVersion,
                                                           String sortOrder, String orderBy, int limit) {
        LuceneQuery luceneQuery = new LuceneQuery("Shipment", "by_all_criteria");

        Query query = new Query(FilterType.AND);
        query.eq(AllConstants.Shipment.RECEIVING_FACILITY_CODE, receivingFacilityCode);
        query.between(AllConstants.Shipment.SERVER_VERSION, serverVersion, Long.MAX_VALUE);

        luceneQuery.setQuery(query.query());
        luceneQuery.setStaleOk(false);
        luceneQuery.setIncludeDocs(true);
        luceneQuery.setLimit(limit);

        if (!StringUtils.isEmptyOrWhitespaceOnly(orderBy) && !StringUtils.isEmptyOrWhitespaceOnly(sortOrder)) {
            luceneQuery.setSort((sortOrder.contains("desc") ? "\\" : "/") + orderBy);
        }

        try {
            LuceneResult luceneResult = db.queryLucene(luceneQuery);
            return luceneDbConnector.asList(luceneResult, Shipment.class);
        } catch (IOException e) {
            throw new LuceneShipmentRepositoryQueryException(e);
        }
    }
}

package org.opensrp.repository;

import org.ektorp.CouchDbConnector;
import org.ektorp.util.Assert;
import org.ektorp.util.Documents;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.domain.Shipment;
import org.opensrp.repository.lucene.LuceneShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AllShipments extends MotechBaseRepository<Shipment> {

    private LuceneShipmentRepository luceneShipmentRepository;

    @Autowired
    public AllShipments(@Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector couchDbConnector,
                           LuceneShipmentRepository luceneShipmentRepository) {
        super(Shipment.class, couchDbConnector);
        this.luceneShipmentRepository = luceneShipmentRepository;
    }

    public void add(Shipment shipment) {
        Assert.isTrue(Documents.isNew(shipment), "shipment entity must be new. " +
                "The revision field should not have a value i.e. should be null");
        db.create(shipment);
    }

    public List<Shipment> findShipmentsByReceivingFacility(String receivingFacilityCode, long serverVersion,
                                                           String sortOrder, String orderBy, int limit) {
        return luceneShipmentRepository.findShipmentsByReceivingFacility(receivingFacilityCode, serverVersion, sortOrder,
                orderBy, limit);
    }
}

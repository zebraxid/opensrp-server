package org.opensrp.repository;

import org.ektorp.CouchDbConnector;
import org.ektorp.util.Assert;
import org.ektorp.util.Documents;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.domain.Shipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class AllShipments extends MotechBaseRepository<Shipment> {

    @Autowired
    protected AllShipments(@Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector couchDbConnector) {
        super(Shipment.class, couchDbConnector);
    }

    public void add(Shipment shipment) {
        Assert.isTrue(Documents.isNew(shipment), "shipment entity must be new");
        db.create(shipment);
    }
}

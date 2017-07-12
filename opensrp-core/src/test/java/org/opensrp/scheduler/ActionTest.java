package org.opensrp.scheduler;


import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class ActionTest {

    @Test
    public void testEqualityAndHashCode() {
        EqualsVerifier.forClass(Action.class).withIgnoredFields("timeStamp", "revision").suppress(Warning.NONFINAL_FIELDS).verify();
    }
}

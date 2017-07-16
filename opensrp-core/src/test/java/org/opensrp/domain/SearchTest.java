package org.opensrp.domain;


import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SearchTest {

    @Test
    public void testEqualAndHashcodeContract() {
        EqualsVerifier.forClass(Search.class)
                .withIgnoredFields("id", "revision")
                .suppress(Warning.NONFINAL_FIELDS)
                .withPrefabValues(User.class, new User("ll"), new User("e"))
                .withRedefinedSuperclass()
                .verify();
    }

    @Test
    public void testGetterAndSetter() {
        Validator validator = ValidatorBuilder.create()
                .with(new SetterTester())
                .with(new GetterTester())
                .build();

        validator.validate(PojoClassFactory.getPojoClass(Search.class));
    }

    @Test
    public void testFullName() {
        Search search = new Search("ss").withFirstName("first").withMiddleName("middle").withLastName("last");
        assertEquals("first middle last", search.fullName());

        search = new Search("ss").withName("first", "middle", null);
        assertEquals("first middle", search.fullName());

        search = new Search("ee", "first", "", "last", new DateTime(0l), true, "male");
        assertEquals("first last", search.fullName());

        search = new Search("ee", "first", "", null, new DateTime(0l), true, "male");
        assertEquals("first", search.fullName());

        search = new Search("ee", null, "middle", "", new DateTime(0l), true, "male");
        assertEquals("middle", search.fullName());

        search = new Search("ee", null, "middle", "last", new DateTime(0l), true, "male");
        assertEquals("middle last", search.fullName());

        search = new Search("ee", "", "", "last", new DateTime(0l), true, "male");
        assertEquals("last", search.fullName());

        search = new Search("ee", "", "", null, new DateTime(0l), true, "male", Collections.EMPTY_LIST, new HashMap<String, String>(), new HashMap<String, Object>());
        assertEquals("", search.fullName());
    }

    @Test
    public void testRelationShip() {
        Search search = new Search("ee", "", "", "last", new DateTime(0l), true, "male", "d", "d");
        search.withBirthdate(new DateTime(0l), true);
        search.withGender("male");
        search.withRelationships(null);
        assertNull( search.findRelatives("d"));

        search.withRelationships(null);
        search.addRelationship("t", "id");
        assertEquals(1, search.getRelationships().size());
        assertEquals(asList("id"), search.findRelatives("t"));
        assertEquals(asList("t"), search.getRelationships("id"));
        assertEquals(0, search.getRelationships("ddd").size());

        search.withRelationships(null);
        search.addRelationship("t", "id");
        assertEquals(1, search.getRelationships().size());
        assertEquals(asList("id"), search.findRelatives("t"));
        assertEquals(asList("t"), search.getRelationships("id"));

    }
}

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

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class ClientTest {

    @Test
    public void testEqualAndHashcodeContract() {
        EqualsVerifier.forClass(Client.class)
                .withIgnoredFields("id","revision")
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

        validator.validate(PojoClassFactory.getPojoClass(Client.class));
    }

    @Test
    public void testSetAndGetRelationship() {
        Client client = new Client();
        client.withRelationships(null);

        assertNull(client.getRelationships());
        assertNull(client.findRelatives("dd"));
        assertEquals(0, client.getRelationships("d").size());

        client.withRelationships(null);
        client.addRelationship("type", "r");
        client.addRelationship("type", "e");
        client.addRelationship("type1", "r");

        assertEquals(2, client.getRelationships().size());
        assertEquals(asList("type1", "type"), client.getRelationships("r"));
        assertEquals(asList("r", "e"), client.findRelatives("type"));

    }

    @Test
    public void testConstructor() {
        String baseEntityId = "id";
        String firstName = "first";
        String middleName = "middleName";
        String lastName = "lastName";
        DateTime birthDate = new DateTime(0l);
        boolean birthDateApprx = true;
        DateTime deathDate = new DateTime(1l);
        boolean deathDateApprox = false;
        String gender = "male";
        String type = "type";
        String identifier = "identifier";
        Client client = new Client(baseEntityId, firstName, middleName, lastName, birthDate, deathDate, birthDateApprx, deathDateApprox, gender, type, identifier);

        assertEquals(firstName, client.getFirstName());
        assertEquals(middleName, client.getMiddleName());
        assertEquals(lastName, client.getLastName());
        assertEquals(birthDate, client.getBirthdate());
        assertEquals(birthDateApprx, client.getBirthdateApprox());
        assertEquals(deathDate, client.getDeathdate());
        assertEquals(deathDateApprox, client.getDeathdateApprox());
        assertEquals(identifier, client.getIdentifier(type));
        assertEquals(gender, client.getGender());
    }


}

package org.opensrp.domain;


import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import static java.util.Arrays.asList;

public class UserTest {

    @Test
    public void testEqualAndHashcodeContract() {
        EqualsVerifier.forClass(User.class)
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

        validator.validate(PojoClassFactory.getPojoClass(User.class));
    }

    @Test
    public void testConstructor() {
        final Class<?> clazz = User.class;
        final Object obj1 = FormTest.getInstance(clazz, "entityId","username", "password", "salt");
        Affirm.affirmNotNull("Should have created an object", obj1);

        final Object obj2 = FormTest.getInstance(clazz, new Object[] {});
        Affirm.affirmTrue("Should have created a different object", obj1 != obj2);
    }

}

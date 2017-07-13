package org.opensrp.api.domain;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.filters.FilterPackageInfo;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.PojoValidator;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

public class LocationTest {
	@Test
	public void validate() {
	    PojoClass pojoClass = PojoClassFactory.getPojoClass(Location.class);
	    Validator pojoValidator = ValidatorBuilder.create()
	            .with(new SetterMustExistRule())
	            .with(new GetterMustExistRule())
	            .with(new GetterTester())
	            .with(new SetterTester())
	            .build();

	    pojoValidator.validate(pojoClass);
	}

}

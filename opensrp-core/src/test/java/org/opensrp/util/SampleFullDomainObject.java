package org.opensrp.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.opensrp.domain.Address;
import org.opensrp.domain.Client;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SampleFullDomainObject {

	public static final String addressType = "addressType";

	public static final String country = "country";

	public static final String stateProvince = "stateProvince";

	public static final String cityVillage = "cityVillage";

	public static final String countryDistrict = "countryDistrict";

	public static final String subDistrict = "subDistrict";

	public static final String town = "town";

	public static final String name = "name";

	public static final String male = "male";

	public static final DateTime birthDate = new DateTime(0l, DateTimeZone.UTC);

	public static final DateTime deathDate = new DateTime(1l, DateTimeZone.UTC);

	public static Map<String, String> identifier = new HashMap<>();

	public static final Map<String, Object> attributes = new HashMap<>();

	public static final String IDENTIFIER_TYPE = "identifierType";

	public static final String IDENTIFIER_VALUE = "identifierValue";

	public static final String ATTRIBUTES_VALUE = "attributesValue";

	public static final String ATTRIBUTES_TYPE = "attributesType";

	static {
		identifier.put(IDENTIFIER_TYPE, IDENTIFIER_VALUE);
		attributes.put(ATTRIBUTES_TYPE, ATTRIBUTES_VALUE);
	}

	public static final Address address = new Address().withAddressType(addressType).withCountry(country)
			.withStateProvince(stateProvince).withCityVillage(cityVillage).withCountyDistrict(countryDistrict)
			.withSubDistrict(subDistrict).withTown(town);

	public static final String BASE_ENTITY_ID = "baseEntityId";

	public static final String DIFFERENT_BASE_ENTITY_ID = "differentBaseEntityId";

	public static final String FIRST_NAME = "firstName";

	public static final String MIDDLE_NAME = "middleName";

	public static final String LAST_NAME = "lastName";

	public static final boolean BIRTH_DATE_APPROX = true;

	public static final boolean DEATH_DATE_APPROX = false;

	public static final String FEMALE = "female";

	public static Client getClient() {
		Client client = new Client(BASE_ENTITY_ID, FIRST_NAME, MIDDLE_NAME, LAST_NAME, birthDate, deathDate,
				BIRTH_DATE_APPROX, DEATH_DATE_APPROX, FEMALE, Collections.singletonList(address), identifier, attributes);
		return client;
	}
}

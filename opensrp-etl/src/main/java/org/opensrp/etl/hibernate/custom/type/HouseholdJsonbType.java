package org.opensrp.etl.hibernate.custom.type;

import java.sql.Types;

import org.opensrp.etl.document.HouseholdDocument;

public class HouseholdJsonbType extends JsonType  {	

	@Override
	public Class<HouseholdDocument> returnedClass() {
		// TODO Auto-generated method stub
		return HouseholdDocument.class;
	}

	@Override
	public int[] sqlTypes() {
		// TODO Auto-generated method stub
		return new int[]{Types.JAVA_OBJECT};
	}

}

package org.opensrp.etl.hibernate.custom.type;

import java.sql.Types;

import org.opensrp.etl.document.ElcoDocument;

public class ElcoJsonbType extends JsonType  {	

	@Override
	public Class<ElcoDocument> returnedClass() {
		// TODO Auto-generated method stub
		return ElcoDocument.class;
	}

	@Override
	public int[] sqlTypes() {
		// TODO Auto-generated method stub
		return new int[]{Types.JAVA_OBJECT};
	}

}

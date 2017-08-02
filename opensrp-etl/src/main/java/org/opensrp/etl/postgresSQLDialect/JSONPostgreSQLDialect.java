package org.opensrp.etl.postgresSQLDialect;

import java.sql.Types;

import org.hibernate.dialect.PostgreSQLDialect;
import org.opensrp.register.mcare.domain.HouseHold;

public class JSONPostgreSQLDialect extends PostgreSQLDialect{
	public JSONPostgreSQLDialect() {
        this.registerColumnType(Types.JAVA_OBJECT, "jsonb");
    }

}

package org.opensrp.register.mcare.dump.type;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

public class HouseholdDocumentType implements UserType {
	
	
	@Override
	public Object assemble(Serializable arg0, Object arg1)
			throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable disassemble(Object arg0) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Object arg0, Object arg1) throws HibernateException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode(Object arg0) throws HibernateException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isMutable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void nullSafeSet(java.sql.PreparedStatement ps, Object value,
			int idx, SessionImplementor session) throws HibernateException,
			SQLException {
		 if (value == null) {
		        ps.setNull(idx, Types.OTHER);
		        return;
		    }
		    try {
		        final ObjectMapper mapper = new ObjectMapper();
		        final StringWriter w = new StringWriter();
		        mapper.writeValue(w, value);
		        w.flush();
		        ps.setObject(idx, w.toString(), Types.OTHER);
		    } catch (final Exception ex) {
		        throw new RuntimeException("Failed to convert Invoice to String: " + ex.getMessage(), ex);
		    }
		
	}

	@Override
	public Object replace(Object arg0, Object arg1, Object arg2)
			throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		// TODO Auto-generated method stub
		try {
			// use serialization to create a deep copy
	    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    	ObjectOutputStream oos = new ObjectOutputStream(bos);
	    	oos.writeObject(value);
	    	oos.flush();
	    	oos.close();
	    	bos.close();
	    	
	    	ByteArrayInputStream bais = new ByteArrayInputStream(bos.toByteArray());
	    	return new ObjectInputStream(bais).readObject();
	    } catch (ClassNotFoundException | IOException ex) {
	        throw new HibernateException(ex);
	    }
	}

	@Override
	public Object nullSafeGet(final ResultSet rs, final String[] names, final SessionImplementor session,
            final Object owner) throws HibernateException, SQLException{
		// TODO Auto-generated method stub
		final String cellContent = rs.getString(names[0]);
	    if (cellContent == null) {
	        return null;
	    }
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
	        return mapper.readValue(cellContent.getBytes("UTF-8"), returnedClass());
	    } catch (final Exception ex) {
	        throw new RuntimeException("Failed to convert String to Invoice: " + ex.getMessage(), ex);
	    }
	}

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

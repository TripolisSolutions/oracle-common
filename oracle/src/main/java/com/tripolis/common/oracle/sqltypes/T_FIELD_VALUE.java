package com.tripolis.common.oracle.sqltypes;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

import org.springframework.jdbc.core.SqlReturnType;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.support.nativejdbc.CommonsDbcpNativeJdbcExtractor;

public class T_FIELD_VALUE implements SqlTypeValue, SqlReturnType {

	public T_FIELD_VALUE() {
		super();
	}
	public T_FIELD_VALUE(BigInteger contactId, BigInteger field_desc_id, String value) {
		this.contactId = contactId;
		this.field_desc_id  = field_desc_id;
		this.value = value;
	}
	
	private BigInteger contactId;
	private BigInteger field_desc_id;
	private String value;
	
	public BigInteger getContactId() {
		return contactId;
	}
	public void setContactId(BigInteger contactId) {
		this.contactId = contactId;
	}
	
	public BigInteger getField_desc_id() {
		return field_desc_id;
	}
	public void setField_desc_id(BigInteger field_desc_id) {
		this.field_desc_id = field_desc_id;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	

	
	public Object[] getOracleRep() {
        return new Object[] {
    		getContactId(),
    		getField_desc_id(),
    		getValue()
        };
	}
	
	@Override
	public void setTypeValue(PreparedStatement ps, int paramIndex, int sqlType, String typeName) throws SQLException {
		Connection con = new CommonsDbcpNativeJdbcExtractor().getNativeConnection(ps.getConnection());
	    StructDescriptor itemDescriptor = new StructDescriptor(typeName, con);
	    STRUCT item = new STRUCT(itemDescriptor, con, getOracleRep());
	    ps.setObject(paramIndex, item);
	}
	@Override
	public Object getTypeValue(CallableStatement cs, int paramIndex,
			int sqlType, String typeName) throws SQLException {
		T_FIELD_VALUE value = getTypeValue((STRUCT) cs.getObject(paramIndex));
		this.contactId = value.getContactId();
		this.field_desc_id = value.getField_desc_id();
		this.value = value.getValue();
		return this;
	}

	
	public static T_FIELD_VALUE getTypeValue(STRUCT t_field_value) throws SQLException {
		Object[] values = t_field_value.getAttributes();
		T_FIELD_VALUE f = new T_FIELD_VALUE(((BigDecimal) values[0]).toBigInteger(), ((BigDecimal) values[1]).toBigInteger(), (String) values[2]);
		return f;

	}
}

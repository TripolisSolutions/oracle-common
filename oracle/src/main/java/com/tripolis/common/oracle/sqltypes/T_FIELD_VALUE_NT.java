package com.tripolis.common.oracle.sqltypes;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;

import org.springframework.jdbc.core.SqlReturnType;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.support.nativejdbc.CommonsDbcpNativeJdbcExtractor;

public class T_FIELD_VALUE_NT implements SqlReturnType, SqlTypeValue {
	private List<T_FIELD_VALUE> values;
	
	public T_FIELD_VALUE_NT() {
		super();
	}
	public T_FIELD_VALUE_NT(List<T_FIELD_VALUE> values) {

		this.values = values;
	}
	
	@Override
	public void setTypeValue(PreparedStatement ps, int paramIndex, int sqlType, String typeName) throws SQLException {
        Connection con =  new CommonsDbcpNativeJdbcExtractor().getNativeConnection(ps.getConnection());
        ArrayDescriptor des = ArrayDescriptor.createDescriptor("T_FIELD_VALUE_NT", con);
        List<Object[]> l = new ArrayList<Object[]>();
        for (T_FIELD_VALUE v : values) {
        	l.add(v.getOracleRep());
        }
        ARRAY a = new ARRAY(des, con, l.toArray());
        ps.setObject(paramIndex, (Object)a);
	}
	@Override
	public Object getTypeValue(CallableStatement cs, int paramIndex,
			int sqlType, String typeName) throws SQLException {
		List<T_FIELD_VALUE> entries = new ArrayList<T_FIELD_VALUE>();
		Array o = cs.getArray(paramIndex);
		Object[] o2 = (Object[]) o.getArray();
		for (Object o3 : o2) {
			entries.add(T_FIELD_VALUE.getTypeValue((STRUCT) o3));
		}
		this.values = entries;
		return this;
	}
	public List<T_FIELD_VALUE> getValues() {
		return values;
	}

}

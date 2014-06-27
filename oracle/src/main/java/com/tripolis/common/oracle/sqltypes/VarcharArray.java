package com.tripolis.common.oracle.sqltypes;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.support.nativejdbc.CommonsDbcpNativeJdbcExtractor;

public class VarcharArray implements SqlTypeValue, Serializable  {
		private String[] varchars;

		public VarcharArray(String[] array) {
			this.varchars = array;
		}
		public VarcharArray(ArrayList<String> array) {
			this.varchars = array.toArray(new String[0]);
		}

        public void setTypeValue(PreparedStatement cs, int index, int sqlType, String typeName) throws SQLException {
            Connection con =  new CommonsDbcpNativeJdbcExtractor().getNativeConnection(cs.getConnection());
            ArrayDescriptor des = ArrayDescriptor.createDescriptor(getTypename(), con);
            ARRAY a = new ARRAY(des, con, varchars);
            cs.setObject(index, (Object)a);
        }
        public static String getTypename() {
        	return "T_VARCHAR_4000_NT";
        }
        public String[] getVarchars() {
			return varchars;
		}
}

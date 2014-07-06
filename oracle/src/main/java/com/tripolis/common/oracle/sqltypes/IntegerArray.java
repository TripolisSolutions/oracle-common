package com.tripolis.common.oracle.sqltypes;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.support.nativejdbc.CommonsDbcpNativeJdbcExtractor;

public class IntegerArray implements SqlTypeValue, Serializable  {
		private Integer[] entityIds;

		public IntegerArray(Integer[] array) {
			this.entityIds = array;
		}
		public IntegerArray(List<Integer> array) {
			this.entityIds = array.toArray(new Integer[0]);
		}
		public IntegerArray(ArrayList<Integer> array) {
			this((List<Integer>) array);
		}

        public void setTypeValue(PreparedStatement cs, int index, int sqlType, String typeName) throws SQLException {
            Connection con =  new CommonsDbcpNativeJdbcExtractor().getNativeConnection(cs.getConnection());
            ArrayDescriptor des = ArrayDescriptor.createDescriptor(con.getSchema() + "." + getTypename(), con);
            ARRAY a = new ARRAY(des, con, entityIds);
            cs.setObject(index, (Object)a);
        }
        public static String getTypename() {
        	return "T_NUMBER";
        }
        public Integer[] getEntityIds() {
			return entityIds;
		}
        
        public int indexOf(int id) {
        	for (int i=0; i<this.getSize(); i++ ) {
        		if(this.entityIds[i] == id) {
        			return i;
        		}
        	}
        	return -1;
        }
        
        public int getSize() {
        	return this.entityIds.length;
        }
}

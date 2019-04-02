package com.sp.call;

public class Test {/*
	

	
	 @SuppressWarnings("deprecation")
	public static void main(String ar[]) throws Exception {

		 //https://stackoverflow.com/questions/26655331/pass-array-as-input-parameter-to-an-oracle-stored-procedure-using-simple-jdbc-ca/49854059#49854059
		 
		 Class.forName("oracle.jdbc.driver.OracleDriver");
		 Connection conn1 = DriverManager.getConnection("jdbc:oracle:thin:@//LX01S172.CORP.PAETEC.COM:1521/MTSVDEV", "SPI_ETL","Welcome123");
//		 	Connection conn1 =datasource.getConnection();
		 	System.out.println(conn1);
		 
		 	OracleConnection oraConn = getOracleConnection(conn1);
		 	
		 	System.out.println(oraConn);
		 
//		 	StructDescriptor structDes= new StructDescriptor("ngmssintegration.ot_interaction_condition", oraConn);
		 	
		 	
		 	InteractionCondition ics[] = new InteractionCondition[1];
		 	ics[0] = new InteractionCondition();
		 		SessionProperties sp[] = new SessionProperties[1];
			StructMapper<InteractionCondition> mapper1 = new BeanPropertyStructMapper<InteractionCondition>();
		 	Struct[] structArray = new Struct[ics.length];
		 	 
		 	int index=0;
		 	 for (InteractionCondition a : ics ) {
		 		 Object[] object=new Object[]{a.getCode(),a.getContext(),a.getMessage(),a.getSeverity(),a.getSi_interaction_condition_id()};
		 		Struct struct = oraConn.createStruct("ngmssintegration.ot_interaction_condition", object);
			                structArray[index]=struct;
			                index++;
		 	 }
		 	 
		 	 
		 	Array array = ((OracleConnection) oraConn).createOracleArray("NGMSSINTEGRATION.TT_INTERACTION_CONDITIONS", structArray);
		 	SessionProperties sesObj = new SessionProperties();
		 	
		 	
		 	private String system_userid="";
			private String schema_name="";
			private String bus_system_name="";
			private String userid="";
			private int segment_limit=0;//new BigDecimal("0");
			private int si_interaction_id=0;//new BigDecimal("0");
		 	
		 	Object[] sesPropsObj=new Object[]{sesObj.getSystem_userid(), sesObj.getSchema_name(),sesObj.getBus_system_name(),sesObj.getUserid(),sesObj.getSegment_limit(),sesObj.getSi_interaction_id()};
		 	  STRUCT sessionPropsStruct=new STRUCT(sessionPropsDesc ,oraConn, sesPropsObj);
		 	Struct sessionStruct = oraConn.createStruct("DATAMANAGEMENT.OT_SESSION_PROPERTIES", sesPropsObj);
		 	
		 	OracleCallableStatement stmt = (OracleCallableStatement) oraConn.
		 			prepareCall("BEGIN ossint.pkg_pi_portfolio_inventory.sp_pi_initiate_build(:i_cust_gaid,:i_ics,:i_session,:o_ics,:o_session,:o_portfolio_id); END;");
		 	stmt.setNUMBER("i_cust_gaid", new NUMBER(204962015));
		 	stmt.setArray("i_ics", array);
		 	stmt.setSTRUCT("i_session", (STRUCT)sessionStruct);
		 	stmt.registerOutParameter("o_ics", Types.ARRAY, "o_ics");
		 	stmt.registerOutParameter("o_session", Types.STRUCT, "o_session");
		 	stmt.registerOutParameter("o_portfolio_id", Types.INTEGER, "o_portfolio_id");
		 	
		 	
		 	stmt.execute();
		 	
		 	
		 	
		 	System.out.println("@@@@@@@@@@@@@@@@  : "+stmt.getInt("o_portfolio_id"));
		 	
		 	
	    }
	 
	 
	 public static OracleConnection getOracleConnection(Connection connection) throws SQLException {
		    OracleConnection oconn = null;
		    try {
//		        if (connection.isWrapperFor(oracle.jdbc.OracleConnection.class)) {
		            oconn = (OracleConnection) connection.unwrap(oracle.jdbc.OracleConnection.class)._getPC();
//		        }
		    } catch (SQLException e) {
		        throw e;
		    }
		    return oconn;
		}

*/}

package com.sp.call.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class InitiateBuildRequest {
	
    private int i_cust_gaid  ;
    private String i_system_userid ;//                     IN  VARCHAR2   -- 'WINFORCE' – Case sensitive, must be CAPS
    private String i_bus_system_name ;   //               IN  VARCHAR2   -- 'Winforce'  - case sensitives, must be fist letter Cap
    private String i_userid ; //                                 IN  VARCHAR2   --  the winforce login of the person that initiated the call  E#
   
    
    public InitiateBuildRequest() {
	}


	public InitiateBuildRequest(int i_cust_gaid, String i_system_userid, String i_bus_system_name, String i_userid) {
		this.i_cust_gaid = i_cust_gaid;
		this.i_system_userid = i_system_userid;
		this.i_bus_system_name = i_bus_system_name;
		this.i_userid = i_userid;
	}


	public int getI_cust_gaid() {
		return i_cust_gaid;
	}


	public void setI_cust_gaid(int i_cust_gaid) {
		this.i_cust_gaid = i_cust_gaid;
	}


	public String getI_system_userid() {
		return i_system_userid;
	}


	public void setI_system_userid(String i_system_userid) {
		this.i_system_userid = i_system_userid;
	}


	public String getI_bus_system_name() {
		return i_bus_system_name;
	}


	public void setI_bus_system_name(String i_bus_system_name) {
		this.i_bus_system_name = i_bus_system_name;
	}


	public String getI_userid() {
		return i_userid;
	}


	public void setI_userid(String i_userid) {
		this.i_userid = i_userid;
	}


	@Override
	public String toString() {
		return "InitiateBuildRequest [i_cust_gaid=" + i_cust_gaid + ", i_system_userid=" + i_system_userid
				+ ", i_bus_system_name=" + i_bus_system_name + ", i_userid=" + i_userid + "]";
	}
    
    

}

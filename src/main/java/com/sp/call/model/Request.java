package com.sp.call.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class Request {
 
    private int i_portfolio_id ;	//                          IN  NUMBER  
    private int i_cust_gaid ; //                             IN  NUMBER,   -- COMMs Customer Global Account ID
    private String i_system_userid ;//                      IN  VARCHAR2  -- 'WINFORCE'
    private String i_bus_system_name  ; //               IN  VARCHAR2  -- 'Winforce'
    private String i_userid   ; //                                  IN  VARCHAR2  --  the winforce login of the person that initiated the call  E#

	public Request() {
		// TODO Auto-generated constructor stub
	}

	public Request(int i_portfolio_id, int i_cust_gaid, String i_system_userid,
			String i_bus_system_name, String i_userid) {
		this.i_portfolio_id = i_portfolio_id;
		this.i_cust_gaid = i_cust_gaid;
		this.i_system_userid = i_system_userid;
		this.i_bus_system_name = i_bus_system_name;
		this.i_userid = i_userid;
	}

	public int getI_portfolio_id() {
		return i_portfolio_id;
	}

	public void setI_portfolio_id(int i_portfolio_id) {
		this.i_portfolio_id = i_portfolio_id;
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
		return "Request [i_portfolio_id=" + i_portfolio_id + ", i_cust_gaid=" + i_cust_gaid
				+ ", i_system_userid=" + i_system_userid + ", i_bus_system_name=" + i_bus_system_name + ", i_userid="
				+ i_userid + "]";
	}
	
	
	
}

package com.sp.call.model;

public class PortfolioResponse {
	
	private int system_interaction_id;
	private int portfolio_id;
	
	public PortfolioResponse(int system_interaction_id, int portfolio_id) {
		this.system_interaction_id = system_interaction_id;
		this.portfolio_id = portfolio_id;
	}
	
	public PortfolioResponse() {
		// TODO Auto-generated constructor stub
	}
	
	public int getSystem_interaction_id() {
		return system_interaction_id;
	}
	public void setSystem_interaction_id(int system_interaction_id) {
		this.system_interaction_id = system_interaction_id;
	}
	public int getPortfolio_id() {
		return portfolio_id;
	}
	public void setPortfolio_id(int portfolio_id) {
		this.portfolio_id = portfolio_id;
	}
	
	@Override
	public String toString() {
		return "PortfolioResponse [system_interaction_id=" + system_interaction_id + ", portfolio_id=" + portfolio_id
				+ "]";
	}

}

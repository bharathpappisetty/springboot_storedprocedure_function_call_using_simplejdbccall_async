package com.sp.call.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="PI_PORTFOLIO_JSON",schema="OSSINT")
public class Portfolio { 
	
	@Id
	@Column(name="ITEM_ID")
	private Integer itemId;
	
	@Column(name="PORTFOLIO_ID")
	private Integer portfolioId;
	
	@Column(name="CUST_GAID")
	private Integer custGaId;
	
	@Column(name="LOC_GAID")
	private Integer locGaId;
	
	@Column(name="JSON")
	@Lob
	private String json;
	
	
	
	public Portfolio() {
		// TODO Auto-generated constructor stub
	}



	public Portfolio(Integer itemId, Integer portfolioId, Integer custGaId, Integer locGaId, String json) {
		this.itemId = itemId;
		this.portfolioId = portfolioId;
		this.custGaId = custGaId;
		this.locGaId = locGaId;
		this.json = json;
	}



	public Integer getItemId() {
		return itemId;
	}



	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}



	public Integer getPortfolioId() {
		return portfolioId;
	}



	public void setPortfolioId(Integer portfolioId) {
		this.portfolioId = portfolioId;
	}



	public Integer getCustGaId() {
		return custGaId;
	}



	public void setCustGaId(Integer custGaId) {
		this.custGaId = custGaId;
	}



	public Integer getLocGaId() {
		return locGaId;
	}



	public void setLocGaId(Integer locGaId) {
		this.locGaId = locGaId;
	}



	public String getJson() {
		return json;
	}



	public void setJson(String json) {
		this.json = json;
	}



	@Override
	public String toString() {
		return "Portfolio [itemId=" + itemId + ", portfolioId=" + portfolioId + ", custGaId=" + custGaId + ", locGaId="
				+ locGaId + ", json=" + json + "]";
	}

	

	

	
	
	
	
	

}

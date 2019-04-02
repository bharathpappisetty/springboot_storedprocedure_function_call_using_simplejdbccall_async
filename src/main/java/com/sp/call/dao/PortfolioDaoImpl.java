package com.sp.call.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sp.call.model.Portfolio;

@Transactional
@Repository
public class PortfolioDaoImpl {
	public static Logger logger = LoggerFactory.getLogger(PortfolioDaoImpl.class);
	
	@PersistenceContext
	@Autowired
	private EntityManager em;
	
	
	public List<Portfolio> getPortfolio(Integer portfolioId) {
		logger.debug("@@@ fetching porfolio details..");
		TypedQuery<Portfolio> query = em.createQuery("SELECT p FROM Portfolio p WHERE p.portfolioId = ?1", Portfolio.class);
		query.setParameter(1, portfolioId);
	    return query.getResultList();
	}
	
	

}

package com.template.appname.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.template.appname.entity.Customer;

@Repository
public class CustomerDAOHibernateImpl implements CustomerDAO {

	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public List<Customer> getCustomers() {
		
		// get the current Hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// create a query
		Query<Customer> query = currentSession.createQuery("from Customer", Customer.class);
		
		// execute query and get result list
		List<Customer> customers = query.getResultList();
		
		// return the results
		return customers;
	}

	@Override
//	@Transactional
//	public Customer getCustomerById(String id) {
//		// get the current Hibernate session
//		Session currentSession = sessionFactory.getCurrentSession();
//		
//		// create a query
//		Customer customer = currentSession.get(Customer.class, id);
//		
//		// return the results
//		return customer;
//	}
	
	public Customer getCustomerById(String id) {
		Customer customer = new Customer();
		
		customer.setCountry("Brasil");
		customer.setTransactionCount(2);
		customer.setFirstName("Foo");
		customer.setLastName("Bar");
		return customer;
	}

}

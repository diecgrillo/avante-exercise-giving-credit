package com.template.appname.dao;

import java.util.List;

import com.template.appname.entity.Customer;

public interface CustomerDAO {

	public List<Customer> getCustomers();
	public Customer getCustomerById(String id);
	
}

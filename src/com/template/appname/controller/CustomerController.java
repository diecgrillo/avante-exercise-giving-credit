package com.template.appname.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.template.appname.dao.CustomerDAO;
import com.template.appname.entity.Customer;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	// need to inject the customer dao
	@Autowired
	private CustomerDAO customerDAO;
	
	@RequestMapping("/showForm")
	public String showForm() {
		return "customer-form";
	}
	
	@RequestMapping("/processForm")
	public String processForm(@ModelAttribute("customerId") String id, Model model) {

		Customer customerFromDB = customerDAO.getCustomerById(id);
		
		String risk = determineCustomerRisk(customerFromDB).getRisk();
		
		model.addAttribute("customer", customerFromDB);
		model.addAttribute("risk", risk);
		
		return "customer-confirmation";
		
	}
	
	private RiskModelColor determineCustomerRisk(Customer customer) {
	
		// TODO define rules in configuration file
		
		int rulesCount=0;
		RiskModelColor risk;
		
		if(customer == null) {
			return RiskModelColor.RED;
		}
		
		// A client must be from Brazil
		if (customer.getCountry() != null &&
				customer.getCountry().equals("Brasil")) {
			rulesCount++;
		}
		
		// A client must have at least 3 transactions (sent or received)
		if (customer.getTransactionCount() >= 3) {
			rulesCount++;
		}
		
		// We have a record of the client’s phone number
		if (customer.getPhoneNumber() != null 
				&& !customer.getPhoneNumber().isEmpty()) {
			rulesCount++;
		}
		
		// The client verified his/her identity
		if (customer.isIdentityChecked()) {
			rulesCount++;
		}
		
		// The client is from either Sao Paulo or Rio
		if (customer.getState() != null 
				&& (customer.getState().equals("São Paulo") 
					|| customer.getState().equals("Rio de Janeiro"))) {
			rulesCount++;
		}
		
		/*
		 * If a client complies with at least 3 rules, then the client is marked ​ GREEN​ .
		 * If a client complies with less than 3 rules but more than 1 rules - client is ​ YELLOW​ .
		 * Otherwise - client is ​ RED​ .
		 */
		if(rulesCount >= 3) {
			risk = RiskModelColor.GREEN;
		} else if(rulesCount >= 1 ) {
			risk = RiskModelColor.YELLOW;
		} else {
			risk = RiskModelColor.RED;
		}
		
		return risk;
	}
	
	@RequestMapping("/list")
	public String listCustomers(Model model) {
		
		// get the customers from the dao
		List<Customer> customers = customerDAO.getCustomers();
		
		// add the customers to the model
		model.addAttribute("customers", customers);
		
		return "list-customers";
	}

}

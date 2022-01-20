package com.zmd.springdemo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zmd.springdemo.dao.CustomerDAO;
import com.zmd.springdemo.entity.Customer;
import com.zmd.springdemo.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	// need to inject customer dao
	@Autowired
	private CustomerDAO customerDAO;
	
	@Override
	@Transactional
	public List<Customer> getCustomers() {
		return customerDAO.getCustomers();
	}

	@Override
	@Transactional
	public void saveCustomer(Customer customer) {
		
		customerDAO.save(customer);
	}

	@Override
	@Transactional
	public Customer getCustomer(int id) {
		
		return customerDAO.getCustomer(id);
	}

	@Override
	@Transactional
	public void deleteCustomer(int id) {
		
		customerDAO.deleteCustomer(id);
	}
	
	@Override
    @Transactional
    public List<Customer> searchCustomers(String theSearchName) {
        return customerDAO.searchCustomers(theSearchName);
    }

}

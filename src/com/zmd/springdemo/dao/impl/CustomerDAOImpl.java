package com.zmd.springdemo.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zmd.springdemo.dao.CustomerDAO;
import com.zmd.springdemo.entity.Customer;
import com.zmd.springdemo.util.SortUtils;

@Repository
public class CustomerDAOImpl implements CustomerDAO {
	
	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Customer> getCustomers(int sortField) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// determine sort field
		String fieldName = null;
		
		switch (sortField) {
			case SortUtils.FIRST_NAME:
				fieldName = "firstName";
				break;
			case SortUtils.LAST_NAME:
				fieldName = "lastName";
				break;
			case SortUtils.EMAIL:
				fieldName = "email";
				break;
			default:
				// if nothing matches the default to sort by lastName
				fieldName = "lastName";
		}
		
		// create a query
		Query<Customer> query = currentSession.createQuery("from Customer order by " + fieldName, Customer.class);
		
		// execute query and get result list
		List<Customer> customers = query.getResultList();
		
		// return the results
		return customers;
	}

	@Override
	public void save(Customer customer) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// save the customer
		currentSession.saveOrUpdate(customer);
		
	}

	@Override
	public Customer getCustomer(int id) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// now retrieve/read from database using the primary key
		Customer customer = currentSession.get(Customer.class, id);
		
		return customer;
	}

	@Override
	public void deleteCustomer(int id) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// delete object with primary key
		Query<Customer> query = currentSession.createQuery("delete from Customer where id=:id");
		query.setParameter("id", id);
		
		query.executeUpdate();
	}
	
	@Override
    public List<Customer> searchCustomers(String theSearchName) {
        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();
        
        Query theQuery = null;
        
        //
        // only search by name if theSearchName is not empty
        //
        if (theSearchName != null && theSearchName.trim().length() > 0) {
            // search for firstName or lastName ... case insensitive
            theQuery = currentSession.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName order by lastName, firstName", Customer.class);
            theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");
        }
        else {
            // theSearchName is empty ... so just get all customers
            theQuery = currentSession.createQuery("from Customer", Customer.class);            
        }
        
        // execute query and get result list
        List<Customer> customers = theQuery.getResultList();
                
        // return the results        
        return customers;
        
    }

	
}

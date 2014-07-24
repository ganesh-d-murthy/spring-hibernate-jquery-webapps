package com.aplus.portal.customer.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aplus.portal.customer.bo.Customer;
import com.aplus.portal.customer.dao.CustomerDao;


public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerDao customerDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int saveCustomer(Customer customer) {
		return customerDao.saveCustomer(customer);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Customer> getCustomersByYear(int year, int start, int limit) {		
		return customerDao.getCustomers("", start, limit);
	}
	
	@Override
	@Transactional(readOnly = true)	
	public Customer getCustomer(Integer id) {
		return customerDao.getCustomer(id);
	}

	@Override
	@Transactional(readOnly = true)	
	public Integer getCustomersCount() {
		return customerDao.getCustomersCount();
	}
	
	@Override
	@Transactional(readOnly = true)	
	public List<Customer> getCustomers(String firstName, int year, int start, int limit) {
		return customerDao.getCustomers(year, firstName, start, limit);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int deleteCustomer(Integer id) {		
		return customerDao.deleteCustomer(id);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateCustomer(Customer customer) {
		customerDao.updateCustomer(customer);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void uploadFile(InputStream inputStream, String fileName,
			String contentType, Integer id) {
		customerDao.uploadFile(inputStream, fileName, contentType, id);
	}

}

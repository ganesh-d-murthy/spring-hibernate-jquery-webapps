package com.aplus.portal.customer.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.aplus.portal.customer.bo.Customer;
import com.aplus.portal.customer.dao.CustomerDao;


public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerDao customerDao;

	@Override
	public int saveCustomer(Customer customer) {
		return customerDao.saveCustomer(customer);
	}

	@Override
	public List<Customer> getCustomersByYear(int year, int start, int limit) {		
		return customerDao.getCustomers("", start, limit);
	}
	
	@Override
	public Customer getCustomer(Integer id) {
		return customerDao.getCustomer(id);
	}

	@Override
	public Integer getCustomersCount() {
		return customerDao.getCustomersCount();
	}
	
	@Override
	public List<Customer> getCustomers(String firstName, int year, int start, int limit) {
		return customerDao.getCustomers(year, firstName, start, limit);
	}

	@Override
	public int deleteCustomer(Integer id) {		
		return customerDao.deleteCustomer(id);
	}
	
	@Override
	public void updateCustomer(Customer customer) {
		customerDao.updateCustomer(customer);
	}
	
	@Override
	public void uploadFile(InputStream inputStream, String fileName,
			String contentType, Integer id) {
		customerDao.uploadFile(inputStream, fileName, contentType, id);
	}

}

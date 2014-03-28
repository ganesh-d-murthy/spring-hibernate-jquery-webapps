package com.aplus.portal.customer.dao;

import java.io.InputStream;
import java.util.List;

import com.aplus.portal.customer.bo.Customer;

public interface CustomerDao {
	
	public int saveCustomer(Customer customer);
	
	public Customer getCustomer(Integer id);
	
	public List<Customer> getCustomers(String firstname, int start, int limit);
	
	public int getCustomersCount();

	public List<Customer> getCustomers(int year, String firstName, int start, int limit);
	
	public int deleteCustomer(Integer id);
	
	public int updateCustomer(Customer customer);
	
	public void uploadFile(InputStream inputStream, String fileName,
			String fileType, Integer id);
}

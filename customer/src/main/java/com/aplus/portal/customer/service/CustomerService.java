/**
 * 
 */
package com.aplus.portal.customer.service;

import java.io.InputStream;
import java.util.List;

import com.aplus.portal.customer.bo.Customer;

public interface CustomerService {
	
	public int saveCustomer(Customer customer);
	
	public List<Customer> getCustomersByYear(int year, int start, int limit);
	
	public Customer getCustomer(Integer id);
	
	public Integer getCustomersCount();
	
	public List<Customer> getCustomers(String firstName, int year, int start, int limit);
	
	public int deleteCustomer(Integer id);
	
	public void updateCustomer(Customer customer);
	
	public void uploadFile(InputStream inputStream, String fileName, String contentType, Integer id);

}

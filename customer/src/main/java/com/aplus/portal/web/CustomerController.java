package com.aplus.portal.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aplus.portal.customer.bo.Customer;
import com.aplus.portal.customer.service.CustomerService;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@RequestMapping(value = "/getallcustomers", produces = "application/json")
	public @ResponseBody
	Map<String, Object> index(@RequestParam("jtStartIndex") int startIndex,
			@RequestParam("jtPageSize") int pageSize,
			@RequestParam(value = "firstName", defaultValue = "") String name,
			@RequestParam(value = "year", defaultValue = "") String searchYear) {
		int year = 0;
		try {
			year = Integer.parseInt(searchYear);
		} catch (Exception e) {
			System.out.println(e);
		}
		List<Customer> customers = customerService.getCustomers(name, year,
				startIndex, pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		int userCount = customerService.getCustomersCount();
		map.put("Result", "OK");
		map.put("TotalRecordCount", userCount);
		map.put("Records", customers);
		return map;
	}

	@RequestMapping(value = "/createcustomer", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(
			@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName,
			@RequestParam("doj") String doj) {
		Customer customer = new Customer();
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			customer.setDoj(dateFormat.parse(doj));
		} catch (ParseException e1) {
			e1.printStackTrace();
			map.put("Result", "ERROR");
			map.put("Message", "Save customer failed. Try again");
			return map;
		}
		try {
			customerService.saveCustomer(customer);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("Result", "ERROR");
			map.put("Message", "Save customer failed. Try again");
			return map;
		}
		map.put("Result", "OK");
		map.put("Record", customer);
		return map;
	}

	@RequestMapping("/filedownload")
	public String download(@RequestParam("id") Integer customerId,
			HttpServletResponse response) {

		Customer cust = customerService.getCustomer(customerId);
		OutputStream out = null;
		try {
			response.setHeader("Content-Disposition", "inline;filename=\""
					+ cust.getFileName() + "\"");
			out = response.getOutputStream();
			response.setContentType(cust.getContentType());
			IOUtils.copy(cust.getContent().getBinaryStream(), out);
			out.flush();
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {

				}
			}
		}
		return null;
	}

	@RequestMapping(value = "/deletecustomer", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam("id") String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			customerService.deleteCustomer(Integer.parseInt(id));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("Result", "ERROR");
			map.put("Message", "Delete customer failed. Try again");
			return map;
		}
		map.put("Result", "OK");
		return map;
	}

	@RequestMapping(value = "/updatecustomer", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, Object> update(
			@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName,
			@RequestParam("doj") String doj, @RequestParam("id") Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		Customer customer = new Customer();
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		try {
			customer.setDoj(dateFormat.parse(doj));
		} catch (ParseException e) {
			e.printStackTrace();
			map.put("Result", "ERROR");
			map.put("Message", "Update customer failed. Try again");
			return map;
		}
		customer.setId(id);
		customerService.updateCustomer(customer);
		map.put("Result", "OK");
		map.put("Record", customer);
		return map;
	}

	@RequestMapping(value = "/fileupload", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, Object> uploadFile(
			@RequestParam("file") MultipartFile multipartFile,
			@RequestParam("id") Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (multipartFile == null) {
			map.put("Result", "ERROR");
			map.put("Message", "File Upload failed. Try again");
			return map;
		}
		String fileName = multipartFile.getOriginalFilename();
		String fileType = multipartFile.getContentType();
		System.out.println(fileName + " " + multipartFile.getSize());
		InputStream inputStream = null;
		try {
			inputStream = multipartFile.getInputStream();
		} catch (IOException e) {
			map.put("Result", "ERROR");
			map.put("Message", "File Upload failed. Try again");
			return map;
		}
		if (inputStream == null) {
			map.put("Result", "ERROR");
			map.put("Message", "File Upload failed. Try again");
			return map;
		}
		customerService.uploadFile(inputStream, fileName, fileType, id);
		map.put("Result", "OK");
		return map;
	}
}

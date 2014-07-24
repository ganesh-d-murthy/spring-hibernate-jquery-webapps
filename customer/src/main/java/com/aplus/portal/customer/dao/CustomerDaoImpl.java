/**
 * 
 */
package com.aplus.portal.customer.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aplus.portal.customer.bo.Customer;

@Repository
public class CustomerDaoImpl implements CustomerDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public int saveCustomer(Customer customer) {
		Session session = sessionFactory.getCurrentSession();
		customer.setUpdateTime(Calendar.getInstance().getTime());
		return (Integer) session.save(customer);
	}

	@Override

	public Customer getCustomer(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		return (Customer) session.get(Customer.class, id);
	}

	@Override
	public List<Customer> getCustomers(String firstname, int start, int limit) {
		Session session = sessionFactory.getCurrentSession();
		List<Customer> customers = session.createQuery("from Customer").list();
		return customers;
	}

	@Override
	public int getCustomersCount() {
		Session session = sessionFactory.getCurrentSession();
		Number count = (Number) session.createQuery(
				"select count(*) from Customer").uniqueResult();
		return ((Long) count).intValue();
	}

	@Override
	public List<Customer> getCustomers(int year, String firstName, int start,
			int limit) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Customer.class);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("firstName"), "firstName")
				.add(Projections.property("lastName"), "lastName")
				.add(Projections.property("doj"), "doj")
				.add(Projections.property("updateTime"), "updateTime")
				.add(Projections.property("contentType"), "contentType")
				.add(Projections.property("fileName"), "fileName")
				.add(Projections.property("id"), "id")).setResultTransformer(
                        Transformers.aliasToBean(Customer.class));;
		if (firstName != null && !firstName.trim().isEmpty()) {
			criteria.add(Restrictions.ilike("firstName", firstName,
					MatchMode.START));
		}
		if (year > 0) {
			criteria.add(Restrictions.sqlRestriction("YEAR(doj) = ? ", year,
					Hibernate.INTEGER));
		}
		criteria.setFirstResult(start);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	@Override
	public int deleteCustomer(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("delete from Customer where id=:id");
		query.setInteger("id", id);
		return query.executeUpdate();
	}

	@Override
	public int updateCustomer(Customer customer) {
		Session session = sessionFactory.getCurrentSession();
		Customer existingCustomer = (Customer) session.get(Customer.class,
				customer.getId());
		if (existingCustomer == null) {
			return 0;
		}
		boolean update = false;
		if (customer.getFirstName() != null
				&& !customer.getFirstName().trim().isEmpty()) {
			existingCustomer.setFirstName(customer.getFirstName());
			update = true;
		}

		if (customer.getLastName() != null
				&& !customer.getLastName().trim().isEmpty()) {
			existingCustomer.setLastName(customer.getLastName());
			update = true;
		}

		if (customer.getFileName() != null
				&& !customer.getFileName().trim().isEmpty()) {
			existingCustomer.setFileName(customer.getFileName());
			update = true;
		}

		if (customer.getDojAsDate() != null
				&& !customer.getDojAsDate().equals(
						existingCustomer.getDojAsDate())) {
			existingCustomer.setDoj(customer.getDojAsDate());
		}

		if (update) {
			existingCustomer.setUpdateTime(Calendar.getInstance().getTime());
			session.update(existingCustomer);
		}
		return 0;
	}

	@Override
	public void uploadFile(InputStream inputStream, String fileName,
			String contentType, Integer id) {
		Blob blob = null;
		try {
			blob = Hibernate.createBlob(inputStream);
		} catch (IOException e) {
			throw new RuntimeException("Exception while making blob");
		}

		if (blob == null) {
			throw new RuntimeException("Exception while making blob");
		}

		Session session = sessionFactory.getCurrentSession();
		Customer existingCustomer = (Customer) session.get(Customer.class, id);
		if (existingCustomer == null) {
			throw new RuntimeException("Exception while getting customer");
		}

		existingCustomer.setContent(blob);
		existingCustomer.setFileName(fileName);
		existingCustomer.setContentType(contentType);
		session.update(existingCustomer);

	}

}

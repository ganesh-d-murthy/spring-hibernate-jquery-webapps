package com.aplus.portal.customer.bo;

import java.sql.Blob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="customer")
@JsonIgnoreProperties({"updateTime", "content"})
public class Customer {

	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;

	@Column(name="firstname")
	private String firstName;

	@Column(name="lastname")
	private String lastName;
	
	@Column(name="content")
	@Lob
	private Blob content;

	@Column(name="doj")
	private Date doj;

	@Column(name="updatetime")
	private Date updateTime;
	
	@Column(name="content_type")
	private String contentType;
	
	@Column(name="filename")
	private String fileName;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDoj() {
		return "/Date("+ doj.getTime() + ")/";
	}
	
	public Date getDojAsDate() {
		return doj;
	}

	public void setDoj(Date doj) {
		this.doj = doj;
	}

	public String getUpdateTime() {
		return "/Date("+ updateTime.getTime() + ")/";
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Blob getContent() {
		return content;
	}

	public void setContent(Blob content) {
		this.content = content;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}

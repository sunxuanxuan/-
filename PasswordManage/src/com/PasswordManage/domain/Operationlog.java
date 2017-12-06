package com.PasswordManage.domain;

import java.util.Date;
/**
 * 
 * 日志类，记录操作；
 * 
 */
public class Operationlog {

	private String id;
	private String operation_type;
	private String operation_object;//操作对象;
	private Date operation_date;
	private String operator;//操作者;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOperation_type() {
		return operation_type;
	}
	public void setOperation_type(String operation_type) {
		this.operation_type = operation_type;
	}
	public String getOperation_object() {
		return operation_object;
	}
	public void setOperation_object(String operation_object) {
		this.operation_object = operation_object;
	}
	public Date getOperation_date() {
		return operation_date;
	}
	public void setOperation_date(Date operation_date) {
		this.operation_date = operation_date;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
		
}

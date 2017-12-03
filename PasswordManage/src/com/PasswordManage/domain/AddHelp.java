package com.PasswordManage.domain;

import java.util.Date;
/**
 * 
 * 辅助添加操作的类，无实际意义；
 *
 */
public class AddHelp {

	private String type1;
	private String type2;
	private String username;
	private String password;
	private String creator;
	private String item_name;
	private String password_lifelength;
	private Date password_expired_date;
	private String ip_address;
	private String ssh_port;
	public String getType1() {
		return type1;
	}
	public void setType1(String type1) {
		this.type1 = type1;
	}
	public String getType2() {
		return type2;
	}
	public void setType2(String type2) {
		this.type2 = type2;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getPassword_lifelength() {
		return password_lifelength;
	}
	public void setPassword_lifelength(String password_lifelength) {
		this.password_lifelength = password_lifelength;
	}
	public Date getPassword_expired_date() {
		return password_expired_date;
	}
	public void setPassword_expired_date(Date password_expired_date) {
		this.password_expired_date = password_expired_date;
	}
	public String getIp_address() {
		return ip_address;
	}
	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}
	public String getSsh_port() {
		return ssh_port;
	}
	public void setSsh_port(String ssh_port) {
		this.ssh_port = ssh_port;
	}
	
}

package com.PasswordManage.domain;

import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
/**
 * 
 * 生成映射文件时注意懒加载问题；
 *
 */
public class Pm_item {

	private String id;
	private String type1;//主机或数据库;
	private String username;//用户名;
	private String password;
	private Date create_date;//记录创建时间;
	private Date password_expired_date;//密码到期时间;
	private String type2;//主机类型或者数据库类型;
	private int password_lifelength;
	private String password_status;
	private String item_name;
	private Pm_user creator;
	private String ip_address;
	private int ssh_port;

	public int getSsh_port() {
		return ssh_port;
	}
	public void setSsh_port(int ssh_port) {
		this.ssh_port = ssh_port;
	}
	public Pm_user getCreator() {
		return creator;
	}
	public void setCreator(Pm_user creator) {
		this.creator = creator;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getIp_address() {
		return ip_address;
	}
	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}
	
	public String getPassword_status() {
		return password_status;
	}
	public void setPassword_status(String password_status) {
		this.password_status = password_status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType1() {
		return type1;
	}
	public void setType1(String type1) {
		this.type1 = type1;
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
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public String getType2() {
		return type2;
	}
	public void setType2(String type2) {
		this.type2 = type2;
	}
	public Date getPassword_expired_date() {
		return password_expired_date;
	}
	public void setPassword_expired_date(Date password_expired_date) {
		this.password_expired_date = password_expired_date;
	}
	public int getPassword_lifelength() {
		return password_lifelength;
	}
	public void setPassword_lifelength(int password_lifelength) {
		this.password_lifelength = password_lifelength;
	}
	
}

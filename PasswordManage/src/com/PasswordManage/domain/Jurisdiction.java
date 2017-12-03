package com.PasswordManage.domain;
/**
 * 
 * 权限类，记录了权限名，权限等级和权限可执行的各种操作；（未实现）
 *
 */
public class Jurisdiction {

	private String id;
	private String name;//权限名;
	private int leval;//权限等级;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLeval() {
		return leval;
	}
	public void setLeval(int leval) {
		this.leval = leval;
	}
	
}

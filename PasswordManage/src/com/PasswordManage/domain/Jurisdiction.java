package com.PasswordManage.domain;
/**
 * 
 * Ȩ���࣬��¼��Ȩ������Ȩ�޵ȼ���Ȩ�޿�ִ�еĸ��ֲ�������δʵ�֣�
 *
 */
public class Jurisdiction {

	private String id;
	private String name;//Ȩ����;
	private int leval;//Ȩ�޵ȼ�;
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

package com.PasswordManage.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.aspectj.util.FileUtil;

import com.PasswordManage.domain.AddHelp;
import com.PasswordManage.service.ManageService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;


public class ManageAction extends ActionSupport implements ModelDriven<AddHelp> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ManageService manageService;
	private Map<String,Object> result=new HashMap<String,Object>();
	private String show_type2;
	private AddHelp addhelp=new AddHelp();
	private String delete_list;
	private String batch_out;
	private InputStream inputStream;
	private File batch_in;
	private String batch_inFileName;
    private String q_item_name;
    private String q_username;
    private String q_ip_address;
    private String q_password_status;
    private String q_type2;
	//获取模型驱动对象
	@Override
	public AddHelp getModel() {
		// TODO Auto-generated method stub
		return addhelp;
	}
	//Action获取的数据
	
	public void setResult(Map<String, Object> result) {
		this.result = result;
	}

	public String getQ_type2() {
		return q_type2;
	}

	public void setQ_type2(String q_type2) {
		this.q_type2 = q_type2;
	}

	public String getQ_item_name() {
		return q_item_name;
	}

	public void setQ_item_name(String q_item_name) {
		this.q_item_name = q_item_name;
	}

	public String getQ_username() {
		return q_username;
	}

	public void setQ_username(String q_username) {
		this.q_username = q_username;
	}

	public String getQ_ip_address() {
		return q_ip_address;
	}

	public void setQ_ip_address(String q_ip_address) {
		this.q_ip_address = q_ip_address;
	}

	public String getQ_password_status() {
		return q_password_status;
	}

	public void setQ_password_status(String q_password_status) {
		this.q_password_status = q_password_status;
	}

	public String getBatch_inFileName() {
		return batch_inFileName;
	}

	public void setBatch_inFileName(String batch_inFileName) {
		this.batch_inFileName = batch_inFileName;
	}

	public File getBatch_in() {
		return batch_in;
	}

	public void setBatch_in(File batch_in) {
		this.batch_in = batch_in;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getBatch_out() {
		return batch_out;
	}

	public void setBatch_out(String batch_out) {
		this.batch_out = batch_out;
	}

	public String getDelete_list() {
		return delete_list;
	}

	public void setDelete_list(String delete_list) {
		this.delete_list = delete_list;
	}

	public Map<String, Object> getResult() {
		return result;
	}

	public void setShow_type2(String show_type2) {
		this.show_type2 = show_type2;
	}

	public String getShow_type2() {
		return show_type2;
	}

	public void setManageService(ManageService manageService) {
		this.manageService = manageService;
	}
	
	//Action函数
	public String count(){
		result=manageService.count();
		return "count";
	}
	public String show(){
		result=manageService.show(show_type2);
		return "show";
	}
	public String add() {
		String re=manageService.add(addhelp);
		result.put("result", re);
		return "add";
	}
    public String delete(){
    	manageService.delete(delete_list);
    	result.put("result","success");
    	return "delete";
    }
    public String update(){
    	manageService.update(addhelp);
    	result.put("result","success");
    	return "update";
    }
    public String batch_out() {
    	manageService.batch_out(batch_out);
    	File f=new File("Batch_out.xls");
    	if(f.exists()){
    		try{
    			inputStream=new FileInputStream(f);
    		}catch(Exception e){
    			System.out.println(e.toString());
    		}
    		f.delete();
    	}
    	return "batch_out";
	}
    public String batch_in() {
    	String desPath=ServletActionContext.getServletContext().getRealPath("/document");
    	File destFile=new File(desPath,batch_inFileName);
    	try{
    		FileUtils.copyFile(batch_in,destFile);
    	}catch(Exception e){
    		System.out.println(e.toString());
    	}
    	manageService.batch_in(destFile,batch_inFileName);
		result.put("result","success");
    	return "batch_in";
	}
    public String query() {
		result=manageService.query(q_ip_address, q_item_name, q_password_status, q_username,q_type2);
    	return "query";
	}
}

package com.PasswordManage.action;

import java.util.HashMap;
import java.util.Map;

import com.PasswordManage.domain.AddHelp;
import com.PasswordManage.domain.Pm_item;
import com.PasswordManage.service.ManageService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;

public class ManageAction extends ActionSupport implements ModelDriven<AddHelp> {

	private ManageService manageService;
	private Map<String,Object> result=new HashMap<String,Object>();
	private String show_type2;
	private AddHelp addhelp=new AddHelp();
	private String delete_list;
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
}

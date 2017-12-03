package com.PasswordManage.action;

import java.rmi.server.SocketSecurityException;

import com.PasswordManage.domain.Pm_user;
import com.PasswordManage.domain.Verification;
import com.PasswordManage.service.LoginService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class LoginAction extends ActionSupport implements ModelDriven<Verification> {
    
	private Verification verification=new Verification();
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Verification getModel() {
		// TODO Auto-generated method stub
		return verification;
	}
    
	private LoginService loginService;

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public String login(){
		Pm_user existUser=loginService.login(verification);
		if(existUser==null){
		    ActionContext.getContext().put("error","’À∫≈ªÚ√‹¬Î¥ÌŒÛ");
			return INPUT;
		}else{
			ActionContext.getContext().put("user", existUser);
			return SUCCESS;
		}
	}
	public String logout(){
		return "logout";
	}
	public String register(){
		String result=loginService.register(verification,name);
		ActionContext.getContext().put("result", result);
		return "register";
	}
}

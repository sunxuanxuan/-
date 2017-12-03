package com.PasswordManage.action;

import java.security.KeyStore.PrivateKeyEntry;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.bag.SynchronizedSortedBag;

import com.PasswordManage.domain.Pm_user;
import com.PasswordManage.service.FirstPageService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;

public class FirstPageAction extends ActionSupport{
     
	private String  account;
    private String  password;
    private Pm_user pm_user;
    private FirstPageService firstPageService;

	public FirstPageService getFirstPageService() {
		return firstPageService;
	}

	public void setFirstPageService(FirstPageService firstPageService) {
		this.firstPageService = firstPageService;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String  jump() {
		pm_user=firstPageService.jump(account, password);
		ActionContext.getContext().put("user", pm_user);
		return "jump_success";
	}
}

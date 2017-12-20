package com.PasswordManage.service;

import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.transaction.annotation.Transactional;

import com.PasswordManage.dao.LoginDao;
import com.PasswordManage.domain.Pm_user;
import com.PasswordManage.domain.Verification;

@Transactional
public class LoginServiceImpl implements LoginService {
    
	private LoginDao loginDao;

	public LoginDao getLoginDao() {
		return loginDao;
	}

	public void setLoginDao(LoginDao loginDao) {
		this.loginDao = loginDao;
	}

	@Override
	public Pm_user login(Verification verification) {
		// TODO Auto-generated method stub
		Pm_user existUser=loginDao.findDao(verification);
		return existUser;
	}

	@Override
	public String register(Verification verification, String name) {
		// TODO Auto-generated method stub
		String  result=loginDao.addUser(verification,name);
		return result;
	}

}

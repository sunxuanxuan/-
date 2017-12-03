package com.PasswordManage.service;

import java.util.Map;

import com.PasswordManage.dao.FirstPageDao;
import com.PasswordManage.domain.Pm_user;

public class FirstPageServiceImpl implements FirstPageService {
    private FirstPageDao firstPageDao;
    
	public FirstPageDao getFirstPageDao() {
		return firstPageDao;
	}

	public void setFirstPageDao(FirstPageDao firstPageDao) {
		this.firstPageDao = firstPageDao;
	}

	@Override
	public Pm_user jump(String account, String password) {
		// TODO Auto-generated method stub
		Pm_user pm_user=firstPageDao.find_pm_user(account,password);
		return pm_user;
	}

}

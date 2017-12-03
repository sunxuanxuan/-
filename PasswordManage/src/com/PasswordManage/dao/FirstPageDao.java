package com.PasswordManage.dao;

import java.util.Map;

import com.PasswordManage.domain.Pm_user;

public interface FirstPageDao {

	Pm_user find_pm_user(String account, String password);

}

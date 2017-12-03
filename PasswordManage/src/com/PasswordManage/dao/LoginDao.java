package com.PasswordManage.dao;

import com.PasswordManage.domain.Pm_user;
import com.PasswordManage.domain.Verification;

public interface LoginDao {

	Pm_user findDao(Verification verification);

	String addUser(Verification verification, String name);

}

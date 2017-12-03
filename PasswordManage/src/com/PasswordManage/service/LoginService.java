package com.PasswordManage.service;

import com.PasswordManage.domain.Pm_user;
import com.PasswordManage.domain.Verification;

public interface LoginService {

	Pm_user login(Verification verification);

	String register(Verification verification, String name);

}

package com.PasswordManage.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.PasswordManage.domain.AddHelp;
import com.PasswordManage.domain.Pm_item;

public interface ManageService {

	Map<String,Object> count();

	Map<String,Object> show(String show_type2);

	String add(AddHelp addhelp);

	void delete(String delete_list);

	void update(AddHelp addhelp);

	void batch_out(String batch_out);

	void batch_in(File destFile,String batch_inFileName);

	Map<String,Object> query(String q_ip_address, String q_item_name, String q_password_status, String q_username,String q_type2);

	void log_dl();

}

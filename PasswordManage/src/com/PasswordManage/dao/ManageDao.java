package com.PasswordManage.dao;

import java.util.List;
import java.util.Map;

import com.PasswordManage.domain.AddHelp;
import com.PasswordManage.domain.Operationlog;
import com.PasswordManage.domain.Pm_item;
import com.PasswordManage.domain.Pm_user;

public interface ManageDao {

	Map<String, Object> Count();

	List<Pm_item> show(String show_type2);

	Pm_user findUser(String creator);

	String add(Pm_item pm_item);

	void delete(List<Pm_item> list);

	Pm_item findPm_item(AddHelp addhelp);

	void update(Pm_item pm_item);

	List<Pm_item> batch_out(List<Pm_item> list0);

	void saveOperationlog(Operationlog operationlog);

	void batch_in(List<Pm_item> pm_items);

	List<Pm_item> query(List<String> values,Map<String,String> mm);

	List<Operationlog> getLog();

}

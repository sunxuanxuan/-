package com.PasswordManage.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.PasswordManage.domain.Pm_user;

public class FirstPageDaoImpl extends HibernateDaoSupport implements FirstPageDao {

	@Override
	public Pm_user find_pm_user(String account, String password) {
		// TODO Auto-generated method stub
		String sql="from Pm_user where account=? and password=?";
		List list=this.getHibernateTemplate().find(sql,account,password);
		if(list.size()>0){
			return (Pm_user)list.get(0);
		}else{
			return null;
		}
		
	}

}

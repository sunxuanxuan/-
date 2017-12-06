package com.PasswordManage.dao;

import java.awt.image.PackedColorModel;
import java.util.Date;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.PasswordManage.domain.Jurisdiction;
import com.PasswordManage.domain.Pm_user;
import com.PasswordManage.domain.Verification;

import net.sf.json.JSONObject;

public class LoginDaoImpl extends HibernateDaoSupport implements LoginDao {

	@Override
	public Pm_user findDao(Verification verification) {
		// TODO Auto-generated method stub
		
		String sql="from Pm_user where account=? and password=?";
		List list=this.getHibernateTemplate().find(sql,verification.getAccount(),verification.getPassword());
		if(list.size()>0){
			return (Pm_user)list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public String addUser(Verification verification, String name) {
		// TODO Auto-generated method stub
		
		Pm_user pm_user=new Pm_user();
		String sql="from Pm_user where account=?";
		List list=this.getHibernateTemplate().find(sql,verification.getAccount());
		if(list.size()>0){
			return "register_failed";
		}else{
		pm_user.setAccount(verification.getAccount());
		pm_user.setPassword(verification.getPassword());
		pm_user.setName(name);
		Date date=new Date();
		pm_user.setRegister_date(date);
		Jurisdiction jurisdiction=(Jurisdiction) this.getHibernateTemplate().find("from Jurisdiction where name=?","∆’Õ®”√ªß").get(0);
		pm_user.setJurisdiction(jurisdiction);
		this.getHibernateTemplate().save(pm_user);
	    return "register_success";
	   }
	}
}

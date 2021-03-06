package com.PasswordManage.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.SynchronousQueue;

import org.apache.poi.poifs.filesystem.OPOIFSDocument;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.omg.PortableInterceptor.INACTIVE;
import org.omg.PortableServer.ThreadPolicyOperations;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.impl.STIconSetTypeImpl;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.PasswordManage.domain.AddHelp;
import com.PasswordManage.domain.Jurisdiction;
import com.PasswordManage.domain.Operationlog;
import com.PasswordManage.domain.Pm_item;
import com.PasswordManage.domain.Pm_user;

public class ManageDaoImpl extends HibernateDaoSupport implements ManageDao {

	@Override
	public Map<String, Object> Count() {
		// TODO Auto-generated method stub
		Map<String,Object> result=new HashMap<String,Object>();
		String host_num;
		String sql="select count(*) from Pm_item where type1=?";
		host_num=this.getHibernateTemplate().find(sql,"主机密码").iterator().next().toString();
		String db_num;
		db_num=this.getHibernateTemplate().find(sql,"数据库密码").iterator().next().toString();
        result.put("host_num",host_num);
        result.put("db_num", db_num);
        
		String sql1="select type2,count(*) from Pm_item group by type2";
		List<Object[]> list=this.getHibernateTemplate().find(sql1);
		for(int i=0;i<list.size();i++){
			String type2=(String) list.get(i)[0];
			String sql2="from Pm_item where type2=?";
			List list_type2=this.getHibernateTemplate().find(sql2,type2);
			int type2_num=list_type2.size();
			int type2_expired=0;
			Date now_date=new Date();
			for(int j=0;j<type2_num;j++){
				Pm_item pm_item=(Pm_item)list_type2.get(j);
				if(now_date.getTime()>pm_item.getPassword_expired_date().getTime()){
					type2_expired++;
					pm_item.setPassword_status("过期");
					this.getHibernateTemplate().update(pm_item);
				}else{
					pm_item.setPassword_status("正常");
					this.getHibernateTemplate().update(pm_item);
				}
			}
			String a=type2+"_num";
			String b=type2+"_expired";
			result.put(a,String.valueOf(type2_num));
			result.put(b,String.valueOf(type2_expired));
		}
		return result;
	}

	@Override
	public List<Pm_item> show(String show_type2) {
		// TODO Auto-generated method stub
		String sql="from Pm_item where type2=?";
		List<Pm_item> list=this.getHibernateTemplate().find(sql,show_type2);
		return list;
	}

	@Override
	public void saveOperationlog(Operationlog operationlog) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(operationlog);
	}
	
	@Override
	public Pm_user findUser(String creator) {
		// TODO Auto-generated method stub
		String sql="from Jurisdiction where name=?";
		Jurisdiction jurisdiction=(Jurisdiction) this.getHibernateTemplate().find(sql,creator).get(0);
		String sql1="from Pm_user where jurisdiction=?";
		Pm_user pm_user=(Pm_user) this.getHibernateTemplate().find(sql1,jurisdiction).get(0);
		return pm_user;
	}

	@Override
	public String add(Pm_item pm_item) {
		// TODO Auto-generated method stub
		String sql="from Pm_item where item_name=?";
		List list=this.getHibernateTemplate().find(sql,pm_item.getItem_name());
		if(list.size()>0){
			return "failed";
		}
		this.getHibernateTemplate().save(pm_item);
		
		//记录操作日志;
		Operationlog operationlog=new Operationlog();
		operationlog.setOperation_date(new Date());
		operationlog.setOperation_object(pm_item.getItem_name());
		operationlog.setOperation_type("添加");
		operationlog.setOperator("超级管理员");
		this.getHibernateTemplate().save(operationlog);
		
		return "success";
	}

	@Override
	public void delete(List<Pm_item> list) {
		// TODO Auto-generated method stub
		for(int i=0;i<list.size();i++){
			Pm_item pm_item=list.get(i);
			this.getHibernateTemplate().delete(pm_item);
			//记录操作日志；
			Operationlog operationlog=new Operationlog();
			operationlog.setOperation_date(new Date());
			operationlog.setOperation_object(pm_item.getItem_name());
			operationlog.setOperation_type("删除");
			operationlog.setOperator("超级管理员");
			this.getHibernateTemplate().save(operationlog);
		}
	}

	@Override
	public Pm_item findPm_item(AddHelp addhelp) {
		// TODO Auto-generated method stub
		String sql="from Pm_item where item_name=?";
		List list=this.getHibernateTemplate().find(sql,addhelp.getItem_name());
		return ((Pm_item)list.get(0));
	}

	@Override
	public void update(Pm_item pm_item) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().update(pm_item);
		//记录操作日志；
		Operationlog operationlog=new Operationlog();
		operationlog.setOperation_date(new Date());
		operationlog.setOperation_object(pm_item.getItem_name());
		operationlog.setOperation_type("更新");
		operationlog.setOperator("超级管理员");
		this.getHibernateTemplate().save(operationlog);
	}

	@Override
	public List<Pm_item> batch_out(List<Pm_item> list0) {
		// TODO Auto-generated method stub
		String sql="from Pm_item where id=?";
		List<Pm_item> list=new ArrayList<Pm_item>();
		for(int i=0;i<list0.size();i++){
			list.add((Pm_item) this.getHibernateTemplate().find(sql,list0.get(i).getId()).get(0));
		}
		return list;
	}

	@Override
	public void batch_in(List<Pm_item> pm_items) {
		// TODO Auto-generated method stub
		for(int i=0;i<pm_items.size();i++){
			Pm_item pm_item=pm_items.get(i);
			if(this.getHibernateTemplate().find("from Pm_item where item_name=?",pm_item.getItem_name()).size()==0){
			this.getHibernateTemplate().save(pm_item);
			//记录操作日志;
			Operationlog operationlog=new Operationlog();
			operationlog.setOperation_date(new Date());
			operationlog.setOperation_object(pm_items.get(i).getItem_name());
			operationlog.setOperation_type("批量导入");
			operationlog.setOperator("超级管理员");
			this.getHibernateTemplate().save(operationlog);
			}
		}
	}

	@Override
	public List<Pm_item> query(List<String> values,Map<String,String> mm) {
		// TODO Auto-generated method stub
		String sql="from Pm_item where ";
		String []strings=new String[values.size()];
		for(int i=0;i<values.size();i++){
			strings[i]=values.get(i);
		}
		sql+=mm.get(strings[0])+"=?";
		for(int i=1;i<strings.length;i++){
			sql+=" and "+mm.get(strings[i])+"=?";
		}
		Object []vv=new Object[strings.length];
		for(int i=0;i<strings.length;i++){
			vv[i]=strings[i];
		}
		List<Pm_item> list=this.getHibernateTemplate().find(sql,vv);
		return list;
	}

	@Override
	public List<Operationlog> getLog() {
		// TODO Auto-generated method stub
		String sql="from Operationlog";
		List<Operationlog> list=this.getHibernateTemplate().find(sql);
		return list;
	}

}

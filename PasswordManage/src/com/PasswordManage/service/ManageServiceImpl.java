package com.PasswordManage.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.KeyStore.PrivateKeyEntry;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.management.RuntimeErrorException;

import org.aspectj.weaver.AjAttribute.PrivilegedAttribute;
import org.springframework.jdbc.support.incrementer.HsqlMaxValueIncrementer;

import com.PasswordManage.dao.ManageDao;
import com.PasswordManage.domain.AddHelp;
import com.PasswordManage.domain.Pm_item;
import com.PasswordManage.domain.Pm_user;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ManageServiceImpl implements ManageService {

	private ManageDao manageDao;
	private String username;
	private String password;
	private String ip_address;
	private int ssh_port;
	//主机修改所用参数;
	private JSch jSch;
	private String[] cmd;
	//数据库修改所用参数;
	private String driverClass;
	private String url;
	private Connection connection;

	public ManageDao getManageDao() {
		return manageDao;
	}

	public void setManageDao(ManageDao manageDao) {
		this.manageDao = manageDao;
	}

	@Override
	public Map<String,Object> count() {
		// TODO Auto-generated method stub
		Map<String,Object> result=manageDao.Count();
		return result;
	}

	@Override
	public Map<String, Object> show(String show_type2) {
		// TODO Auto-generated method stub
		List<Pm_item> list=manageDao.show(show_type2);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("num", String.valueOf(list.size()));
		for(int i=0;i<list.size();i++){
			map.put("item_"+i, list.get(i));
		}
		return map;
	}

	@Override
	public String add(AddHelp addhelp) {
		// TODO Auto-generated method stub
		Pm_item pm_item=new Pm_item();
		pm_item.setType1(addhelp.getType1());
		pm_item.setType2(addhelp.getType2());
		pm_item.setUsername(addhelp.getUsername());
		pm_item.setPassword(addhelp.getPassword());
		pm_item.setCreate_date(new Date());
		pm_item.setCreator(manageDao.findUser(addhelp.getCreator()));
		pm_item.setPassword_lifelength(Integer.parseInt(addhelp.getPassword_lifelength()));
		pm_item.setPassword_expired_date(addhelp.getPassword_expired_date());
		Date n_Date=new Date();
		if(addhelp.getPassword_expired_date().getTime()>=n_Date.getTime()){
		   pm_item.setPassword_status("正常");
		}else{
		   pm_item.setPassword_status("过期");
		}
		pm_item.setIp_address(addhelp.getIp_address());
		if(addhelp.getSsh_port()!=null){
		pm_item.setSsh_port(Integer.parseInt(addhelp.getSsh_port()));
		}
		pm_item.setItem_name(addhelp.getItem_name());
		String re=manageDao.add(pm_item);
		return re;
	}

	@Override
	public void delete(String delete_list){
		
		//commons-lang3-3.2不支持JSONArray,额外引入了commons-lang-2.4包;
		
		JSONArray jsonArray=JSONArray.fromObject(delete_list);
		List<Pm_item> list=new ArrayList<>();
		for(int i=0;i<jsonArray.size();i++){
			JSONObject jsonObject=jsonArray.getJSONObject(i);
			list.add((Pm_item) jsonObject.toBean(jsonObject,Pm_item.class));
		}
		manageDao.delete(list);
	}

	@Override
	public void update(AddHelp addhelp) {
		// TODO Auto-generated method stub
		Pm_item pm_item=manageDao.findPm_item(addhelp);
		
		/*if(addhelp.getType1()=="主机密码"){
			ip_address=pm_item.getIp_address();
			username=pm_item.getUsername();
			password=pm_item.getPassword();
			ssh_port=pm_item.getSsh_port();
			cmd[0]="passwd \n";
			cmd[1]=pm_item.getPassword()+"\n";
			cmd[2]=addhelp.getPassword()+"\n";
			cmd[3]=addhelp.getPassword()+"\n";
			
			jSch=new JSch();
			
			try{
				//创建session并且打开连接；
				Session session=jSch.getSession(username,ip_address,ssh_port);
				session.setPassword(password);
				Properties config=new Properties();
				config.put("StrictHostKeyChecking", "no");
				session.setConfig(config);
				int timeout=60000;
				session.setTimeout(timeout);
				session.connect();
				
				//打开通道，设置通道类型，执行命令；
				Channel channel=(Channel)session.openChannel("shell");
				channel.connect(1000);
				InputStream instream=channel.getInputStream();
				OutputStream outputStream=channel.getOutputStream();
				for(int i=0;i<cmd.length;i++){
					 outputStream.write(cmd[i].getBytes());
					 outputStream.flush();
				     byte [] data=new byte[instream.available()];
				     int ln=instream.read(data);
				     if(ln<0){
				    	 throw new Exception("network error!");
				     }
				     String temp=new String(data,0,ln,"UTF-8");
				     System.out.println(temp);
				}
				outputStream.close();
				instream.close();
				channel.disconnect();
				session.disconnect();
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			driverClass="com.mysql.jdbc.Driver";
			url="jdbc:mysql:///"+pm_item.getItem_name();
			username=pm_item.getUsername();
			password=pm_item.getPassword();
            //注册驱动；
			try{
				Class.forName(driverClass);
				connection=(Connection) DriverManager.getConnection(url,username,password);
				String sql="USE mysql;update user set authentication_string=? where user=? and host=?";
				PreparedStatement ps=(PreparedStatement) connection.prepareStatement(sql);
				ps.setString(1, addhelp.getPassword());
				ps.setString(2, "root");
				ps.setString(3, "localhost");
				if(ps.executeUpdate()>0){
					System.out.println("更改成功！");
				}
			}catch(Exception e){
				throw new RuntimeException(e);
			}
			
		}*/
		pm_item.setPassword(addhelp.getPassword());
		manageDao.update(pm_item);
	}
	
}

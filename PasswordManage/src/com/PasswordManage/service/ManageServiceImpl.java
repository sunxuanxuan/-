package com.PasswordManage.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.KeyStore.PrivateKeyEntry;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.management.RuntimeErrorException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.dbcp.dbcp.datasources.SharedPoolDataSource;
import org.aspectj.weaver.AjAttribute.PrivilegedAttribute;
import org.aspectj.weaver.reflect.ReflectionWorld;
import org.eclipse.jdt.internal.compiler.tool.EclipseCompiler;
import org.springframework.jdbc.support.incrementer.HsqlMaxValueIncrementer;
import org.springframework.transaction.annotation.Transactional;

import com.PasswordManage.dao.ManageDao;
import com.PasswordManage.domain.AddHelp;
import com.PasswordManage.domain.Operationlog;
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

@Transactional
public class ManageServiceImpl implements ManageService {

	private ManageDao manageDao;
	/*private String username;
	private String password;
	private String ip_address;
	private int ssh_port;
	//主机修改所用参数;
	private JSch jSch;
	private String[] cmd;
	//数据库修改所用参数;
	private String driverClass;
	private String url;
	private Connection connection;*/

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
		pm_item.setPassword_expired_date(addhelp.getPassword_expired_date());
		if(addhelp.getPassword_expired_date().getTime()>new Date().getTime()){
			pm_item.setPassword_status("正常");
		}else{
			pm_item.setPassword_status("过期");
		}
		manageDao.update(pm_item);
	}

	@Override
	public void batch_out(String batch_out) {
		// TODO Auto-generated method stub
		JSONArray jsonArray=JSONArray.fromObject(batch_out);
		List<Pm_item> list0=new ArrayList<Pm_item>();
		for(int i=0;i<jsonArray.size();i++){
			JSONObject jsonObject=jsonArray.getJSONObject(i);
			list0.add((Pm_item) JSONObject.toBean(jsonObject,Pm_item.class));
		}
		List<Pm_item> list=manageDao.batch_out(list0);
		String type1=list.get(0).getType1();
		String type=type1.substring(0, type1.indexOf("密"));
		HSSFWorkbook wb=new HSSFWorkbook();
		HSSFSheet sheet=wb.createSheet();
		HSSFRow row=sheet.createRow(0);
		HSSFCell cell=row.createCell(0);
		//写入表头;
		cell.setCellValue(type+"类型");
		cell=row.createCell(1);
		cell.setCellValue(type+"名");
		cell=row.createCell(2);
		cell.setCellValue("用户名");
		cell=row.createCell(3);
		cell.setCellValue("密码");
		cell=row.createCell(4);
		cell.setCellValue("创建者");
		cell=row.createCell(5);
		cell.setCellValue("密码有效期");
		cell=row.createCell(6);
		cell.setCellValue("密码到期时间");
		cell=row.createCell(7);
		cell.setCellValue("记录创建时间");
		cell=row.createCell(8);
		cell.setCellValue("密码状态");
		if(type.equals("主机")){
			cell=row.createCell(9);
			cell.setCellValue("IP地址");
			cell=row.createCell(10);
			cell.setCellValue("ssh端口");
		}
		//写入记录;
		for(int i=0;i<list.size();i++){
			Pm_item pm_item=list.get(i);
			row=sheet.createRow(i+1);
			cell=row.createCell(0);
			cell.setCellValue(pm_item.getType2());
			cell=row.createCell(1);
			cell.setCellValue(pm_item.getItem_name());
			cell=row.createCell(2);
			cell.setCellValue(pm_item.getUsername());
			cell=row.createCell(3);
			cell.setCellValue(pm_item.getPassword());
			cell=row.createCell(4);
			cell.setCellValue(pm_item.getCreator().getJurisdiction().getName());
			cell=row.createCell(5);
			cell.setCellValue(pm_item.getPassword_lifelength());
			cell=row.createCell(8);
			cell.setCellValue(pm_item.getPassword_status());
			if(type.equals("主机")){
				cell=row.createCell(9);
				cell.setCellValue(pm_item.getIp_address());
				cell=row.createCell(10);
				cell.setCellValue(pm_item.getSsh_port());
			} 
			String formatDate=null;
			DateFormat dFormat=new SimpleDateFormat("yyyy-MM-dd");
			formatDate=dFormat.format(pm_item.getPassword_expired_date());
			String s=formatDate.replace("-","/");
			cell=row.createCell(6);
			cell.setCellValue(s);
			formatDate=dFormat.format(pm_item.getCreate_date());
			s=formatDate.replace("-","/");
			cell=row.createCell(7);
			cell.setCellValue(s);
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try{
			wb.write(os);
		}catch(IOException e){
			e.printStackTrace();
		}
		byte [] content=os.toByteArray();
		File file=new File("Batch_out.xls");
		OutputStream fos=null;
		try{
			fos=new FileOutputStream(file);
			
			fos.write(content);
			os.close();
			fos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		//操作日志记录;
		for(int i=0;i<list.size();i++){
			Operationlog operationlog=new Operationlog();
			operationlog.setOperation_date(new Date());
			operationlog.setOperation_object(list.get(i).getItem_name());
			operationlog.setOperation_type("批量导出");
			operationlog.setOperator("超级管理员");
			manageDao.saveOperationlog(operationlog);
		}
	}

	@Override
	public void batch_in(File destFile,String batch_inFileName){
		// TODO Auto-generated method stub
		try{
	        List<Pm_item> pm_items=new ArrayList<Pm_item>();
			FileInputStream inputStream=new FileInputStream(destFile);
		    Workbook wb=null;
		    if(batch_inFileName.endsWith("xls")){
		    	wb=new HSSFWorkbook(inputStream);
		    }else if(batch_inFileName.endsWith("xlsx")){
		    	wb=new XSSFWorkbook(inputStream);
		    }
		    Sheet sheet=wb.getSheetAt(0);
		    int curR=0;
		    String type1 = null;
		    for(Row row:sheet){
		    	if(curR==0){
		    		curR++;
		    		Cell firstCell=row.getCell(0);
		    		type1=firstCell.toString().substring(0,firstCell.toString().indexOf("类"))+"密码";
		    		continue;
		    	}
		    	if(row.getCell(0).toString().equals("")){
		    		break;
		    	}
		       Pm_item pm_item=new Pm_item();
		       Cell cell=row.getCell(0);
		       pm_item.setType1(type1);
		       pm_item.setType2(cell.toString());
		       cell=row.getCell(1);
		       pm_item.setItem_name(cell.toString());
		       cell=row.getCell(2);
		       pm_item.setUsername(cell.toString());
		       cell=row.getCell(3);
		       pm_item.setPassword(cell.toString());
		       cell=row.getCell(4);
		       pm_item.setCreator(manageDao.findUser(cell.toString()));
		       cell=row.getCell(5);
		       pm_item.setPassword_lifelength((int)cell.getNumericCellValue());
		       cell=row.getCell(6);
		       if(cell.getCellTypeEnum()==CellType.NUMERIC){
		    	   pm_item.setPassword_expired_date(cell.getDateCellValue());
		       }else{
		    	   SimpleDateFormat sff=new SimpleDateFormat("yy-MM-dd");
		    	   String ss=cell.toString().replace("/","-");
		    	   Date date=sff.parse(ss);
		    	   pm_item.setPassword_expired_date(date);
		       }
		       cell=row.getCell(7);
		       pm_item.setCreate_date(new Date());
		       cell=row.getCell(8);
		       pm_item.setPassword_status(cell.toString());
		       if(type1.equals("主机密码")){
		    	   cell=row.getCell(9);
		    	   pm_item.setIp_address(cell.toString());
		    	   cell=row.getCell(10);
		    	   pm_item.setSsh_port((int)cell.getNumericCellValue());
		       }
		       pm_items.add(pm_item);
		    }
		    inputStream.close();
		    manageDao.batch_in(pm_items);
	  }catch(Exception e){
		  System.out.println(e.getMessage());
	  }
	}

	@Override
	public Map<String,Object> query(String q_ip_address, String q_item_name, String q_password_status, String q_username,String q_type2) {
		// TODO Auto-generated method stub
		List<String> values=new ArrayList<String>();
		Map<String,String> mm=new HashMap<String,String>();
		if(!q_ip_address.equals("")){
			values.add(q_ip_address);
			mm.put(q_ip_address,"ip_address");
		}
		if(!q_item_name.equals("")){
			values.add(q_item_name);
			mm.put(q_item_name,"item_name");
		}
		if(!q_password_status.equals("")){
			values.add(q_password_status);
			mm.put(q_password_status,"password_status");
		}
		if(!q_username.equals("")){
			values.add(q_username);
			mm.put(q_username,"username");
		}
		if(!q_type2.equals("")){
			values.add(q_type2);
			mm.put(q_type2,"type2");
		}
		List<Pm_item> list=manageDao.query(values,mm);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("num", String.valueOf(list.size()));
		for(int i=0;i<list.size();i++){
			map.put("item_"+i, list.get(i));
		}
		return map;
	}

	@Override
	public void log_dl(){
		// TODO Auto-generated method stub
		List<Operationlog> list=manageDao.getLog();
		File file=new File("log.txt");
		try{
			FileWriter fW=new FileWriter(file);
			BufferedWriter bW=new BufferedWriter(fW);
			for(int i=0;i<list.size();i++){
				Operationlog o=list.get(i);
				bW.write(o.getOperation_date()+"  "+o.getOperator()+"  "+o.getOperation_type()+"  "+o.getOperation_object());
				bW.newLine();
				bW.flush();
			}
			fW.close();
			bW.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}

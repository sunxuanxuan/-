<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="icons/icon.png"> 
<link rel="stylesheet" href="css/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="css/management.css"type="text/css" >
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="js/management.js"></script>
<title>运维管理</title>
</head>
<body>
<!-- 导航栏 -->
<header>
 <nav>
  <div id="logo">
    <a href="#">运维管理</a>
  </div>
  <ul id="h_list">
    <li><a href="#">部署管理</a></li>
    <li><a href="#">密码管理</a></li>
    <li><a href="#">运维工具</a></li>
    <li><a href="#">BOSS运维</a></li>
    <li><a href="#">系统管理</a></li>
  </ul>
 </nav>
 <div id="path">
  <div id="p_p">
   <p>你当前的位置为:</p>
   <span><a href="FirstPage.jsp">首页</a></span>
   <span>></span>
   <span><a href="#">运维管理</a></span>
   <span>></span>
   <span><a href="#">密码管理</a></span>
  </div>
  <div id="p_u">
    <span>当前登录用户:</span><span><s:property value="#user.getName()"/></span>
  </div>
 </div>
</header>
<!-- 主体 -->
<div id="body">
  <div id="b_list">
    <div id="l_h">
     <span>管理列表</span>
    </div>
    <div>
      <ul id="ztree1" class="ztree"></ul>
    </div>
  </div>
  <div id="b_body">
  
  </div>
</div>
<!-- 页脚 -->
<footer>
 <ul>
     <li><img alt="weibo" src="icons/wb.png"></li>
     <li><img alt="facebook" src="icons/fb.png"></li>
     <li><img alt="email" src="icons/email.png"></li>
     <li><img alt="address" src="icons/address.png"></li>
  </ul>
  <br/>
  <label>Copyright&copy;Crazy丶轩Sir</label>
</footer>
</body>
</html>
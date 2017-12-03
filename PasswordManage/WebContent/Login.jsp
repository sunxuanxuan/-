<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="icons/icon.png">
<link href="css/login.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/login.js"></script>
<title>登录</title>
</head>
<body>

  <!-- 导航部分 -->
  <header>
    <nav>
      <div id="logo">
        <a href="#">移动云</a>
      </div>
      <ul>
        <li><a href="#">设置</a></li>
        <li><a href="FirstPage.jsp">退出</a></li>
      </ul>
    </nav>
  </header>
  
  <!-- 登录部分 -->
  <div id="inner">
     <div id="summary">
       <h1>轻量应用服务器开发</h1>
       <br/>
       <p>快速搭建和易于管理的轻量级服务器，简单易上手，一分钟搭建应用。更低价格，更高服务增值</p>
     </div>
     <div id="login">
       <p>密码登录</p>
       <form action="login_login" method="post">
         <input id="account" type="text" value="账号/邮箱/手机号" name="account"/>
         <input id="password"type="text" value="密码" name="password">
         <button type="submit">登录</button>
         <br/>
         <br/>
         <span><a href="#">忘记密码</a></span>
         <span><a href="Register.jsp">免费注册</a></span>
       </form>
       <br/>
       <div>
         <span class="errorMessage"><s:property value="#error"/></span>
       </div>
     </div>
  </div>
  
  <!-- 页脚部分 -->
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
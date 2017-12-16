<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="icons/icon.png">
<link href="css/register.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/register.js"></script>
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
     <div id="register">
       <p>账号注册</p>
       <form action="login_register" method="post" onsubmit="return check()">
         <input id="account" type="text" value="账号/邮箱/手机号" name="account"/>
         <input id="password"type="text" value="密码" name="password">
         <input id="name" type="text" value="昵称" name="name">
         <button type="submit">注册</button>
       </form>
       <br/>
       <div>
         <span><s:actionerror/></span>
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
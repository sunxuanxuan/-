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
<script type="text/javascript">
function show_update(data){
	var checkedBoxes=getCheckedBoxes();
	if(checkedBoxes.length==0){
		alert("未勾选任何记录!");
		return;
	}else if(checkedBoxes.length>1){
		alert("选项太多，一次请勾选一条记录!");
		return;
	}else{
		var id=checkedBoxes[0].attr("id");
		var item=data["item_"+id.substr(9, 1)];
		show_add();
		var title=$("#title").html();
		var parent=title.substring(0,title.indexOf("添"));
		$("#title").html(parent+"修改");
		var divs=$("#show").find(".add_div");
		divs.each(function(){
			var div_id=$(this).attr("id");
			if(div_id=="type2"){
				var options=$(this).find("option");
				options.each(function(){
					if($(this).attr("value")==item.type2){
						$(this).attr("selected","true");
					}else{
						$(this).attr("disabled","disabled");
					}
				});
			}else if(div_id=="password"||div_id=="confirm_password"){
			    var div_input=$(this).find("input");
			    div_input.attr("value",item.password);
			}else if(div_id=="creator"){
				var div_input=$(this).find("input");
				div_input.attr("value",item[div_id].jurisdiction.name);
				div_input.attr("disabled","disabled");
			}else if(div_id=="password_expired_date"){
				var div_input=$(this).find("input");
				var date=item[div_id];
				div_input.attr("value",date.substr(0, date.indexOf("T")));
				div_input.attr("disabled","disabled");
			}else{
				var div_input=$(this).find("input");
				div_input.attr("value",item[div_id]);
				div_input.attr("disabled","disabled");
			}
		});
		var button=$("#show").find("#submit");
		button.attr("onclick","javascript:update_submit()");
	}
}
function update_submit(){
	if($("#password input").val()!=$("#confirm_password input").val()){
		alert("两次密码输入不一致,请重新输入！");
		return;
	}
	var title=$("#title").html();
	var type1=title.substring(0,title.indexOf("修"));
	$.ajax({
		type:"post",
		url:"manage_update.action",
		data:{
			"type1":type1,
			"type2":$("#type2 option:selected").val(),
			"username":$("#username input").val(),
			"password":$("#password input").val(),
			"creator":$("#creator input").val(),
			"item_name":$("#item_name input").val(),
			"password_lifelength":$("#password_lifelength input").val(),
			"password_expired_date":$("#password_expired_date input").val(),
			"ip_address":$("#ip_address input").val(),
			"ssh_port":$("#ssh_port input").val()
		},
		dataType: "json",
		success:function(data){
			if(data["result"]=="success"){
				alert("修改成功！");
				var zTree=$.fn.zTree.getZTreeObj("ztree1");
				var node=zTree.getNodeByParam("name",$("#type2 option:selected").val());
			    zTree.selectNode(node);
			    zTree.setting.callback.onClick(null,zTree.setting.treeId,node);
			}
		},
		error:function(e){
			alert("Error!");
		}
	})
}
</script>
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
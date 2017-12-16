/**
 * 
 */
$(document).ready(function(){
	$("#inner").fadeIn("slow");
	$("header").slideDown();
	$("#password").focus(function(){
		$(this).attr("value","");
		$(this).attr("type","password");
		$(this).css("color","#000000");
	})
	$("#password").blur(function(){
		if($(this).val()==""){
			$(this).attr("type","text");
			$(this).attr("value","密码");
			$(this).css("color","#C0C0C0");
		}
	})
	$("#account").focus(function(){
		$(this).attr("value","");
		$(this).css("color","#000000");
	})
	$("#account").blur(function(){
		if($(this).val()==""){
			$(this).attr("value","账号/邮箱/手机号");
			$(this).css("color","#C0C0C0");
		}
	})
	$("#name").focus(function(){
		$(this).attr("value","");
		$(this).css("color","#000000");
	})
	$("#name").blur(function(){
		if($(this).val()==""){
			$(this).attr("value","昵称");
			$(this).css("color","#C0C0C0");
		}
	})
	var re;
	re="${result}";
	if(re=="register_success"){
		alert("注册成功，跳转至登录界面！");
		window.location.href="Login.jsp";
	}else if(re=="register_failed"){
		alert("该账号已被注册！");
	}
});
function check() {
	var account=$("#account");
	var password=$("#password");
	var name=$("#name");
	if(account.val()==""||account.val()=="账号/邮箱/手机号"){
		alert("请填写完整！");
		return false;
	}
	if(password.val()==""||password.val()=="密码"){
		alert("请填写完整！");
		return false;
	}
	if(name.val()==""||name.val()=="昵称"){
		alert("请填写完整！");
		return false;
	}
}
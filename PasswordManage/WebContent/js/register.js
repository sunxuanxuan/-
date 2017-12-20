/**
 * 
 */
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
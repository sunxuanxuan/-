/**
 * 
 */
var zTreeObj;
function zTreeOnClick(event, treeId, treeNode){
	var nodeName=treeNode.name;
	if(nodeName=="密码管理"){
		/*获取管理信息总览*/
		$.ajax({
			url:"manage_count.action",
			type:"post",
			data:{
			},
			dataType: "json",
			success: function(data){
				$("#b_body").empty();
				var title=$("<span id=\"title\">统计信息</span>")
				$("#b_body").append(title);
				var host=$("<p id=\"type11\">主机密码总数: <span id=\"host_num\" class=\"num\"></span></p>");
				$("#b_body").append(host);
				var db=$("<p id=\"type12\">数据库密码总数: <span id=\"db_num\" class=\"num\"></span></p>");
				$("#b_body").append(db);
				$("#host_num").html(data.host_num);
				$("#db_num").html(data.db_num);
				var type1_nodes=treeNode.children;
				for(var i=0;i<type1_nodes.length;i++){
					var type2_nodes=type1_nodes[i].children;
					var type2_name=new Array();
					for(var j=0;j<type2_nodes.length;j++){
						type2_name[j]=type2_nodes[j].name;
					}
					var table=$("<table></table>");
					if(type1_nodes[i].name=="数据库密码"){
						table.attr("id","db_table");
					}else if(type1_nodes[i].name=="主机密码"){
						table.attr("id","host_table");
					}
					for(var j=0;j<type2_nodes.length;j++){
						if(data[type2_name[j]+"_num"]==null){
							continue;
						}
						var tr=$("<tr></tr>");
						var th1=$("<td></td>");
						th1.html(type2_name[j]);
						tr.append(th1);
						var th2=$("<td class=\"num\"></td>");
						th2.html(data[type2_name[j]+"_num"]);
						tr.append(th2);
						var th3=$("<td></td>");
						th3.html("正常");
						tr.append(th3);
						var th4=$("<td class=\"num\"></td>");
						var zc=parseInt(data[type2_name[j]+"_num"])-parseInt(data[type2_name[j]+"_expired"]);
						th4.html(zc.toString());
						tr.append(th4);
						var th5=$("<td></td>");
						th5.html("过期");
						tr.append(th5);
						var th6=$("<td class=\"num\"></td>");
						th6.html(data[type2_name[j]+"_expired"]);
						tr.append(th6);
						table.append(tr);
					}
					$("#b_body").append(table);
				}
			},
			error:function(e){
				alert("Error!");
			}
		})
	}else if(treeNode.children==null){
		/*获取并展示某种类别的所有元素*/
		var parent=treeNode.getParentNode().name;
		$.ajax({
			type:"post",
			url:"manage_show.action",
			data:{
				"show_type2":treeNode.name
			},
			dataType: "json",
		    success:function(data){
		    	$("#b_body").empty();
		    	var title=$("<span id=\"title\"></span>")
		    	title.html(parent+"管理");
				$("#b_body").append(title);
				var show=$("<div id=\"show\"></div>");
				$("#b_body").append(show);
				var operation=$("<div id=\"operation\"></div>");
				var obj=JSON.stringify(data);
/*函数传参问题！*/  var ul=$("<ul><li><a id=\"add\" href=\"javascript:show_add()\">添加</a></li><li><a id=\"update\" href=\"javascript:void(0)\" onclick='show_update("+obj+")'>修改</a></li><li><a id=\"delete\" href=\"javascript:void(0)\" onclick='show_delete("+obj+")'>删除</a></li><li><a id=\"batch_in\"href=\"javascript:batch_in()\">批量导入</a></li><li><a id=\"batch_out\"href=\"javascript:void(0)\" onclick='batch_out("+obj+")'>批量导出</a></li></ul>");
				operation.append(ul);
				$("#show").append(operation);
				var show_table=$("<table id=\"show_table\"></table>");
				var show_table_th=$("<tr id=\"show_table_th\"></tr>");
				var th_td1=$("<td class=\"td1\"></td>");
				th_td1.html("<input type=\"checkbox\" disabled=\"true\"/>");
				show_table_th.append(th_td1);
				var th_td2=$("<td class=\"td2\"></td>");
				th_td2.html(parent.substring(0,parent.indexOf("密"))+"名");
				show_table_th.append(th_td2);
				if(treeNode.getParentNode().name=="主机密码"){
				var th_td3=$("<td class=\"td3\"></td>");
				th_td3.html("IP地址");
				show_table_th.append(th_td3);
				}
				var th_td4=$("<td class=\"td4\"></td>");
				th_td4.html("用户名");
				show_table_th.append(th_td4);
				var th_td5=$("<td class=\"td5\"></td>");
				th_td5.html("密码");
				show_table_th.append(th_td5);
				var th_td6=$("<td class=\"td6\"></td>");
				th_td6.html("密码状态");
				show_table_th.append(th_td6);
				var th_td7=$("<td class=\"td7\"></td>");
				th_td7.html("创建人");
				show_table_th.append(th_td7);
				var th_td8=$("<td class=\"td8\"></td>");
				th_td8.html("创建日期");
				show_table_th.append(th_td8);
				show_table.append(show_table_th);
				/*for(var i=0;i<parseInt(data["num"]);i++){
					if(i>7){
						break;
					}
					var a="item_"+i;
					var tr=$("<tr></tr>");
					if(i%2==0){
						tr.attr("class","even");
					}else{
						tr.attr("class","odd");
					}
					var td1=$("<td class=\"td1\"></td>");
					var checkbox=$("<input type=\"checkbox\"/>");
					checkbox.attr("id","checkbox_"+i);
					td1.append(checkbox);
					tr.append(td1);
					var td2=$("<td class=\"td2\"></td>");
					td2.html(data[a].item_name);
					tr.append(td2);
					if(treeNode.getParentNode().name=="主机密码"){
					var td3=$("<td class=\"td3\"></td>");
					td3.html(data[a].ip_address);
					tr.append(td3);
					}
					var td4=$("<td class=\"td4\"></td>");
					td4.html(data[a].username);
					tr.append(td4);
					var td5=$("<td class=\"td5\"></td>");
					var str=data[a].password.substr(0,1);
					for(var k=0;k<data[a].password.length-1;k++){
						str+="*";
					}
					td5.html(str); 
					tr.append(td5);
					var td6=$("<td class=\"td6\"></td>");
					td6.html(data[a].password_status);
					if(data[a].password_status=="过期"){
						td6.css("color","red");
					}
					tr.append(td6);
					var td7=$("<td class=\"td7\"></td>");
					td7.html(data[a].creator.jurisdiction.name);
					tr.append(td7);
					var td8=$("<td class=\"td8\"></td>");
					td8.html(data[a].create_date);
					tr.append(td8);
					show_table.append(tr);
				}*/
				$("#show").append(show_table);
				var fathernode=treeNode.getParentNode().name;
				show_by_page(data,1,fathernode);
				if(fathernode=="主机密码"){
				var change_page=$("<p>当前页数为<span id='curpage'>1</span>/<span id='totalpage'>1</span> ,<a id='last_page' href='javascript:lastpage("+obj+",\"主机密码\")'>上一页</a>/<a id='next_page' href='javascript:nextpage("+obj+",\"主机密码\")'>下一页</a></p>");
				}else if(fathernode=="数据库密码"){
			    var change_page=$("<p>当前页数为<span id='curpage'>1</span>/<span id='totalpage'>1</span> ,<a id='last_page' href='javascript:lastpage("+obj+",\"数据库密码\")'>上一页</a>/<a id='next_page' href='javascript:nextpage("+obj+",\"数据库密码\")'>下一页</a></p>");	
				}
				$("#show").append(change_page);
				var tp=parseInt(parseInt(data["num"])/7+1);
				$("#totalpage").html(tp.toString());
		    },
		    error:function(e){
		    	 alert("Error!");
		    }
		})
	}
}
var setting={
	view:{
		showLine: true
	},
	callback: {
		onClick: zTreeOnClick
	}
};
var zNodes=[{ name:"密码管理",open: false,children: 
		[{name:"主机密码",open:false,children:[
		   {name:"linux主机"},    
		   {name:"unix主机"},
		   {name:"aix主机"},
		]},
		{name:"数据库密码",open:false,children:[
		   {name:"oracle数据库"},
		   {name:"mysql数据库"},
		   {name:"sqlserver数据库"},
		 ]},]}
	];
$(document).ready(function(){
	zTreeObj=$.fn.zTree.init($("#ztree1"), setting, zNodes); 
});

/*添加事件页面元素生成*/
function show_add(){
	var title=$("#title").html();
	var parent=title.substring(0,title.indexOf("管"));
	$("#show").empty();
	$("#title").html(parent+"添加");
	
	//添加用于获取数据具体信息的页面元素
	if(parent=="主机密码"){
		var type2=$("<div id=\"type2\" class=\"add_div\"></div>");
		var type2_label=$("<span class=\"label\">系统类型:</span>");
		var type2_input=$("<select name=\"type2\" class=\"input\"><option value=\"linux主机\">linux主机</option><option value=\"unix主机\">unix主机</option><option value=\"aix主机\">aix主机</option></select>");
	    type2.append(type2_label);
	    type2.append(type2_input);
	    
	    var ipaddress=$("<div id=\"ip_address\" class=\"add_div\"></div>");
	    var ipaddress_label=$("<span class=\"label\">IP地址:</span>")
	    var ipaddress_input=$("<input type=\"text\" name=\"ip_address\" class=\"input\"/>");
	    ipaddress.append(ipaddress_label);
	    ipaddress.append(ipaddress_input);
	    
	    var ssh_port=$("<div id=\"ssh_port\" class=\"add_div\"></div>");
	    var ssh_port_label=$("<span class=\"label\">ssh端口:</span>")
	    var ssh_port_input=$("<input type=\"text\" name=\"ssh_port\" class=\"input\" value=\"22\"/>");
	    ssh_port_input.attr("onkeyup","this.value=this.value.replace(/[^0-9]/g,'')");
		ssh_port_input.attr("onafterpaste","this.value=this.value.replace(/[^0-9]/g,'')");
	    ssh_port.append(ssh_port_label);
	    ssh_port.append(ssh_port_input);
	    
	    $("#show").append(type2);
	    $("#show").append(ipaddress);
	    $("#show").append(ssh_port);
	}else if(parent=="数据库密码"){
		var type2=$("<div id=\"type2\" class=\"add_div\"></div>");
		var label=$("<span class=\"label\">数据库类型:</span>");
		var type2_input=$("<select name=\"type2\" class=\"input\"><option value=\"oracle数据库\">oracle数据库</option><option value=\"mysql数据库\">mysql数据库</option><option value=\"sqlserver数据库\">sqlserver数据库</option></select>");
	    type2.append(label);
	    type2.append(type2_input);
	    $("#show").append(type2);
	}
	var username=$("<div id=\"username\" class=\"add_div\"></div>");
	var username_label=$("<span class=\"label\">用户名:</span>");
	var username_input=$("<input type=\"text\" name=\"username\" class=\"input\"/>");
    username.append(username_label);
    username.append(username_input);
    $("#show").append(username);
    
    var password=$("<div id=\"password\" class=\"add_div\"></div>");
	var password_label=$("<span class=\"label\">密码:</span>");
	var password_input=$("<input type=\"password\" name=\"password\" class=\"input\"/>");
	password.append(password_label);
	password.append(password_input);
    $("#show").append(password);
    
    var confirm_password=$("<div id=\"confirm_password\" class=\"add_div\"></div>");
	var confirm_password_label=$("<span class=\"label\">确认密码:</span>");
	var confirm_password_input=$("<input type=\"password\" name=\"confirm_password\" class=\"input\"/>");
	confirm_password.append(confirm_password_label);
	confirm_password.append(confirm_password_input);
    $("#show").append(confirm_password);
    
    var creator=$("<div id=\"creator\" class=\"add_div\"></div>");
	var creator_label=$("<span class=\"label\">创建者:</span>");
	var creator_input=$("<input type=\"text\" name=\"creator\" class=\"input\" value=\"超级管理员\" readonly=\"true\"/>");
	creator.append(creator_label);
	creator.append(creator_input);
    $("#show").append(creator);
    
    var item_name=$("<div id=\"item_name\" class=\"add_div\"></div>");
	var item_name_label=$("<span class=\"label\"></span>");
	var item_name_input=$("<input type=\"text\" name=\"item_name\" class=\"input\"/>");
	item_name_label.html(parent.substring(0,parent.indexOf("密"))+"名称:");
	item_name.append(item_name_label);
	item_name.append(item_name_input);
    $("#show").append(item_name);
    
    var lifelength=$("<div id=\"password_lifelength\" class=\"add_div\"></div>");
	var lifelength_label=$("<span class=\"label\">密码有效期:</span>");
	var lifelength_input=$("<input type=\"text\" name=\"password_lifelength\" class=\"input\"/>");
	lifelength_input.attr("onkeyup","this.value=this.value.replace(/[^0-9]/g,'')");
	lifelength_input.attr("onafterpaste","this.value=this.value.replace(/[^0-9]/g,'')");
	lifelength.append(lifelength_label);
	lifelength.append(lifelength_input);
    $("#show").append(lifelength);
    
    var password_expired_date=$("<div id=\"password_expired_date\" class=\"add_div\"></div>");
	var password_expired_date_label=$("<span class=\"label\">密码到期时间:</span>");
	var password_expired_date_input=$("<input type=\"date\" name=\"password_expired_date\" value=\"2017-12-10\" class=\"input\"/>");
	password_expired_date.append(password_expired_date_label);
	password_expired_date.append(password_expired_date_input);
    $("#show").append(password_expired_date);
    
    var submit=$("<button id=\"submit\" type=\"button\" onclick=\"javascript:add_submit()\">确定</button>");
    var back=$("<button id=\"back\" type=\"button\" onclick=\"javascript:add_back()\">返回</button>")
    $("#show").append(submit);
    $("#show").append(back);
  
}
//添加操作的数据提交;
function add_submit(){
	
	var title=$("#title").html();
	var type1=title.substring(0,title.indexOf("添"));
	
	var inputs=document.getElementById("show").getElementsByTagName("input");
	for(var i=0;i<inputs.length;i++){
		if(inputs[i].value==""){
		   alert("请输入完整信息！");
		   return;
		}
	}		
    if($("#password input").val()!=$("#confirm_password input").val()){
		alert("两次密码输入不一致,请重新输入！");
		return;
	}
    if($("#ipaddress input").val()!=null&&!isIp($("#ipaddress input").val())){
		alert("IP地址格式有误，请重新输入!");
		return;
	}
	$.ajax({
		type:"post",
		url:"manage_add.action",
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
			alert("添加成功！");
			var zTree = $.fn.zTree.getZTreeObj("ztree1");
			var node=zTree.getNodeByParam("name",$("#type2 option:selected").val());
		    zTree.selectNode(node);
		    zTree.setting.callback.onClick(null,zTree.setting.treeId,node);
			}else{
				alert("该"+type1+"已存在！");
			}
		},
		error:function(e){
			alert("Error!");
		}
	})
}
//判断IP地址格式是否合法;
function isIp(ip){
	var reg =  /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/     
	return reg.test(ip);    
}
//添加操作返回;
function add_back(){
	var zTree=$.fn.zTree.getZTreeObj("ztree1");
	var node=zTree.getNodeByParam("name",$("#type2 option:selected").val());
    zTree.selectNode(node);
    zTree.setting.callback.onClick(null,zTree.setting.treeId,node);
}
//获取勾选了的表格行;
function getCheckedBoxes(){
	var checkBoxes=$("#show_table").find("input[type='checkbox']");
	var checkedBoxes=new Array();
	var i=0;
	checkBoxes.each(function(){
		if($(this).is(':checked')){
		   checkedBoxes[i++]=$(this);
		}
	});
	return checkedBoxes;
}
//删除操作;
function show_delete(data){
	var checkedBoxes=getCheckedBoxes();
	if(checkedBoxes.length==0){
		alert("未勾选任何记录!");
		return;
	}
	var checkedBoxes_id=new Array();
	var delete_list=new Array();
	for(var i=0;i<checkedBoxes.length;i++){
		var id=checkedBoxes[i].attr("id");
		checkedBoxes_id[i]=id.substr(9, 1);
	}
	for(var i=0;i<checkedBoxes_id.length;i++){
		var a="item_"+checkedBoxes_id[i];
		delete_list[i]=data[a];
	}
	if(delete_list.length&&confirm("您选择了"+delete_list.length+"项，是否确定删除？")){
		var d_l=JSON.stringify(delete_list);
		$.ajax({
			type:"post",
			url:"manage_delete.action",
			data:{
				"delete_list":d_l
			},
			dataType: "json",
			success: function(data){
				if(data["result"]=="success"){
					alert("删除成功！");
					var zTree=$.fn.zTree.getZTreeObj("ztree1");
					var node=zTree.getNodeByParam("name",delete_list[0].type2);
				    zTree.selectNode(node);
				    zTree.setting.callback.onClick(null,zTree.setting.treeId,node);
				}
			},
			error: function(e){
				alert("Error!");
			}
		})
	}
}
//update操作;
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
//批量导出操作;
function batch_out(data){
	var checkedBoxes=getCheckedBoxes();
	if(checkedBoxes.length==0){
		alert("未勾选任何记录！")
		return;
	}
	var checkedBoxes_id=new Array();
	var item_list=new Array();
	for(var i=0;i<checkedBoxes.length;i++){
		var id=checkedBoxes[i].attr("id");
		checkedBoxes_id[i]=id.substr(9, 1);
	}
	for(var i=0;i<checkedBoxes_id.length;i++){
		var a="item_"+checkedBoxes_id[i];
		item_list[i]=data[a];
	}
	var batch_out=JSON.stringify(item_list);
	if($("#batch_out_form").length==0){
	   var form=$("<form method='post' action='manage_batch_out' id='batch_out_form'></form>");
	   var input=$("<input type='text' name='batch_out'/>");
	   input.attr("value",batch_out);
	   form.append(input);
	   $("#show").append(form);
	}else{
	   $("#batch_out_form").css("display","block");
	}
	form.submit();
	$("#batch_out_form").css("display","none");
}
//批量导入;
function batch_in(){
	if($("#mask").length==0){
	  var mask=$("<div id=\"mask\"></div>");
	  var div_iput=$("<div id=\"div_input\"></div>");
	  var body_height=$(document.body).height();
	  var body_width=$(document.body).width();
	  var left=(body_width-300)/2;
	  var top=(body_height-150)/2;
	  div_iput.css("left",left);
	  div_iput.css("top",top);
	  var form=$("<form id='batch_in_form'></form>");
	  var input=$("<span><input type=\"file\" name=\"batch_in\" id=\"file_input\"/></span>");
	  var submit=$("<button id='batch_in_submit' type='button' onclick='javascript:file_submit()'>上传</button>")
	  var back=$("<button id='batch_in_back' type='button' onclick='javascript:file_back()'>返回</button>");
	  form.append(back);
	  form.append(input);
	  form.append(submit);
	  div_iput.append(form);
	  $("#body").append(mask);
	  $("#body").append(div_iput);
	}else{
		$("#mask").css("display","block");
		$("#div_input").css("display","block");
	}
}
function file_submit(){
	if(!$("#file_input").val().length){
		var batch_in_error=$("<span id='batch_in_error'>还未选择文件</span>");
		$("#div_input").append(batch_in_error);
	}else{
		var aa=new FormData($("#batch_in_form")[0]);
		$.ajax({
			url:"manage_batch_in",
			type:"post",
			data:aa,
	        async: false,  
	        cache: false,
	        contentType: false,
	        processData: false, 
			success: function(data){
				if(data["result"]=="success"){
					$("#mask").css("display","none");
					$("#div_input").css("display","none");
					alert("上传成功！");
					var zTree=$.fn.zTree.getZTreeObj("ztree1");
					var node=zTree.getNodeByParam("name","linux主机");
				    zTree.selectNode(node);
				    zTree.setting.callback.onClick(null,zTree.setting.treeId,node);
				}
			},
			error:function(e){
				alert("Error!");
			}
		})
	}
}
function file_back(){
	$("#mask").css("display","none");
	$("#div_input").css("display","none");
}
//翻页;
function show_by_page(data,curpage,fathernode){
	for(var i=(curpage-1)*7;i<parseInt(data["num"]);i++){
		if(i==curpage*7){
			break;
		}
		var a="item_"+i;
		var tr=$("<tr></tr>");
		if(i%2==0){
			tr.attr("class","even");
		}else{
			tr.attr("class","odd");
		}
		var td1=$("<td class=\"td1\"></td>");
		var checkbox=$("<input type=\"checkbox\"/>");
		checkbox.attr("id","checkbox_"+i);
		td1.append(checkbox);
		tr.append(td1);
		var td2=$("<td class=\"td2\"></td>");
		td2.html(data[a].item_name);
		tr.append(td2);
		if(fathernode=="主机密码"){
		var td3=$("<td class=\"td3\"></td>");
		td3.html(data[a].ip_address);
		tr.append(td3);
		}
		var td4=$("<td class=\"td4\"></td>");
		td4.html(data[a].username);
		tr.append(td4);
		var td5=$("<td class=\"td5\"></td>");
		var str=data[a].password.substr(0,1);
		for(var k=0;k<data[a].password.length-1;k++){
			str+="*";
		}
		td5.html(str); 
		tr.append(td5);
		var td6=$("<td class=\"td6\"></td>");
		td6.html(data[a].password_status);
		if(data[a].password_status=="过期"){
			td6.css("color","red");
		}
		tr.append(td6);
		var td7=$("<td class=\"td7\"></td>");
		td7.html(data[a].creator.jurisdiction.name);
		tr.append(td7);
		var td8=$("<td class=\"td8\"></td>");
		td8.html(data[a].create_date);
		tr.append(td8);
		$("#show_table").append(tr);
	}
}
function lastpage(data,fathernode){
    var curpage=parseInt($("#curpage").html());
	var totalpage=parseInt($("#totalpage").html());
	if(curpage!=1){
		curpage--;
		emptytd(fathernode);
		show_by_page(data, curpage, fathernode);
		$("#curpage").html(curpage.toString());
	}
}
function nextpage(data,fathernode){
	var curpage=parseInt($("#curpage").html());
	var totalpage=parseInt($("#totalpage").html());
	if(curpage!=totalpage){
		curpage++;
		emptytd(fathernode);
		show_by_page(data, curpage, fathernode);
		$("#curpage").html(curpage.toString());
	}
}
function emptytd(fathernode){
	$("#show_table").empty();
	var show_table_th=$("<tr id=\"show_table_th\"></tr>");
	var th_td1=$("<td class=\"td1\"></td>");
	th_td1.html("<input type=\"checkbox\" disabled=\"true\"/>");
	show_table_th.append(th_td1);
	var th_td2=$("<td class=\"td2\"></td>");
	th_td2.html(fathernode.substring(0,fathernode.indexOf("密"))+"名");
	show_table_th.append(th_td2);
	if(fathernode=="主机密码"){
	var th_td3=$("<td class=\"td3\"></td>");
	th_td3.html("IP地址");
	show_table_th.append(th_td3);
	}
	var th_td4=$("<td class=\"td4\"></td>");
	th_td4.html("用户名");
	show_table_th.append(th_td4);
	var th_td5=$("<td class=\"td5\"></td>");
	th_td5.html("密码");
	show_table_th.append(th_td5);
	var th_td6=$("<td class=\"td6\"></td>");
	th_td6.html("密码状态");
	show_table_th.append(th_td6);
	var th_td7=$("<td class=\"td7\"></td>");
	th_td7.html("创建人");
	show_table_th.append(th_td7);
	var th_td8=$("<td class=\"td8\"></td>");
	th_td8.html("创建日期");
	show_table_th.append(th_td8);
	$("#show_table").append(show_table_th);
}
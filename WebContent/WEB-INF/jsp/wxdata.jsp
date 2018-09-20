<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>聊天数据</title>
		<script type="text/javascript" src="js/jquery-1.4.2.js"></script>
		<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.5.2/themes/default/easyui.css"/>
		<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.5.2/themes/icon.css"/>
		<script type="text/javascript" src="js/jquery-easyui-1.5.2/jquery.min.js"></script>
		<script type="text/javascript" src="js/jquery-easyui-1.5.2/jquery.easyui.min.js"></script>
		<script type="text/javascript">
		function load(){
			var tmp =${wxdata}; 
			alert(""+tmp);
		}
		function newUser(){
			var tmp = ${wxdata};
			alert(""+tmp);
			var tmp = $wxdata;
			alert(""+tmp);
			var tmp = wxdata;
			alert(""+tmp);
			}
		function abc(){
			alert("abc");
		}
		</script>
	</head>
	<body onload='load()'>
		<table id="dg" title="My Users" class="easyui-datagrid" style="width:550px;height:250px"
				url="/GetAllData.action"
				toolbar="#toolbar"
				rownumbers="true" fitColumns="true" singleSelect="true">
			<thead>
				<tr>
					<th field="dataid" width="50">dataid</th>
					<th field="dealtime" width="50">dealtime Name</th>
					<th field="tousername" width="50">tousername</th>
					<th field="fromusername" width="50">fromusername</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">New User</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">Edit User</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">Remove User</a>
		</div>
		<div>
		<button onclick="abc()">abc</button>
		</div>
	</body>
</html>
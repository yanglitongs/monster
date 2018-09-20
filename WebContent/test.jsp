<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>用户管理</title>
</head>
<script type="text/javascript" src="js/jquery-1.4.2.js"></script>
<script type="text/javascript">
	function search(){
		var a = document.getElementsByTagName('label');
		var arr=[];
		for(var i = 0; i < a.length; i++){
            if(a[i].id != '' && a[i].id != null){
                ids.push(a[i].id);//添加ID
            }
            var dd = a[i].href.substring(a[i].href.lastIndexOf('/'),a[i].href.length).split('/')[1];
            alert(dd); //拿到链接后面的值
        }
		
		//for(var i=0;i<document.childNodes.length;i++){
	     //   arr.push(document.childNodes[i].outerHTML);
	   // }
		//alert(arr.join(""));
	}
</script>

<body>
<input type="text" name="searchName" id="searchName" value="">
<button onclick="search()">查询</button>
	<table border="1">
		<tr>
			<td>ID</td>
			<td>用户ID</td>
			<td>用户微信</td>
			<td>用户昵称</td>
			<td>用户备注</td>
			<td>所在城市</td>
			<td>所在省份</td>
			<td>所在国家</td>
			<td>用户分组</td>
			<td>用户状态</td>
		</tr>
	</table>
	<label ID="ID1" STYLE="color:blue;font-weight:bold">123</label></br>
	<label ID="ID2">123456</label></br>
	<label ID="ID3">1yang</label></br>
	<label ID="ID4">1yangli</label></br>
	<label ID="ID5">1tong</label></br>
	<label ID="ID6">2beijing</label></br>
	<label ID="ID7">2beijing</label></br>
	<label ID="ID8">2china</label></br>
	<label ID="ID9">2beijing</label></br>
</body>
</html>
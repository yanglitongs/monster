<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<script type="text/javascript" src="js/jquery-1.4.2.js"></script>
<script type="text/javascript">
	function search(){
	var id2=$("#searchName")[0].value;
	var ccid=$("#ccid").val();
	location.href="<%=request.getContextPath()%>/xq.action?pname="+id2+"&ccid="+ccid;
	}
	function add(){
	$("#div").attr("style","display:block");
	}
	function toUpdate(id){
	var ccid=$("#ccid").val();
	//alert(ccid);
	$.ajax({
			url:"<%=request.getContextPath()%>/toUpdatePerson.action",
			data:{pid:id,ccid:ccid},
			dataType:"json",
			type:"post",
			success:function(obj){
 				$("input[id='pmanager1']").val(obj.pmanager);
 				$("input[id='pname1']").val(obj.pname);
				$("input[id='pphone1']").val(obj.pphone);
 				$("#pemail1").val(obj.pemail);
 			$("input[value='"+obj.psex+"']").attr("checked","true");
 				$("div[id='div1']").attr("style","display:block");
			}
		}) 
	}
	
	function del(pid){
		var msg = confirm("确定删除吗");
		if(msg){
			$.ajax({
			url:"<%=request.getContextPath()%>/del.action",
			data:{pid:pid},
			dataType:"text",
			type:"post",
			success:function(obj){
				location.reload();
			}
		}) 
			
		}else{
			alert("删除失败！");
		} 
	}
	
	</script>
<body>
<input type="text" name="searchName" id="searchName" value="${searchName}">
<button onclick="search()">查询</button>
<table border="1">
	<tr> 
	
	<td>人员名称</td>
	<td>性别</td>
	<td>职务</td>
	<td>手机</td>
	<td>邮箱</td>
	<td>操作<button onclick="add()">添加</button></td>
	</tr>
	<c:forEach items="${personList}" var="p">
	<tr>
	<td>${p.pname }</td>
	<td>${p.psex=='0'? '女' : '男'}</td>
	<td>${p.pmanager }</td>
	<td>${p.pphone }</td>
	<td>${p.pemail}</td>
	<td><button onclick="toUpdate(${p.pid})">更新</button>
	<button onclick="del(${p.pid})">删除</button></td>
	</tr>
	</c:forEach><br>
</table>
<div id="div" style="display:none">
	<form action="<%=request.getContextPath()%>/addPerson.action">
	<c:forEach items="${personList}" var="p">
	<input type="hidden" name="ccid" id="ccid" value="${p.ccid }">
	</c:forEach>
		姓名<input type="text" name="pname"><br>
		职位<input type="text" name="pmanager"><br>
		电话<input type="text" name="pphone"><br>
		邮箱<input type="text" name="pemail"><br>
		性别<input type="radio" name="psex" value="1">男
		<input type="radio" name="psex" value="0">女
		<input type="submit">
	</form>
</div>

<div id="div1" style="display:none">
	<form action="<%=request.getContextPath()%>/updatePerson.action">
	<c:forEach items="${personList}" var="p">
	<input type="hidden" name="ccid" id="ccid" value="${p.ccid }">
	<input type="hidden" name="pid" id="pid" value="${p.pid }">
	</c:forEach>
		姓名<input type="text" name="pname" id="pname1"><br>
		职位<input type="text" name="pmanager" id="pmanager1"><br>
		电话<input type="text" name="pphone" id="pphone1"><br>
		邮箱<input type="text" name="pemail" id="pemail1"><br>
		性别<input type="radio" name="psex" value="1" id="psex1">男
		<input type="radio" name="psex" value="0" id="psex1">女
		<input type="submit">
	</form> 
</div>
</body>
</html>
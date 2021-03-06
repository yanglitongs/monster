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
		var id2=$("#searchName")[0].value;
		location.href="<%=request.getContextPath()%>/userList.action?nickname="+id2;
	}	
	
	
	function toupdate(id){
	$("#cid").attr("checked","false");
	$.ajax({
		url:"<%=request.getContextPath()%>/toUpdate.action",
		data:{ccid:id},
		dataType:"json",
		type:"post",
		success:function(obj){
			$("input[id='ccid']").val(obj.ccid);
			$("input[name='cname']").val(obj.cname);
			$("input[name='cperson']").val(obj.cperson);
			$("#cphone").val(obj.cphone);
		$("input[value='"+obj.cid+"']").attr("checked","true");
			$("div[id='Div']").attr("style","display:block");
		}
	})
	}
	
	function add(){
	$("#addDiv").attr("style","display:block")
	}
	
	function del(id){
		var msg = confirm("确定删除吗");
		if(msg){
			$.ajax({
			url:"<%=request.getContextPath()%>/delete.action",
			data:{ccid:id},
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
	function xq(id){
		location.href="<%=request.getContextPath()%>/xq.action?ccid="+id;
	}
</script>

<body>
<input type="text" name="searchName" id="searchName" value="${searchName}">
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
			<td>用户性别</td>
			<td>用户分组</td>
			<td>用户状态</td>
			<td>操作<button onclick="add()">添加</button>	</td>
		</tr>
		<c:forEach items="${userList}" var="l">
		<tr id="tr_${l.mpid}}">
			<td>${l.mpid}</td>
			<td>${l.userid}</td>
			<td>${l.username}</td>
			<td>${l.nickname}</td>
			<td>${l.remarkname}</td>
			<td>${l.city}</td>
			<td>${l.province}</td>
			<td>${l.country}</td>
			<td>${l.gender}</td>
			<td>${l.groupid}</td>			
			<td>${l.userstate}</td>
			<td><button onclick="toupdate(${l.mpid})">更新</button>
			<button onclick="del(${l.mpid})">删除</button>
			<button onclick="xq(${l.mpid})">详情</button>
			</td>
		</tr>
		</c:forEach>
	</table>
	
</body>

<div id="addDiv" style="display: none">
	<form action="<%=request.getContextPath()%>/add.action" method="post">
		公司名<input type="text" id="cname" name="cname"><br>
		公司性质:
		<c:forEach items="${xingzhi }" var="x">
			<input type="radio" value="${x.cid }" name="cid"> ${x.xingzhi }
			</c:forEach>
		<br>
		联系人：<input type="text" name="cperson"><br>
		联系电话：<input type="text" name="cphone"><br>
		电子邮件：<input type="text" name="cemail"><br>
		行业：
		<c:forEach items="${hangye }" var="h">
		<input type="checkbox" name="cwork" value="${h.cwork }">${h.workname }
		</c:forEach>
			<input type="submit" value="提交">
	</form>
</div>
<div id="Div" style="display: none">
	<form action="<%=request.getContextPath()%>/update.action">
	<input type="hidden" name="ccid" id="ccid"/>
	公司名：<input type="text" name="cname" id="cname"><br>
	公司性质<c:forEach items="${xingzhi }" var="x" >
	<input type="checkbox" name="cid" id="cid" value="${x.cid }">${x.xingzhi }
	</c:forEach><br>
	联系人<input type="text" name="cperson" id="cperson"><br>
	联系电话<input type="text" name="cphone" id="cphone"><br>
	<input type="submit">
	</form>

</div>
</html>
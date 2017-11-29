<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Java后端WebSocket的Tomcat实现</title>
</head>
<body>
	Welcome<br><input id="text" type="text">
	<button onclick="send()">发送消息</button>
	<hr>
	<button onclick="closeWebSocket()">关闭WebSocket连接</button>
	<hr>
	<div id="message"></div>
</body>

<script type="text/javascript">
	var websocket = null;
	//判断当前浏览器是否支持WebSocket
	if('WebSocket' in window){
		websocket = new WebSocket("ws://localhost:8080/JavaWebSocket/websocket");
	}else{
		alert('当前浏览器 Not support websocket');
	}
	
	//连接错误的回调方法
	websocket.onerror = function(){
		setMessageInnerHTML("WebSocket连接发生错误");
	}
	////连接成功的回调方法
	websocket.onopen = function(){
		setMessageInnerHTML("WebSocket连接成功");
	}
	
	websocket.onmessage = function(event){
		setMessageInnerHTML(event.data);
	}
	
	window.onbeforeunload = function(){
		closeWebSocket();
	}
	
	//将消息显示在网页上
	function setMessageInnerHTML(innerHTML){
		document.getElementById('message').innerHTML += innerHTML +'<br/>';
	}
	
	//关闭WebSocket连接
	function closeWebSocket(){
		websocket.close();
	}
	
	//发送消息
	function send(){
		var message = document.getElementById('text').value;
		websocket.send(message);
	}
	
</script>
</html>
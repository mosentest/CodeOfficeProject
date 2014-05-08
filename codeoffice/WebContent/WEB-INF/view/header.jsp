<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="page.title" /></title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/main.css">
<link rel="stylesheet" type="text/css" href="css/custom-theme/jquery-ui-1.10.3.custom.css">
<script src="js/jquery-2.0.3.js"></script>
<script src="js/ch.js"></script>
<script src="js/jquery-ui-1.10.3.custom.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('.buttons').button();
		$('navigation').tabs({
			collapsible : true
		});
		$('.link').mouseover(function() {
			$(this).addClass('current');
		});
		$('.link').mouseout(function() {
			$(this).removeClass('current');
		});
	});
</script>
</head>
<body>
	<div id="login">
		<c:if test="${currentUser != null}">
			<a href="${currentUser.id}">${currentUser.account}</a>
			|<a href="settings"><spring:message code="page.settings" /></a>
			|<a href="logout"><spring:message code="page.logout" /></a>
		</c:if>
		<c:if test="${currentUser == null}">
			<a href="login" style="color: red;"><spring:message code="page.notlogin" text="You are not logged in!"/></a>
			|<a href="login"><spring:message code="page.login" /></a>
			|<a href="register"><spring:message code="page.register" /></a>
		</c:if>
	</div>
	<div id="header">
		<div id="header-content">
			<div id="header-site">
				<a href="/" class="imglink">
					<img src="img/codeoffice_logo.png" width="60px" height="60px"/>
					<span style="font-size: 38px;"><spring:message code="page.site" /></span>
				</a>
			</div>
			<div id="header-menu">
				<span class="link <c:if test="${navigation == null or navigation eq 'home'}">navigation</c:if>">
					<a href="/"><spring:message code="page.homepage" /></a>
				</span>
				<span class="link <c:if test="${navigation eq 'ask'}">navigation</c:if>">
					<a href="ask/home"><spring:message code="page.ask" /></a>
				</span>
				<span class="link <c:if test="${navigation eq 'euler'}">navigation</c:if>">
					<a href="euler/list/0"><spring:message code="page.euler" /></a>
				</span>
				<span class="link <c:if test="${navigation eq 'code'}">navigation</c:if>">
					<a href="#"><spring:message code="page.codeshare" /></a>
				</span>
				<span class="link <c:if test="${navigation eq 'article'}">navigation</c:if>">
					<a href="#"><spring:message code="page.article" /></a>
				</span>
				<span class="link <c:if test="${navigation eq 'forum'}">navigation</c:if>">
					<a href="#"><spring:message code="page.forum" /></a>
				</span>
			</div>
			<div id="sub-menu">
				<span class="link"><a href="vhome">Vote</a></span>
				<span class="link"><a href="#">Form</a></span>
				<span class="link"><a href="vote">Create vote</a></span>
				<span class="link"><a href="#">menu 1</a></span>
				<span class="link"><a href="#">menu 1</a></span>
				<span class="link"><a href="#">menu 1</a></span>
				<span class="link"><a href="#">menu 1</a></span>
				<span class="link"><a href="#">menu 1</a></span>
				<span class="link"><a href="#">menu 1</a></span>
			</div>
		</div>
	</div>
	<div id="middle">
		<c:if test="${returnLink}">
			<a href="javascript:window.history.back()"><spring:message code="page.back" /></a>
		</c:if>
		<div class="errorMessage">
			<c:if test="${errorMessages != null}">
				<c:forEach items="${errorMessages}" var="message">
					<p>${message}</p>
				</c:forEach>
			</c:if>
		</div>
		<div class="warnMessage">
			<c:if test="${warnMessages != null}">
				<c:forEach items="${warnMessages}" var="message">
					<p>${message}</p>
				</c:forEach>
			</c:if>
		</div>
		<div class="noticeMessage">
			<c:if test="${noticeMessages != null}">
				<c:forEach items="${noticeMessages}" var="message">
					<p>${message}</p>
				</c:forEach>
			</c:if>
		</div>
	</div>
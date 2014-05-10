<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
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
	<security:authorize access="isAuthenticated()">
		<div id="header-login">
			<span><security:authentication property="principal.enterprise.name"/>,&nbsp;</span>
			<a href="enterprise/<security:authentication property="principal.username"/>"><security:authentication property="principal.username"/></a>
			|<a href="enterprise/settings"><spring:message code="page.settings" /></a>
			|<a href="enterprise/logout"><spring:message code="page.logout" /></a>
		</div>
	</security:authorize>
	<div id="header">
		<div id="header-content">
			<div id="header-site">
				<a href="/" class="imglink">
					<img src="img/codeoffice.png"/>
				</a>
			</div>
			<security:authorize access="isAuthenticated()">
				<div id="header-menu">
					<span class="link <c:if test="${param.navigation eq 'home'}">navigation</c:if>">
						<a href="enterprise"><spring:message code="page.home" /></a>
					</span>
					<span class="link <c:if test="${param.navigation eq 'dashboard'}">navigation</c:if>">
						<a href="enterprise/dashboard"><spring:message code="page.dashboard" /></a>
					</span>
					<span class="link <c:if test="${param.navigation eq 'project'}">navigation</c:if>">
						<a href="enterprise/project"><spring:message code="page.project" /></a>
					</span>
					<span class="link <c:if test="${param.navigation eq 'case'}">navigation</c:if>">
						<a href="enterprise/case"><spring:message code="page.case" /></a>
					</span>
					<span class="link <c:if test="${param.navigation eq 'messagecenter'}">navigation</c:if>">
						<a href="enterprise/messagecenter"><spring:message code="page.messagecenter" /></a>
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
			</security:authorize>
		</div>
	</div>
	<div id="middle">
		<c:if test="${returnLink}">
			<a href="javascript:window.history.back()"><spring:message code="page.back" /></a>
		</c:if>
		<div class="error-message">
			<c:if test="${errorMessages != null}">
				<c:forEach items="${errorMessages}" var="message">
					<p>${message}</p>
				</c:forEach>
			</c:if>
		</div>
		<div class="warn-message">
			<c:if test="${warnMessages != null}">
				<c:forEach items="${warnMessages}" var="message">
					<p>${message}</p>
				</c:forEach>
			</c:if>
		</div>
		<div class="notice-message">
			<c:if test="${noticeMessages != null}">
				<c:forEach items="${noticeMessages}" var="message">
					<p>${message}</p>
				</c:forEach>
			</c:if>
		</div>
	</div>
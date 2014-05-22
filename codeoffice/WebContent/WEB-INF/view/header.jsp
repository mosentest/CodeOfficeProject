<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<!DOCTYPE html>
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
<title><spring:message code="application.title" /></title>
<link rel="stylesheet" href="assets/css/style.css">
<link rel="stylesheet" href="assets/css/main.css">
<link rel="stylesheet" href="assets/css/custom-theme/jquery-ui-1.10.3.custom.css">
<script src="assets/js/jquery-2.0.3.js"></script>
<script src="assets/js/ch.js"></script>
<script src="assets/js/jquery-ui-1.10.3.custom.js"></script>
<script>
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
		if ($('.leftmenu').length > 0) {
			var left = $('.leftmenu')[0];
			var main = $('.maincontent')[0];
			if (left.offsetHeight >= main.offsetHeight) {
				main.style.border = "none";
				left.style.borderRight = "1px solid #E5E5E5";
			} else {
				main.style.borderLeft = "1px solid #E5E5E5";
				left.style.border = "none";
			}	
			
		}
		$('.horizontal-tab li').click(function() {
			$('.horizontal-tab li').removeClass('active');
			$(this).addClass('active');
		});
	});
</script>
</head>
<body>
	<security:authorize access="isAuthenticated()">
		<div id="header-login">
			<spring:message var="text_logout" code="application.logout"/>
			<spring:message var="text_settings" code="application.settings"/>
			<spring:message var="text_logout" code="application.logout"/>
			<c:set var="text_username"><security:authentication property="principal.username"/></c:set>
			<c:set var="text_enterprise_name"><security:authentication property="principal.enterprise.name"/></c:set>
			<security:authorize access="hasRole('ROLE_GLOBAL_ADMIN')">
				<code:imagelink image="icon_setting" text="${text_settings}" link="settings/home" border="false"/>|
			</security:authorize>
			<code:imagelink image="icon_home" text="${text_enterprise_name}" link="" border="false" width="150"/>
			|<code:imagelink image="icon_user" text="${text_username}" link="user/${text_username}" border="false"/>
			|<code:imagelink image="icon_logout" text="${text_logout}" link="logout" border="false"/>
		</div>
	</security:authorize>
	<div id="header">
		<div id="header-content">
			<div id="header-site">
				<a href="/" class="imglink">
					<img src="assets/img/codeoffice.png"/>
				</a>
			</div>
			<security:authorize access="isAuthenticated()">
				<div id="header-menu">
					<span class="link <c:if test="${param.navigation eq 'home'}">navigation</c:if>">
						<a href="/"><spring:message code="application.home" /></a>
					</span>
					<span class="link <c:if test="${param.navigation eq 'dashboard'}">navigation</c:if>">
						<a href="dashboard"><spring:message code="application.dashboard" /></a>
					</span>
					<span class="link <c:if test="${param.navigation eq 'project'}">navigation</c:if>">
						<a href="project"><spring:message code="application.project" /></a>
					</span>
					<span class="link <c:if test="${param.navigation eq 'case'}">navigation</c:if>">
						<a href="case"><spring:message code="application.case" /></a>
					</span>
					<span class="link <c:if test="${param.navigation eq 'messagecenter'}">navigation</c:if>">
						<a href="messagecenter"><spring:message code="application.messagecenter" /></a>
					</span>
				</div>
				<div id="sub-menu">
					<c:forEach items="${sessionScope.SETTING_SUBMENU}" var="submenu">
						<span class="link"><a href="${submenu.link}">${submenu.title}</a></span>
					</c:forEach>
				</div>
			</security:authorize>
		</div>
	</div>
	<div id="middle">
		<c:if test="${returnLink}">
			<a href="javascript:window.history.back()"><spring:message code="application.back" /></a>
		</c:if>
		<div class="error-message">
			<c:if test="${not empty sessionScope.ERROR_MESSAGE}">
				<c:forEach items="${sessionScope.ERROR_MESSAGE}" var="m">
					<p>${m}</p>
				</c:forEach>
				<c:remove var="ERROR_MESSAGE" scope="session"/>
			</c:if>
		</div>
		<div class="warn-message">
			<c:if test="${not empty sessionScope.WARN_MESSAGE}">
				<c:forEach items="${sessionScope.WARN_MESSAGE}" var="m">
					<p>${m}</p>
				</c:forEach>
				<c:remove var="WARN_MESSAGE" scope="session"/>
			</c:if>
		</div>
		<div class="notice-message">
			<c:if test="${not empty sessionScope.NOTICE_MESSAGE}">
				<c:forEach items="${sessionScope.NOTICE_MESSAGE}" var="m">
					<p>${m}</p>
				</c:forEach>
				<c:remove var="NOTICE_MESSAGE" scope="session"/>
			</c:if>
		</div>
	</div>
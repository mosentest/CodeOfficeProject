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
<link rel="shortcut icon" href="favicon.ico" />
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
			var left = $('.leftmenu').first();
			var main = $('.maincontent').first();
			if (left.height() >= main.height()) {
				left.css({'border-right' : '1px solid #E5E5E5'});
				main.css({'border' : 'none'});
			} else {
				main.css({'border-left' : '1px solid #E5E5E5'});
				left.css({'border' : 'none'});
			}	
			
		}
		$('.horizontal-tab li').click(function() {
			$('.horizontal-tab li').removeClass('active');
			$(this).addClass('active');
		});
		$('.dropdown-indicator').click(function() {
			$('.dropdown ul').css({'left' : '-9999px'});
			var position = $(this).position();
			$(this).next('.dropdown').find('ul').css({'left' : position.left - 10, 'top' : position.top + 27});
			return false;
		});
		$('.dropdown ul').mouseleave(function() {
			$(this).css({'left' : '-9999px'});
		});
	});
</script>
</head>
<body>
<spring:message var="text_logout" code="application.logout"/>
<spring:message var="text_settings" code="application.settings"/>
<spring:message var="text_logout" code="application.logout"/>
<security:authorize access="isAuthenticated()">
<div id="header">
	<div class="left">
		<div class="link settings imglink">
			<a class="iconfont-large iconfont-appswitcher dropdown-indicator" href="javascript:void(0);">&nbsp;</a>
			<div class="dropdown">
				<ul>
					<li class="dropdown-title">Issues</li>
					<li class="dropdown-link"><a href="#">Link 1</a></li>
					<li class="dropdown-separator">&nbsp;</li>
					<li class="dropdown-title">Title 2</li>
					<li class="dropdown-link"><a href="#">Link 2</a></li>
					<li class="dropdown-link"><a href="#">Link 3</a></li>
					<li class="dropdown-separator">&nbsp;</li>
					<li class="dropdown-link"><a href="#">Link 4</a></li>
				</ul>
			</div>
		</div>
		<div class="logo link">
			<a href="dashboard.html"><img src="assets/img/icon-logo.png" width="57" height="30"/></a>
		</div>
		<div class="link imglink">
			<a href="settings/home.html"><spring:message code="application.administration"/></a>
		</div>
		<div class="link imglink">
			<a href="javascript:void(0);" class="dropdown-indicator"><spring:message code="application.dashboards"/>
			<img class="icon-module icon-module-menu-indicator" src="assets/img/empty.png"/></a>
			<div class="dropdown">
				<ul>
					<li class="dropdown-title">Dashboard</li>
					<li class="dropdown-link"><a href="#">Link 1</a></li>
					<li class="dropdown-separator">&nbsp;</li>
					<li class="dropdown-title">Title 2</li>
					<li class="dropdown-link"><a href="#">Link 2</a></li>
					<li class="dropdown-link"><a href="#">Link 3</a></li>
					<li class="dropdown-separator">&nbsp;</li>
					<li class="dropdown-link"><a href="#">Link 4</a></li>
				</ul>
			</div>
		</div>
		<div class="link imglink">
			<a href="javascript:void(0);" class="dropdown-indicator"><spring:message code="application.projects"/>
			<img class="icon-module icon-module-menu-indicator" src="assets/img/empty.png"/></a>
			<div class="dropdown">
				<ul>
					<li class="dropdown-title">Projects</li>
					<li class="dropdown-link"><a href="#">Link 1</a></li>
					<li class="dropdown-separator">&nbsp;</li>
					<li class="dropdown-title">Title 2</li>
					<li class="dropdown-link"><a href="#">Link 2</a></li>
					<li class="dropdown-link"><a href="#">Link 3</a></li>
					<li class="dropdown-separator">&nbsp;</li>
					<li class="dropdown-link"><a href="#">Link 4</a></li>
				</ul>
			</div>
		</div>
		<div class="link imglink">
			<a href="javascript:void(0);" class="dropdown-indicator"><spring:message code="application.issues"/>
			<img class="icon-module icon-module-menu-indicator" src="assets/img/empty.png"/></a>
			<div class="dropdown">
				<ul>
					<li class="dropdown-title">Issues</li>
					<li class="dropdown-link"><a href="#">Link 1</a></li>
					<li class="dropdown-separator">&nbsp;</li>
					<li class="dropdown-title">Title 2</li>
					<li class="dropdown-link"><a href="#">Link 2</a></li>
					<li class="dropdown-link"><a href="#">Link 3</a></li>
					<li class="dropdown-separator">&nbsp;</li>
					<li class="dropdown-link"><a href="#">Link 4</a></li>
				</ul>
			</div>
		</div>
		<div class="link imglink">
			<a href="javascript:void(0);" class="dropdown-indicator"><spring:message code="application.messagecenter"/>
			<img class="icon-module icon-module-menu-indicator" src="assets/img/empty.png"/></a>
			<div class="dropdown">
				<ul>
					<li class="dropdown-title">Message center</li>
					<li class="dropdown-link"><a href="#">Link 1</a></li>
					<li class="dropdown-separator">&nbsp;</li>
					<li class="dropdown-title">Title 2</li>
					<li class="dropdown-link"><a href="#">Link 2</a></li>
					<li class="dropdown-link"><a href="#">Link 3</a></li>
					<li class="dropdown-separator">&nbsp;</li>
					<li class="dropdown-link"><a href="#">Link 4</a></li>
				</ul>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
	<div class="right">
		<div class="quicksearch">
			<form action="" method="GET">
				<input type="text" id="quicksearch" placeholder="Quick search"/>
				<input type="submit" class="hidden"/>
			</form>
		</div>
		<div class="link">
			<a href="enterprise/info.html"><security:authentication property="principal.enterprise.name"/>
			<img class="icon-module icon-module-menu-indicator" src="assets/img/empty.png"/></a>
		</div>
		<div class="link"><a href="personal/info.html"><security:authentication property="principal.fullname"/></a></div>
		<div class="link"><a href="logout.html">Logout</a></div>
		<div class="clearfix"></div>
	</div>
	<div class="clearfix"></div>
</div>
</security:authorize>
<security:authorize access="isAnonymous()">
<div id="header">
	<div class="left">
		<div class="logo link">
			<a href="dashboard.html"><img src="assets/img/icon-logo.png" width="57" height="30"/></a>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
</security:authorize>
<div id="middle">
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
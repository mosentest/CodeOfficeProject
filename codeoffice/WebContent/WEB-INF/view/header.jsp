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
<link rel="icon" type="image/x-icon" href="assets/img/favicon.ico" />
<link rel="stylesheet" href="assets/css/style.css">
<link rel="stylesheet" href="assets/css/main.css">
<link rel="stylesheet" href="assets/css/custom-theme/jquery-ui-1.10.3.custom.css">
<script src="assets/js/jquery-2.0.3.js"></script>
<script src="assets/js/codeoffice.js"></script>
<script src="assets/js/jquery-ui-1.10.3.custom.js"></script>
</head>
<body>
<spring:message var="text_logout" code="application.logout"/>
<spring:message var="text_settings" code="application.settings"/>
<spring:message var="text_logout" code="application.logout"/>
<security:authorize access="isAuthenticated()">
<div id="header">
	<div class="left">
		<div class="link settings imglink">
			<a class="iconfont-large iconfont-appswitcher dropdown-indicator" href="javascript:void(0);"></a>
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
			<a href="administration/global.html"><spring:message code="application.administration"/></a>
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

<div id="middle">
	<c:if test="${not empty sessionScope.INFO_MESSAGE}">
	<code:info type="info" message="application.info">
		<c:forEach items="${sessionScope.INFO_MESSAGE}" var="m">
			<p>${m}</p>
		</c:forEach>
	</code:info>
	</c:if>
	<c:if test="${not empty sessionScope.TIP_MESSAGE}">
	<code:info type="tip" message="application.tip">
		<c:forEach items="${sessionScope.TIP_MESSAGE}" var="m">
			<p>${m}</p>
		</c:forEach>
	</code:info>
	</c:if>
	<c:if test="${not empty sessionScope.WARNING_MESSAGE}">
	<code:info type="warning" message="application.warning">
		<c:forEach items="${sessionScope.WARNING_MESSAGE}" var="m">
			<p>${m}</p>
		</c:forEach>
	</code:info>
	</c:if>
	<c:if test="${not empty sessionScope.ERROR_MESSAGE}">
	<code:info type="error" message="application.error">
		<c:forEach items="${sessionScope.ERROR_MESSAGE}" var="m">
			<p>${m}</p>
		</c:forEach>
	</code:info>
	</c:if>
	<c:if test="${not empty sessionScope.ALERT_MESSAGE}">
	<code:info type="alert" message="application.alert">
		<c:forEach items="${sessionScope.ALERT_MESSAGE}" var="m">
			<p>${m}</p>
		</c:forEach>
	</code:info>
	</c:if>
</div>
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
<link rel="stylesheet" href="assets/css/defaults.css">
<link rel="stylesheet" href="assets/css/sprites.css">
<link rel="stylesheet" href="assets/css/custom-theme/jquery-ui-1.10.3.custom.css">
<script src="assets/js/jquery-2.0.3.js"></script>
<script src="assets/js/codeoffice.js"></script>
<script src="assets/js/jquery-ui-1.10.3.custom.js"></script>
</head>
<body>
<spring:message var="text_settings" code="application.settings"/>
<spring:message var="text_logout" code="application.logout"/>
<security:authorize access="isAuthenticated()">
<c:if test="${sessionScope.ANNOUNCEMENT.enabled}">
<code:info type="${sessionScope.ANNOUNCEMENT.announcementLevel.info}" banner="true" message="${sessionScope.ANNOUNCEMENT.announcement}"/>
</c:if>
<div id="banner">
	<c:if test="${not empty INFO}"><code:info type="info" message="${INFO}" banner="true"/></c:if>
	<c:if test="${not empty TIP}"><code:info type="tip" message="${TIP}" banner="true"/></c:if>
	<c:if test="${not empty WARNING}"><code:info type="warning" message="${WARNING}" banner="true"/></c:if>
	<c:if test="${not empty ERROR}"><code:info type="error" message="${ERROR}" banner="true"/></c:if>
	<c:if test="${not empty ALERT}"><code:info type="alert" message="${ALERT}" banner="true"/></c:if>
</div>
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
			<a href="dashboard.html"><img src="assets/img/core/icon-logo.png" width="57" height="30"/></a>
		</div>
		<div class="link imglink">
			<a href="javascript:void(0);" class="dropdown-indicator"><spring:message code="application.administration"/>
			<img class="icon-module icon-module-menu-indicator" src="assets/img/core/empty.png"/></a>
			<div class="dropdown">
				<ul>
					<li class="dropdown-link"><a href="administration/system.html"><spring:message code="administration.system"/></a></li>
					<li class="dropdown-link"><a href="administration/add-ons.html"><spring:message code="administration.addons"/></a></li>
					<li class="dropdown-separator">&nbsp;</li>
					<li class="dropdown-link"><a href="administration/userManagement.html"><spring:message code="administration.userManagement"/></a></li>
					<li class="dropdown-link"><a href="administration/project.html"><spring:message code="administration.project"/></a></li>
					<li class="dropdown-link"><a href="administration/issue.html"><spring:message code="administration.issue"/></a></li>
				</ul>
			</div>
		</div>
		<div class="link imglink">
			<a href="javascript:void(0);" class="dropdown-indicator"><spring:message code="application.dashboards"/>
			<img class="icon-module icon-module-menu-indicator" src="assets/img/core/empty.png"/></a>
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
			<img class="icon-module icon-module-menu-indicator" src="assets/img/core/empty.png"/></a>
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
			<img class="icon-module icon-module-menu-indicator" src="assets/img/core/empty.png"/></a>
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
			<img class="icon-module icon-module-menu-indicator" src="assets/img/core/empty.png"/></a>
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
			<img class="icon-module icon-module-menu-indicator" src="assets/img/core/empty.png"/></a>
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
			<a href="dashboard.html"><img src="assets/img/core/icon-logo.png" width="57" height="30"/></a>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
</security:authorize>
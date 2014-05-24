<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="title"><spring:message code="settings.enterprise_settings"/></div>
<div id="content">
	<jsp:include page="/WEB-INF/view/settings/settings_menu.jsp">
		<jsp:param name="menu" value="home"/>
	</jsp:include>
	<div id="maincontent">
	set default settings
		<code:info title="application.goto_home_page" type="info">
		<span>hahahahahhahahhahahahh</span> <a href="test">testing</a>
		</code:info>
		<div class="sep-30"></div>
		<code:info title="application.goto_home_page" type="warning">
		<span>hahahahahhahahhahahahh</span>
		</code:info>
		<div class="sep-30"></div>
		<code:info title="application.goto_home_page" type="alert"></code:info>
		<div class="sep-30"></div>
		<code:info message="application.goto_home_page" type="tip"></code:info>
	</div>
	<div class="clearfix"></div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
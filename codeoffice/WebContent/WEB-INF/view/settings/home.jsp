<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="content">
	<jsp:include page="/WEB-INF/view/settings/settings_menu.jsp">
		<jsp:param name="menu" value="home"/>
	</jsp:include>
	<div id="maincontent">Settings home</div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
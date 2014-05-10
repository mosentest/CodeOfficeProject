<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/view/enterprise/header.jsp">
	<jsp:param name="navigation" value="project"/>
</jsp:include>
<div id="content">
	<div class="element">
		<div class="info"><spring:message code="project.home"/></div>
		<div class="content">
			<jsp:include page="/WEB-INF/view/enterprise/project/projectmenu.jsp">
				<jsp:param name="menu" value="newproject"/>
			</jsp:include>
			<div class="maincontent">
				project form
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/view/enterprise/footer.jsp" />
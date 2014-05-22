<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<jsp:include page="/WEB-INF/view/header.jsp">
	<jsp:param name="navigation" value="project"/>
</jsp:include>
<div id="content">
	<div class="element">
		<div class="info"><spring:message code="project.home"/></div>
		<div class="content">
			<jsp:include page="/WEB-INF/view/project/projecthome_menu.jsp">
				<jsp:param name="menu" value="home"/>
			</jsp:include>
			<div class="maincontent"></div>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
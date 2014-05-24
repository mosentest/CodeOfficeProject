<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:include page="/WEB-INF/view/header.jsp">
	<jsp:param name="navigation" value="home"/>
</jsp:include>
<div id="content">
	<div class="sub-element">
		<div class="sub-element-info"><div class="sub-element-title"><spring:message code="application.dashboard"/></div></div>
		<div class="sub-element-content">
			
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
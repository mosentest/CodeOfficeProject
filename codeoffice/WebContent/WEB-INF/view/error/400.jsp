<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<jsp:include page="/WEB-INF/view/header.jsp" />
<style type="text/css">
	.access-title {
		font-size: 32px;
	}
	#access-content {
		width: 800px;
		margin: 20px auto;
	}
</style>
<div id="main">
	<div class="element">
		<div class="title">400 - Bad request</div>
		<div class="content">
			<div id="access-content">
				<table class="default-table center">
					<tr>
						<td><span class="access-title"><spring:message code="application.bad_request"/></span></td>
					</tr>
					<c:if test="${not empty error}">
					<tr>
						<td><img src="assets/img/accessdenied.png"/></td>
					</tr>
					<tr class="separator-tr"><td></td></tr>
					<tr>
						<td><span class="error-message" style="font-weight: bold; "><spring:message code="application.error"/>: </span>
						<span><spring:message code="application.bad_request_message"/></span></td>
					</tr>
					</c:if>
					<tr class="separator-tr"><td></td></tr>
					<tr>
						<security:authorize access="isAuthenticated()">
						<td><a href="/"><spring:message code="application.goto_home_page"/></a></td>
						</security:authorize>
					</tr>
				</table>
			</div>			
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/view/enterprise/header.jsp" />
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
		<div class="title">403 - access denied</div>
		<div class="content">
			<div id="access-content">
				<table class="default-table center">
					<tr>
						<td><span class="access-title">Unauthorized access</span></td>
					</tr>
					<c:if test="${not empty error}">
					<tr>
						<td><img src="img/accessdenied.png"/></td>
					</tr>
					<tr class="separator-tr"><td></td></tr>
					<tr>
						<td><span class="error-message" style="font-weight: bold; ">Error: </span><span>${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}</span></td>
					</tr>
					</c:if>
					<tr class="separator-tr"><td></td></tr>
					<tr>
						<td><a href="/enterprise/login">Go back to login page.</a></td>
					</tr>
				</table>
			</div>			
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/view/enterprise/footer.jsp" />
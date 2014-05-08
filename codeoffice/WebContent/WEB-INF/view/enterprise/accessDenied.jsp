<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/view/enterprise/header.jsp" />
<style type="text/css">
	.access-title {
		
	}
</style>
<div id="main">
	<div class="element">
		<div class="title">403 - access denied</div>
		<div class="content">
			<h1 class="access-title">Unauthorized access</h1>
			<c:if test="${not empty error}">
				<div class="errormessage">
					<span>Login failed</span>
				</div>
			</c:if>
			<a href="/enterprise/login">Go back to login page.</a>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/view/enterprise/footer.jsp" />
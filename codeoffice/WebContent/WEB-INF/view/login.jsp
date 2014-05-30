<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/WEB-INF/view/header.jsp" />
<div id="content" class="panel-content">
	<div class="panel-element">
		<div class="panel-element-info">
			<div class="panel-element-title"><spring:message code="application.login"/></div>
		</div>
		<div class="panel-element-content">
			<form action="<c:url value='j_spring_security_check'/>" method="POST">
				<table class="form-table">
					<tr>
						<td colspan="2" class="errorMessage">
							<c:if test="${error eq 'expired'}"><spring:message code="application.sessionExpired"/></c:if>
							<c:if test="${error eq 'invalid'}"><spring:message code="application.invalidSession"/></c:if>
						</td>
					</tr>
					<tr>
						<td><label for="account">Account/Email</label></td>
						<td><input type="text" name="j_username" id="account"/></td>
					</tr>
					<tr>
						<td><label for="password">Password</label></td>
						<td><input type="password" name="j_password" id="password"/></td>
					</tr>
					<tr>
						<td></td>
						<td><input class="button" type="submit" value="<spring:message code="application.login"/>"/></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp">
	<jsp:param name="cssClass" value="panel-footer"/>
</jsp:include>
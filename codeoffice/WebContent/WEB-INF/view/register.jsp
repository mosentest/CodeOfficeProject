<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/view/header.jsp" />
<style type="text/css">
#register_content {
	width: 600px;
	margin: 30px auto;
}
</style>
<div class="element">
	<div class="title"><spring:message code="page.registration"/></div>
	<div class="content">
		<div id="register_content" class="border">
			<form:form action="register" method="POST" modelAttribute="user">
				<table class="form_table">
					<tr>
						<td colspan="2" class="errorMessage"></td>
					</tr>
					<tr>
						<td class="standardHeader"><spring:message code="page.account"/>(<spring:message code="page.mail"/>)</td>
						<td><form:input path="account" size="12"/></td>
					</tr>
					<tr>
						<td class="standardHeader"><spring:message code="page.username"/></td>
						<td><form:input path="username" size="30"/></td>
					</tr>
					<tr>
						<td class="standardHeader"><spring:message code="page.password"/></td>
						<td><form:password path="password" size="30"/></td>
					</tr>
					<tr>
						<td class="standardHeader"><spring:message code="page.confirm_password"/></td>
						<td><form:password path="confirmPassword" size="30"/></td>
					</tr>
					<tr>
						<td class="standardHeader"><spring:message code="page.gender"/></td>
						<td><form:radiobutton path="gender" value="true"/><spring:message code="page.male"/>
						<form:radiobutton path="gender" value="false"/><spring:message code="page.female"/></td>
					</tr>
					<tr>
						<td class="standardHeader"><spring:message code="page.security_question"/></td>
						<td><form:select path="securityQuestion">
							<c:forEach var="entry" items="${securityQuestions}">
								<option value="${entry.key}"><spring:message code="${entry.value}"/></option>
							</c:forEach>
						</form:select></td>
					</tr>
					<tr>
						<td class="standardHeader"><spring:message code="page.security_answer"/></td>
						<td><form:input path="securityAnswer" size="30"/></td>
					</tr>
					<tr>
						<td class="standardHeader"><spring:message code="page.captcha"/></td>
						<td></td>
					</tr>
					<tr>
						<td colspan="2">
							<input class="buttons" type="submit" value="<spring:message code="page.register"/>"/>
						</td>
					</tr>
				</table>
			</form:form>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
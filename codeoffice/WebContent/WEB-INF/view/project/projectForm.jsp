<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/view/header.jsp" />
<script type="text/javascript">
$(function() {
	$(".datepicker").datepicker({
		showOn: "button",
		buttonImage: "img/datepicker.jpg",
		buttonImageOnly: true
	});
});
<%--
private String code;
private String title;
private String description;
private Date end;
private boolean current;
--%>
 </script>
<div class="dashboard_element form">
	<div class="dashboard_element_title"><spring:message code="project.createproject"/></div>
	<div class="dashboard_element_content">
		<form:form action="project/create" method="POST" modelAttribute="project">
			<table class="form_table">
				<tr>
					<td colspan="2"><form:errors path="*" cssClass="errorMessage"/></td>
				</tr>
				<tr>
					<td class="standardHeader"><spring:message code="project.code"/></td>
					<td><form:input path="code" size="3"/></td>
				</tr>
				<tr>
					<td class="standardHeader"><spring:message code="project.name"/></td>
					<td><form:input path="name" size="100"/></td>
				</tr>
				<tr>
					<td class="standardHeader"><spring:message code="project.summary"/></td>
					<td><form:input path="summary" size="100"/></td>
				</tr>
				<tr>
					<td class="standardHeader"><spring:message code="project.description"/></td>
					<td><form:textarea path="description" cols="90" rows="12"/></td>
				</tr>
				<tr>
					<td class="standardHeader"><spring:message code="project.end"/></td>
					<td><form:input path="end" class="datepicker"/></td>
				</tr>
				<tr>
					<td class="standardHeader"><spring:message code="project.lead"/></td>
					<td><form:select path="lead/id">
						<c:forEach var="user" items="${users}">
							<option value="${user.id}">${user.lastName} ${user.firstName}</option>
						</c:forEach>
					</form:select></td>
				</tr>
				<tr>
					<td class="standardHeader"><spring:message code="project.attachment"/></td>
					<td><input type="file" name="attachment"/></td>
				</tr>
				<tr>
					<td colspan="2">
						<input class="buttons" type="submit" value="<spring:message code="issue.submit"/>"/>
						<input class="buttons" type="button" value="<spring:message code="issue.return"/>"/>
					</td>
				</tr>
			</table>
		</form:form>
	</div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
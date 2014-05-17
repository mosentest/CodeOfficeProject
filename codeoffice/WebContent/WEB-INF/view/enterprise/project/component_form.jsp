<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<jsp:include page="/WEB-INF/view/enterprise/header.jsp">
	<jsp:param name="navigation" value="project"/>
</jsp:include>
<link rel="stylesheet" type="text/css" href="css/project.css">
<spring:message var="text_update" code="application.update"/>
<spring:message var="text_create" code="application.create"/>
<spring:message var="text_select" code="application.select"/>
<div id="content">
	<div class="element">
		<div class="info"><jsp:include page="/WEB-INF/view/enterprise/project/project_header.jsp"/></div>
		<div class="content">
			<jsp:include page="/WEB-INF/view/enterprise/project/component_menu.jsp"/>
			<div class="maincontent">
				<div class="element">
					<div class="title"><spring:message code="${edit ? 'component.create_component' : 'component.edit_component'}"/></div>
					<div class="content">
						<form:form action="" id="merge-form" modelAttribute="component" method="POST">
						<table class="default-table">
							<tr>
								<td colspan="2"><c:if test="${edit}"><form:hidden path="id"/></c:if></td>
							</tr>
							<tr>
								<td class="label-header"><spring:message code="component.code"/>:</td>
								<td><form:input path="code"/></td>
							</tr>
							<tr>
								<td class="label-header"><spring:message code="component.project"/>:</td>
								<td>${project.name}</td>
							</tr>
							<tr>
								<td class="label-header"><spring:message code="component.name"/>:</td>
								<td><form:input path="name"/></td>
							</tr>
							<tr>
								<td class="label-header"><spring:message code="component.lead"/>:</td>
								<td>
									<form:select multitple="single" path="lead.id">
										<form:option value="0" label="------ ${text_select} ------"/>
										<c:forEach items="${leadGroup}" var="roleGroup">
											<optgroup label="${roleGroup.role.name}">
												<c:forEach items="${roleGroup.users}" var="user">
													<option label="${user.fullName}" value="${user.id}" ${component.lead.id eq user.id ? 'selected=selected' : ''}/>
												</c:forEach>
											</optgroup>
										</c:forEach>
									</form:select>	
								</td>
							</tr>
							<tr>
								<td class="label-header"><spring:message code="component.default_reporter"/>:</td>
								<td>
									<form:select multitple="single" path="defaultReporter.id">
										<form:option value="0" label="------ ${text_select} ------"/>
										<c:forEach items="${userGroup}" var="roleGroup">
											<optgroup label="${roleGroup.role.name}">
												<c:forEach items="${roleGroup.users}" var="user">
													<option label="${user.fullName}" value="${user.id}" ${component.defaultReporter.id eq user.id ? 'selected=selected' : ''}/>
												</c:forEach>
											</optgroup>
										</c:forEach>
									</form:select>	
								</td>
							</tr>
							<tr>
								<td class="label-header"><spring:message code="component.default_assignee"/>:</td>
								<td>
									<form:select multitple="single" path="defaultAssignee.id">
										<form:option value="0" label="------ ${text_select} ------"/>
										<c:forEach items="${userGroup}" var="roleGroup">
											<optgroup label="${roleGroup.role.name}">
												<c:forEach items="${roleGroup.users}" var="user">
													<option label="${user.fullName}" value="${user.id}" ${component.defaultAssignee.id eq user.id ? 'selected=selected' : ''}/>
												</c:forEach>
											</optgroup>
										</c:forEach>
									</form:select>		
								</td>
							</tr>
							<tr>
								<td class="label-header"><spring:message code="component.description"/>:</td>
								<td><form:textarea path="description" cols="30" rows="6"/></td>
							</tr>
							<tr>
								<td colspan="2"><input id="mergebutton" class="button largebutton" type="submit" value="${edit ? text_update : text_create}"/></td>
							</tr>
						</table>
						</form:form>
					</div>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/view/enterprise/footer.jsp" />
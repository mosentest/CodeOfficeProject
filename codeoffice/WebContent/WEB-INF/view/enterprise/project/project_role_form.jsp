<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="codefunction" uri="http://www.codeoffice.com/codefunction" %>
<jsp:include page="/WEB-INF/view/enterprise/header.jsp">
	<jsp:param name="navigation" value="project"/>
</jsp:include>
<link rel="stylesheet" type="text/css" href="css/project.css">
<script type="text/javascript">
function save(event) {
	
}
</script>
<spring:message var="text_save" code="application.save"/>
<spring:message var="text_select" code="application.select"/>
<div id="content">
	<div class="element">
		<div class="info"><jsp:include page="/WEB-INF/view/enterprise/project/project_header.jsp"/></div>
		<div class="content">
			<jsp:include page="/WEB-INF/view/enterprise/project/project_menu.jsp">
				<jsp:param name="menu" value="rolegroup"/>
			</jsp:include>
			<div class="maincontent">
				<div class="mainelement">
					<div class="title imglink"><img src="img/icon_authority.png"/><span class="titlespan"><spring:message code="role.manage_rolegroup"/></span></div>
					<div class="content">
					<form:form action="" modelAttribute="roleGroup" method="POST">
					<table class="default-table">
						<tr>
							<td class="label-header"><spring:message code="rolegroup.role"/>:</td>
							<td>
								<c:if test="${roleGroup.id}"></c:if>
								<form:select multitple="single" path="role">
									<form:option value="0" label="------ ${text_select} ------"/>
									<c:forEach items="${roles}" var="role">
										<option label="${user.fullName}" value="${user.id}" ${component.lead.id eq user.id ? 'selected=selected' : ''}/>
									</c:forEach>
								</form:select>	
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<input id="mergebutton" class="button largebutton" type="submit" value="${edit ? text_update : text_save}" onclick="save(event);"/>
							</td>
						</tr>
					</table>
					</form:form>
					</div>
					<div class="sep-30"></div>
					<div class="subelement">
						<div class="title"></div>
						<div class="content"></div>
					</div>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/view/enterprise/footer.jsp" />
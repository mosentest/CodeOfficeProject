<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="/WEB-INF/view/enterprise/header.jsp">
	<jsp:param name="navigation" value="project"/>
</jsp:include>
<link rel="stylesheet" type="text/css" href="css/project.css">
<div id="content">
	<div class="element">
		<div class="info"><jsp:include page="/WEB-INF/view/enterprise/project/project_header.jsp"/></div>
		<div class="content">
			<jsp:include page="/WEB-INF/view/enterprise/project/project_menu.jsp">
				<jsp:param name="menu" value="rolegroup"/>
			</jsp:include>
			<div class="maincontent">
				<div class="mainelement">
					<div class="title imglink"><img src="img/office/icon_rolegroups.png"/><span class="titlespan"><spring:message code="project.rolegroup"/></span></div>
					<div class="content">
					<table class="default-table left-header">
						<tr>
							<th class="role"><spring:message code="rolegroup.role"/></th>
							<th class="role"><spring:message code="rolegroup.role_description"/></th>
							<th class="users"><spring:message code="rolegroup.users"/></th>
						</tr>
						<c:forEach items="${roleGroups}" var="roleGroup">
						<tr class="border-bottom">
							<td class="role">${roleGroup.role.name}</td>
							<td class="role">${roleGroup.role.description}</td>
							<td class="users">
								<c:forEach items="${roleGroup.users}" var="user">
									<span class="rolegroup-user"><code:user user="${user}" width="30" height="30"/></span>
									<span class="minorspace"></span><span class="minorspace"></span>
								</c:forEach>
							</td>
						</tr>
						</c:forEach>
					</table>
					</div>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/view/enterprise/footer.jsp" />
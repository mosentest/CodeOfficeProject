<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="title"><spring:message code="administration.enterprise_administration"/></div>
<div id="sub-menu">
	<jsp:include page="/WEB-INF/view/administration/administration_submenu.jsp">
		<jsp:param name="menu" value="system"/>
	</jsp:include>
</div>
<spring:message var="text_add_group" code="administration.um.globalpermission.addGroup"/>
<spring:message var="text_add_user" code="administration.um.globalpermission.addUser"/>
<spring:message var="text_view_users" code="administration.um.globalpermission.viewUser"/>
<spring:message var="text_reset" code="application.reset"/>
<spring:message var="text_remove" code="application.remove"/>
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/system_menu.jsp">
		<jsp:param name="menu" value="globalpermission"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title"><spring:message code="administration.system.globalPermissionSettings"/></div>
				<div class="sub-element-description">Edit global permissions settings.</div>
			</div>
			<div class="sub-element-content">
				<table class="list-table">
					<tr class="list-table-header">
						<td><spring:message code="administration.um.globalpermission.permission"/></td>
						<td><spring:message code="administration.um.globalpermission.groups"/></td>
						<td><spring:message code="administration.um.globalpermission.users"/></td>
						<td><spring:message code="administration.um.globalpermission.operations"/></td>
					</tr>
					<c:forEach items="${globalPermissionSettings}" var="permission">
					<tr class="list-table-item">
						<c:set var="description">${permission.globalPermission.key}.description</c:set>
						<td>
							<span class="title-info"><spring:message code="${permission.globalPermission.key}"/></span><br/>
							<span class="description-info"><spring:message code="${description}"/></span></td>
						<td>
							<ul class="info-ul-list">
								<c:forEach items="${permission.userGroups}" var="group">
									<li>${group.name}<br/>
									<span class="info-ul-list-span">
									<a class="link" href="administration/userGroup/${group.name}.html">${text_view_users}</a><span class="minorspace">&#183;</span>
									<a class="link" href="#">${text_remove}</a></span>
									</li>
								</c:forEach>
							</ul>
						</td>
						<td>
							<ul class="info-ul-list">
								<c:forEach items="${permission.users}" var="user">
									<li><code:user user="${user}" width="20" height="20" showLink="false"/><br/>
									<span class="info-ul-list-span"><a class="link" href="#">${text_remove}</a></span>
									</li>
								</c:forEach>
							</ul>
						</td>
						<td><a class="link" href="#">${text_add_group}</a><span class="minorspace">&#183;</span>
							<a class="link" href="#">${text_add_user}</a><span class="minorspace">&#183;</span>
							<a class="link" href="#">${text_reset}</a>
						</td>
					</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="codefunction" uri="http://www.codeoffice.com/codefunction" %>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<div id="title"><spring:message code="administration.title"/></div>
<div id="sub-menu">
	<jsp:include page="/WEB-INF/view/administration/administration_submenu.jsp">
		<jsp:param name="menu" value="system"/>
	</jsp:include>
</div>
<div id="content">
	<jsp:include page="/WEB-INF/view/administration/system_menu.jsp">
		<jsp:param name="menu" value="globalpermission"/>
	</jsp:include>
	<div id="maincontent">
		<div class="sub-element">
			<div class="sub-element-info">
				<div class="sub-element-title"><spring:message code="administration.system.globalpermission.scheme.title"/></div>
			</div>
			<div class="sub-element-content">
				<table class="permission-table">
					<tr>
						<td></td>
						<c:forEach items="${globalPermissions}" var="permission">
						<td class="permission-table-title"><spring:message code="${permission.key}"/></td>
						</c:forEach>
					</tr>
					<c:forEach items="${globalPermissionSchemes}" var="scheme">
					<tr>
						<td class="permission-table-row-title">
							<span class="title-info"><spring:message code="${scheme.key}"/></span><br />
							<c:set var="description">${scheme.key}.description</c:set>
							<span class="description-info"><spring:message code="${description}"/></span>
						</td>
						<c:forEach items="${globalPermissions}" var="inner">
						<td>
							<c:set var="on" value="${codefunction:bitOn(inner.fullAuthority, scheme.value)}"/>
							<c:if test="${on}"><span class="iconfont-large iconfont-approve" style="color: green;"></span></c:if>
							<c:if test="${not on}"><span class="iconfont-large iconfont-remove" style="color: red;"></span></c:if>
						</td>
						</c:forEach>
					</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />